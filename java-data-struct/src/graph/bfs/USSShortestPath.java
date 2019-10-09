package graph.bfs;

import graph.representation.Graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Unweighted Single Source Shortest Path
 */
public class USSShortestPath {
    private Graph graph;
    private int vs;
    private boolean[] visited;
    private int[] pre;
    private int[] dis;

    private int target;

    public USSShortestPath(Graph graph, int target){
        this.graph = graph;
        this.target = target;
        vs = graph.getVertexes();
        visited = new boolean[vs];
        dis = new int[vs];

        for(int v = 0; v < vs; v++){
            dis[v] = -1;
        }

        for(int v = 0; v < vs; v++){
            if(!visited[v]){
                if(bfs(v)){
                    break;
                }
            }
        }
    }

    public boolean bfs(int source){
        Queue<Integer> queue= new LinkedList<>();
        visited[source] = true;
        queue.add(source);
        dis[source] = 0;

        while(!queue.isEmpty()){
            int v = queue.poll();
            for(int adj : graph.adj(v)){
                if(!visited[adj]) {
                    visited[adj] = true;
                    queue.add(adj);
                    dis[adj] = dis[v] + 1;

                    if(adj == target){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public int[] getDis(){
        return dis;
    }

    public int distance(){
        return dis[target];
    }


    public static void main(String[] args) {
        Graph graph = new Graph("input/color.txt");
        USSShortestPath shortestPath = new USSShortestPath(graph, 5);

        for(int d : shortestPath.getDis()){
            System.out.print(d + " ");
        }
        System.out.println();
        System.out.println(shortestPath.distance());
    }
}
