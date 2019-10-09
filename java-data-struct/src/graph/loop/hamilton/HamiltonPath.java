package graph.loop;

import graph.representation.Graph;

import java.util.LinkedList;

public class HamiltonPath {
    private Graph graph;
    private boolean[] visited;
    private int vs;
    private int[] pre;
    private int end = -1;
    private int source;

    public HamiltonPath(Graph g, int source){
        graph = g;
        vs = g.getVertexes();
        visited = new boolean[vs];
        pre = new int[vs];
        this.source = source;

        dfs(source, source, vs);
    }

    private boolean dfs(int v, int parent, int left) {
        visited[v] = true;
        pre[v] = parent;
        left--;
        if(left == 0){
            end = v;
            return true;
        }

        for(int w : graph.adj(v))
            if(!visited[w])
                if(dfs(w, v, left))
                    return true;

        visited[v] = false;
        return false;
    }

    public Iterable<Integer> path(){
        LinkedList<Integer> ret = new LinkedList<>();
        if(end == -1) return ret;

        int cur = end;
        while(cur != source){
            ret.addFirst(cur);
            cur = pre[cur];
        }
        ret.addFirst(source);

        return ret;
    }

    public static void main(String[] args) {
        Graph simpleGraph = new Graph("input/simple-hamilton.txt");
        HamiltonPath simpleHm = new HamiltonPath(simpleGraph,0);
        System.out.println(simpleHm.path());

        HamiltonPath hm = new HamiltonPath(simpleGraph,1);
        System.out.println(hm.path());
    }

}

