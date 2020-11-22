package ex1.src;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * This class represents the algorithms which applicable on a undirectional weighted graph.
 * the algorithms including- 
 * 1.a deep copy of the graph.
 * 2.init(graph).
 * 3.getGraph();
 * 4.isConnected();
 * 5. double shortestPathDist(int src, int dest);
 * 6. List<node_data> shortestPath(int src, int dest);
 * 7. Save(file);
 * 8. Load(file);
 * @author shira.a.
 */

public class WGraph_Algo implements weighted_graph_algorithms{
	private weighted_graph graph;
	static final String SPILT_BY=",";
	
	public WGraph_Algo(){
		graph=new WGraph_DS();
	}
	/**
	 * This function init the graph on which this set of algorithms operates on.
	 */
	@Override
	public void init(weighted_graph g) {
		graph=g;
	}
	/**
	 * This function return the underlying graph of which this class works.
	 * @return 
	 */

	@Override
	public weighted_graph getGraph() {
		return graph;
	}
	/**
	 * This function creates another graph which is a copy of this graph.
	 * @return return the new identical graph.
	 */
	@Override
	public weighted_graph copy() {
		weighted_graph answer=new WGraph_DS(graph);
		return answer;
	}
	/**
	 *This function checks if the graph is a connected one.
	 * A connected graph is a graph in which there is a path from every node to each
     * other node in the graph.
     * This function uses the BFS algorithm, because this algorithm efficiently visits
     * all the key nodes in a graph and marks them, and on that way it can be known 
     * if the graph is connected or not. 
     * therefore the run time of this function is O(V+E).
     * V- the number of the vertexes in the graph.E- the number of the edges in the graph.
	 * @return returns true if the graph is connected, and false if it isn't.
	 */
	@Override
	public boolean isConnected() {
		if(graph.getV().size()==1||graph.getV().size()==0) return true;
		Stack<node_info> stack=new Stack<>();
		node_info ezer;
		node_info pop;
		Iterator<node_info> itr=graph.getV().iterator();
		node_info current=itr.next();
		stack.push(current);
		current.setInfo("t");
		while(!stack.isEmpty()) {
			  pop=stack.pop();
			  if(graph.getV(pop.getKey()).size()!=0){
			     Iterator<node_info> itr2=graph.getV(pop.getKey()).iterator();
			      while(itr2.hasNext()) {
				        ezer=itr2.next();
				        if(graph.getNode(ezer.getKey()).getInfo().equals("f")) {
					       stack.push(ezer);
					       graph.getNode(ezer.getKey()).setInfo("t");
				       }
			     }
			  }
		}
		boolean d=true;
		for(node_info node:graph.getV()) {
			if(node.getInfo().equals("f")) d=false;
			else node.setInfo("f");
		}
		return d;
	}
	/**
	 * This function returns the lowest distance between src and dest.
	 *This function uses the Dijkstra algorithm, because this algorithm solves the problem
	 * of finding he shortest path with the lowest distance between nodes in a weighted graph.
	 * Therefore,the function runs in ((| V | + | E |)*log | V |), as is:
	 * V: the number of the vertexes in the graph.
	 * E: the number of the edges in the graph.
	 * The function is also Updates the fields of tag and info after they have been changed  
	 * while using  the Dijkstra algorithm's function.
	 * @param src- this is the node_id of the first node in the path.
	 * @param dest- this is the node_id of the last node in the path.
	 * @return returns the lowest distance between src and dest.
	 */

