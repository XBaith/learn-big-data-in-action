package graph.bfs;

import graph.representation.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GraphBFS {
    private Graph graph;
    private boolean[] visited;
    private int vs;

    private ArrayList<Integer> order = new ArrayList<>();

    public GraphBFS(Graph graph){
        this.graph = graph;
        vs = graph.getVertexes();
        visited = new boolean[vs];

        for(int v = 0; v < vs; v++){
            if(!visited[v]) {
                //order.add(v);
                bfs(v);
            }
        }
    }

    private void bfs(int source){
        //为了方便在队尾添加元素，用LinkedList
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;

        while(!queue.isEmpty()){
            int v = queue.poll();
            order.add(v);
            for(int adj : graph.adj(v)){
                if(!visited[adj]){
                    queue.add(adj);
                    visited[adj] = true;
                }
            }
        }
    }

    public Iterable<Integer> getOrder(){
        return order;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("input/g.txt");
        GraphBFS bfs = new GraphBFS(graph);
        System.out.println(bfs.getOrder());
    }
}
