package graph.dfs;

import graph.representation.Graph;

public class AllPath {
    private SingleSourcePath[] paths;
    private Graph graph;

    public AllPath(Graph graph){
        this.graph = graph;
        paths = new SingleSourcePath[graph.getVertexes()];
        for(int v = 0; v < graph.getVertexes(); v++){
            paths[v] = new SingleSourcePath(graph, v);
        }
    }

    public SingleSourcePath[] getPaths(){
        return paths;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("input/g.txt");
        AllPath allPath = new AllPath(graph);
        for(SingleSourcePath path : allPath.getPaths()){
            System.out.println(path.path(6));
        }
    }
}
