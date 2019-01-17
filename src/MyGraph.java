/*
 * TCSS 342 Project 3
 */

import java.util.*;

/**
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 * 
 * @author UWT, modify by Ruitao Yu, Arjun Prajapati, Aayush Shah
 * @version February 28 2018
 */
public class MyGraph implements Graph {
	
	/** This map store the vertex's out-degree in a list as value of the map, the vertex as a key */
	public Map<Vertex, ArrayList<Edge>> myDegreeMap
					= new HashMap<Vertex, ArrayList<Edge>>();
	
	/** can get the vertex object by it's label.*/
	public Map<String, Vertex> getVertexByName;
	
	/** This set store the visited location. */
	private Set<Vertex> pinkArea;
	
	/** This list store all the vertexes. */
	private ArrayList<Vertex> myVertexs;
	
	/** This list store all the edges. */
	private ArrayList<Edge> myEdges;


	/**
	 * Creates a MyGraph object with the given collection of vertices and the
	 * given collection of edges.
	 * 
	 * @param v
	 *            a collection of the vertices in this graph
	 * @param theEdges
	 *            a collection of the edges in this graph
	 * @throws Exception 
	 */
	public MyGraph(Collection<Vertex> theVertexs, Collection<Edge> theEdges) throws Exception {
		
		// Store two list as fields
		myVertexs = (ArrayList<Vertex>)theVertexs;
		myEdges = (ArrayList<Edge>)theEdges;
		
		// set up the string to vertex map
		// Check if the input are valid, then create a degree map for each vertex
		setUpGetByName();
		checkValidInput();
		createDegreeMap();
	}

	/**
	 * Set up the map which can be access by its label.
	 */
	private void setUpGetByName() {
		// Initialized the map
		// for each vertex, put it into the map as value and the labels as keys
		getVertexByName = new HashMap<String, Vertex>();
		for(Vertex ver : myVertexs) {
			getVertexByName.put(ver.getLabel(), ver);
		}
	}

	/**
	 * Create a degree map for each vertex.
	 */
	private void createDegreeMap() {
		/*
		 * Loop through every edge, put them into a list as
		 * a value of the map, they keys are the 'from' vertex in each edge
		 */
		for(Edge edge : myEdges) {
			ArrayList<Edge> degree = new ArrayList<Edge>();
			if (myDegreeMap.isEmpty() || !myDegreeMap.containsKey(edge.getSource())) {
				// if empty or dot not exist, put a new key and list as value
				degree.add(edge);
				myDegreeMap.put(edge.getSource(), degree);
			} else {// if key exists, put the edge into its degree list
				myDegreeMap.get(edge.getSource()).add(edge);
			}
		}
	}

	/**
	 * Check if the input files are valid.
	 * @throws Exception 
     *
	 *             if weight less than 0 or vertex does not exist.
	 *             if duplicate edge appears.
	 */
	private void checkValidInput() throws Exception {

		for(Edge edge : myEdges) {
			/*
			 * If vertex is not exist in the list
			 * or weight is less than 0
			 * or duplicate edge found
			 * throw exceptions
			 */
			if (!myVertexs.contains(edge.getSource()) 
					|| !myVertexs.contains(edge.getDestination())) {
				throw new Exception("Vertexs list doest not contain the reading vetex!");
			} else if (edge.getWeight() < 0){
				throw new Exception("Edge cost can not less than 0!");
			}
			
			int count = 0;
			for(Edge otherEdge : myEdges) {
				if ((edge.getSource().equals(otherEdge.getSource()) 
						&& edge.getDestination().equals(otherEdge.getDestination()))
						&& (edge.getWeight() != otherEdge.getWeight())) {
					count++;
				}
			}
			// more than 2 same edges, throw exception
			if (count >=1) {
				throw new Exception("Duplicate edges found");
			}
		}
	}

	/**
	 * Return the collection of vertices of this graph
	 * 
	 * @return the vertices as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Vertex> vertices() {
		return (Collection<Vertex>) myVertexs;
	}

	/**
	 * Return the collection of edges of this graph
	 * 
	 * @return the edges as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Edge> edges() {
		return (Collection<Edge>) myEdges;
	}

	/**
	 * Return a collection of vertices adjacent to a given vertex v. i.e., the
	 * set of all vertices w where edges v -> w exist in the graph. Return an
	 * empty collection if there are no adjacent vertices.
	 * 
	 * @param theVertex
	 *            one of the vertices in the graph
	 * @return an iterator-able collection of vertices adjacent to v in the graph
	 * @throws IllegalArgumentException
	 *             if v does not exist.
	 */
	@Override
	public Collection<Vertex> adjacentVertices(Vertex theVertex) {
		
		// Check if the vertex exist
		checkExistence(theVertex, theVertex);

		/*
		 * Loop through every edge, if found any in or out degree with 
		 * the vertex, add it to the list.
		 */
		Set<Vertex> myAdjacentVertices = new HashSet<Vertex>();
		
		for(Edge edge : myEdges) {
			if (edge.getSource().equals(theVertex)) {
				myAdjacentVertices.add(getVertexByName.get(edge.getDestination().getLabel()));
			}
		}

		return myAdjacentVertices;
	}
	
