package graph.bfs;

import graph.representation.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SingleSourcePath {
    private Graph graph;
    private boolean[] visited;
    private int vs;
    private int source;
    private int[] pre;

    public SingleSourcePath(Graph graph, int source){
        this.graph = graph;
        this.source = source;
        vs = graph.getVertexes();
        visited = new boolean[vs];

        pre = new int[vs];
        for(int i = 0; i < vs; i++){
            pre[i] = -1;
        }

        bfs(source);
    }

    private void bfs(int source){
        //为了方便在队尾添加元素，用LinkedList
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        pre[source] = source;

        while(!queue.isEmpty()){
            int v = queue.poll();
            for(int adj : graph.adj(v)){
                if(!visited[adj]){
                    queue.add(adj);
                    visited[adj] = true;
                    pre[adj] = v;
                }
            }
        }
    }

    public boolean isConnect(int target){
        return graph.validVertex(target) && visited[target];
    }

    public Iterable<Integer> path(int target){
        LinkedList<Integer> ret = new LinkedList<>();
        if(isConnect(target)){
            int cur = target;
            while(cur != source){
                ret.addFirst(cur);
                cur = pre[cur];
            }
            ret.addFirst(source);
        }
        return ret;
    }


//    public static void main(String[] args) {
//        Graph graph = new Graph("input/g.txt");
//        SingleSourcePath bfs = new SingleSourcePath(graph, 0);
//        System.out.println(bfs.path(4));
//    }
}