package graph.dfs;

import graph.representation.Graph;

public class BinaryGragh {
    private Graph graph;
    private boolean[] visited;
    private int vs;
    private int[] color;

    private static final int GREEN = -1;
    private static final int BULE = 1;

    public BinaryGragh(Graph graph){
        this.graph = graph;
        vs = graph.getVertexes();
        visited = new boolean[vs];
        color = new int[vs];

//        for(int i = 0; i < vs; i++){
//            color[i] = -1;
//        }
        color[0] = GREEN;
        for(int v = 0; v < vs; v++){
            if(!visited[v]){
                dfs(v, color[v]);
            }

        }
    }

    private void dfs(int v, int c) {
        visited[v] = true;
        System.out.println(v);
        color[v] = c;
        for(int w : graph.adj(v)){
            if(!visited[w]){
                dfs(w, -c);
            }
        }
    }

    public int[] getColor(){
        return color;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("input/color.txt");
        BinaryGragh bg = new BinaryGragh(graph);

        for(int c : bg.getColor()) {
            System.out.print(c + " ");
        }
    }
}
