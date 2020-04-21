package graph.weight.minimum.tree;

import graph.weight.representation.WeightedEdge;
import graph.weight.representation.WeightedGraph;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class PQPrim {
    private WeightedGraph graph;
    private int vs;
    /**
     * 1.顶点是否遍历过
     * 2.避免形成环
     * 3.划分不同的切分
     */
    private boolean[] visited;
    private int cccount = 0;

    private ArrayList<WeightedEdge> mst;

    public PQPrim(WeightedGraph graph){
        this.graph = graph;
        vs = graph.getVertexes();
        visited = new boolean[vs];
        mst = new ArrayList<>();

        for(int v = 0; v < vs; v++){
            if(!visited[v]){
                dfs(v);
                cccount++;
            }
        }

        if(cccount > 1)
            throw new RuntimeException("graph is not connected");

        prim();
    }

    private void prim() {
        //初始化visited为Prim遍历做准备
        for(int v = 0; v < vs; v++)
            visited[v] = false;

        //Prim
        visited[0] = true;
        PriorityQueue<WeightedEdge> pq = new PriorityQueue<>();
        //初始化把其实顶点所有的边放入待处理的横切边队列
        for(int w : graph.adj(0))
            pq.add(new WeightedEdge(0, w, graph.getWeight(0 ,w)));

        //所有横切边都处理完后退出
        while(!pq.isEmpty()){
            WeightedEdge minEdge = pq.poll();
            //检查是否形成环
            if(visited[minEdge.getFrom()] && visited[minEdge.getTo()])
                continue;

            mst.add(minEdge);
            //新顶点遍历其横切边
            int newv = visited[minEdge.getFrom()] == false ? minEdge.getFrom() : minEdge.getTo();
            for(int w : graph.adj(newv)){
                if(!visited[w]) {
                    pq.add(new WeightedEdge(newv, w, graph.getWeight(newv, w)));
                }
            }

            visited[newv] = true;
        }

    }

    private void dfs(int v) {
        visited[v] = true;

        for(int w : graph.adj(v)){
            if(!visited[w]){
                dfs(w);
            }
        }
    }

    public Iterable<WeightedEdge> minSpanTree(){
        return mst;
    }
}