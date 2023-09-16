PROJECT TITLE: GraphInput - Graph Input Utility
PURPOSE OF PROJECT: Facilitate reading graph data from a file
VERSION: Fall 2023 (1)
AUTHOR: Dr. Jody Paul

DESCRIPTION:
Utility reads in text data for a weighted graph described by its edges and presents it as a list of edges.
Each line of the text represents a single edge.
Each edge in the data file is expected to be represented by a comma-delimited triple:
NameOfNode1, NameOfNode2, Weight
Can be used for directed and undirected graphs as subject to interpretation of the data.
This utility does not address graphs that contain isolated nodes (nodes of degree 0).

USAGE:
* API GENERATION
- Use javadoc to generate API for utility class File2Edges
javadoc -d docs File2Edges.java
xdg-open docs/index.html

* DEMONSTRATION
- Command-line driver class DemoMain demonstrates typical usage (no CLI parameters)
javac DemoMain.c
java DemoMain

* UNIT TESTING
- JUnit-based class File2EdgesTest has methods that generate test data and run basic tests



/*Adjavency List. 
 * One list for each Node.  
 * A: is adjacent to [(B,weight)]
 * B: is adjacent it [(a,weight),(c,weight)]
 * C is adjacent to [(B,weight)]  tuple
 * 
 * this is a collection of lists. it can be a List<List> 
 * or whatever you want it to me. Just remember taht the size of the list is the number of nodes
 * and if the nodes are ordered, then you can have the order of the lsit
 * reflect the ordering of the nodes. 
 * 
 * demo uses tree set so that it stays in order. 
 * which is nice :-) 
 * once you have the nodes in a fix order, you can 
 * associate position zero with a list of nodes.  
 * get fancy if you want. class with a key. or just by ordering. 
 * 
 * 
 * hes using the List interface.  doesnt matter if you use array list or linked list
 * because it's just a list.  in Java you dont have to implement linking at all, it happens automatically. 
 * doesnt matter what kind of list. 
 * when I go to implement that, i have to make a choice. 
 * we are using a list, whether under that list is linked or array. doesnt matter, java handles it. 
 * 
 */

//One list for each node. 