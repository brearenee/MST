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
        int i=1;
        graphEdges = File2Edges.getEdgesFromFile(dataFileName);
        int size = graphEdges.size();

        while (i < size){
            System.out.println("the smallest edge is: ");
            Edge smallestEdge = findSmallestEdge();
            System.out.println(smallestEdge.toString());


            //test if that edge is in the adjacency list
            System.out.println("is this edge in the Adjacency Matrix?:");
            Boolean inAdjList =areEdgesinAjList(smallestEdge, adjacencyList);
            System.out.println(inAdjList);

            if (inAdjList == false){
                addToAdjacencyList(smallestEdge, adjacencyList);
            }
            i++;
        }



        System.out.println("this is new adjacenyList ");
        for(List<Edge> edgeList : adjacencyList){
            for (Edge edge : edgeList){
                System.out.print(edge.toString() + "  ");

            }
            System.out.println(" ");

        }


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
            System.out.println(0001);
            if (innerList.get(count).start() == smallestEdge.start()) {
                System.out.println("1");
                list = adjacencyList.get(count);
                System.out.println(list);
                list.add(smallestEdge);
                System.out.println("3");
                adjacencyList.remove(count);
                System.out.println("4");
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
}
