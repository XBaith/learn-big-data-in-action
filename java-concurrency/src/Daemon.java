/**
 * Daemon作为中后台的辅助线程，在JVM关闭后不一定执行finally块
 * 因此不能指望通过finally来关闭或清理资源
 * @author acer
 */
public class Daemon {
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable{
        @Override
        public void run() {
            try{
                SleepUtils.seconds(10);
            }finally {
                System.out.println("Daemon Thread finally run.");
            }
        }
    }
}
