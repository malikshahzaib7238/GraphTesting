package graph;

import java.util.*;

/**
 * An implementation of Graph using edges.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   A ConcreteEdgesGraph represents a directed weighted graph with vertices and edges.
    //   The vertices are stored in a set to ensure uniqueness, and the edges are stored in a list.
    //   Each edge has a source, target, and weight.
    // Representation invariant:
    //   - No duplicate vertices in the vertices set.
    //   - The edges list contains valid edges where source and target vertices exist in the vertices set.
    //   - Edge weights are non-negative integers.
    // Safety from rep exposure:
    //   - The vertices and edges collections are encapsulated to avoid external modification.
    
    // Constructor to initialize the graph
    public ConcreteEdgesGraph() {
        // Initializes an empty graph with no vertices or edges.
    }
    
    // Check representation invariant (useful for debugging)
    private void checkRep() {
        // Ensure no duplicate vertices
        assert vertices.size() == new HashSet<>(vertices).size() : "Duplicate vertices found.";
        
        // Ensure all edges have valid source and target vertices
        for (Edge edge : edges) {
            assert vertices.contains(edge.source) : "Edge has an invalid source vertex.";
            assert vertices.contains(edge.target) : "Edge has an invalid target vertex.";
            assert edge.weight >= 0 : "Edge weight must be non-negative.";
        }
    }

    @Override
    public boolean add(String vertex) {
        return vertices.add(vertex); // Adds the vertex if it's not already present
    }

    @Override
    public int set(String source, String target, int weight) {
        if (!vertices.contains(source) || !vertices.contains(target)) {
            throw new IllegalArgumentException("Source or target vertex not found.");
        }

        // If an edge already exists between source and target, update its weight
        for (Edge edge : edges) {
            if (edge.source.equals(source) && edge.target.equals(target)) {
                int oldWeight = edge.weight;
                edge.weight = weight;
                checkRep();
                return oldWeight; // Return the old weight
            }
        }

        // If no edge exists, add a new one
        edges.add(new Edge(source, target, weight));
        checkRep();
        return 0; // Return 0 indicating no previous weight
    }

    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) {
            return false; // Vertex doesn't exist
        }

        // Remove all edges involving this vertex
        edges.removeIf(edge -> edge.source.equals(vertex) || edge.target.equals(vertex));
        vertices.remove(vertex); // Remove the vertex itself
        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        return new HashSet<>(vertices); // Return a copy of the vertices set
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.target.equals(target)) {
                sources.put(edge.source, edge.weight);
            }
        }
        return sources;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.source.equals(source)) {
                targets.put(edge.target, edge.weight);
            }
        }
        return targets;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Edge edge : edges) {
            sb.append(edge.source).append(" -> ").append(edge.target).append(" [weight=").append(edge.weight).append("]\n");
        }
        return sb.toString();
    }
}

/**
 * Represents an edge in the graph.
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 */
class Edge {
    
    final String source;
    final String target;
    int weight;
    
    // Abstraction function:
    //   An Edge represents a directed edge between a source and target vertex with a weight.
    // Representation invariant:
    //   - The source and target must be valid vertices in the graph.
    //   - The weight must be non-negative.
    // Safety from rep exposure:
    //   - The source and target fields are immutable.
    //   - Weight is mutable but can only be modified via the set method in ConcreteEdgesGraph.

    // Constructor for the edge
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    // Check representation invariant for debugging
    private void checkRep() {
        assert source != null : "Source vertex cannot be null";
        assert target != null : "Target vertex cannot be null";
        assert weight >= 0 : "Edge weight must be non-negative";
    }

    @Override
    public String toString() {
        return source + " -> " + target + " [weight=" + weight + "]";
    }
}
