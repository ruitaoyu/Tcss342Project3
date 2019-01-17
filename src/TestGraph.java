/*
 * TCSS 342 Project 3
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;


/**
 * Test class for the graph.
 * test all cases of the default test data.
 *
 * @author UWT, modify by Ruitao Yu, Arjun Prajapati, Aayush Shah
 * @version February 28 2018
 *
 */
public class TestGraph {

	/**
	 * Main method.
	 * @param args command line arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		
		// call the read graph method to get an MyGraph object from two input files
		MyGraph g = readGraph("files/vertex.txt","files/edge.txt");
		ArrayList<Vertex> vertexList = (ArrayList<Vertex>) g.vertices();
		Vertex from;
		Vertex to;
		
		/*
		 * Check the shortest path of two points that can only travel from on to the other.
		 */
		System.out.println("First, we need to test the shortest path between SEA and LAX since we can only\n"
				+ "travel in one edge between them, which is from SEA to LAX and the distance is 300 miles\n"
				+ "let's call SEA to LAX first:\n");
		shortest(g, "SEA", "LAX");
		System.out.println("\nsince the distance from SEA to LAX is 300, greater than the path from SEA-SFO-LAX\n"
				+ "So, the result is correct.");
		System.out.println("Now, let's find the shortest path from LAX to SEA:\n");
		shortest(g, "LAX", "SEA");
		System.out.println("\nBecause we cannot travel from LAX to SEA directelly\n"
				+ "So, the result is correct.");
		System.out.println("***************************************************\n");
		
		/*
		 * check the longest path in this graph.
		 */
		System.out.println("Next, we are going to test the longest path in this graph.\n"
				+ "which is from SEA to IAD:");
		shortest(g, "SEA", "IAD");
		System.out.println("***************************************************\n");
		
		/*
		 * use random to generate and random input vertex
		 */
		Random rand = new Random();
		System.out.println("In this test, we are going to test the shortestpath() 1000 times with random\n"
				+ " input from the vertex, we have very high chance to get a correct calculation\n"
				+ " if we don't see any exception because the default data are a valid inputs");
		
		/*
		 * Testing the default input.
		 * Run 1000 times to get the all possible path
		 */
		for (int i = 0; i < 1000; i++) {
			from = vertexList.get(rand.nextInt(vertexList.size()));
			to = vertexList.get(rand.nextInt(vertexList.size()));
			g.shortestPath(from, to);
		}
		System.out.println("No exception founded!");
		System.out.println("***************************************************\n");

		/*
		 * Test the custom input.
		 * isolation case and travel to itself case.
		 */
		System.out.println("In this test, we have a custom input file that have 4 vertex inside, \n"
				+ "[MCD, KFC, BUR, POE]\n"
				+ "But only MCD and KFC, BUR and POE can travel to each other.\n");
		
		g = readGraph("files/fastfoodVertex.txt","files/fastfoodEdge.txt");
		vertexList = (ArrayList<Vertex>) g.vertices();
		
		System.out.println("The purpose of this graph is to test if what happened if we have two vertexs\n"
				+ "cannot travel to each other\n"
				+ "So, we are going to find the shortest distance from MCD to BUR: \n");
		shortest(g, "MCD", "BUK");
		System.out.println("It is true that we cannot travel from MCD to BUK.");
		System.out.println("Next, we are going see what if the start point is the same as the end point: \n");
		shortest(g, "MCD", "MCD");
		System.out.println("Which satisfy the require result of this homework.");
		System.out.println("***************************************************");
		
		/*
		 * testing the exceptions.
		 */
	    System.out.println("");
	    System.out.println("Next, we are going to test exception when encounter invalid data\n");
	    System.out.println("When we have duplicate vertex(should not throw): \n");
	    
		/*
		 * Should not throw exception since we ignore the duplicate vertex.
		 */
	    try {
	    		g = readGraph("files/fastfoodduplicatevertex.txt","files/fastfoodEdge.txt");
	    } catch (Exception e) {
	    		System.out.println("It should not throw exceptions when found duplicate vertexs.");
	    }
	    System.out.println("When we have cost is less han 0(should throw): ");
	    
		/*
		 * Should throw exception since cost is less than 0.
		 */
	    try {
	    		g = readGraph("files/fastfoodVertex.txt","files/fastfoodnegativedge.txt");
	    } catch (Exception e) {
	    		System.out.println("Exceptions throwed as: "  + e.getMessage() + "\n");
	    }
	    System.out.println("When we have unknown vertex(should throw): ");
	    
		/*
		 * Should throw exception since we have unknown vertex.
		 */
	    try {
	    		g = readGraph("files/fastfoodVertex.txt","files/ffUnknownVertexInEdge.txt");
	    } catch (Exception e) {
	    		System.out.println("Exceptions throwed as: " + e.getMessage() + "\n");
	    }
	    System.out.println("When we have duplicate edge with different cost(should throw): ");
	    
		/*
		 * Should throw exception since we have duplicate edges with different weight.
		 */
	    try {
	    		g = readGraph("files/fastfoodVertex.txt","files/ffDuplicateEdge.txt");
	    } catch (Exception e) {
	    		System.out.println("Exceptions throwed as: " + e.getMessage() + "\n");
	    }
		System.out.println("***************************************************");
		
	}

	/**
	 * Read two text files, and transfer than into two lists.
	 * @param theVertexFileDir the directory of the vertex file
	 * @param theEdgeFileDir the directory of the edge file
	 * @return
	 * @throws Exception 
	 */
	public static MyGraph readGraph(String theVertexFileDir, String theEdgeFileDir) throws Exception {
		/*
		 * Scanner the two files to make two lists
		 * use two lists as MyGraph class's parameter to create a MyGraph object
		 * then return it
		 */
		Scanner reader = null;
		try {
			reader = new Scanner(new File(theVertexFileDir));
		} catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: "+theVertexFileDir);
			System.exit(2);
		}

		Collection<Vertex> vertexList = new ArrayList<Vertex>();
		while(reader.hasNext())
			vertexList.add(new Vertex(reader.next()));

		try {
			reader = new Scanner(new File(theEdgeFileDir));
		} catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: "+theEdgeFileDir);
			System.exit(2);
		}

		Collection<Edge> edgeList = new ArrayList<Edge>();
		while(reader.hasNext()) {
			try {
				Vertex from = new Vertex(reader.next());
				Vertex to = new Vertex(reader.next());
				int weight = reader.nextInt();
				edgeList.add(new Edge(from,to,weight));
			} catch (NoSuchElementException e2) {
				System.err.println("EDGE FILE FORMAT INCORRECT");
				System.exit(3);
			}
		}

		return new MyGraph(vertexList,edgeList);
	}
	
	/**
	 * Print out the path in a test format.
	 * @param thePath the shortest path
	 * @param from start point
	 * @param to end point
	 */
	public static void printPath(Path thePath, Vertex from, Vertex to) {
		if (thePath == null) {
			System.out.printf("We can not get from %s to %s.\n", from, to);
		} else  {
			System.out.printf("The shortest path from %s to %s is %s, total %d miles.\n"
				, from, to, thePath.vertices.toString(), thePath.cost);
		}
	}
	
	/**
	 * Find the shortest path base on the vertex labels.
	 * @param g the graph
	 * @param from start point as string
	 * @param to end point as string
	 */
	public static void shortest(MyGraph g, String from, String to) {
		Vertex fromVer = new Vertex(from);
		Vertex toVer = new Vertex(to);
		Path shortest = g.shortestPath(fromVer, toVer);
		printPath(shortest, fromVer, toVer);
	}

}
