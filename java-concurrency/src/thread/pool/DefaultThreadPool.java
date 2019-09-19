package thread.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
    private final static int MAX_THREAD_NUMBERS = 10;
    private final static int DEAFAULT_THREAD_NUMBERS = 5;
    private final static int MIN_THREAD_NUMBERS = 1;

    private final LinkedList<Job> jobs = new LinkedList<>();
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());

    private int workerNum = DEAFAULT_THREAD_NUMBERS;
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool(){
        initalizeWorkers(DEAFAULT_THREAD_NUMBERS);
    }

    public DefaultThreadPool(int workerNum){
        this.workerNum = workerNum > MAX_THREAD_NUMBERS ? MAX_THREAD_NUMBERS :
                (workerNum < MIN_THREAD_NUMBERS ? MIN_THREAD_NUMBERS : workerNum);
        initalizeWorkers(this.workerNum);
    }

    @Override
    public void execute(Job job) {
        synchronized (jobs){
            if(job != null) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for(Worker w : workers){
            w.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs){
            if(num + workerNum > MAX_THREAD_NUMBERS){
                num = MAX_THREAD_NUMBERS - workerNum;
            }
            initalizeWorkers(num);
            workerNum += num;
        }
    }

    @Override
    public void removeWorkers(int num) {
        synchronized (jobs){
            if(num < MIN_THREAD_NUMBERS || num >= workerNum){
                throw new IllegalArgumentException("workers number is illegal");
            }
            int count = 0;
            while(count < num){
                Worker worker = workers.get(count);
                if(workers.remove(worker)){
                    worker.shutdown();
                }
            }
            workerNum -= num;
        }
    }

    @Override
    public int getJobSize() {
        return workerNum;
    }

    private void initalizeWorkers(int num){
        for(int i = 0; i < num; i++){
            Worker worker =  new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Workers " + threadNum.incrementAndGet());
            thread.start();
        }
    }

    private class Worker implements Runnable {
        private volatile boolean running = true;
        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    try {
                        while (jobs.isEmpty()) {
                            job.wait();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;

                    }
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception ignore) {
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }

    }
}
