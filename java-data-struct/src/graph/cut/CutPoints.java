package graph.cut;

import graph.representation.Graph;

import java.util.ArrayList;

public class CutPoints {
    private Graph graph;
    private boolean[] visited;
    private int vs;
    private int[] order;
    private int[] low;
    private int cnt = 0;

    private ArrayList<Integer> points;

    public CutPoints(Graph g){
        graph = g;
        vs = g.getVertexes();
        visited = new boolean[vs];
        order = new int[vs];
        low = new int[vs];
        points = new ArrayList<>();

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

        int child = 0;

        for(int w : graph.adj(v)){
            if(!visited[w]){
                child++;
                dfs(w, v);
                low[v] = Math.min(low[v], low[w]);
                if(v != parent && low[w] >= order[v] && !points.contains(v))
                    points.add(v);
                else if(v == parent && child > 1 && !points.contains(v))
                    points.add(v);
            }else if(w != parent){
                low[v] = Math.min(low[v], low[w]);
            }
        }
    }

    public ArrayList<Integer> getPoints(){
        return points;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("input/bridge.txt");
        CutPoints ct = new CutPoints(graph);
        System.out.println(ct.getPoints());

        Graph pointsGraph = new Graph("input/bridges.txt");
        CutPoints points = new CutPoints(pointsGraph);
        System.out.println(points.getPoints());

        Graph treeGraph = new Graph("input/tree.txt");
        CutPoints tree = new CutPoints(treeGraph);
        System.out.println(tree.getPoints());
    }
}
