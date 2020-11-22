package ex1.src;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
/**
 * This class represents an undirectional weighted graph.
 * Each vertex in the graph is from the type of node_info, which have a unique key, tag and info fields.
 * Every edge has a weight which has to be a positive number.
 * This class represents the graph uses the data structure from the type of HahMap.
 * The class uses two HashMap -> one for the vertexes in the graph with the pair of <key,node>
 * and another for the neighbors of every vertex in the graph with the pair of <node,Node_WEdges>.
 * The object Node_WEdges stores the neighbors of every node with and the weight of the edge between 
 * this node and the neighbor. 
 * The class supports several operations applicable on a graph,
 * such as removing or adding a vertex or an edge, returning the weight of existing edge and etc.
 * @author shira.a.
 */

public class WGraph_DS implements weighted_graph{
	private class NodeInfo implements node_info,Comparable<node_info> {
		private int key;
		 private String info;
		 private double tag;
		 /**
		  * This function constructs a NodeInfo.
		  */
		 public NodeInfo(int k) {
			 this.key=k;
			 this.tag=Double.POSITIVE_INFINITY;
			 this.info="f";
		 }
		 /**
		  * This function returns the key (id) associated with this node.
		  * @return the key (id) of this node.
		  */
		@Override
		public int getKey() {
			return key;
		}
		/**
		 * This function returns the remark (meta data) associated with this node.
		 * @return the meta data of this node.
		 */
		@Override
		public String getInfo() {
			return info;
		}
		/**
		 * This function changes the meta data of this node to the excepted string. 
		 */
		@Override
		public void setInfo(String s) {
			info=""+s;
		}
		/**
		 * This function returns the weight of the edge between this node.
		 */
		@Override
		public double getTag() {
			return tag;
		}
		/**
		 * This function changes the weight of the edge between this node.
		 */
		@Override
		public void setTag(double t) {
			tag=t;	
		}
		@Override
		public int compareTo(node_info o) {
			int ans=0;
			if(this.tag-o.getTag()>0) ans=1;
			else if(this.tag-o.getTag()<0) ans=-1;
			return ans;
		}
	 }
	 //WGraph_DS:
	 private HashMap<Integer,node_info> _map;
	 private HashMap<node_info,Node_WEdges> nodes_ni;
	 private  int counterEdges=0;
	 private  int changes=0;
	 
