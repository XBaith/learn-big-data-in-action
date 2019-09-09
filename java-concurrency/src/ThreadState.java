import java.util.concurrent.TimeUnit;

/**
 * @author acer
 */
public class ThreadState {
    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        new Thread(new Block(), "BlockThread-1").start();
        new Thread(new Block(), "BlockThread-2").start();
    }

    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.seconds(100);
            }
        }
    }

    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Block implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Block.class) {
                   SleepUtils.seconds(100);
                }
            }
        }
    }
}
