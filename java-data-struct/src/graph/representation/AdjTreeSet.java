package graph.representation;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AdjTreeSet implements Cloneable{
    private int vertexes;
    private int edges;
    private TreeSet<Integer>[] adj;

    public AdjTreeSet(String path){
        File file = new File(path);
        try(Scanner scanner = new Scanner(file)){
            vertexes = scanner.nextInt();
            if(vertexes < 0){throw new IllegalArgumentException("vertexs must be non-negative");}
            edges = scanner.nextInt();
            if(edges < 0){throw new IllegalArgumentException("vertexs must be non-negative");}
            adj = new TreeSet[vertexes];
            for(int i = 0 ; i < vertexes ; i++){
                adj[i] = new TreeSet<>();
            }

            for(int i = 0 ; i < edges; i++){
                int from = scanner.nextInt();
                validVertex(from);
                int to = scanner.nextInt();
                validVertex(to);
                if(from == to){throw  new IllegalArgumentException("Self-Loop Error");}
                if(adj[from].contains(to)){throw new IllegalArgumentException("Parallel Edge Error");}

                adj[from].add(to);
                adj[to].add(from);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean validVertex(int v) {
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
        return (validVertex(from) && validVertex(to)) && adj[from].contains(to);
    }

    public Iterable<Integer> adj(int v){
        validVertex(v);
        return adj[v];
    }

    public int degree(int v){
        validVertex(v);
        return adj[v].size();
    }

    public void removeEdge(int from, int to){
        validVertex(from);
        validVertex(to);
        adj[from].remove(to);
        adj[to].remove(from);
    }

    @Override
    public Object clone() {
        Graph cloned = null;
        try {
            cloned = (Graph) super.clone();
            cloned.setAdj(new TreeSet[vertexes]);
            for (int v = 0; v < vertexes; v++) {
                cloned.getAdj()[v] = new TreeSet<>();
                for (int w : adj(v)) {
                    cloned.getAdj()[v].add(w);
                }
            }

        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return cloned;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Vertexes: %d, Edges: %d\n", vertexes, edges));
        for(int from = 0; from < vertexes; from++){
            sb.append(from + " -> ");

            int vs = adj[from].size();
            int count = 1;

            for(int to : adj[from]){
                if(count++ == vs){
                    sb.append(to);
                }else {
                    sb.append(to + ",");
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public void setAdj(TreeSet[] newAdj){
        this.adj = newAdj;
    }

    public TreeSet<Integer>[] getAdj(){
        return adj;
    }

}
