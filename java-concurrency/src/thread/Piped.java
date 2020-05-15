package thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 线程之间通过管道流输入/输出
 * PipedOutputStream, PipedInputStream是字节流
 * PipedReader, PipedWriter是面向字符流
 * 线程管道之间通信的介质是内存
 */
public class Piped {
    public static void main(String[] args) throws IOException{
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        out.connect(in);
        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        int receive = 0;
        while((receive = System.in.read()) != -1) {
            out.write(receive);
        }
    }

    static class Print implements Runnable{
        private PipedReader in;
        public Print(PipedReader in){
            this.in = in;
        }
        @Override
        public void run() {
            int receive = 0;
            try {
                while((receive = in.read()) != -1){
                    System.out.print((char)receive);
                }
            }catch (Exception ignore){

            }

        }
    }
}


