package tree.union;

public class UnionFindV2 implements UnionFind {
    private int[] parents;

    public UnionFindV2(int size){
        parents = new int[size];
        for(int i = 0 ; i < size; i++){
            parents[i] = i;
        }
    }

    private int find(int p){
        while(p != parents[p]){
            p = parents[p];
        }

        return parents[p];
    }

    @Override
    public int getSize() {
        return parents.length;
    }

    @Override
    public void union(int p, int q) {
        if(p < 0 || p >= parents.length || q < 0 || q >= parents.length){
            throw new IllegalArgumentException("out of array bound");
        }
        int pRoot = parents[p];
        int qRoot = parents[q];
        if(pRoot != qRoot){
            parents[p] = qRoot;
        }else { return; }
    }

    @Override
    public boolean isConnected(int p, int q) {
        if(p < 0 || p >= parents.length || q < 0 || q >= parents.length){
            throw new IllegalArgumentException("out of array bound");
        }

        return find(p) == find(q);
    }
}
