package graph.loop.hamilton;

import graph.representation.Graph;

import java.util.LinkedList;

public class HamiltonLoop {
    private Graph graph;
    private boolean[] visited;
    private int vs;
    private int[] pre;
    private int end = -1;

    public HamiltonLoop(Graph g){
        graph = g;
        vs = g.getVertexes();
        visited = new boolean[vs];
        pre = new int[vs];

        dfs(0, 0, vs);
    }

    private boolean dfs(int v, int parent, int left) {
        visited[v] = true;
        pre[v] = parent;
        left--;

        for(int w : graph.adj(v)){
            if(!visited[w]){
                if(dfs(w, v, left))
                    return true;
            }else if(w == 0 && left == 0){
                end = v;
                return true;
            }
        }
        visited[v] = false;
        return false;
    }

    private boolean allVisited(){
        for(boolean v : visited){
            if(!v)
                return false;
        }
        return true;
    }

    public Iterable<Integer> hamiltonLoop(){
        LinkedList<Integer> ret = new LinkedList<>();
        if(end == -1) return ret;

        int cur = end;
        while(cur != 0){
            ret.addFirst(cur);
            cur = pre[cur];
        }
        ret.addFirst(0);

        return ret;
    }

    public static void main(String[] args) {
        Graph simpleGraph = new Graph("input/hamiltonloop");
        HamiltonLoop simpleHm = new HamiltonLoop(simpleGraph);
        System.out.println(simpleHm.hamiltonLoop());

        Graph hmGraph = new Graph("input/hamilton.txt");
        HamiltonLoop hamiltonLoop = new HamiltonLoop(hmGraph);
        System.out.println(hamiltonLoop.hamiltonLoop());
    }

}
