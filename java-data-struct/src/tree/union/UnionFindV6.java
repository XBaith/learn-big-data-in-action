package tree.union;

/**
 * 基于树的<b>排名</b>优化，对树整体路径压缩，把树的高度在find过程中大大降低，直至树的高度为2。
 * rank只是粗略的用于比较树的高度，因此不用多余的维护。
 * 但是对比V5版本的，由于递归的消耗，效率稍低一点。
 */
public class UnionFindV6 implements UnionFind {
    private int[] parents;
    private int[] rank;

    public UnionFindV6(int size){
        parents = new int[size];
        rank = new int[size];
        //初始化每个集合的根，保证每个元素都在不同的集合中
        for(int i = 0 ; i < size; i++){
            parents[i] = i;
            rank[i] = 1;
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
        if (p != parents[p]){
            parents[p] = find(parents[p]);
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
        if(rank[qRoot] < rank[pRoot]){
            parents[qRoot] = pRoot;
        }else if(rank[qRoot] > rank[pRoot]) {
            parents[pRoot] = qRoot;
        }else {
            parents[pRoot] = qRoot;
            rank[qRoot] ++;
        }
    }

    /**
     * 查询两个元素是否连接
     * O(h),h是树的高度
     * @param p
     * @param q
     * @return 如果是一个集合为true
     */
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}

