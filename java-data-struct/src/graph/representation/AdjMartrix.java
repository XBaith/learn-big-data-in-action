package graph.representation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdjMartrix {
    private int vertexes;
    private int edges;
    private int[][] adj;

    public AdjMartrix(String path){
        File file = new File(path);
        try(Scanner scanner = new Scanner(file)){
            vertexes = scanner.nextInt();
            if(vertexes < 0){throw new IllegalArgumentException("vertexs must be non-negative");}
            edges = scanner.nextInt();
            if(edges < 0){throw new IllegalArgumentException("vertexs must be non-negative");}
            adj = new int[vertexes][vertexes];

            for(int i = 0 ; i < edges; i++){
                int from = scanner.nextInt();
                validVertex(from);
                int to = scanner.nextInt();
                validVertex(to);
                if(from == to){throw  new IllegalArgumentException("Self-Loop Error");}
                if(adj[from][to] == 1){throw new IllegalArgumentException("Parallel Edge Error");}
                adj[from][to] = 1;
                adj[to][from] = 1;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean validVertex(int v) {
        if(v < 0 || v >= vertexes){
            throw new IllegalArgumentException("new Vertex@ " + v + "is invalid");
        }
        return true;
    }

    public int getVertexes(){
        return vertexes;
    }

    public int getEdges(){
        return edges;
    }

    public boolean hasEdge(int from, int to){
        return adj[from][to] == 1 && (validVertex(from) && validVertex(to));
    }

    public ArrayList<Integer> adj(int v){
        ArrayList<Integer> adjs = null;
        if(validVertex(v)) {
            adjs = new ArrayList<>();
            for (int i = 0; i < vertexes; i++) {
                if (adj[v][i] == 1) {
                    adjs.add(i);
                }
            }
        }
        return adjs;
    }

    public int degree(int v){
        return adj(v).size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Vertexes: %d, Edges: %d\n", vertexes, edges));
        for(int i = 0; i < vertexes; i++){
            for(int j = 0; j < vertexes; j++){
                sb.append(adj[i][j] + " ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AdjMartrix martrix = new AdjMartrix("input/g.txt");
        System.out.println(martrix);
    }
}
