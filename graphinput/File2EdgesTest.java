package graphinput;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The test class for File2Edges.
 *
 * @author  Dr. Jody Paul
 * @version Fall 2023 (1)
 */
public class File2EdgesTest {
    @Test
    public void parseDataFileGraph1() {
        List<Edge> edges = File2Edges.getEdgesFromFile("graph1.txt");
        assertEquals("Building A", edges.get(0).start());
        assertEquals("Building B", edges.get(0).end());
        assertEquals(5, edges.get(0).weight());
        assertEquals("Building A", edges.get(1).start());
        assertEquals("Building C", edges.get(1).end());
        assertEquals(4, edges.get(1).weight());
        assertEquals("Building E", edges.get(4).start());
        assertEquals("Building A", edges.get(4).end());
        assertEquals(2, edges.get(4).weight());
    }

    @Test
    public void parseDataFileMuddyTown() {
        List<Edge> edges = File2Edges.getEdgesFromFile("muddytown.txt");
        assertEquals(20, edges.size());
        assertEquals("House A", edges.get(0).start());
        assertEquals("House B", edges.get(0).end());
        assertEquals(5, edges.get(0).weight());
        assertEquals("House A", edges.get(2).start());
        assertEquals("House E", edges.get(2).end());
        assertEquals(4, edges.get(2).weight());
        assertEquals("House C", edges.get(19).start());
        assertEquals("House B", edges.get(19).end());
        assertEquals(3, edges.get(19).weight());
    }

    /**
     * Utility to create a file for writing data.
     * @param filename the name of the file to create
     */
    private static void createTestDataFile(String filename) {
        try {
            File datafile = new File(filename);
            if (datafile.createNewFile()) {
                // System.out.println("Test data file created: " + datafile.getName());
            } else {
                // System.out.println("Using existing test data file: " + filename);
            }
        } catch (IOException e) {
            System.out.println("Error creating test data file." + filename);
            e.printStackTrace();
        }
    }

    /**
     * Utility to write data to a file.
     * @param filename the name of the file for writing
     * @param data the data to be written
     */
    private static void writeDataToFile(String filename, String data) {
        try {
            FileWriter fwriter = new FileWriter(filename);
            fwriter.write(data);
            fwriter.close();
            // System.out.println("Successfully wrote data to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing test data to file: " + filename);
            e.printStackTrace();
        }
    }

    /**
     * Constructor establishes test data files.
     */
    public File2EdgesTest() {
        createTestDataFile("graph1.txt");
        writeDataToFile("graph1.txt", """
        Building A, Building B, 5
        Building A, Building C, 4
        Building B, Building D, 3
        Building C, Building D, 4
        Building E, Building A, 2""");
        createTestDataFile("muddytown.txt");
        writeDataToFile("muddytown.txt", """
        House A, House B, 5
        House A, House C, 3
        House A, House E, 4
        House E, House I, 2
        House E, House G, 4
        House E, House C, 5
        House I, House G, 3
        House I, House J, 3
        House J, House G, 2
        House J, House F, 3
        House J, House H, 4
        House F, House G, 4
        House F, House C, 3
        House F, House B, 2
        House F, House H, 3
        House B, House D, 3
        House H, House D, 2
        House H, House B, 4
        House G, House C, 4
        House C, House B, 3""");
    }
}
