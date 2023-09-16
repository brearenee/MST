package msu.edu;
import msu.edu.graphinput.*;
import java.util.List;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;


public class Mst {
    static final String DEF_FILE_NAME = "town.txt";
    private String dataFileName;
    private List<Edge> graphEdges;
    private List<List<Edge>> adjacencyList = new ArrayList<>();

    public static void main(String[] args) {
        Mst mst = new Mst();
        mst.getAdjacenyMatrix(DEF_FILE_NAME);
    }

    public Mst(String filename) {
        dataFileName = filename;
    }

    //overloaded constructor with no input.
    public Mst() {
        this(DEF_FILE_NAME);
    }


    private void getAdjacenyMatrix(String filename) {
        int i = 0;
        graphEdges = File2Edges.getEdgesFromFile(dataFileName);
        int size = graphEdges.size();

        while (i < size) {
            Edge smallestEdge = findSmallestEdge();
            System.out.println(smallestEdge.toString());
            System.out.println(isEdgeinAjList(smallestEdge, adjacencyList));
            if (!isEdgeinAjList(smallestEdge, adjacencyList)){
                addToAdjacencyList(smallestEdge, adjacencyList);
            }
            i++;
        }
        printAdjList(adjacencyList);
    }

    public static void printAdjList(List<List<Edge>> AjList) {
        for (List<Edge> edgeList : AjList) {
            for (Edge edge : edgeList) {
                System.out.println(edge);
            }
        }
    }

    private boolean addToAdjacencyList(Edge smallestEdge, List<List<Edge>> adjList) {
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

    private boolean isEdgeinAjList(Edge edge, List<List<Edge>> AdjacenyList) {
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