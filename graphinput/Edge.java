package graphinput;
/**
 * Represents an edge within a graph.
 * Represents directed and weighted edges.
 * To accommodate undirected edges, assume start and end
 * nodes are simply endpoints of an undirected edge.
 * To accommodate unweighted edges, ignore the weight
 * or set all weights to 1.
 * @version Fall 2023 (2)
 * @author Dr. Jody Paul
 */
public class Edge <Edge>{
    public String startNode;
    public String endNode;
    public int weight;
    public Edge(String start, String end, int wt) {
        this.startNode = start;
        this.endNode = end;
        this.weight = wt;
    }

    public String start() { return this.startNode; }

    public String end() { return this.endNode; }

    public int weight() { return this.weight; }

    @Override
    public String toString() {
        return this.startNode + ", "
        + this.endNode + ", "
        + this.weight;
    }
}