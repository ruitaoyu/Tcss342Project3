/*
 * TCSS 342 Project 3
 */

import java.util.*;
import java.io.*;

/**
 * Driver program that reads in a graph and prompts user for shortest paths in the graph.
 * (Intentionally without comments.  Read through the code to understand what it does.)
 * 
 * @author UWT, modify by Ruitao Yu, Arjun Prajapati, Aayush Shah
 * @version February 28 2018
 */
public class FindPaths {
	
	/**
	 * Main method.
	 * @param args command line arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		
		// call the read graph method to get an MyGraph object from two input files
		MyGraph g = readGraph("files/vertex.txt","files/edge.txt");
		
		/*
		 * create a scanner, then ask user for two inputs
		 * input has to be in the vertices list, if not, end the program
		 */
		Scanner console = new Scanner(System.in);
		Collection<Vertex> vertexList = g.vertices();
		while(true) {
			System.out.print("Start vertex? ");
			Vertex from = new Vertex(console.nextLine());
			if(!vertexList.contains(from)) {
				System.out.println("no such vertex");
				console.close();
				System.exit(0);
			}
			
			System.out.print("Destination vertex? ");
			Vertex to = new Vertex(console.nextLine());
			if(!vertexList.contains(to)) {
				System.out.println("no such vertex");
				console.close();
				System.exit(0);
			}
			
			/*
			 * If the input is valid, call the shortest path method in MyGraph class
			 * and get and path object.
			 * print out the shortest path
			 */
			Path shortestPath = g.shortestPath(from, to);
			System.out.printf("Shortest path from %s to %s:\n", from.toString(), to.toString());
			if (shortestPath == null) {
				System.out.println("does not exist.");
			} else {
				for(Vertex ver : shortestPath.vertices) {
					System.out.print(ver.toString() + " ");
				}
				System.out.println("\n" + shortestPath.cost);
			}
		}
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
}
