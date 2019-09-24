package graph.dfs;

import graph.representation.Graph;

import java.util.ArrayList;
import java.util.Stack;

public class GraphDFS {
    //前序遍历
    private ArrayList<Integer> preOrder = new ArrayList<>();
    //后序遍历
    private ArrayList<Integer> postOrder = new ArrayList<>();
    private int[] visited;
    private Graph graph;
    //联通分量
    private int cccount = 0;
    //图的顶点总数
    private int vs;

    public GraphDFS(Graph graph){
        this.graph = graph;
        vs = graph.getVertexes();
        visited = new int[vs];
        for(int i = 0; i < vs; i++){
            visited[i] = -1;
        }
        //保证遍历到不连通图的所有节点
        for(int v = 0 ; v < vs; v++){
            if(visited[v] == -1){
                preDfs(v, cccount);
                cccount++;
            }
        }
    }

    //前序dfs
    private void preDfs(int v, int ccid) {
        visited[v] = ccid;
        preOrder.add(v);
        for(int w : graph.adj(v)){
            if(visited[w] == -1){
                preDfs(w, ccid);
            }
        }
    }

    //前序非递归dfs
    private void nonRecursivePreOrderDfs(int v, int ccid){
        Stack<Integer> stack = new Stack<>();
        visited[v] = ccid;
        stack.push(v);
        while(!stack.isEmpty()){
            int vertex = stack.pop();
            preOrder.add(vertex);
            for(int w : graph.adj(vertex)){
                if(visited[w] == -1){
                    stack.push(w);
                    visited[w] = ccid;
                }
            }
        }
    }

    //后序dfs
    private void postDfs(){
        clean();
        for(int v = 0 ; v < graph.getVertexes() ; v++){
            if(visited[v] == -1){
                postDfs(v, cccount);
                cccount++;
            }
        }
    }

    private void postDfs(int v, int ccid) {
        visited[v] = ccid;
        for(int w : graph.adj(v)){
            if(visited[w] == -1){
                postDfs(w, ccid);
            }
        }
        postOrder.add(v);
    }

    public Iterable<Integer> getPreOrder(){
        return preOrder;
    }

    public Iterable<Integer> getPostOrder(){
        postDfs();
        return postOrder;
    }

    //清理visited
    private void clean(){
        visited = new int[vs];
    }

    //联通分量
    public int connectedComponents(){
        for(int e : visited)
            System.out.print(e + " ");
        System.out.println();
        return cccount;
    }

    //查看传入的两个顶点是否连通
    public boolean isConnected(int from, int to){
        return graph.validVertex(from) && graph.validVertex(to) && visited[from] == visited[to];
    }

    //求出图的联通分量分别包含哪些顶点
    public ArrayList<Integer>[] components(){
        ArrayList<Integer>[] cpts = new ArrayList[cccount];
        for(int i = 0; i < cccount; i++){
            cpts[i] = new ArrayList<>();
        }

        for(int ccid = 0; ccid < cccount; ccid++){
            for(int j = 0; j < visited.length; j++){
                if(visited[j] == ccid){
                    cpts[ccid].add(j);
                }
            }
        }
        return cpts;
    }

    public static void main(String[] args) {
        GraphDFS dfs = new GraphDFS(new Graph("input/g.txt"));
        System.out.println(dfs.getPreOrder());
        //[0, 2, 6, 3, 1, 4, 5]
        //[0, 1, 3, 2, 6, 4, 5]
        //System.out.println(dfs.getPostOrder());

        System.out.println(dfs.connectedComponents());
        ArrayList<Integer>[] cpts = dfs.components();
        for(int ccid = 0; ccid < cpts.length; ccid++){
            System.out.print("ccid " + ccid + ": ");
            for(int v : cpts[ccid]){
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }
}
