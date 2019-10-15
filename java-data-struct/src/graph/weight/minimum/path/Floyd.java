package graph.weight.minimum.path;

import graph.weight.representation.WeightedGraph;

import java.util.Arrays;

public class Floyd {
    private WeightedGraph graph;
    private int[][] dis;
    private boolean hasNegativeCycle = false;

    public Floyd(WeightedGraph graph){
        this.graph = graph;
        dis = new int[graph.getVertexes()][graph.getVertexes()];
        //初始化dis
        for(int i = 0; i < graph.getVertexes(); i++)
            Arrays.fill(dis[i], Integer.MAX_VALUE);

        for(int v = 0; v < graph.getVertexes(); v++){
            dis[v][v] = 0;
            for(int w : graph.adj(v))
                dis[v][w] = graph.getWeight(v, w);
        }

        //更新所有点对的最短路径距离
        floyd();
    }

    private void floyd() {

        for(int t = 0; t < graph.getVertexes(); t++) {
            for(int v = 0; v < graph.getVertexes(); v++)
                for(int w = 0; w < graph.getVertexes(); w++)
                    if(dis[v][t] != Integer.MAX_VALUE && dis[t][w] != Integer.MAX_VALUE
                            &&dis[v][t] + dis[t][w] < dis[v][w])
                        dis[v][w] = dis[v][t] + dis[t][w];
        }

    }
}
