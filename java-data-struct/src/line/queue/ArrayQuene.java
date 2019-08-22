package line.queue;

import line.array.Array;

public class ArrayQuene<E> implements Queue<E> {
    private Array<E> array;

    public ArrayQuene(int capacity){
        array = new Array<>(capacity);
    }

    public ArrayQuene(){
        array = new Array<>();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    public int getCapacity(){
        return array.getCapacity();
    }

    @Override
    public boolean isEmpty() {
        return array.getSize() == 0;
    }

    @Override
    public void enquene(E value) {
        array.addLast(value);
    }

    @Override
    public E dequene() {
        return array.removeFirst();
    }

    @Override
    public E getFront() {
        return array.get(0);
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("Quene: FRONT [");
        for(int i = 0 ; i < array.getSize() ; i++){
            sb.append(array.get(i));
            if(i != array.getSize() - 1){
                sb.append(", ");
            }
        }
        sb.append("] TAIL");
        return  sb.toString();
    }

}
