/**
 * 在抛出InterruptedException之后会重置interrupt属性
 */
public class InterruptThread {

    public static void main(String[] args) {
        //睡眠线程
        Thread sleep = new Thread(new SleepRunner(), "SleepThread");
        sleep.setDaemon(true);
        Thread busy = new Thread(new BusyRunner(), "BusyThread");
        busy.setDaemon(true);
        sleep.start();
        busy.start();
        //为了线程充分启动
        SleepUtils.seconds(5);

        sleep.interrupt();
        busy.interrupt();

        System.out.println(sleep.getName() + ": " + sleep.isInterrupted());
        System.out.println(busy.getName() + ": " + busy.isInterrupted());
        //防止线程立即退出
        SleepUtils.seconds(2);
    }

    static class SleepRunner implements Runnable{
        @Override
        public void run() {
            while(true){
                SleepUtils.seconds(10);
            }
        }
    }

    static class BusyRunner implements Runnable{
        @Override
        public void run() {
            while(true){}
        }
    }
}
