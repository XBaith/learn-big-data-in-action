package producerconsumerqueue;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;

public class DisruptorPCQueue {

    /* 消息 */
    private static class Data {
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    private static class Producer {
        private final RingBuffer<Data> ringBuffer;   //环形缓冲区

        private Producer(RingBuffer<Data> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        public void pushData(ByteBuffer byteBuffer) {
            long sequence = ringBuffer.next();
            Data data = ringBuffer.get(sequence);
            data.setValue(byteBuffer.getInt(0)); //填充数据
            ringBuffer.publish(sequence);
        }
    }

    private static class Consumer implements WorkHandler<Data> {

        @Override
        public void onEvent(Data data) throws Exception {
            System.out.println("Consumer " + Thread.currentThread().getId()
                    + " process data: [" + data.getValue() + "]");
        }
    }

    private static class DataFactory implements EventFactory<Data> {
        @Override
        public Data newInstance() {
            return new Data();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Disruptor<Data> disruptor = new Disruptor<>(new DataFactory(),
                1024,
                Executors.defaultThreadFactory());
        disruptor.handleEventsWithWorkerPool(new Consumer(),
                new Consumer(), new Consumer());
        disruptor.start();

        RingBuffer<Data> ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for(int n = 0;; n++) {
            System.out.println("add data " + n);
            byteBuffer.putInt(0, n);
            producer.pushData(byteBuffer);
            Thread.sleep(100);
        }
    }
}
