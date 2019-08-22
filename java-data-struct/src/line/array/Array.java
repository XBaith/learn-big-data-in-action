package line.array;

public class Array<E> {
    private E[] data;
    private int size;

    public Array(int capacity){
        data = (E[])new Object[capacity];
        size = 0;
    }
    public Array(){
        this(10);
    }
    public Array(E[] array){
        data = (E[])new Object[array.length];
        for(int i = 0 ; i < array.length ; i++){
            data[i] = array[i];
        }
        size = array.length;
    }

    public int getSize(){
        return size;
    }

    public int getCapacity(){
        return data.length;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void addLast(E v){
        if(size == data.length){
            resize(2 * data.length);
        }
        data[size] = v;
        size++;
    }

    public void add(int index, E v){
        if(index < 0 || index > size){
            throw new IllegalArgumentException("add fail, index is illegal.");
        }
        if(size == data.length){
            resize(2 * data.length);
        }
        for(int i = size - 1; i >= index; i--){
            data[i + 1] = data[i];
        }
        data[index] = v;
        size++;
    }

    public void addFirst(E v){
        add(0, v);
    }

    public E remove(int index){
        if(index < 0 || index > size - 1){
            throw new IllegalArgumentException("delete fail, index is illegal.");
        }
        E removeElement = data[index];
        for(int i = index; i < size - 1; i++){
            data[i] = data[i + 1];
        }
        size--;
        data[size] = null;
        if(size == data.length / 4 && data.length / 2 != 0){
            resize(data.length / 2);
        }
        return removeElement;
    }

    public E removeFirst(){
        return remove(0);
    }

    public E removeLast(){
        return remove(size - 1);
    }

    public boolean removeElement(E v){
        for(int i = 0 ; i < size ; i++){
            if(data[i].equals(v)){
                remove(i);
                return true;
            }
        }
        return false;
    }


    public void set(int index, E v){
        if(index <0 || index > size - 1){
            throw new IllegalArgumentException("set fail, index is illegal.");
        }
        data[index] = v;
    }

    public E get(int index){
        if(index < 0 || index > size - 1){
            throw new IllegalArgumentException("get fail, index is illegal.");
        }
        return data[index];
    }

    public boolean contains(E v){
        for(int i = 0 ; i < size ; i++){
            if(data[i].equals(v)){
                return true;
            }
        }
        return false;
    }

    public int findElement(E v){
        for(int i = 0 ; i < size ; i++){
            if(data[i].equals(v)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString(){
        if(size == 0){
            return "array is empty";
        }else {
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("Array size: %d, capacity: %d\n", size, data.length));
            sb.append("[");
            for (int i = 0; i < size - 1; i++) {
                sb.append(data[i] + ", ");
            }
            sb.append(data[size - 1] + "]");
            return sb.toString();
        }
    }

    private void resize(int newCapacity){
        E[] newData = (E[])new Object[newCapacity];
        for(int i = 0 ; i < size ; i++){
            newData[i] = data[i];
        }
        data = newData;
    }

    public void swap(int k, int parent) {
        if(k < 0 || parent < 0 || k > getSize() - 1 || parent > getSize() - 1){
            throw new IllegalArgumentException("index is illegal.");
        }
        E temp = data[k];
        data[k] = data[parent];
        data[parent] = temp;
    }
}
