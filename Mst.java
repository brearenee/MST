
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
        ArrayList<LinkedList<Edge>> adjList = new ArrayList<>();
        Set<Edge> mst = new HashSet<>();

        List<Edge> sortedEdges = getSortedEdges(graphEdges);
        TreeMap<String, Integer> nodeKey = createNodeKey(sortedEdges);

        adjList = createAdjList(sortedEdges, nodeKey);
        mst = mst(adjList);

        for (Edge edge : mst){
            System.out.println(edge.toString());
        }

        int sum = sum(mst);
        System.out.println(sum);
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

    public static boolean createsCycle(Edge edge1, TreeMap<String, Integer> nodeIndex, ArrayList<LinkedList<Edge>> adjList) {
        /*
        While I was able to catch direct cycles, I was having a very hard time implementing indirect cycles so I used
        ChatGPT to help me. I still had to move things around, and run through the algorithm on paper to make sure I understood it,
        but this is my conversation. https://chat.openai.com/c/73ec904b-d607-4a10-8e5c-aedaf803457f
        */

        boolean[] visited = new boolean[adjList.size()];
        java.util.Stack<Integer> stack = new java.util.Stack<>();

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
                    return true;
                }
            }
        }return false;
    }

    private static ArrayList<LinkedList<Edge>> createAdjList(List<Edge> sortedList, TreeMap<String, Integer> nodeKey) {
        int listSize = nodeKey.size();
        ArrayList<LinkedList<Edge>> adjList = new ArrayList<>(listSize);
        TreeSet<String> visitedNodes = new TreeSet<>();
        LinkedList<Edge> list1;
        LinkedList<Edge> list2;

        int sIndex;
        int eIndex;
        boolean createsCycle = false;
        String start;
        String end;

        //initialize LinkedLists inside Adjacency List.
        for (int i = 0; i < listSize; i++) {
            LinkedList<Edge> innerList = new LinkedList<>();
            adjList.add(innerList);
        }

        for (Edge node : sortedList) {
            start = node.start();
            end = node.end();
            list1 = adjList.get(nodeKey.get(start));
            list2 = adjList.get(nodeKey.get(end));

            //if lists are empty, no cycles.
            if (list1.isEmpty() || list2.isEmpty()){
                adjList.get(nodeKey.get(start)).add(node);
                adjList.get(nodeKey.get(end)).add(node);
                createsCycle=true;
            }

            //check for Cycles.
            if (!createsCycle){
                createsCycle = createsCycle(node,nodeKey,adjList);
            }
            //if no cycles exist, add to adj list.
            if (!createsCycle){
                adjList.get(nodeKey.get(start)).add(node);
                adjList.get(nodeKey.get(end)).add(node);
            }
            createsCycle = false;
        }
        return adjList;
    }//endfunction

    private static int sum (Set<Edge> mst){
        int sum=0;
        for (Edge edge: mst){
            int weight = edge.weight;
                sum += edge.weight();
        }
        return sum;
    }

    private static Set<Edge> mst (ArrayList<LinkedList<Edge>> adjList){
        Set<Edge> mst = new HashSet<>();
        for (List<Edge> edges : adjList) {
            mst.addAll(edges);
        }
        return mst;
    }

}//end class
