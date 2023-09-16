package graphinput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

/**
 * General utility functions for reading
 * weighted graph as edges from a file.
 * @version Fall 2023 (3)
 */
public class File2Edges {
    /** Hidden constructor for utility class. */
    private File2Edges() { }

    /**
     * Read contents of a file as one edge per line read.
     * @param filename the name of the data file
     * @return a list of edges
     */
    public static List<Edge> getEdgesFromFile(String filename) {
        List<String> edgeStrings = getDataFromFile(filename);
        List<Edge> edges = new ArrayList<Edge>();
        for (String s : edgeStrings) {
            edges.add(parseEdgeString(s));
        }
        return edges;
    }

    /**
     * Read contents of a file into a list of strings,
     * one string per line read.
     *
     * @param  filename  the name of the file to be read in
     * @return    the contents of the named file
     */
    public static List<String> getDataFromFile(String filename) {
        List<String> result = new ArrayList<String>();
        try {
            BufferedReader incoming = new BufferedReader(new FileReader(filename));
            result.add(incoming.readLine());
            while(result.get(result.size() - 1) != null) {
                result.add(incoming.readLine());
            }
        }
        catch (IOException e) {
            System.err.println("I/O Error: " + e);
            System.exit(1);
        }
        result.remove(result.size() - 1);
        return result;
    }

    /**
     * Parse a string into 3 components
     * (startNode, endNode, weight) and
     * construct Edge object.
     *
     * @param  edgeString  the edge information as "start, end, cost"
     * @return  newly constructed Edge
     */
    public static Edge parseEdgeString(String edgeString) {
        String[] parts = edgeString.split(", ", 3);
        return new Edge(parts[0], parts[1], Integer.parseInt(parts[2]));
    }
}