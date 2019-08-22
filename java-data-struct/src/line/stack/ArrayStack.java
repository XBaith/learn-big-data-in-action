package line.stack;

import line.array.Array;

public class ArrayStack<E> implements Stack<E>{
    private Array<E> array;

    public ArrayStack(){
        array = new Array<>();
    }

    public ArrayStack(int capacity){
        array = new Array<>(capacity);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return array.getSize() == 0;
    }

    public int getCapacity(){
        return array.getCapacity();
    }

    @Override
    public void push(E value) {
        array.addLast(value);
    }

    @Override
    public E pop() {
        return array.removeLast();
    }

    @Override
    public E peek() {
        return array.get(array.getSize() - 1);
    }
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("Stack [ ");
        for(int i = 0 ; i < array.getSize() ; i++){
            sb.append(array.get(i));
            if(i != array.getSize() - 1){
                sb.append(", ");
            }
        }
        sb.append("] top");
        return sb.toString();
    }


}
