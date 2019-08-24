package tree.union;

public class UnionFindV3 implements UnionFind {
    private int[] parents;
    private int[] elmentsSum;

    public UnionFindV3(int size){
        parents = new int[size];
        elmentsSum = new int[size];
        //初始化每个集合的根，保证每个元素都在不同的集合中
        for(int i = 0 ; i < size; i++){
            parents[i] = i;
            elmentsSum[i] = 1;
        }
    }

    /**
     * 查找元素p所对应的集合编号
     * O(h),h是树的高度
     * @param p
     * @return
     */
    private int find(int p){
        if(p < 0 || p >= parents.length){
            throw new IllegalArgumentException("out of array bound");
        }
        while(p != parents[p]){
            p = parents[p];
        }
        return parents[p];
    }

    @Override
    public int getSize() {
        return parents.length;
    }

    /**
     * 合并两个元素所在的集合
     * O(h),h为树的高度
     * @param p
     * @param q
     */
    @Override
    public void union(int p, int q) {
        int pRoot = parents[p];
        int qRoot = parents[q];
        if(pRoot == qRoot){
            return;
        }
        if(elmentsSum[qRoot] < elmentsSum[pRoot]){
            parents[qRoot] = pRoot;
            elmentsSum[pRoot] += elmentsSum[qRoot];
        }else{
            parents[pRoot] = qRoot;
            elmentsSum[qRoot] += elmentsSum[pRoot];
        }
    }

    /**
     * 查询两个元素是否连接
     * O(h),h是树的高度
     * @param p
     * @param q
     * @return 是一个集合为true
     */
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}

