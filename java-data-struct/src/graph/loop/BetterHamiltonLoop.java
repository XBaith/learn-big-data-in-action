package graph.loop;

import graph.representation.Graph;

import java.util.LinkedList;

public class BetterHamilton {
    private Graph graph;
    private int vs;
    private int[] pre;
    private int end = -1;

    public BetterHamiltonLoop(Graph g){
        graph = g;
        vs = g.getVertexes();
        pre = new int[vs];

        //表示已经遍历过的顶点个数
        int visited = 0;
        dfs(visited, 0, 0, vs);
    }

    private boolean dfs(int visited, int v, int parent, int left) {
        visited += (1 << v);
        pre[v] = parent;
        left--;

        for(int w : graph.adj(v)){
            if((visited & (1 << w)) == 0){
                if(dfs(visited, w, v, left))
                    return true;
            }else if(w == 0 && left == 0){
                end = v;
                return true;
            }
        }
        visited -= (1 << v);
        return false;
    }


    public Iterable<Integer> path(){
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
        BetterHamilton simpleHm = new BetterHamilton(simpleGraph);
        System.out.println(simpleHm.path());

        Graph hmGraph = new Graph("input/hamilton.txt");
        BetterHamilton hamiltonLoop = new BetterHamilton(hmGraph);
        System.out.println(hamiltonLoop.path());
    }

}
