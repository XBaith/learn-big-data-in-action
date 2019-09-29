package graph.dfs;

import graph.representation.Graph;

public class BinaryGragh {
    private Graph graph;
    private boolean[] visited;
    private int vs;
    private int[] color;
    private boolean isBipartite = true;

    public BinaryGragh(Graph graph){
        this.graph = graph;
        vs = graph.getVertexes();
        visited = new boolean[vs];
        color = new int[vs];

        for(int v = 0; v < vs; v++){
            if(!visited[v]){
                if(!dfs(v, -1)){
                    isBipartite = false;
                    break;
                }
            }

        }
    }

    private boolean dfs(int v, int c) {
        visited[v] = true;
        //System.out.println(v);
        color[v] = c;
        for(int w : graph.adj(v)){
            if(!visited[w]){
                if(!dfs(w, -c)){
                    return false;
                }
            }else if(color[w] == color[v]){
                return false;
            }
        }
        return true;
    }

    public int[] getColor(){
        return color;
    }

    public boolean isBipartite(){
        return isBipartite;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("input/color.txt");
        BinaryGragh bg = new BinaryGragh(graph);

        for(int c : bg.getColor()) {
            System.out.print(c + " ");
        }
        System.out.println();
        System.out.println(bg.isBipartite());

        Graph graph2 = new Graph("input/non-bipartite");
        BinaryGragh bg2 = new BinaryGragh(graph2);

        for(int c : bg2.getColor()) {
            System.out.print(c + " ");
        }
        System.out.println();
        System.out.println(bg2.isBipartite());
    }
}
