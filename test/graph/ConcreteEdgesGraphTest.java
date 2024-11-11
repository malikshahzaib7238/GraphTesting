	package graph;
	
	import static org.junit.Assert.*;
	
	import org.junit.Test;
	import java.util.Map;
	import java.util.Set;
	
	/**
	 * Tests for ConcreteEdgesGraph.
	 * 
	 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
	 * well as tests for that particular implementation.
	 * 
	 * Tests against the Graph spec should be in GraphInstanceTest.
	 */
	public class ConcreteEdgesGraphTest extends GraphInstanceTest {
	
	    /*
	     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
	     */
	    @Override public Graph<String> emptyInstance() {
	        return new ConcreteEdgesGraph();
	    }
	
	    /*
	     * Testing ConcreteEdgesGraph...
	     */
	    
	    // Testing strategy for ConcreteEdgesGraph.toString()
	    // - Test empty graph: Should return an empty string
	    // - Test graph with one edge: Should return the edge in the correct format
	    // - Test graph with multiple edges: Should return all edges in correct format
	
	    @Test
	    public void testToString_emptyGraph() {
	        ConcreteEdgesGraph graph = (ConcreteEdgesGraph) emptyInstance();
	        assertEquals("", graph.toString());
	    }
	
	    @Test
	    public void testToString_singleEdge() {
	        ConcreteEdgesGraph graph = (ConcreteEdgesGraph) emptyInstance();
	        graph.add("A");
	        graph.add("B");
	        graph.set("A", "B", 5);
	        assertEquals("A -> B [weight=5]\n", graph.toString());
	    }
	
	    @Test
	    public void testToString_multipleEdges() {
	        ConcreteEdgesGraph graph = (ConcreteEdgesGraph) emptyInstance();
	        graph.add("A");
	        graph.add("B");
	        graph.add("C");
	        graph.set("A", "B", 5);
	        graph.set("B", "C", 10);
	        assertEquals("A -> B [weight=5]\nB -> C [weight=10]\n", graph.toString());
	    }
	
	    /*
	     * Testing Edge...
	     */
	    
	    // Testing strategy for Edge:
	    // - Create edges with valid source, target, and weight
	    // - Ensure edge correctly stores the data (source, target, weight)
	    // - Test toString method of Edge
	    
	    @Test
	    public void testEdge_creation() {
	        Edge edge = new Edge("A", "B", 5);
	        assertEquals("A", edge.source);
	        assertEquals("B", edge.target);
	        assertEquals(5, edge.weight);
	    }
	
	    @Test
	    public void testEdge_toString() {
	        Edge edge = new Edge("A", "B", 5);
	        assertEquals("A -> B [weight=5]", edge.toString());
	    }
	
	    @Test(expected = IllegalArgumentException.class)
	    public void testSetEdge_invalidVertices() {
	        ConcreteEdgesGraph graph = (ConcreteEdgesGraph) emptyInstance();
	        graph.set("A", "B", 5); // "A" and "B" are not in the graph, should throw exception
	    }
	
	    @Test
	    public void testAddVertex() {
	        ConcreteEdgesGraph graph = (ConcreteEdgesGraph) emptyInstance();
	        assertTrue(graph.add("A"));
	        assertTrue(graph.vertices().contains("A"));
	    }
	
	    @Test
	    public void testRemoveVertex() {
	        ConcreteEdgesGraph graph = (ConcreteEdgesGraph) emptyInstance();
	        graph.add("A");
	        graph.add("B");
	        graph.set("A", "B", 5);
	        assertTrue(graph.remove("A"));
	        assertFalse(graph.vertices().contains("A"));
	        assertFalse(graph.sources("B").containsKey("A"));
	        assertFalse(graph.targets("A").containsKey("B"));
	    }
	
	    @Test
	    public void testSetEdge_updateWeight() {
	        ConcreteEdgesGraph graph = (ConcreteEdgesGraph) emptyInstance();
	        graph.add("A");
	        graph.add("B");
	        graph.set("A", "B", 5);
	        int oldWeight = graph.set("A", "B", 10);
	        assertEquals(5, oldWeight); // old weight should be 5
	        assertEquals(10, graph.targets("A").get("B").intValue()); // new weight should be 10
	    }
	
	    @Test
	    public void testSources() {
	        ConcreteEdgesGraph graph = (ConcreteEdgesGraph) emptyInstance();
	        graph.add("A");
	        graph.add("B");
	        graph.add("C");
	        graph.set("A", "B", 5);
	        graph.set("C", "B", 10);
	        
	        Map<String, Integer> sources = graph.sources("B");
	        assertEquals(2, sources.size());
	        assertEquals(5, sources.get("A").intValue());
	        assertEquals(10, sources.get("C").intValue());
	    }
	
	    @Test
	    public void testTargets() {
	        ConcreteEdgesGraph graph = (ConcreteEdgesGraph) emptyInstance();
	        graph.add("A");
	        graph.add("B");
	        graph.add("C");
	        graph.set("A", "B", 5);
	        graph.set("A", "C", 10);
	        
	        Map<String, Integer> targets = graph.targets("A");
	        assertEquals(2, targets.size());
	        assertEquals(5, targets.get("B").intValue());
	        assertEquals(10, targets.get("C").intValue());
	    }
	}
