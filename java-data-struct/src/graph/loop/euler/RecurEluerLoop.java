package graph.loop.euler;

import graph.representation.Graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class RecurEluerLoop {
    private Graph graph;
    private int vs;
    private boolean[] visited;
    private int cccount;


    public RecurEluerLoop(Graph graph){
        this.graph = graph;
        vs = graph.getVertexes();
        visited = new boolean[vs];

        for(int v = 0; v < vs; v++){
            if(!visited[v]) {
                dfs(v);
                cccount++;
            }
        }
    }

    private void dfs(int v) {
        visited[v] = true;
        for(int w : graph.adj(v)){
            if(!visited[w])
                dfs(w);
        }
    }

    public int compents(){
        return cccount;
    }

    public boolean hasEluerLoop(){
        for(int v = 0; v < vs; v++)
            if(graph.degree(v) % 2 == 1)
                return false;

        return cccount == 1;
    }

    public Iterable<Integer> eluerLoop(){
        Stack<Integer> loop = new Stack<>();
        if(!hasEluerLoop()){
            return loop;
        }
        Graph cloned = (Graph) graph.clone();
        eluerLoop(0, cloned, loop);

        return loop;
    }

    private void eluerLoop(int v, Graph cloned, List<Integer> loop){
        if(cloned.degree(v) == 0){
            loop.add(v);
            return;
        }

        while(cloned.degree(v) != 0){
            int w = cloned.adj(v).iterator().next();
            cloned.removeEdge(v, w);
            eluerLoop(w, cloned, loop);
        }
        //v的边删除完后
        loop.add(v);
    }

    public static void main(String[] args) {
        Graph g = new Graph("input/eluer.txt");
        System.out.println((new RecurEluerLoop(g).eluerLoop()));
    }
}