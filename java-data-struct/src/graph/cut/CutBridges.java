package graph.cut;

import graph.representation.Edge;
import graph.representation.Graph;

import java.util.ArrayList;

public class CutBridges {
    private Graph graph;
    private boolean[] visited;
    private int vs;
    private int[] order;
    private int[] low;
    private int cnt = 0;

    private ArrayList<Edge> bridges;

    public CutBridges(Graph g){
        graph = g;
        vs = g.getVertexes();
        visited = new boolean[vs];
        order = new int[vs];
        low = new int[vs];
        bridges = new ArrayList<>();

        for(int v = 0; v < vs; v++){
            if(!visited[v]){
                dfs(v, v);
            }
        }
    }

    private void dfs(int v, int parent) {
        visited[v] = true;
        order[v] = cnt++;
        low[v] = order[v];

        for(int w : graph.adj(v)){
            if(!visited[w]){
                dfs(w, v);
                low[v] = Math.min(low[v], low[w]);
                if(low[w] > order[v])
                    bridges.add(new Edge(v, w));
            }else if(w != parent){
                low[v] = Math.min(low[v], low[w]);
            }
        }
    }

    public ArrayList<Edge> getBridges(){
        return bridges;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("input/bridge.txt");
        CutBridges cb = new CutBridges(graph);
        System.out.println(cb.getBridges());

        Graph bridgesGraph = new Graph("input/bridges.txt");
        CutBridges bridges = new CutBridges(bridgesGraph);
        System.out.println(bridges.getBridges());

        Graph treeGraph = new Graph("input/tree.txt");
        CutBridges tree = new CutBridges(treeGraph);
        System.out.println(tree.getBridges());
    }
}
