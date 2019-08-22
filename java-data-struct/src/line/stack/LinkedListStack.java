package line.stack;

import line.list.LinkedList;

public class LinkedListStack<E> implements Stack<E>{
    private LinkedList<E> linkedList;

    public LinkedListStack(){
        linkedList = new LinkedList<>();
    }

    @Override
    public int getSize() {
        return linkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public void push(E value) {
        linkedList.addFirst(value);
    }

    @Override
    public E pop() {
        return linkedList.removeFirst();
    }

    @Override
    public E peek() {
        return linkedList.getFirst();
    }
    public static void main(String[] args) {
        LinkedListStack<Integer> stack = new LinkedListStack<>();
        for(int i = 0 ; i < 10 ; i++){
            stack.push(i);
        }
        System.out.println(stack.pop());
        System.out.println(stack.peek());
    }
}
