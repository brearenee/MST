
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
    //helped function to tell if two edges share any nodes
    private static boolean doNodesCreateCycle (Edge edge1, Edge edge2){
        if (edge1.start().equals(edge2.start()) || edge1.start().equals(edge2.end()) ||
                edge1.end().equals(edge2.start()) || edge1.end().equals(edge2.end())) {
            System.out.println("Cycle Created.");
            System.out.println("should NOT have been added. ");
            return true;
        }
        else return false;
    }

    public static boolean createsIndirectCycle(Edge edge1, TreeMap<String, Integer> nodeIndex, ArrayList<LinkedList<Edge>> adjList) {
        // Perform a DFS to check if adding (u, v) creates an indirect cycle
        boolean[] visited = new boolean[adjList.size()];
        java.util.Stack<Integer> stack = new java.util.Stack<>();

        // Start DFS from vertex u
        int edge1Index = nodeIndex.get(edge1.start());
        stack.push(edge1Index);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            visited[current] = true;

            for (Edge edge : adjList.get(current)) {
                int start = nodeIndex.get(edge.start());
                int end = nodeIndex.get(edge.end());

                int neighbor = (nodeIndex.get(edge.start()) == current) ? nodeIndex.get(edge.end()) : nodeIndex.get(edge.start());

                if (!visited[neighbor]) {
                    stack.push(neighbor);
                } else if (neighbor != nodeIndex.get(edge1.start())) {
                    // If the neighbor is visited and not the source vertex, then it's an indirect cycle
                    return true;
                }


            }


        }return false;
    }

    private static ArrayList<LinkedList<Edge>> createMST(List<Edge> sortedList, TreeMap<String, Integer> nodeKey) {
        int size = sortedList.size();
        int listSize = nodeKey.size();
        ArrayList<LinkedList<Edge>> adjList = new ArrayList<>(listSize);
        int sIndex;
        int eIndex;
        boolean createsCycle = false;
        TreeSet<String> visitedNodes = new TreeSet<>();
        LinkedList<Edge> list1;
        LinkedList<Edge> list2;
        String start;
        String end;

        //initialize LinkedLists inside Adjacency List.
        for (int i = 0; i < listSize; i++) {
            LinkedList<Edge> innerList = new LinkedList<>();
            adjList.add(innerList);
        }


        //creates direct cycle?




        for (Edge node : sortedList) {
            System.out.println("Starting Edge: " + node);
            start = node.start();
            end = node.end();
            list1 = adjList.get(nodeKey.get(start));
            list2 = adjList.get(nodeKey.get(end));

            //if lists are empty, no cycles.
            if (list1.isEmpty() || list2.isEmpty()){
                System.out.println("Empty List, so Adding Node to both lists. : ");
                adjList.get(nodeKey.get(start)).add(node);
                adjList.get(nodeKey.get(end)).add(node);
                createsCycle=true;
            }
            //check for direct cycles
            if (!createsCycle) {
                for (Edge node1 : list1) {
                    for (Edge node2 : list2) {
                        System.out.println("checking for direct Cycles: ");
                        createsCycle = doNodesCreateCycle(node1, node2);
                        System.out.println(createsCycle);
                    }
                }
            }
            //check for indirect Cycles.
            if (!createsCycle){
                System.out.println("checking for indirect Cycles: ");
                createsCycle = createsIndirectCycle(node,nodeKey,adjList);
                System.out.println(createsCycle);
            }
            //if no cycles exist, add to adj list.
            if (!createsCycle){
                System.out.println("No cycles, so adding: ");
                adjList.get(nodeKey.get(start)).add(node);
                adjList.get(nodeKey.get(end)).add(node);
            }

            createsCycle = false;


        }




        return adjList;
    }//endfunction
}//end class
