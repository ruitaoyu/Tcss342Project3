/*
 * TCSS 342 Project 3
 */

/**
 *  The Path class.
 *  
 * @author UWT, modify by Ruitao Yu, Arjun Prajapati
 * @version February 28 2018
 */
import java.util.List;

/**
 * This is the class for storing a path.
 * 
 * @author UWT
 * @version February 28 2018
 */

public class Path {
	// we use public fields fields here since this very simple class is
	// used only for returning multiple results from shortestPath
	public final List<Vertex> vertices;
	public final int cost;

	public Path(List<Vertex> vertices, int cost) {
		this.vertices = vertices;
		this.cost = cost;
	}
}
