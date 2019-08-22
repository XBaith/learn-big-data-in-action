package line.list;

public class LinkedList<E> {
    private int size;
    private Node<E> head;

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

    public LinkedList() {
        head = new Node<>();
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(int index, E data) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("add fail, index is illegal!");
        }
        Node<E> prev = head;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        prev.next = new Node<>(data, prev.next);
        size++;
    }

    public void addFirst(E data) {
        add(0, data);
    }

    public void addLast(E data) {
        add(size, data);
    }

    public boolean add(E data){
        addLast(data);
        return true;
    }

    public E remove(int index){
        Node<E> prve = head;
        for(int i = 0 ; i < index; i++){
            prve = prve.next;
        }

        Node<E> delNode = prve.next;
        prve.next = delNode.next;
        delNode.next = null;
        size--;

        return delNode.data;
    }

    public E removeFirst(){
        return remove(0);
    }

    public E removeLast(){
        return remove(size - 1);
    }

    public void removeByElement(E data){
        Node<E> prve = head;
        while (prve.next != null){
            if(prve.next.data.equals(data)){
                Node<E> delNode = prve.next;
                prve.next =delNode.next;
                delNode.next = null;
                size--;
                break;
            }
            prve = prve.next;
        }
    }


    public boolean contains(E data){
        Node<E> cur = head.next;
        while(cur != null){
            if(cur.data.equals(data)){
                return true;
            }else{
                cur = cur.next;
            }
        }
        return false;
    }

    public E get(int index){
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get Fail, Index is illegal.");
        }
        Node<E> cur = head.next;
        for(int i = 0; i < index; i++){
            cur = cur.next;
        }
        return cur.data;
    }

    public E getLast(){
        return get(size - 1);
    }

    public E getFirst(){
        return get(0);
    }

    public void set(int index, E data){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("Set Fail. Index is illegal.");
        }
        Node<E> prve = head.next;
        for(int i = 0; i < index; i++){
            prve = prve.next;
        }
        prve.next.data = data;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("List: size = " + size + "\n");
        sb.append("datas: [");
        for (Node cur = head.next; cur != null; cur = cur.next) {
            sb.append(cur.data + " -> ");
        }
        sb.append("null]");
        return sb.toString();
    }

}
