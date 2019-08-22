package line.queue;

public class LinkedListQuene<E> implements Queue<E> {

    private class Node<E> {
        E data;
        Node<E> next;

        Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }

        Node() {
            this(null, null);
        }

        Node(E data) {
            this(data, null);
        }
    }

    private int size;
    private Node<E> head, tail;

    public LinkedListQuene(){
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void enquene(E value) {
        if(tail == null){
            tail = new Node<>(value);
            head = tail;
        }else{
            Node<E> enNode = new Node<>(value);
            tail.next = enNode;
            tail = tail.next;
        }
        size++;
    }

    @Override
    public E dequene() {
        if(isEmpty()){
            throw new IllegalArgumentException("Dequene Fail, Quene is empty.");
        }
        Node<E> delNode = head;
        head = head.next;
        if(head == tail){
            tail = null;
        }
        return delNode.data;
    }

    @Override
    public E getFront() {
        if(isEmpty()){
            throw new IllegalArgumentException("Dequene Fail, Quene is empty.");
        }
        return head.data;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("LinkedListQuene: front ");
        Node<E> cur = head;
        while(cur != null){
            sb.append(cur.data + " -> ");
            cur = cur.next;
        }
        sb.append("null tail");
        return sb.toString();
    }
}
