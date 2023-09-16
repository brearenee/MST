package msu.edu;
import msu.edu.graphinput.*;
import java.util.List;
import java.util.ArrayList;


public class Mst {
    static final String DEF_FILE_NAME = "town.txt";
    private final String dataFileName;
    private List<Edge> graphEdges;
    private final List<List<Edge>> adjacencyList = new ArrayList<>();

    public static void main(String[] args) {
        List<List<Edge>> mstList;
        Mst mst;

        if (args.length==1){
           mst = new Mst(args[0]);
        }
        else{
            mst = new Mst(DEF_FILE_NAME);

        }
        mstList = mst.getMST();
        printAdjList(mstList);

    }

    public Mst(String filename) {
        dataFileName = filename;
    }

    //overloaded constructor with no input.
    public Mst() {
        this(DEF_FILE_NAME);
    }


    private List<List<Edge>> getMST() {
        int i = 0;
        graphEdges = File2Edges.getEdgesFromFile(dataFileName);
        int size = graphEdges.size();
        while (i < size) {
            Edge smallestEdge = findSmallestEdge();
            if (!isEdgeInAjList(smallestEdge, adjacencyList)){
                addToAdjacencyList(smallestEdge);
            }
            i++;
        }
        return adjacencyList;
    }

    public static void printAdjList(List<List<Edge>> AjList) {
        for (List<Edge> edgeList : AjList) {
            for (Edge edge : edgeList) {
                System.out.println(edge);
            }
        }
    }

    private boolean addToAdjacencyList(Edge smallestEdge) {
        List<Edge> list = new ArrayList<>();
        int count = 0;


        if (adjacencyList.isEmpty()) {
            list.add(smallestEdge);
            adjacencyList.add(list);
            return true;
        }

        for (List<Edge> innerList : adjacencyList) {
            if (innerList.get(0).start().equals(smallestEdge.start())) {
                adjacencyList.get(count).add(smallestEdge);
                return true;
            } else count++;
        }

        list.add(smallestEdge);
        adjacencyList.add(list);
        return true;
    }

    private Edge findSmallestEdge() {
        Edge smallest = graphEdges.get(0);
        for (Edge edge : graphEdges) {
            if (edge.weight() < smallest.weight()) {
                smallest = edge;
            }
        }
        graphEdges.remove(smallest);
        return smallest;

    }

    private boolean isEdgeInAjList(Edge edge, List<List<Edge>> AdjacenyList) {
        int start = 0;
        int end = 0;


        for (List<Edge> listEdge : AdjacenyList) {
            for (Edge node : listEdge) {
                if (node.start().equals(edge.start())) start++;
                if (node.end().equals(edge.end())) end++;
                if (start > 0 && end > 0) return true;
            }
        }
        return false;
    }

}