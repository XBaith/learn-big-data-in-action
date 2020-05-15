package producerconsumerqueue;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者-消费者队列示例
 * @author jacky bai
 */
public class ProducerConsumerQueue {

    /* 生产者 */
    private static class Producer implements Runnable{
        volatile boolean isRunning = true;  //运行状态
        BlockingQueue<Integer> queue; //作为临界区
        static AtomicInteger count = new AtomicInteger();    //数据总数
        final int SLEEP_TIME = 1000; //休眠时间

        Producer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println("Start Producer: " + Thread.currentThread().getId());

            try {
                while (isRunning) {
                    Thread.sleep(SLEEP_TIME);
                    int data = count.incrementAndGet();
                    System.out.println(data + " is produced");
                    if(!queue.offer(data, 2, TimeUnit.SECONDS)) {
                        System.err.println("failed to produce " + data);
                    }
                }
            }catch (InterruptedException e){
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        public void stopRunning() {
            isRunning = false;
        }
    }

    /* 消费者 */
    private static class Consumer implements Runnable{
        BlockingQueue<Integer> queue;
        final int SLEEP_TIME = 1000;

        Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println("Start Consumer: " + Thread.currentThread().getId());

            try{
                while(true) {
                    int data = queue.take();   //消费数据
                    System.out.println(data + " is consumed");
                    Thread.sleep(SLEEP_TIME);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        Producer producerA = new Producer(queue);
        Producer producerB = new Producer(queue);
        Producer producerC = new Producer(queue);
        Consumer consumerA = new Consumer(queue);
//        Consumer consumerB = new Consumer(queue);
//        Consumer consumerC = new Consumer(queue);

        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(producerA);
        service.submit(producerB);
        service.submit(producerC);
        service.submit(consumerA);
//        service.submit(consumerB);
//        service.submit(consumerC);

        Thread.sleep(10 * 1000);
        producerA.stopRunning();
        producerB.stopRunning();
        producerC.stopRunning();
        System.out.println("stop producing...");

        service.shutdown();
    }
}