	@Override
	public double shortestPathDist(int src, int dest) {
		List<node_info> path=new LinkedList<node_info>();
		path=DiijkstraAlgorithm(src,dest);
		if(path!=null){
			double answer=0;
			if(path.size()==1) return answer;
			for(node_info node:path) {
				 if(node.getKey()==dest) answer=node.getTag();
			}
			for(node_info node:graph.getV()) {
				node.setInfo("f");
				node.setTag(Double.POSITIVE_INFINITY);
			}
			return answer;
		}
		return -1;
	}
	/**
	 * This function returns the shortest path with the lowest distance between src(node_id) and dest(node_id).
	 * This function uses the Dijkstra algorithm, because this algorithm solves the problem
	 * of finding he shortest path with the lowest distance between nodes in a weighted graph.
	 * Therefore,the function runs in ((| V | + | E |)*log | V |), as is:
	 * V: the number of the vertexes in the graph.
	 * E: the number of the edges in the graph.
	 * The function is also Updates the fields of tag and info after they have been changed  
	 * while using  the Dijkstra algorithm's function.
	 * @param src- this is the node_id of the first node in the path.
	 * @param dest- this is the node_id of the last node in the path.
	 * @return returns a list of the shortest path with the lowest distance.
	 */
	@Override
	public List<node_info> shortestPath(int src, int dest) {
		List<node_info> path=new LinkedList<node_info>();
		List<node_info> answer=new LinkedList<node_info>();
		path=DiijkstraAlgorithm(src,dest);
		if(path!=null) {
			Stack<node_info> stack= new Stack<node_info>();
			for(node_info node:graph.getV()) {
				node.setInfo("f");
				node.setTag(Double.POSITIVE_INFINITY);
			}
			for(node_info node_:path) {
				stack.add(node_);
			}
			while(!stack.isEmpty()) {
				  answer.add(stack.pop());
			}
			return answer;	
		}
		return null;
		
	}
	/**
	 * This function returns the smallest number between the current node's tag 
	 * and the sum of prev's tag with weight of the edge between prev<--> ni.
	 * @param ni - this is the current node.
	 * @param prev - this is the father of this current node.
	 * @param weight - this is the weight of the edge between prev<--> ni.
	 * @return
	 */
	private double findMin(node_info ni, node_info prev,double weight) {
		double w= prev.getTag()+weight;
		return Math.min(ni.getTag(), w);
	}
	/**
	 * This function implements the Dijkstra's algorithm.
	 * Dijkstra's algorithm is an algorithm for finding the shortest paths with the lowest distance,between nodes
	 * in a weighted(only positive) graph.
	 * This function uses a min-priority queue for storing the optional next nodes of every current 
	 * node and sorted them by the lowest weight,and from the neighbor which has the lowest weight- 
	 * the path will continue. 
	 * Therefore,the function runs in ((| V | + | E |)*log | V |), as is:
	 * V: the number of the vertexes in the graph.
	 * E: the number of the edges in the graph.
	 * @param src- this is the node_id of the first node in the path.
	 * @param dest- this is the node_id of the last node in the path.
	 * @return returns a list of the shortest path with the lowest distance between src<-->dest.
	 */
	private List<node_info> DiijkstraAlgorithm(int src, int dest){
		node_info node1=graph.getNode(src);
		node_info node2=graph.getNode(dest);
		if(node1==null||node2==null) return null;
		List<node_info>answer=new LinkedList<node_info>();
		if(src==dest) {
			answer.add(node1);
			return answer;
		}
		PriorityQueue<node_info> priQ= new PriorityQueue<node_info>();
		Queue<node_info> current= new LinkedList<node_info>();
		HashMap<Integer,node_info> parents=new HashMap<>();
		Queue<node_info> visitedNodes= new LinkedList<node_info>();
		node1.setTag(0);
		current.add(node1);
		node1.setInfo("t");
		parents.put(node1.getKey(), node1);
		node_info node;
		node_info vertex;
		double oldTag;
		double w;
		while(!current.isEmpty()) {
			  node=current.poll();
			  for(node_info ni:graph.getV(node.getKey())) {
				  vertex=graph.getNode(ni.getKey());
				  if(vertex.getInfo().equals("f")) {
					  oldTag=vertex.getTag();
					  w=graph.getEdge(node.getKey(), ni.getKey());
					  vertex.setTag(findMin(vertex,node,w));
					  priQ.add(vertex);
					  visitedNodes.add(vertex);
					  if(oldTag!=vertex.getTag()) {
						  if(!parents.containsKey(vertex.getKey()))
							  parents.put(vertex.getKey(), node);
						  else parents.replace(vertex.getKey(), node);
					  }
				  }
			  }
			  if(!priQ.isEmpty()) {
			     vertex=priQ.poll();
			     current.add(vertex);
			     priQ.clear();
			     graph.getNode(vertex.getKey()).setInfo("t");
			  }
			  else {
				    if(!visitedNodes.isEmpty()) current.add(visitedNodes.remove()); 
			  }
		}
		if(node2.getInfo().equals("f")) {
			for(node_info c:graph.getV()) {
				c.setInfo("f");
				c.setTag(Double.POSITIVE_INFINITY);
			}
			return null;
		}
		answer.add(node2);
		int key=node2.getKey();
		node_info toAdd;
		while(key!=src) {
			 toAdd=parents.get(key);
			 key=toAdd.getKey();
			 answer.add(toAdd);	 
		}
		return answer;
	}
	/**
	 * This function saves this weighted undirected graph to the given
     * file name.
     * @param file- the file name.
     * @return true if the file was successfully saved, or false in case it wasn't.
	 */

	@Override
	public boolean save(String file) {
		boolean ans=false;
		try {
			PrintWriter writer=new PrintWriter(new File(file));
			StringBuilder build=new StringBuilder();
			for(node_info vertex:graph.getV()) {
				build.append(vertex.getKey());
				for(node_info ni:graph.getV(vertex.getKey())) {
					build.append(",");
					build.append(ni.getKey());
					build.append(",");
					build.append(graph.getEdge(vertex.getKey(), ni.getKey()));
				}
				build.append("\n");
				writer.write(build.toString());
				build.setLength(0);
			}
			writer.close();
			ans=true;
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return ans;
	}
	/**
	 * This method loads a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file- file name.
     * @return true- if the graph was successfully loaded, or false in case it wasn't.
	 */
	@Override
	public boolean load(String file) {
		String line="";
		try {
			weighted_graph g=new WGraph_DS();
			BufferedReader br=new BufferedReader(new FileReader(file));
			int v;
			int ni;
			double w;
			int i=0;
			while((line=br.readLine())!=null) {
				  String[] userInfo=line.split(SPILT_BY); 
				  i=0;
			      v=Integer.parseInt(userInfo[i]);
			      g.addNode(v);
				  while(i<userInfo.length-1) {
					    ni=Integer.parseInt(userInfo[i+1]);
					    w= Double.parseDouble(userInfo[i+2]);
					    if(g.getNode(ni)==null) g.addNode(ni);
					    g.connect(v, ni, w);
					    i+=2;
					    }
			}  
		    br.close();
		    graph=g;
			return true;
			}
		catch(IOException e) {
			  e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof WGraph_Algo)) return false;
		WGraph_Algo graph_other=(WGraph_Algo)other;
	    weighted_graph a= getGraph();
	    weighted_graph b= graph_other.getGraph();
	    return a.equals(b);
	}
}
