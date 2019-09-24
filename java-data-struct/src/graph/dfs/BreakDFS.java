package graph.dfs;

import graph.representation.Graph;

import java.util.ArrayList;
import java.util.Collections;

public class BreakDFS {
    private Graph graph;
    private boolean[] visited;
    private int source;
    private int[] pre;
    //目标顶点
    private int target;

    public BreakDFS(Graph graph, int source, int target){
        graph.validVertex(source);
        graph.validVertex(target);

        this.graph = graph;
        this.source = source;
        this.target = target;

        int vs = graph.getVertexes();
        visited = new boolean[vs];
        pre = new int[vs];
        for(int i = 0; i < vs; i ++){
            pre[i] = -1;
        }
        dfs(source, source);
        for(boolean v : visited){
            System.out.print(v + " ");
        }
        System.out.println();
    }

    /**
     * 优化点：当遍历到目标定点时直接返回，但在最差情况，即目标定点与源不连通，那需要遍历全部顶点也就是O(n)复杂度
     * @param source
     * @param parent
     * @return
     */
    private boolean dfs(int source, int parent) {
        visited[source] = true;
        pre[source] = parent;
        if(source == target) return true;
        for(int w : graph.adj(source))
            if(!visited[w]){
                if(dfs(w, source)){
                    return true;
                }
            }
        return false;
    }

    public boolean isConnected(){
        return visited[target];
    }

    public Iterable<Integer> path(){
        ArrayList<Integer> res = new ArrayList<>();
        if(isConnected()) {
            int cur = target;
            while (cur != source) {
                res.add(cur);
                cur = pre[cur];
            }
            res.add(source);
            Collections.reverse(res);
            return res;
        }
        return res;
    }


    public static void main(String[] args) {
        Graph graph = new Graph("input/g.txt");
        int source = 0;
        int target = 6;
        BreakDFS bk = new BreakDFS(graph, source, target);
        System.out.println(source + " to 6: " + bk.path());

        System.out.println();

        BreakDFS  s2one = new BreakDFS(graph, source, 1);
        System.out.println(source + " to 1: " + s2one.path());
    }

}

