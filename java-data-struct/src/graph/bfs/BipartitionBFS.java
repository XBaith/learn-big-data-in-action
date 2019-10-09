package graph.bfs;

import graph.representation.Graph;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.LinkedList;
import java.util.Queue;

public class BipartitionBFS {
    private Graph graph;
    private int vs;
    private boolean[] visited;
    private int[] colors;
    private boolean bipartite = true;

    public BipartitionBFS(Graph graph){
        this.graph = graph;
        vs = graph.getVertexes();

        visited = new boolean[vs];
        colors = new int[vs];

        for(int v = 0; v < vs; v++){
            if(!visited[v]){
                if(!bfs(v)){
                    bipartite = false;
                    break;
                }
            }
        }
    }

    private boolean bfs(int source) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        colors[source] = -1;

        while(!queue.isEmpty()){
            int v = queue.poll();
            for(int adj : graph.adj(v)){
                if(!visited[adj]) {
                    visited[adj] = true;
                    queue.add(adj);
                    colors[adj] = -colors[v];
                }else if(colors[adj] == colors[v]){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isBipartition(){
        return bipartite;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("input/color.txt");
        BipartitionBFS bipartitionBFS = new BipartitionBFS(graph);
        System.out.println(bipartitionBFS.isBipartition());

        Graph graph1 = new Graph("input/non-bipartite");
        BipartitionBFS bipartitionBFS1 = new BipartitionBFS(graph1);
        System.out.println(bipartitionBFS1.isBipartition());
    }
}
