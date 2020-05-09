package concurrency.sync;

public class DisppearRequest implements Runnable{

    static DisppearRequest instance = new DisppearRequest();

    static int count = 0;

    public static void main(String[] args) throws InterruptedException{
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(count);
    }

    @Override
    public void run() {
        for(int i = 0; i < 100000; i++) {
            count++;
        }
    }
}
