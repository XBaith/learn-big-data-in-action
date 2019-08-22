package line.queue;

public interface Queue<E> {
    public int getSize();
    public boolean isEmpty();
    public void enquene(E value);
    public E dequene();
    public E getFront();
}
