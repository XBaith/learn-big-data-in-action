package graph.bfs;

import graph.representation.Graph;

import java.util.LinkedList;
import java.util.Queue;

public class CycleDetection {
    private Graph graph;
    private int vs;
    private boolean[] visited;
    private int[] pre;
    private boolean cycle = false;

    public CycleDetection(Graph graph){
        this.graph = graph;
        vs = graph.getVertexes();

        visited = new boolean[vs];
        pre = new int[vs];
        for(int i = 0; i < vs; i++){
            pre[i] = -1;
        }

        for(int v = 0; v < vs; v++){
            if(!visited[v]){
                if(bfs(v)) {
                    cycle = true;
                    break;
                }
            }
        }
    }

    private boolean bfs(int source) {
        Queue<Integer> queue = new LinkedList<>();
        visited[source] = true;
        queue.add(source);
        pre[source] = source;

        while(!queue.isEmpty()){
            int v = queue.poll();

            for(int adj : graph.adj(v)){
                if(!visited[adj]) {
                    visited[adj] = true;
                    queue.add(adj);
                    pre[adj] = v;
                }else if(adj != pre[v]){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasCycle(){
        return cycle;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("input/gg.txt");
        CycleDetection cycleDetection = new CycleDetection(graph);

        System.out.println(cycleDetection.hasCycle());
    }
}
