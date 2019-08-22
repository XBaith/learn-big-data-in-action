package tree.bst.segment;

public class SegmentTree<E> {
    private E[] tree;
    private E[] data;
    private Merger<E> merger;

    @SuppressWarnings("unchecked")
    public SegmentTree(E[] arr, Merger<E> merger) {
        this.merger = merger;

        data = (E[]) new Object[arr.length];
        //copy array
        System.arraycopy(arr, 0, data, 0, arr.length);

        tree = (E[]) new Object[4 * arr.length];
        buildSegmentTree(0, 0, data.length - 1);
    }

    private void buildSegmentTree(int treeIndex, int left, int right) {
        if (left == right) {
            tree[treeIndex] = data[left];
            return;
        }
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        int mid = left + (right - left) / 2;
        buildSegmentTree(leftTreeIndex, left, mid);
        buildSegmentTree(rightTreeIndex, mid + 1, right);

        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    public E querry(int querryL, int querryR) {
        if (querryL < 0 || querryL >= data.length || querryR < 0 || querryR >= data.length
                || querryL > querryR) {
            throw new IllegalArgumentException("querry is illegal.");
        }
        return querry(0, 0, data.length - 1, querryL, querryR);
    }

    private E querry(int treeIndex, int left, int right, int querryL, int querryR) {
        if (left == querryL && right == querryR) {
            return tree[treeIndex];
        }
        int mid = left + (right - left) / 2;

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        if (querryL >= mid + 1) {
            return querry(rightTreeIndex, mid + 1, right, querryL, querryR);
        } else if (querryR <= mid) {
            return querry(leftTreeIndex, left, mid, querryL, querryR);
        }else{
            E leftResult = querry(leftTreeIndex,left,mid,querryL,mid);
            E rightResult = querry(rightTreeIndex, mid + 1, right, mid + 1, querryR);
            return merger.merge(leftResult,rightResult);
        }
    }

    public E get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("index is not illegal.");
        }
        return data[index];
    }

    public int getSize() {
        return data.length;
    }

    /**
     * 根据传入的节点索引返回左孩子节点的索引
     *
     * @param parents
     * @return left child index
     */
    private int leftChild(int parents) {
        return parents * 2 + 1;
    }

    /**
     * 根据传入的节点索引返回右孩子节点的索引
     *
     * @param parents
     * @return right child index
     */
    private int rightChild(int parents) {
        return parents * 2 + 2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) {
                sb.append(tree[i]);
            } else {
                sb.append("null");
            }

            if (i < tree.length - 1) {
                sb.append(",");
            }
        }
        return sb.append("]").toString();
    }
}
