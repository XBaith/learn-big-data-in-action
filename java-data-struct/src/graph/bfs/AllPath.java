package graph.bfs;

import graph.representation.Graph;

public class AllPath {
    private Graph graph;
    private SingleSourcePath[] paths;
    private int vs;

    public AllPath(Graph graph){
        this.graph = graph;
        vs = graph.getVertexes();

        paths = new SingleSourcePath[vs];
        for(int v = 0; v < vs; v++){
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
