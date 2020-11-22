package ex1.tests;
import ex1.src.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class WGraph_DSTest {
	private WGraph_DS graph;
	@BeforeEach
	void setUp() {
	  graph=new WGraph_DS();
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
 }
	@Test
	public void test_updateWeight() {
		graph=new WGraph_DS();
		graph.addNode(16);
		graph.addNode(2);
		graph.connect(16,2,2);
		graph.connect(16,2,5);
		double acual=graph.getEdge(16,2);
		assertEquals(5, acual);
	}
	@Test
	public void test_edgeSize() {
		int numEdge=graph.edgeSize();
		int acualNum=19;
		assertEquals(numEdge,acualNum);
	}
	@Test
	public void test_nodeSize() {
		int size=graph.nodeSize();
		int acualNum=10;
		assertEquals(size,acualNum);
	}
	@Test
	public void test_removeEdge() {
		graph.removeEdge(0, 3);
		int numEdge=graph.edgeSize();
		int acualNum=18;
		assertEquals(numEdge,acualNum);
	}
	@Test
	public void test_removeVertex() {
		Collection<node_info>collect=graph.getV(0);
		graph.removeNode(0);
		boolean d=true;
		for(node_info cur:collect) {
			for(node_info node:graph.getV(cur.getKey()))
				if(node.getKey()==0) d=false;
		}
		assertEquals(d,true);
	}
	@Test
	public void test_numEdgesAfterRemove() {
		graph.removeNode(0);
		int numEdge=graph.edgeSize();
		int acualNum=14;
		assertEquals(numEdge,acualNum);
	}
	@Test
	public void test_hasEdgeF() {
		boolean bol=graph.hasEdge(2, 20);
		assertEquals(bol, false);
	}
	@Test
	public void test_hasEdgeT() {
		boolean bol=graph.hasEdge(2, 7);
		assertEquals(bol, true);
	}
	@Test
	public void test_getEdge() {
	  double w=graph.getEdge(3, 6);
	  assertEquals(w, 12);
	}
	@Test
	public void test_getV() {
		int key=0;
		boolean bol=true;
		for(node_info node:graph.getV()) {
			if(node.getKey()!=key) bol=false;
			key++;
		}
		assertEquals(bol, true);
	}
	@Test
	public void test_getV_ofCurrentNode() {
		boolean bol=true;
		Collection<node_info> collection=graph.getV(4);
		graph.removeNode(4);
		for(node_info node:collection) {
			if(graph.hasEdge(4, node.getKey())) bol=false;
		}
		assertEquals(bol, true);
	}
}
