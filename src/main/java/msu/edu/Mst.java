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


public class Mst{
    static final String DEF_FILE_NAME = "town.txt";
    private String dataFileName;
    private List<Edge> graphEdges;
    private List<List<Edge>> adjacencyList = new ArrayList<>();

    public static void main(String[] args){
        Mst mst = new Mst();
        mst.wrapperFunction("test");
    }

    public Mst(String filename) {
        dataFileName = filename;
    }

    //overloaded constructor with no input.
    public Mst() {
        this(DEF_FILE_NAME);
    }


    private void wrapperFunction(String filename){
        int i=0;
        graphEdges = File2Edges.getEdgesFromFile(dataFileName);
        int size = graphEdges.size();

        System.out.println("");
        System.out.println("Order of Smallest Edges");
        while (i < size){
            Edge smallestEdge = findSmallestEdge();
            System.out.println(smallestEdge.toString());
            i++;
        }

        System.out.println("Final graph List");
        String list = listToString(graphEdges);
        System.out.println(list);


        //seeding my adjacency matrix for testing purposes
        Edge edge1 = new Edge("x","y", 100);
        Edge edge2 = new Edge("x","y", 200);
        Edge edge3 = new Edge("x","y", 300);
        Edge edge4 = new Edge("x","y", 400);
        Edge edge5 = new Edge("x","y", 500);
        List<Edge> listEdges= new ArrayList<>() ;
        listEdges.add(edge1);
        listEdges.add(edge2);
        List<Edge> listEdges2 = new ArrayList<>();
        listEdges2.add(edge3);
        listEdges2.add(edge4);
        listEdges2.add(edge5);
        adjacencyList.add(listEdges);
        adjacencyList.add(listEdges2);

        String adjList = listListtoString(adjacencyList);
        System.out.println(adjList);
    }

    public static void printAdjacenyList( List<List<Edge>> AjList){
        for (List<Edge> edgeList : AjList){
            for (Edge edge : edgeList){
                System.out.print(edge + " ");
            }
            System.out.println(" ");
        }
    }

    //its going to add to the adjacency list
    private boolean addToAdjacencyList(Edge smallestEdge, List<List<Edge>> adjList ){
        List<Edge> list = new ArrayList<>();
        int count=0;

        if (adjacencyList.isEmpty()){
            list.add(smallestEdge);
            adjacencyList.add(list);
            return true;
        }

        for( List<Edge> innerList : adjacencyList){
            if (innerList.get(count).start() == smallestEdge.start()) {
                list = adjacencyList.get(count);
                System.out.println(list);
                list.add(smallestEdge);
                adjacencyList.remove(count);
                adjacencyList.add(list);
                count++;
                break;
            }

        }
        list.add(smallestEdge);
        adjacencyList.add(list);

        return true;

    }

    //find smallest node in the list of edges.
    private Edge findSmallestEdge (){
        Edge smallest = graphEdges.get(0);
        for (Edge edge : graphEdges){
            if ( edge.weight() < smallest.weight()) {
                smallest = edge;
            }
        }
        graphEdges.remove(smallest);
        return  smallest;

    }

    private boolean areEdgesinAjList ( Edge edge, List<List<Edge>> AdjacenyList ){
        String node1 = edge.start();
        String node2 = edge.end();
        boolean counter1 = false;
        boolean counter2 = false;

        if (AdjacenyList.isEmpty()) return false;
        for ( List<Edge> list : AdjacenyList) {
            for (Edge listEdge : list ){
                if (listEdge.start() == node1 || listEdge.start() == node2){
                    counter1 = true;
                }
                if (listEdge.end() == node1 || listEdge.end() == node2){
                    counter2 = true;
                }
            }
            if (counter1 !=true && counter2 != true ){
                return false;
            }
        }
        return true;
    }

    public String listListtoString (List<List<Edge>> list) {
        String innerList="";
        String fullList="";
        for (List<Edge> edgeList : list) {
            for (Edge edge : edgeList){
                innerList = innerList + edge.toString();
            }
            fullList = innerList + "/n";
        }
        return fullList;
    }

    public String listToString(List<Edge> list){
        String stringList ="";
        for ( Edge edge : list ) {
            stringList = stringList + edge.toString() + "/n";
        }
        return stringList;
    }
}
