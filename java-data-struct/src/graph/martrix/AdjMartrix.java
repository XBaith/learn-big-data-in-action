package graph.martrix;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AdjMartrix {
    private int vertixs;
    private int edges;
    private int[][] adj;
    public AdjMartrix(String path){
        File file = new File(path);
        try(Scanner scanner = new Scanner(file)){
            vertixs = scanner.nextInt();
            edges = scanner.nextInt();
            adj = new int[vertixs][vertixs];

            for(int i = 0 ; i < edges; i++){
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                adj[from][to] = 1;
                adj[to][from] = 1;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Vertixs: %d, Edges: %d\n", vertixs, edges));
        for(int i = 0 ; i < vertixs; i++){
            for(int j = 0 ; j < vertixs; j++){
                sb.append(adj[i][j] + " ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AdjMartrix martrix = new AdjMartrix("input/adjMartrix.txt");
        System.out.println(martrix);
    }
}
