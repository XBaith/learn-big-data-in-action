package tree.union;

public class UnionFindV1 implements UnionFind {
    private int[] unionId;

    public UnionFindV1(int size) {
        unionId = new int[size];
        for (int i = 0; i < unionId.length; i++) {
            unionId[i] = i;
        }
    }

    @Override
    public int getSize() {
        return unionId.length;
    }

    private int find(int p) {
        return unionId[p];
    }

    /**
     * O(n)
     *
     * @param p
     * @param q
     */
    @Override
    public void union(int p, int q) {
        if (p < 0 || p >= unionId.length || q < 0 || q >= unionId.length) {
            throw new IllegalArgumentException("invail index");
        }
        int pId = find(p);
        int qId = find(q);
        if (pId == qId) {
            return;
        }
        for (int i = 0; i < unionId.length; i++) {
            if (unionId[i] == qId) {
                unionId[i] = pId;
            }
        }
    }

    /**
     * O(1)
     *
     * @param p
     * @param q
     * @return
     */
    @Override
    public boolean isConnected(int p, int q) {
        if (p < 0 || p >= unionId.length || q < 0 || q >= unionId.length) {
            throw new IllegalArgumentException("invail index");
        }
        return find(p) == find(q);
    }
}
