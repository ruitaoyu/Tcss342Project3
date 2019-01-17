/*
 * TCSS 342 Project 3
 */
/**
 * Representation of a graph vertex
 *
 * @author UWT, modify by Ruitao Yu, Arjun Prajapati, Aayush Shah
 * @version February 28 2018
 */
public class Vertex {
	// label attached to this vertex
	private String label;
	
	// the current recorded cost for this vertex
	private int cost;
	
	// is known or not
	private boolean isKnown;
	
	// the last travel path
	private Vertex path;

	/**
	 * Construct a new vertex
	 * 
	 * @param label
	 *            the label attached to this vertex
	 */
	public Vertex(String label) {
		if (label == null)
			throw new IllegalArgumentException("null");
		this.label = label;
	}
	
	/**
	 * Get the cost of this node.
	 * @return the cost as integer
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * set the cost of this node.
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}


	/**
	 * Get a vertex label
	 * 
	 * @return the label attached to this vertex
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Get the known status of this node.
	 * @return return boolean, true as known, false as unknown
	 */
	public boolean isKnown() {
		return isKnown;
	}
	
	/**
	 * set known or not of this node.
	 */
	public void setKnown(boolean isKnown) {
		this.isKnown = isKnown;
	}
	
	/**
	 * Get the last path of this node.
	 * @return a vertex object
	 */
	public Vertex getPath() {
		return path;
	}
	
	/**
	 * set the last path of this node.
	 */
	public void setPath(Vertex path) {
		this.path = path;
	}

	/**
	 * A string representation of this object
	 * 
	 * @return the label attached to this vertex
	 */
	public String toString() {
		return label;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}
	

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Vertex other = (Vertex) obj;
		if (label == null) {
			return other.label == null;
		} else {
			return label.equals(other.label);
		}
	}

}
