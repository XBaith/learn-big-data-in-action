package line.queue;

public class LoopQueue<E> {
    private E[] data;
    private int front, tail;

    public LoopQueue(){
        data = (E[])new Object[11];
    }

    public LoopQueue(int capacity){
        data = (E[])new Object[capacity + 1];
    }
}
