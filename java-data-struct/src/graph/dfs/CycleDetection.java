package graph.dfs;

import graph.representation.Graph;

public class CycleDetection {
    private Graph graph;
    private boolean[] visited;
    private int vs;
    private boolean hasCycle = false;

    public CycleDetection(Graph graph){
        this.graph = graph;
        vs = graph.getVertexes();
        visited = new boolean[vs];
        for(int v = 0;v < vs; v++){
            if(!visited[v]) {
                if(dfs(v, v)){
                    hasCycle = true;
                }
            }
        }
    }

    private boolean dfs(int v, int parent) {
        visited[v] = true;
        for(int w : graph.adj(v)){
            if(!visited[w]){
                if(dfs(w, v)){
                    return true;
                }
            }else if(w != parent){
                return true;
            }
        }
        return false;
    }

    public boolean getCycle(){
        return hasCycle;
    }


    public static void main(String[] args) {
        Graph g = new Graph("input/g.txt");
        CycleDetection cycle = new CycleDetection(g);
        System.out.println(cycle.getCycle());

        Graph gg = new Graph("input/gg.txt");
        CycleDetection noCycle = new CycleDetection(gg);
        System.out.println(noCycle.getCycle());
    }
}
