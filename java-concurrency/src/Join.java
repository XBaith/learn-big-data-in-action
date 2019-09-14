/**
 * 线程B调用A.join(),说明线程A退出后才返回到join()继续执行线程B的程序
 */
public class Join {

    public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for(int i = 0 ; i < 10 ; i++){
            Thread thread = new Thread(new Domino(previous), String.valueOf(i));
            System.out.println(thread.getName() + "     start");
            thread.start();
            previous = thread;
        }
        SleepUtils.seconds(3);
        System.out.println(Thread.currentThread().getName() + "     done.");
    }

    static class Domino implements Runnable{
        Thread previous = null;
        public Domino(Thread previous){
            this.previous = previous;
        }
        @Override
        public void run() {
            try {
                System.out.println(previous.getName() + " join.");
                previous.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + "       terminate.");
        }
    }
}
