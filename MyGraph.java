import java.util.*;

/**
 * A representation of a graph.
 * Assumes that we do not have negative cost edges in the graph.
 */
public class MyGraph implements Graph {
    // you will need some private fields to represent the graph
    // you are also likely to want some private helper methods

    // YOUR CODE HERE
	private Map<Vertex, ArrayList<Edge>> adj;
	private List<Edge> edges;
	 private ArrayList<Vertex> myVertices;

    /**
     * Creates a MyGraph object with the given collection of vertices
     * and the given collection of edges.
     * @param v a collection of the vertices in this graph
     * @param e a collection of the edges in this graph
     */
    public MyGraph(Collection<Vertex> v, Collection<Edge> e) {

	// YOUR CODE HERE
    	  myVertices = new ArrayList<Vertex>();
          edges = new ArrayList<Edge>();
          
          myVertices.addAll(v);
          edges.addAll(e);

    }

    /** 
     * Return the collection of vertices of this graph
     * @return the vertices as a collection (which is anything iterable)
     */
    public Collection<Vertex> vertices() {

	// YOUR CODE HERE
        return myVertices;

    }

    /** 
     * Return the collection of edges of this graph
     * @return the edges as a collection (which is anything iterable)
     */
    public Collection<Edge> edges() {

	// YOUR CODE HERE
		return edges;

    }

    /**
     * Return a collection of vertices adjacent to a given vertex v.
     *   i.e., the set of all vertices w where edges v -> w exist in the graph.
     * Return an empty collection if there are no adjacent vertices.
     * @param v one of the vertices in the graph
     * @return an iterable collection of vertices adjacent to v in the graph
     * @throws IllegalArgumentException if v does not exist.
     */
    public Collection<Vertex> adjacentVertices(Vertex v) {

	// YOUR CODE HERE
    	if (!myVertices.contains(v)){
            throw new IllegalArgumentException();
        }
        
        ArrayList<Vertex> adVertices = new ArrayList <Vertex>();
        
        for(int i = 0; i < edges.size(); i++){
            Edge current = edges.get(i);
            if(v.equals(current.getSource())){
                adVertices.add(current.getDestination());
            }
        }
        return adVertices;
    }

    /**
     * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed graph.
     * Assumes that we do not have negative cost edges in the graph.
     * @param a one vertex
     * @param b another vertex
     * @return cost of edge if there is a directed edge from a to b in the graph, 
     * return -1 otherwise.
     * @throws IllegalArgumentException if a or b do not exist.
     */
    public int edgeCost(Vertex a, Vertex b) {

	// YOUR CODE HERE
    	if (!adj.containsKey(a))
			return -1;
		for (Edge v : adj.get(a)) {
			if (v.getDestination().equals(b))
				return v.getWeight();
		}
		return -1;

    }

    /**
     * Returns the shortest path from a to b in the graph, or null if there is
     * no such path.  Assumes all edge weights are nonnegative.
     * Uses Dijkstra's algorithm.
     * @param a the starting vertex
     * @param b the destination vertex
     * @return a Path where the vertices indicate the path from a to b in order
     *   and contains a (first) and b (last) and the cost is the cost of 
     *   the path. Returns null if b is not reachable from a.
     * @throws IllegalArgumentException if a or b does not exist.
     */
    public Path shortestPath(Vertex a, Vertex b) {

	// YOUR CODE HERE (you might comment this out this method while doing Part 1)
    	if (!adj.containsKey(a) || !adj.containsKey(b))
			return null;
		List<Vertex> vertices = new ArrayList<Vertex>();
		if (a.equals(b)) {
			vertices.add(a);
			return new Path(vertices, 0);
		}
		for (Vertex v: adj.keySet()) {
			v.distance = Integer.MAX_VALUE;
			v.known = false;
		}
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>();
		a.distance = 0;
		q.add(a);
		vertices = dijkstra(q, b, adj.keySet());
		return new Path(vertices, b.distance);
	}

	private List<Vertex> dijkstra(PriorityQueue<Vertex> q, Vertex b, Set<Vertex> keys) {
		while (!q.isEmpty()) {
			Vertex current = q.poll();
			current.known = true;

			for (Edge e: adj.get(current)) {
				Vertex next = null;
				for (Vertex v: keys) {
					if (v.equals(e.getDestination())) {
						next = v;
						break;
					}
				}
				if (!next.known) {
					int c1 = current.distance + e.getWeight();
					int c2 = next.distance;

					if (c1 < c2) {
						q.remove(next);
						next.distance = c1;
						next.path = current;
						q.add(next);
						if (next.equals(b)) {
							b.distance = next.distance;
							b.path = next.path;
						}
					}
				}
			}
		}
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = b; vertex != null; vertex = vertex.path)
			path.add(vertex);
		Collections.reverse(path);
		return path;
	}
    }

    
    