	public WGraph_DS() {
		_map=new HashMap<Integer,node_info>();
		nodes_ni=new HashMap<node_info,Node_WEdges>();
	}
	public WGraph_DS(weighted_graph other){
		_map=new HashMap<Integer,node_info>();
		nodes_ni=new HashMap<node_info,Node_WEdges>();
		node_info toAdd;
		node_info ni;
		for(node_info node:other.getV()) {
			if(!_map.containsKey(node.getKey())) 
				addNode(node.getKey());	
			toAdd=_map.get(node.getKey());
			for(node_info current:other.getV(node.getKey())) {
				if(!_map.containsKey(current.getKey())) 
					addNode(current.getKey());
				ni=_map.get(current.getKey());
				if(!hasEdge(toAdd.getKey(),ni.getKey())) {
					double w=other.getEdge(toAdd.getKey(),ni.getKey());
					nodes_ni.get(toAdd).addNi(ni,w);
					nodes_ni.get(ni).addNi(toAdd,w);
				}
			}
		}
		this.counterEdges=other.edgeSize();
		this.changes=other.getMC();
	}
	 /**
	  *This function finds the node_info by it's node_id.
	 * @param key- this is the id of some node_info.
	 * @return returns the node_info which owns that key, or null if the node isn't existent.
	  */
	@Override
	public node_info getNode(int key) {
		return _map.get(key);
	}
	/**
	 * This function checks in O(1), if there is an edge between two node_info by their node_id.
	 * @param node1- this is the first node_id.
	 * @param node2- this is the second node_id.
	 * @return return true if there is an edge, and false if there isn't.
	 */
	@Override
	public boolean hasEdge(int node1, int node2) {
		if(_map.containsKey(node1)&&_map.containsKey(node2)) {
			if(node1==node2) return false;
			node_info node_1=getNode(node1);
			node_info node_2=getNode(node2);
			if((nodes_ni.get(node_1).hasNi(node_2))&&(nodes_ni.get(node_2).hasNi(node_1))) return true;
		}
		return false;
	}
	@Override
	public double getEdge(int node1, int node2) {
		if(node1==node2) return -1;
		node_info node_1=getNode(node1);
		node_info node_2=getNode(node2);
		return nodes_ni.get(node_1).getWeight(node_2);
		
	}
	/**
	 * This function adds in O(1),a new node to the graph with the 
	 * given key- in case there is already a node with such a key.
     * @param key- this is the id of the new vertex to add.
	 */
	@Override
	public void addNode(int key) {
		if(!_map.containsKey(key)) {
			node_info toAdd=new NodeInfo(key);
			_map.put(key, toAdd);
			Node_WEdges n_e=new Node_WEdges();
			nodes_ni.put(toAdd,n_e);
		}
		changes++;
		return ;	
	}
	/**
	 * This function connects in O(1), an edge with weight between two nodes,in case 
	 * they are'nt adjacent, if they are- the method updates the weight of the edge.
	 * @param node1- this is the first node_id.
	 * @param node2- this is the second node_id.
	 * @throws IOException 
	 */
	@Override
	public void connect(int node1, int node2, double w){
		if(node1==node2) return;
		node_info node_1=getNode(node1);
		node_info node_2=getNode(node2);
		if(node_1==null&&node_2==null) return;
		if(hasEdge(node1,node2)) {
			nodes_ni.get(node_1).setWeight(node_2, w);
			nodes_ni.get(node_2).setWeight(node_1, w);
			changes++;
		}
		else {
			  nodes_ni.get(node_1).addNi(node_2,w);
			  nodes_ni.get(node_2).addNi(node_1,w);
			  counterEdges++;
			  changes++;
		}	
	}
	/**
	 * This function creates in O(1), another pointer for the collection which
	 * representing all the nodes in the graph.
	 * @return return Collection<node_info>- the another pointer. 
	 */
	@Override
	public Collection<node_info> getV() {
		Collection<node_info> another=_map.values();
		return another;
	}
	/**
	 *This method returns a Collection containing all the 
	 *nodes connected to node_id.
     * @return Collection<node_info>.
	 */
	@Override
	public Collection<node_info> getV(int node_id) {
		node_info node=getNode(node_id);
		if(node!=null) {
		Collection<node_info>another=nodes_ni.get(node).getNi().keySet();
		return another;
		}
		return null;
	}
	/**
	 * This function removes the node from the graph by it's node_id.
	 * In addition, the function removes all the edges which starts or ends at this node.
	 * the function run in 0(|v|) times. v -number of the nodes in the graph.
	 * @param key- this is the node_id which need to be removed.
	 * @return returns the node which removed.
	 */
	@Override
	public node_info removeNode(int key) {
		if(!_map.containsKey(key)) return null;
		if(getV(key).size()!=0) {
		    node_info[] arr=new node_info[getV(key).size()];
		    getV(key).toArray(arr);
		    for(int i=0;i<arr.length;i++) {
		    	nodes_ni.get(arr[i]).removeNode(getNode(key));
		    	counterEdges--;
		    }
		}
		changes++;
		return _map.remove(key);
	}
	/**
	 * 
	 */
	@Override
	public void removeEdge(int node1, int node2) {
		if(hasEdge(node1,node2)) {
			node_info node_1=getNode(node1);
			node_info node_2=getNode(node2);
			nodes_ni.get(node_1).removeNode(node_2);
			nodes_ni.get(node_2).removeNode(node_1);
			counterEdges--;
			changes++;
		}
	}
	/**
	 * This function finds in O(1), the numbers of the nodes in the graph.
	 */
	@Override
	public int nodeSize() {
		return _map.size();
	}
	/**
	 * This function returns in O(1), the number of the edges in the graph.
	 * @return return the number of the edges.
	 */
	@Override
	public int edgeSize() {
		return counterEdges;
	}
	/**
	 * This function returns in O(1), the number of the changes that has been made if the graph.
	 * @return return getMC- the number of the changes.
	 */
	@Override
	public int getMC() {
		return changes;
	}
	/**
	 * This function returns true if this WGraph_DS and the object are equals.
	 * The function decides if those two are equals if the object contains the same vertex
	 * in the graph, and each vertex have the same neighbors.
	 * @param other- this is the object which compared to this graph.
	 * @return return true- if the objects are equals, and false in case they arn't.
	 */
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof WGraph_DS)) return false;
		WGraph_DS graph_other=(WGraph_DS)other;
		if((nodeSize()!= graph_other.nodeSize())||(edgeSize()!=graph_other.edgeSize())) return false;
		for(node_info node:getV()) {
			if(graph_other.getNode(node.getKey())==null) return false;
			for(node_info ni:getV(node.getKey())) {
				if(!graph_other.hasEdge(node.getKey(),ni.getKey())) return false;
				if(graph_other.getEdge(node.getKey(),ni.getKey())!=getEdge(node.getKey(),ni.getKey())) return false;
			}
		}
		return true;
	}
	/**
	 * This function creates a string which describes the 
	 * graph,by the key of every vertex in the graph and of it neighbors.
	 * @return the string which describes the graph.
	 */
	@Override
	public String toString() {
		String string="";
		for(node_info v:getV()) {
			if(v!=null) {
				string+="the key of this node is ->";
			   string+=""+v.getKey()+" it beighbors are->";
			   for(node_info ni:getV(v.getKey())) {
				    if(ni!=null) string+=""+ni.getKey()+", \n";
				
			}
			}
		}
		return string;
	}
	
	
	
}



	



