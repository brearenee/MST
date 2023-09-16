
import graphinput.Edge;
import graphinput.File2Edges;
import java.util.List;
import java.util.ArrayList;


public class Mst {
    static final String DEF_FILE_NAME = "town.txt";
    private final String dataFileName;
    private List<Edge> graphEdges;
    private final List<List<Edge>> adjacencyList = new ArrayList<>();
    private List<Edge> sortedEdges = new ArrayList<>();

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
       // System.out.println(getTotalCost(mstList));

    }
    public Mst(String filename) {
        dataFileName = filename;
    }

    public Mst() {
        this(DEF_FILE_NAME);
    }

    private List<List<Edge>> getMST() {
        int i = 0;
        graphEdges = File2Edges.getEdgesFromFile(dataFileName);
        setUpAdjList();
        int size = graphEdges.size();
        getSortedEdges(graphEdges);
        for (Edge edge : sortedEdges){
            System.out.println(edge.toString());
        }

        /*
        while (i < size) {
            Edge smallestEdge = findSmallestEdge();
            if (!isEdgeInAjList(smallestEdge, adjacencyList)){
                addToAdjacencyList(smallestEdge);
            }
            i++;*/
       // List<Edge> testList = new ArrayList<>();
        //testList.add(graphEdges.get(0));
        return adjacencyList;
    }
    //takes in GraphEdges and adds each node to the ADJ list.  Since the graph is unweighted, both orders are added.
    private void setUpAdjList () {
        List<Edge> tempList;
        Edge reverseEdge;
        for (Edge edge : graphEdges) {
            tempList = new ArrayList<>();
            tempList.add(edge);
            adjacencyList.add(tempList);
            tempList.remove(edge);

            //add in it's reverse. treeset so duplicates will not be added.
            reverseEdge = new Edge( edge.end(), edge.start(), edge.weight());
            tempList.add(reverseEdge);
            adjacencyList.add(tempList);


        }
    }

    private void getSortedEdges (List<Edge> unsortedList){
        int i = 0;
        while (i< graphEdges.size()){
            Edge smallestEdge = findSmallestEdge();
            sortedEdges.add(smallestEdge);
            }
            i++;
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


    public static void printAdjList(List<List<Edge>> AjList) {
        for (List<Edge> edgeList : AjList) {
            for (Edge edge : edgeList) {
                System.out.println(edge);
            }
        }
    }

    /*
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



    private boolean isEdgeInAjList(Edge edge, List<List<Edge>> adjacenyList) {
        int start = 0;
        int end = 0;


        for (List<Edge> listEdge : adjacenyList) {
            for (Edge node : listEdge) {
                if (node.start().equals(edge.start())) start++;
                if (node.end().equals(edge.end())) end++;
                if (start > 0 && end > 0) return true;
            }
        }
        return false;
    }

    public static int getTotalCost(List<List<Edge>> mstList){
        int sum=0;
        for (List<Edge> innerList: mstList){
            for( Edge edge : innerList){
                sum+=edge.weight();
            }
        }
        return sum;
    }

         */
}

