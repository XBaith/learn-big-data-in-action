package graph.weight.representation;

import graph.representation.Graph;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class WeightedGraph {
    private int vertexes;
    private int edges;
    private TreeMap<Integer, Integer>[] adj;

    public WeightedGraph(String path){
        File file = new File(path);
        try(Scanner scanner = new Scanner(file)){
            vertexes = scanner.nextInt();
            if(vertexes < 0){throw new IllegalArgumentException("vertexs must be non-negative");}
            edges = scanner.nextInt();
            if(edges < 0){throw new IllegalArgumentException("vertexs must be non-negative");}
            adj = new TreeMap[vertexes];
            for(int i = 0 ; i < vertexes ; i++){
                adj[i] = new TreeMap<>();
            }

            for(int i = 0 ; i < edges; i++){
                int from = scanner.nextInt();
                validVertex(from);
                int to = scanner.nextInt();
                validVertex(to);
                int weight = scanner.nextInt();

                //检测自环边
                if(from == to){throw  new IllegalArgumentException("Self-Loop Error");}
                //检测平行边
                if(adj[from].containsKey(to)){throw new IllegalArgumentException("Parallel Edge Error");}

                adj[from].put(to, weight);
                adj[to].put(from, weight);
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
        return (validVertex(from) && validVertex(to)) && adj[from].containsKey(to);
    }

    public int getWeight(int from, int to){
        if(hasEdge(from, to))
            return adj[from].get(to);
        throw new IllegalArgumentException(String.format("Edge %d-%d is not existed", from, to));
    }

    public Iterable<Integer> adj(int v){
        validVertex(v);
        return adj[v].keySet();
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
        WeightedGraph cloned = null;
        try {
            cloned = (WeightedGraph) super.clone();
            cloned.setAdj(new TreeMap[vertexes]);
            for (int v = 0; v < vertexes; v++) {
                cloned.getAdj()[v] = new TreeMap<>();
                for (Map.Entry<Integer, Integer> entry: adj[v].entrySet()) {
                    cloned.getAdj()[v].put(entry.getKey(), entry.getValue());
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

            for(int to : adj[from].keySet()){
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

    public void setAdj(TreeMap<Integer, Integer>[] newAdj){
        this.adj = newAdj;
    }

    public TreeMap<Integer, Integer>[] getAdj(){
        return adj;
    }

}
