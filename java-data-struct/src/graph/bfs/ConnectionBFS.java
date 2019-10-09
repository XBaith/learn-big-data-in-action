package graph.bfs;

import graph.representation.Graph;

import java.util.LinkedList;
import java.util.Queue;

public class ConnectionBFS {
    private Graph graph;
    private int vs;
    private boolean[] visited;
    private int[] compents;
    private int cccount = 0;

    public ConnectionBFS(Graph graph){
        this.graph = graph;
        vs = graph.getVertexes();
        visited = new boolean[vs];
        compents = new int[vs];

        for(int v = 0; v < vs; v++){
            if(!visited[v]){
                bfs(v, cccount);
                cccount++;
            }
        }

    }

    private void bfs(int source, int ccid) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        //compents[source] = ccid;

        while(!queue.isEmpty()){
            int v = queue.poll();
            compents[v] = ccid;
            for(int w : graph.adj(v)){
                if(!visited[w]) {
                    visited[w] = true;
                    queue.add(w);
                }
            }
        }
    }

    public boolean isConnected(int v, int w){
        return graph.validVertex(v) && graph.validVertex(w) && compents[v] == compents[w];
    }

    public int[] getCompents(){
        return compents;
    }

    public int getCccount(){
        return cccount;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("input/g.txt");
        ConnectionBFS connectionBFS = new ConnectionBFS(graph);

        System.out.println("cccount: " + connectionBFS.getCccount());

        for(int c : connectionBFS.getCompents()){
            System.out.print(c + " ");
        }
        System.out.println();

        System.out.println("0 -?> 5: " + connectionBFS.isConnected(0, 5));

        System.out.println("0 -?> 6: " + connectionBFS.isConnected(0, 6));
    }
}
