package line.stack;

public interface Stack<E> {
    public int getSize();
    public boolean isEmpty();
    public void push(E value);
    public E pop();
    public E peek();
}
