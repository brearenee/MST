package graphinput;
import java.util.List;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Set;
import java.util.TreeSet;

/**
 * Demonstration of the use of File2Edges.
 *
 * @author Dr. Jody Paul
 * @version Fall 2023 (1)
 */
public class DemoMain {
    /** Demo graph data file. */
    private String dataFileName = "demograph.txt";
    /** Edges of graph. */
    private List<Edge> graphEdges;

    /**
     * Demonstration driver program
     * reads demo data and displays
     * the names of nodes.
     *
     * @param  args  ignored
     */
    public static void main(String[] args) {
        DemoMain demo = new DemoMain();
        // Read in list of edges.
        demo.graphEdges = File2Edges.getEdgesFromFile(demo.dataFileName);
        // Use a set to collect node names.
        Set<String> nodes = new TreeSet<String>();
        for (Edge edge : demo.graphEdges) {
            nodes.add(edge.start());
            nodes.add(edge.end());
        }
        // Display all nodes by name.
        System.out.println("Graph Nodes:");
        nodes.forEach(System.out::println);
    }

    /**
     * Constructor initializes instance variables
     * and ensures demonstration data file exists.
     */
    public DemoMain() {
        // Initialize empty list of edges.
        this.graphEdges = new LinkedList<Edge>();
        // Establish demo data file.
        try {
            File datafile = new File(dataFileName);
            datafile.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating test data file." + dataFileName);
            e.printStackTrace();
        }
        try {
            FileWriter fwriter = new FileWriter(dataFileName);
            fwriter.write("""
                Demo Node 1, Demo Node 22, 15
                Demo Node X, Demo Node 4, 14
                Demo Node 22, Demo Node 4, 13
                Demo Node 1, Demo Node X, 14
                Demo Node 5, Demo Node 1, 12""");
            fwriter.close();
        } catch (IOException e) {
            System.out.println("Error writing test data to file: " + dataFileName);
            e.printStackTrace();
        }
    }
}
