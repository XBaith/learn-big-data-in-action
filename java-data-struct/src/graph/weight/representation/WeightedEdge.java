package graph.weight.representation;

public class WeightedEdge implements Comparable<WeightedEdge>{
    private int from, to, weight;

    public WeightedEdge(int from, int to, int weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int getFrom(){return from;}

    public int getTo(){return to;}

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(WeightedEdge another){
        return weight - another.weight;
    }

    @Override
    public String toString(){
        return String.format("(%d-%d: %d)", from, to, weight);
    }
}
