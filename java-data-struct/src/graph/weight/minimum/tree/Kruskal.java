package graph.weight.minimum.tree;

import graph.weight.representation.WeightedEdge;
import graph.weight.representation.WeightedGraph;
import tree.union.UnionFind;
import tree.union.UnionFindV6;

import java.util.ArrayList;
import java.util.Collections;

public class Kruskal {
    private WeightedGraph graph;
    private int vs;
    private boolean[] visited;
    //连通分量
    private int cccount = 0;
    //图中所有边的集合
    private ArrayList<WeightedEdge> edges;
    //最小生成树的边集合
    private ArrayList<WeightedEdge> mst;

    public Kruskal(WeightedGraph graph){
        this.graph = graph;
        vs = graph.getVertexes();
        visited = new boolean[vs];
        edges = new ArrayList<>();
        mst = new ArrayList<>();

        for(int v = 0; v < vs; v++) {
            if(!visited[v]) {
                dfs(0);
                cccount++;
            }
        }

        //检测连通性
        if(cccount > 1)
            throw new RuntimeException("Graph is not connected");
        //将图中的所有图根据权重升序排序
        Collections.sort(edges);

        UnionFind uf = new UnionFindV6(vs);

        for(WeightedEdge edge : edges){
            int v = edge.getFrom();
            int w = edge.getTo();
            if(!uf.isConnected(v, w)){
                mst.add(edge);
                uf.union(v, w);
            }
        }
    }

    private void dfs(int v){
        visited[v] = true;

        for(int w : graph.adj(v)){
            if(!visited[w])
                dfs(w);
            //只添加一次边
            if(v < w)
                edges.add(new WeightedEdge(v, w, graph.getWeight(v, w)));
        }
    }

    public Iterable<WeightedEdge> minSpanTree(){
        return mst;
    }
}
