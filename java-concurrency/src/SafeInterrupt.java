public class SafeInterrupte {
    private static int i = 0;

    public static void main(String[] args) {
        Thread interrupt
    }

    static class Runner implements Runnable{
        private volatile boolean on = true;
        @Override
        public void run() {
            while(on && !Thread.currentThread().isInterrupted()){
                i++;
            }
        }

        public void cancel(){
            on = false;
        }
    }
}
