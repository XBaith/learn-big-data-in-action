package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Priority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) throws Exception{
        List<Job> jobs = new ArrayList<>(10);
        for(int i = 0 ; i < 10 ; i++){
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job);
            thread.setPriority(priority);
            thread.start();
        }
        //voliatle变量写，前LoadStore后StoreStore屏障，保证写不会被编译器和处理器重排序
        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;
        for (Job job : jobs){
            System.out.println("[" + job.priority + "]" + "\tCount: " + job.count);

        }

    }


    static class Job implements Runnable{
        private int priority;
        private long count;
        Job(int priority){
            this.priority = priority;
        }
        @Override
        public void run() {
            while(notStart){
                Thread.yield();
            }
            while(notEnd){
                Thread.yield();
                count++;
            }
        }
    }
}
