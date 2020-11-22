package ex1.src;
import java.util.HashMap;
/**
 *This class represents a collection of all the neighbors of a current node (vertex),
 *uses the data structure from the type of HahMap. 
 *Each neighbor has a second pair- weight of the edges between it and the node. 
 *The class supports several operations applicable on the neighbors collection of the node,
 *such as removing or adding a neighbor(an edge) and etc.
 * @author shira.a.
 */
public class Node_WEdges  {
	
	private HashMap<node_info,Double> ni;
	
	public  Node_WEdges () {
		ni=new HashMap<node_info,Double>();
	}
	/**
	 * This function returns the collection with all the neighbor nodes of this node_data.  
	 * @return HashMap-a data structure which representing all the neighbors of this current node_info.
	 */
	public HashMap<node_info,Double> getNi() {
		return ni;
	}
	/**
	 * This function checks if there is an edge between this<-->other
	 *  @param other- this is a node which may be connected to this node. 
	 * @return returns true is this<--> other are adjacent, and false in case they aren't.
	 */
	public boolean hasNi(node_info other) {
		if(ni.containsKey(other)) return true;
		return false;
	}
	/**
	 * This function adds node other to be another neighbor of this current node_info. 
	 * @param other- this is the node which need to be add as a new neighbor.
	 */
	public void addNi(node_info other, double w) {
		ni.put(other, w);
	}
	/**
	 * This function removes the edge this<-->other.
	 * @param other- this is the node which the edge ends at.
	 */
	public void removeNode(node_info other) {
		if(ni.containsKey(other)) 
			ni.remove(other);
		
	}
	/**
	 * This function returns the weight of the edge between 
	 * this<-->other,in case the edge between them is existent.
	 * @param other- this is the other node,which the edge is connected between.
	 * @return the weight of the edge between this<-->other,or -1 in case the edge isn't existent. 
	 */
	public double getWeight(node_info other) {
		if(hasNi(other)) {
			return ni.get(other);
		}
		return -1;
	}
	public void setWeight(node_info other, double w) {
		if(hasNi(other)) {
			ni.replace(other, w);
		}
	}
	
}
