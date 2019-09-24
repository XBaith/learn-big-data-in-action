package graph.dfs;

import graph.representation.Graph;

import java.util.ArrayList;
import java.util.Collections;

public class SingleSourcePath {
    private Graph graph;
    private boolean[] visited;
    private int source;
    private int[] pre;

    public SingleSourcePath(Graph graph, int source){
        graph.validVertex(source);
        this.graph = graph;
        this.source = source;

        int vs = graph.getVertexes();
        visited = new boolean[vs];
        pre = new int[vs];
        for(int i = 0; i < vs; i ++){
            pre[i] = -1;
        }
        postDfs(source, source);
    }

    private void dfs(int source, int parent) {
        visited[source] = true;
        pre[source] = parent;
        for(int w : graph.adj(source))
            if(!visited[w]){
                dfs(w, source);
            }
    }
    private void postDfs(int source, int parent) {
        visited[source] = true;
        for(int w : graph.adj(source)) {
            if (!visited[w]) {
                dfs(w, source);
                pre[source] = parent;
            }
        }

    }

    public boolean isConnected(int to){
        return graph.validVertex(to) && visited[to];
    }

    public Iterable<Integer> path(int to){
        ArrayList<Integer> res = new ArrayList<>();
        if(isConnected(to)) {
            int cur = to;
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
        SingleSourcePath ssPath = new SingleSourcePath(graph, source);
        System.out.println(source + " to 6: " + ssPath.path(6));
        System.out.println(source + " to 5: " + ssPath.path(5));
    }

}
