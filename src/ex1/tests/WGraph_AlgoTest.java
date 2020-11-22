package ex1.tests;
import ex1.src.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WGraph_AlgoTest {
	private WGraph_Algo graph_A;
	@BeforeEach
	void setUp() {
	  graph_A=new WGraph_Algo();
	  weighted_graph graph=new WGraph_DS();
	  for(int i=0;i<10;i++) 
		   graph.addNode(i); 
	  int k1=0;int k2=0;
	  Random rand = new Random(1);
	  int r_w;
	  int j;
	  while(k1!=10) {
		    j=0;
		  while(j!=3) {
			    k2= rand.nextInt(10);
			    r_w = rand.nextInt(20);
			    graph.connect(k1, k2, r_w);
			    j++;
		  }
		  k1++;
	  }	 
	  graph_A.init(graph);
 }
	@Test
	public void isConnected() {
		boolean bol=graph_A.isConnected();
		assertEquals(bol,true);
	}
	@Test
	public void isConnected_afterRemove_0() {
		graph_A.getGraph().removeNode(0);
		boolean bol=graph_A.isConnected();
		assertEquals(bol,true);
	}
	@Test
	public void isConnected_afterRemove_0_6() {
		graph_A.getGraph().removeNode(0);
		graph_A.getGraph().removeNode(6);
		boolean bol=graph_A.isConnected();
		assertEquals(bol,false);
	}
	@Test
	public void isConnected_afterRemove_6_4() {
		graph_A.getGraph().removeNode(6);
		graph_A.getGraph().removeNode(4);
		boolean bol=graph_A.isConnected();
		assertEquals(bol,true);
	}
	@Test
	public void isConnected_RemoveNodes_6_4_8() {
		graph_A.getGraph().removeNode(6);
		graph_A.getGraph().removeNode(4);
		graph_A.getGraph().removeNode(8);
		boolean bol=graph_A.isConnected();
		assertEquals(bol,false);
	}
	@Test
	public void isConnected_RemoveEdges() {
		graph_A.getGraph().removeEdge(0, 7);
		graph_A.getGraph().removeEdge(4, 7);
		graph_A.getGraph().removeEdge(2, 8);
		boolean bol=graph_A.isConnected();
		assertEquals(bol,false);
	}
	@Test
	public void isConnected_RemoveSinglEdge() {
		graph_A.getGraph().removeEdge(0, 7);
		graph_A.getGraph().removeEdge(4, 7);
		graph_A.getGraph().removeEdge(2, 8);
		boolean bol=graph_A.isConnected();
		assertEquals(bol,false);
	}
	@Test
	public void isConnected_RemoveEdgeAndNodes() {
		graph_A.getGraph().removeNode(6);
		graph_A.getGraph().removeNode(4);
		graph_A.getGraph().removeEdge(8, 0);
		graph_A.getGraph().removeEdge(8, 2);
		boolean bol=graph_A.isConnected();
		assertEquals(bol,false);
	}
	@Test
	public void copy() {
		weighted_graph graph=graph_A.copy();
		assertEquals(graph, graph_A.getGraph());
	}
	@Test
	public void copy_changWeight() {
		weighted_graph graph=graph_A.copy();
		graph.connect(0, 3, 12);
		assertNotEquals(graph, graph_A.getGraph());
	}
	@Test
	public void copy_removeEdge() {
		weighted_graph graph=graph_A.copy();
		graph.removeEdge(2,7);
		assertNotEquals(graph, graph_A.getGraph());
		
	}
	@Test
	public void copy_removeNode() {
		weighted_graph graph=graph_A.copy();
		graph.removeNode(9);
		assertNotEquals(graph, graph_A.getGraph());
	}
	@Test
	public void shortestPath() {
		List<node_info> list=graph_A.shortestPath(0, 8);
		int[] arr= {0,8};
		boolean bol=true;
		if(arr.length!=list.size()) bol=false;
		for(int i=0;i<arr.length;i++) {
			if(arr[i]!=list.get(i).getKey()) bol=false;
		}
		assertEquals(bol,true);
	}
	@Test
	public void shortestPathF() {
		List<node_info> list=graph_A.shortestPath(0, 3);
		int[] arr= {0,5,3};
		boolean bol=true;
		if(list!=null) {
		if(arr.length!=list.size())bol=false;
		else {
		for(int i=0;i<arr.length;i++) 
			if(arr[i]!=list.get(i).getKey()) bol=false;
		}
		}
		bol=false;
		assertEquals(bol,false);
	}
	@Test
	public void shortestPathWeight() {
		double weight=graph_A.shortestPathDist(0,9);
		assertEquals(weight,8.0);
	}
	@Test
	public void WeightOfNoPath() {
		double weight=graph_A.shortestPathDist(0,20);
		assertEquals(weight,-1);
	}
	@Test
	public void shortestPathOfNoPath() {
		List<node_info> list=graph_A.shortestPath(0,20);
		boolean bol=false;
		if(list==null) bol=true;
		assertEquals(bol,true);
	}
	@Test
	public void shortestPathOfNotConnectedGraph() {
		graph_A.getGraph().removeNode(6);
		graph_A.getGraph().removeNode(4);
		graph_A.getGraph().removeNode(8);
		List<node_info> list=graph_A.shortestPath(5,2);
		boolean bol=false;
		if(list==null) bol=true;
		assertEquals(bol,false);
	}
	@Test
	public void NotExistPathOfNotConnectedGraph() {
		graph_A.getGraph().removeNode(6);
		graph_A.getGraph().removeNode(4);
		graph_A.getGraph().removeNode(8);
		List<node_info> list=graph_A.shortestPath(2,9);
		boolean bol=false;
		if(list==null) bol=true;
		assertEquals(bol,true);
	}
	@Test
	public void create_graphMilion() {
		weighted_graph g=new WGraph_DS();
		for(int i=0;i<1000000;i++) {
			g.addNode(i);
		}
		int j=0;
		int r=100;
		while(j!=1000000) {
			for(int p=1;p<11;p++) {
				if((j+p)>=999999)
				   g.connect(j, p, r*((double)1/p));
				else g.connect(j, j+p, r*((double)1/p));
			}
			j++; 
		}
		int edges=g.edgeSize();
		assertEquals(10000000,edges);
		int vertexes=g.nodeSize();
		assertEquals(1000000,vertexes);
	}
	@Test
	public void save() {
		String file="test.txt";
		graph_A.save(file);
		weighted_graph graph=graph_A.copy();
		WGraph_Algo graph_B=new WGraph_Algo();
		graph_B.init(graph);
		graph_A.load(file);
		assertEquals(graph_B.getGraph(), graph_A.getGraph());
	}
}
