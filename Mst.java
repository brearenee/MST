
import graphinput.Edge;
import graphinput.File2Edges;
import java.util.*;


public class Mst {
    static final String DEF_FILE_NAME = "town.txt";

    public static void main(String[] args) {
        Mst mst = new Mst();
        if (args.length == 1) {
            mst.getMst(args[0]);
        } else {
            mst.getMst(DEF_FILE_NAME);
        }
    }

    public Mst() {
    }

    private static void getMst(String filename) {
        List<Edge> graphEdges = File2Edges.getEdgesFromFile(filename);
        List<Edge> sortedEdges = new ArrayList<>();
        ArrayList<LinkedList<Edge>> adjList = new ArrayList<>();
        sortedEdges = getSortedEdges(graphEdges);
        System.out.println("sorted Edges: ");
        for (Edge edge : sortedEdges) {
            System.out.println(edge.toString());
        }
        TreeMap<String, Integer> nodeKey = createNodeKey(sortedEdges);
        System.out.println("new nodeKey:");

        for (Map.Entry<String, Integer> entry : nodeKey.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
        }
        adjList = createMST(sortedEdges, nodeKey);
        System.out.println("Adjacency List:");
        for (int i = 0; i < adjList.size(); i++) {
            LinkedList<Edge> linkedList = adjList.get(i);

            System.out.println("Linked List " + i + ": " + linkedList);
        }
    }

    private static List<Edge> getSortedEdges(List<Edge> unsortedList) {

        List<Edge> sortedEdges = new ArrayList<>();
        Edge smallest = unsortedList.get(0);
        int size = unsortedList.size();

        int i = 0;
        while (i < size) {
            smallest = unsortedList.get(0);

            for (Edge edge : unsortedList) {
                int edgeWeight = edge.weight();
                int smallestWeight = smallest.weight();
                if (edgeWeight <= smallestWeight) smallest = edge;
            }
            sortedEdges.add(smallest);
            unsortedList.remove(smallest);
            i++;
        }
        return sortedEdges;
    }


    private static TreeMap<String, Integer> createNodeKey(List<Edge> sortedList) {
        TreeMap<String, Integer> keyMap = new TreeMap<>();
        String node;
        int count = 0;
        Integer duplicate;

        for (Edge edge : sortedList) {
            node = edge.start();
            if (!keyMap.containsKey(node)) {
                keyMap.put(node, count++);

            }
            node = edge.end();
            if (!keyMap.containsKey(node)) {
                keyMap.put(node, count++);
            }
        }
        //I wanted node A:0 , B:1, C:2 just for my own sake, so I'm reordering
        count = 0;
        for (Map.Entry<String, Integer> entry : keyMap.entrySet()) {
            String key = entry.getKey();
            keyMap.put(key, count++);
        }
        return keyMap;
    }


    private static ArrayList<LinkedList<Edge>> createMST(List<Edge> sortedList, TreeMap<String, Integer> nodeKey) {
        int size = sortedList.size();
        int listSize = nodeKey.size();
        ArrayList<LinkedList<Edge>> adjList = new ArrayList<>(listSize);
        LinkedList<Edge> list1;
        LinkedList<Edge> list2;
        Edge revEdg;

        //initialize LinkedLists inside Adjacency List.
        for (int i = 0; i < listSize; i++) {
            LinkedList<Edge> innerList = new LinkedList<>();
            adjList.add(innerList);
        }

        int sIndex;
        int eIndex;
        boolean createsCycle = false;


        for (Edge node : sortedList) {
            System.out.println("Sorted Node: " + node);
            // Edge revEdge = new Edge(node.end(), node.start(), node.weight());
            sIndex = nodeKey.get(node.start());
            eIndex = nodeKey.get(node.end());

            list1 = adjList.get(sIndex);
            list2 = adjList.get(eIndex);

            if (list1.isEmpty() || list2.isEmpty()) {
                System.out.println("lists empty");
                adjList.get(sIndex).add(node);
                adjList.get(eIndex).add(node);
                System.out.println("ADDED: " + adjList.get(sIndex));
                System.out.println("ADDED: " + adjList.get(eIndex));
                createsCycle = true;

            }
            else {
                for (Edge edge : list1) {
                    for (Edge edge2 : list2) {
                        if (edge.start().equals(edge2.start()) || edge.start().equals(edge2.end()) ||
                                edge.end().equals(edge2.start()) || edge.end().equals(edge2.end())) {
                            System.out.println("do I ever reach this inside loop?");
                            //System.out.println(node);
                            //System.out.println(edge);
                           // System.out.println(edge2);

                            createsCycle = true;
                        }

                    }
                }
            }
            if (!createsCycle)
            {
                System.out.println("Adding at index");
                adjList.get(sIndex).add(node);
                adjList.get(eIndex).add(node);
                System.out.println("ADDING TO BOTH INDEXS:" + node);
            }
            createsCycle = false;
            //containsEdge2 = false;
        }
        return adjList;
    }//end function



}//end class
