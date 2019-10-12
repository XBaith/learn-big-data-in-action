package graph.weight.minimum;

import graph.weight.representation.WeightedEdge;
import graph.weight.representation.WeightedGraph;

import java.util.ArrayList;

public class Prim {
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

    public Prim(WeightedGraph graph){
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


        for(int i = 1; i < vs; i++){
            //最小横切边
            WeightedEdge minEdge = new WeightedEdge(-1, -1, Integer.MAX_VALUE);

            for(int v = 0; v < vs; v++){
                //从切分的当前部分开始
                if(visited[v]) {
                    //找其他相连的属于不同部分的顶点，找到最小横切边
                    for (int w : graph.adj(v)) {
                        if (!visited[w] && minEdge.getWeight() > graph.getWeight(v, w))
                            minEdge = new WeightedEdge(v, w, graph.getWeight(v, w));
                    }
                }
            }
            mst.add(minEdge);
            visited[minEdge.getFrom()] = visited[minEdge.getTo()] = true;
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
