import java.util.concurrent.TimeUnit;

public class SleepUtils {
    public static final void seconds(int sec){
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
