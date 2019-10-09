package graph.loop.euler;

import graph.representation.Graph;

import java.util.Stack;

public class EluerLoop {
    private Graph graph;
    private int vs;
    private boolean[] visited;
    private int cccount;

    public EluerLoop(Graph graph){
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

    public Iterable<Integer> eluerLoop() {
        Stack<Integer> loop = new Stack<>();
        if(!hasEluerLoop())
            return loop;

        Stack<Integer> curPath = new Stack<>();
        Graph cloned = (Graph) graph.clone();

        int cur = 0;
        curPath.push(cur);
        while(!curPath.isEmpty()) {
            if(cloned.degree(cur) != 0){
                curPath.push(cur);
                int w = cloned.adj(cur).iterator().next();
                cloned.removeEdge(cur, w);
                cur = w;
            }else{
                loop.push(cur);
                cur = curPath.pop();
            }
        }

        return loop;
    }

    public static void main(String[] args) {
        Graph g = new Graph("input/eluer.txt");
        System.out.println((new EluerLoop(g).eluerLoop()));
    }
}
