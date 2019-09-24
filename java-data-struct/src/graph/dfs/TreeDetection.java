package graph.dfs;

import graph.representation.Graph;

/**
 * 判断图是否为一棵树
 * 条件有俩个：１.是否有环；２.是否只有一个联通分量
 */
public class TreeDetection {
    private Graph graph;
    private int[] visited;
    private int vs;
    private boolean hasCycle = false;
    private int cccount = 0;

    public TreeDetection(Graph graph){
        this.graph = graph;
        vs = graph.getVertexes();
        visited = new int[vs];
        for(int i = 0; i < vs; i++){
            visited[i] = -1;
        }

        for(int v = 0;v < vs; v++){
            if(visited[v] == -1) {
                dfs(v, v, cccount);
                cccount++;
            }
        }
    }

    private void dfs(int v, int parent, int ccid) {
        visited[v] = ccid;
        for(int w : graph.adj(v)){
            if(visited[w] == -1){
                dfs(w, v, ccid);
            }else if(w != parent){
                hasCycle =  true;
            }
        }
    }

    public boolean getCycle(){
        return hasCycle;
    }

    public boolean isTree(){
        return cccount == 1 ? true : false && hasCycle;
    }


    public static void main(String[] args) {
        Graph g = new Graph("input/tree.txt");
        TreeDetection cycle = new TreeDetection(g);
        System.out.println(cycle.isTree());
    }
}
