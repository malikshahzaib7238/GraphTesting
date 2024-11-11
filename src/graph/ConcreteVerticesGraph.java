package graph;

import java.util.*;

/**
 * An implementation of Graph using vertices and edges.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   A ConcreteVerticesGraph represents a directed weighted graph where each vertex is uniquely labeled,
    //   and edges have weights. The graph is represented by a list of Vertex objects where each vertex contains
    //   its label and a map of its outgoing edges with the corresponding weights.
    // Representation invariant:
    //   - No two vertices can have the same label.
    //   - Each edge has a non-negative weight.
    // Safety from rep exposure:
    //   - The list of vertices and their contents are encapsulated to prevent external modification.
    
    // Constructor to initialize the graph
    public ConcreteVerticesGraph() {
        // Initializes an empty graph with no vertices.
    }
    
    // Check representation invariant (useful for debugging)
    private void checkRep() {
        // Ensure no duplicate vertices in the graph
        Set<String> vertexLabels = new HashSet<>();
        for (Vertex vertex : vertices) {
            assert vertex != null : "Vertex should not be null";
            assert !vertexLabels.contains(vertex.label) : "Duplicate vertex found: " + vertex.label;
            vertexLabels.add(vertex.label);
        }
    }

    @Override
    public boolean add(String vertex) {
        for (Vertex v : vertices) {
            if (v.label.equals(vertex)) {
                return false; // Vertex already exists
            }
        }
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }

    @Override
    public int set(String source, String target, int weight) {
        Vertex sourceVertex = getVertex(source);
        Vertex targetVertex = getVertex(target);
        
        if (weight == 0) {
            // Remove edge if weight is 0
            if (sourceVertex != null) {
                return sourceVertex.removeEdge(target);
            }
        } else {
            // Add or update the edge with the given weight
            if (sourceVertex == null) {
                add(source); // Add the source vertex if not already present
                sourceVertex = getVertex(source);
            }
            if (targetVertex == null) {
                add(target); // Add the target vertex if not already present
                targetVertex = getVertex(target);
            }
            return sourceVertex.setEdge(target, weight);
        }
        return 0;
    }

    @Override
    public boolean remove(String vertex) {
        Vertex v = getVertex(vertex);
        if (v == null) {
            return false; // Vertex does not exist
        }
        // Remove the vertex and all its edges
        vertices.remove(v);
        for (Vertex vertexToCheck : vertices) {
            vertexToCheck.removeEdge(vertex); // Remove any edges pointing to the removed vertex
        }
        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        Set<String> vertexLabels = new HashSet<>();
        for (Vertex v : vertices) {
            vertexLabels.add(v.label);
        }
        return vertexLabels;
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex v : vertices) {
            int weight = v.getEdgeWeight(target);
            if (weight != 0) {
                sources.put(v.label, weight);
            }
        }
        return sources;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        Vertex sourceVertex = getVertex(source);
        if (sourceVertex != null) {
            targets.putAll(sourceVertex.getEdges());
        }
        return targets;
    }

    // Helper method to get a vertex by its label
    private Vertex getVertex(String label) {
        for (Vertex v : vertices) {
            if (v.label.equals(label)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) {
            sb.append(v.label).append(" -> ").append(v.getEdges()).append("\n");
        }
        return sb.toString();
    }
}

/**
 * Represents a vertex in the graph.
 * Each vertex contains a label and a map of its outgoing edges.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {
    
    final String label;
    private final Map<String, Integer> edges; // Maps target vertex to the edge weight
    
    // Abstraction function:
    //   A Vertex object contains a label (the vertex's identifier) and a map of its outgoing edges with weights.
    // Representation invariant:
    //   - The vertex's label must be unique within the graph.
    //   - All edge weights must be non-negative.
    // Safety from rep exposure:
    //   - The edges map is encapsulated to avoid direct modification from outside.

    // Constructor for the vertex
    public Vertex(String label) {
        this.label = label;
        this.edges = new HashMap<>();
    }

    // Check representation invariant for debugging
    private void checkRep() {
        assert label != null : "Label cannot be null";
        for (Map.Entry<String, Integer> entry : edges.entrySet()) {
            assert entry.getValue() >= 0 : "Edge weight cannot be negative";
        }
    }

    // Sets an edge from this vertex to another with the given weight
    public int setEdge(String target, int weight) {
        if (weight == 0) {
            return removeEdge(target); // Remove the edge if weight is zero
        }
        return edges.put(target, weight) != null ? weight : 0; // Return old weight or 0 if new edge
    }

    // Removes the edge to the given target and returns the previous weight
    public int removeEdge(String target) {
        return edges.remove(target) != null ? edges.size() : 0;
    }

    // Returns the weight of the edge to the given target
    public int getEdgeWeight(String target) {
        return edges.getOrDefault(target, 0);
    }

    // Returns all outgoing edges from this vertex
    public Map<String, Integer> getEdges() {
        return new HashMap<>(edges); // Return a copy to protect the internal structure
    }

    @Override
    public String toString() {
        return label + ": " + edges.toString();
    }
}
