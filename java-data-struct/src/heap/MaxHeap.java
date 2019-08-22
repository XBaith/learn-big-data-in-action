package heap;

import line.array.Array;

/**
 * 最大堆
 * @param <E>
 */
public class MaxHeap<E extends Comparable<E>> {
    private Array<E> data;

    public MaxHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MaxHeap() {
        data = new Array<>();
    }

    /**
     * heaptify操作——将一个传入的数组变为最大堆，时间复杂度度时O(n)，比将数组插入到空堆O(nlongn)中要快
     * @param array
     */
    public MaxHeap(E[] array){
        data = new Array<>(array);
        for(int i = parent(data.getSize() - 1) ; i >= 0 ; i--){
            siftDown(i);
        }
    }

    public int size() {
        return data.getSize();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    private int parent(int index) {
        return index / 2;
    }

    private int leftChild(int index) {
        return index * 2;
    }

    private int rightChild(int index) {
        return index * 2 + 1;
    }

    public void add(E e) {
        data.addLast(e);
        siftUp(data.getSize() - 1);
    }

    /**
     * 插入数值后的平衡堆操作——上浮
     * @param k
     */
    private void siftUp(int k) {
        while (k > 0 && data.get(parent(k)).compareTo(data.get(k)) < 0) {
            data.swap(k, parent(k));
            k = parent(k);
        }
    }

    public E findMax() {
        return data.get(0);
    }

    public E extractMax() {
        E max = findMax();
        data.swap(0, data.getSize() - 1);
        data.removeLast();
        siftDown(0);
        return max;
    }

    /**
     * 去除最大值后的平衡堆操作——下沉
     * @param k
     */
    private void siftDown(int k) {
        while (leftChild(k) < data.getSize()) {
            int j = leftChild(k);
            //找出k左右孩子的最大孩子节点
            if (j + 1 < data.getSize() &&
                    data.get(j).compareTo(data.get(j + 1)) < 0) {
                j = rightChild(k);
                //或者可以表达为j++
            }
            //如果其中最大的孩子节点大于父亲节点k则交换数值
            if (data.get(j).compareTo(data.get(k)) > 0) {
                data.swap(k, j);
            }else {
                break;
            }
            k = j;
        }
    }

    public E replace(E e){
        E ret = findMax();
        data.set(0, e);
        siftDown(0);
        return ret;
    }

}
