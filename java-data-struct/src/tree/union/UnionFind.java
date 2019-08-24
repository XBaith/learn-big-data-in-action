package tree.union;

public interface UnionFind {
    int getSize();

    /**
     * 合并两个集合
     * @param p
     * @param q
     */
    void union(int p, int q);

    /**
     * 查找两个元素是否连接
     * @param p
     * @param q
     * @return 连接为true，不连接为false
     */
    boolean isConnected(int p, int q);
}
