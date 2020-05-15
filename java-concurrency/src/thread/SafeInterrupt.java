package thread;

/**
 * 安全的两种关闭线程的方式
 */
public class SafeInterrupt {

    public static void main(String[] args) {
        Thread interrupt = new Thread(new Runner(), "InterruptedThread: ");
        interrupt.start();
        SleepUtils.seconds(1);
        interrupt.interrupt();

        Runner safe = new Runner();
        Thread flag = new Thread(safe, "FlagThread: ");
        flag.start();
        SleepUtils.seconds(1);
        safe.cancel();
    }

    static class Runner implements Runnable{
        private long i = 0;
        private volatile boolean on = true;
        @Override
        public void run() {
            while(on && !Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }

        public void cancel(){
            on = false;
        }
    }
}