	/**
	 * Check if the vertex is in to list.
	 * @param theVer the vertex need to be checked
	 * @return 
	 * 		if exist, true, otherwise, false.
	 */
	private void checkExistence(Vertex one, Vertex theOther) {
		if(!myVertexs.contains(one) || !myVertexs.contains(theOther)) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed
	 * graph. Assumes that we do not have negative cost edges in the graph.
	 * 
	 * @param one
	 *            one vertex
	 * @param theOther
	 *            another vertex
	 * @return cost of edge if there is a directed edge from a to b in the
	 *         graph, return -1 otherwise.
	 * @throws IllegalArgumentException
	 *             if a or b do not exist.
	 */
	@Override
	public int edgeCost(Vertex one, Vertex theOther) {
		// If any vertex is not in the list, throw exception
		checkExistence(one, theOther);
		
		/*
		 * default cost is -1, which means these two vertexes are not adjacent
		 * get the out-degree of 'one', and check if there is a destination call 'theOther'
		 * if found, return the cost
		 */
		int cost = -1;
		for(Edge edge : myDegreeMap.get(one)) {
			if ((edge.getDestination().equals(theOther))) {
				cost = edge.getWeight();
			}
		}
		return cost;
	}

	/**
	 * Returns the shortest path from a to b in the graph, or null if there is
	 * no such path. Assumes all edge weights are nonnegative. Uses Dijkstra's
	 * algorithm.
	 * 
	 * @param from
	 *            the starting vertex
	 * @param to
	 *            the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *         and contains a (first) and b (last) and the cost is the cost of
	 *         the path. Returns null if b is not reachable from a.
	 * @throws IllegalArgumentException
	 *             if a or b does not exist.
	 */
	public Path shortestPath(Vertex from, Vertex to) {
		// If any vertex is not in the list, throw exception
		checkExistence(from, to);
		
		// Initialize a Path object and its parameters
		Path shorestPath;
		List<Vertex> list = new ArrayList<Vertex>();
		int lowestCost = 0;
		// default shortest
		shorestPath = new Path(list, lowestCost);
		
		/*
		 * If two location are the same location, add one of their name into the list
		 * and lowest cost as 0.
		 * 
		 * otherwise, call the findShortest to find the shortest path.
		 */
		if(from.equals(to)) {
			list.add(from);
		} else {
			shorestPath = dijkstra(from, to);
		}
		return shorestPath;
	}

	/**
	 * Initialized the pink area as hashSet
	 * 				   each vertex's cost as infinity
	 * 				   each vertex as unknown
	 * 				   each vertex's path as null
	 * @param start the start location
	 */
	private void initilizedData(Vertex start) {
		pinkArea = new HashSet<Vertex>();
		// for each vertex, set the cost to infinity but the start vertex
		for(Vertex vertex : myVertexs) {
			if (vertex.equals(start)){
				vertex.setCost(0);
			} else {
				vertex.setCost(Integer.MAX_VALUE);
			}
			
			// set path null and unknown
			vertex.setPath(null);
			vertex.setKnown(false);
		}
		
	}
	
	/**
	 * get the smallest cost of vertex from the unknown vertex.
	 * @return
	 */
	private Vertex getSmallest() {
		// define the return vertex, record the cost as infinity
		Vertex result = null;
		int min = Integer.MAX_VALUE;
		
		// if any cost is less than the current recorded on
		// update the return vertex to new one
		for(Vertex vertex : myVertexs) {
			if ( !vertex.isKnown() && vertex.getCost() < min) {
				min = vertex.getCost();
				result = vertex;
			} 
		}
		return result;
	}
	
	/**
	 * This method uses the dijkstra algorithm to find the shortest path .
	 * @param from starting location
	 * @param to destination
	 * 
	 * @return the shortest path
	 */
	private Path dijkstra(Vertex from, Vertex to) {
		// initialized the data structure we need to find the shortest
		initilizedData(from);

		// while the pink area is not including all the vertex
		while(pinkArea.size() != myVertexs.size()) {
			
			/*
			 * get the smallest unknown vertex from the list
			 * 		if null; get out of this while loop
			 * if so, set the vertex as known and add to the pink area
			 */
			Vertex current = getSmallest();
			if (current == null) break;
			current.setKnown(true);
			pinkArea.add(current);
			
			/*
			 * for each vertex that is an adjacent  to the current selected vertex 
			 * if the a the adjacent is unknown, calculate the new cost from the current vertex
			 * to this vertex.
			 * 	if cost is smallest, update
			 * 
			 */
			for(Vertex des : adjacentVertices(current)) {
				if (!des.isKnown() 
						&& ((edgeCost(current, des) + current.getCost()) < des.getCost())) {
					des.setCost(edgeCost(current, des) + current.getCost());
					des.setPath(current);
				}
			}
		}
		
		// the shortest path as a list
		List<Vertex> list = new ArrayList<Vertex>();
		
		/*
		 * get the path by calling the getPath() method inside every vertex
		 * store in the list
		 */
		Vertex temp = getVertexByName.get(to.getLabel());
		int cost = temp.getCost();
		while (temp.getPath() != null) {
			list.add(0, temp);
			temp = temp.getPath();
		}
		list.add(0, from);
		
		// create a Path object
		Path path = new Path(list, cost);
		
		// if the path cost is infinity, make it null as unreachable
		if (cost == Integer.MAX_VALUE) path = null;
		return path;
	}
}
