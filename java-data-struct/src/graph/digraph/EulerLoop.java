package graph.digraph;

import graph.representation.Graph;

public class EulerLoop {
    private Graph G;

    public EulerLoop(Graph G){
        this.G = G;
    }

    private boolean hasEulerLoop(){

        DirectedCycleDetection cc = new DirectedCycleDetection(G);
        if(cc.count() > 1) return false;

        for(int v = 0; v < G.getVertexes(); v ++)
            if(G.degree(v) % 2 == 1) return false;
        return true;
    }
}
