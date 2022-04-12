// --== CS400 File Header Information ==--
// Name: Lee Hung Ting
// Email: hlee864@@wisc.edu
// Notes to Grader: None

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.NoSuchElementException;

public class CS400Graph<T> implements GraphADT<T> {

    /**
     * Vertex objects group a data field with an adjacency list of weighted
     * directed edges that lead away from them.
     */
    protected class Vertex {
        public T data; // vertex label or application specific data
        public LinkedList<Edge> edgesLeaving;

        public Vertex(T data) {
            this.data = data;
            this.edgesLeaving = new LinkedList<>();
        }
    }

    /**
     * Edge objects are stored within their source vertex, and group together
     * their target destination vertex, along with an integer weight.
     */
    protected class Edge {
        public Vertex target;
        public int weight;

        public Edge(Vertex target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    protected Hashtable<T, Vertex> vertices; // holds graph verticies, key=data
    public CS400Graph() { vertices = new Hashtable<>(); }

    /**
     * Insert a new vertex into the graph.
     * 
     * @param data the data item stored in the new vertex
     * @return true if the data can be inserted as a new vertex, false if it is 
     *     already in the graph
     * @throws NullPointerException if data is null
     */
    public boolean insertVertex(T data) {
        if(data == null) 
            throw new NullPointerException("Cannot add null vertex");
        if(vertices.containsKey(data)) return false; // duplicate values are not allowed
        vertices.put(data, new Vertex(data));
        return true;
    }
    
    /**
     * Remove a vertex from the graph.
     * Also removes all edges adjacent to the vertex from the graph (all edges 
     * that have the vertex as a source or a destination vertex).
     * 
     * @param data the data item stored in the vertex to remove
     * @return true if a vertex with *data* has been removed, false if it was not in the graph
     * @throws NullPointerException if data is null
     */
    public boolean removeVertex(T data) {
        if(data == null) throw new NullPointerException("Cannot remove null vertex");
        Vertex removeVertex = vertices.get(data);
        if(removeVertex == null) return false; // vertex not found within graph
        // search all vertices for edges targeting removeVertex 
        for(Vertex v : vertices.values()) {
            Edge removeEdge = null;
            for(Edge e : v.edgesLeaving)
                if(e.target == removeVertex)
                    removeEdge = e;
            // and remove any such edges that are found
            if(removeEdge != null) v.edgesLeaving.remove(removeEdge);
        }
        // finally remove the vertex and all edges contained within it
        return vertices.remove(data) != null;
    }
    
    /**
     * Insert a new directed edge with a positive edge weight into the graph.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @param weight the weight for the edge (has to be a positive integer)
     * @return true if the edge could be inserted or its weight updated, false 
     *     if the edge with the same weight was already in the graph
     * @throws IllegalArgumentException if either source or target or both are not in the graph, 
     *     or if its weight is < 0
     * @throws NullPointerException if either source or target or both are null
     */
    public boolean insertEdge(T source, T target, int weight) {
        if(source == null || target == null) 
            throw new NullPointerException("Cannot add edge with null source or target");
        Vertex sourceVertex = this.vertices.get(source);
        Vertex targetVertex = this.vertices.get(target);
        if(sourceVertex == null || targetVertex == null) 
            throw new IllegalArgumentException("Cannot add edge with vertices that do not exist");
        if(weight < 0) 
            throw new IllegalArgumentException("Cannot add edge with negative weight");
        // handle cases where edge already exists between these verticies
        for(Edge e : sourceVertex.edgesLeaving)
            if(e.target == targetVertex) {
                if(e.weight == weight) return false; // edge already exists
                else e.weight = weight; // otherwise update weight of existing edge
                return true;
            }
        // otherwise add new edge to sourceVertex
        sourceVertex.edgesLeaving.add(new Edge(targetVertex,weight));
        return true;
    }    
    
    /**
     * Remove an edge from the graph.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return true if the edge could be removed, false if it was not in the graph
     * @throws IllegalArgumentException if either source or target or both are not in the graph
     * @throws NullPointerException if either source or target or both are null
     */
    public boolean removeEdge(T source, T target) {
        if(source == null || target == null) throw new NullPointerException("Cannot remove edge with null source or target");
        Vertex sourceVertex = this.vertices.get(source);
        Vertex targetVertex = this.vertices.get(target);
        if(sourceVertex == null || targetVertex == null) throw new IllegalArgumentException("Cannot remove edge with vertices that do not exist");
        // find edge to remove
        Edge removeEdge = null;
        for(Edge e : sourceVertex.edgesLeaving)
            if(e.target == targetVertex) 
                removeEdge = e;
        if(removeEdge != null) { // remove edge that is successfully found                
            sourceVertex.edgesLeaving.remove(removeEdge);
            return true;
        }
        return false; // otherwise return false to indicate failure to find
    }
    
    /**
     * Check if the graph contains a vertex with data item *data*.
     * 
     * @param data the data item to check for
     * @return true if data item is stored in a vertex of the graph, false otherwise
     * @throws NullPointerException if *data* is null
     */
    public boolean containsVertex(T data) {
        if(data == null) throw new NullPointerException("Cannot contain null data vertex");
        return vertices.containsKey(data);
    }
    
    /**
     * Check if edge is in the graph.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return true if the edge is in the graph, false if it is not in the graph
     * @throws NullPointerException if either source or target or both are null
     */
    public boolean containsEdge(T source, T target) {
        if(source == null || target == null) throw new NullPointerException("Cannot contain edge adjacent to null data"); 
        Vertex sourceVertex = vertices.get(source);
        Vertex targetVertex = vertices.get(target);
        if(sourceVertex == null) return false;
        for(Edge e : sourceVertex.edgesLeaving)
            if(e.target == targetVertex)
                return true;
        return false;
    }
    
    /**
     * Return the weight of an edge.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return the weight of the edge (0 or positive integer)
     * @throws IllegalArgumentException if either sourceVertex or targetVertex or both are not in the graph
     * @throws NullPointerException if either sourceVertex or targetVertex or both are null
     * @throws NoSuchElementException if edge is not in the graph
     */
    public int getWeight(T source, T target) {
        if(source == null || target == null) throw new NullPointerException("Cannot contain weighted edge adjacent to null data"); 
        Vertex sourceVertex = vertices.get(source);
        Vertex targetVertex = vertices.get(target);
        if(sourceVertex == null || targetVertex == null) throw new IllegalArgumentException("Cannot retrieve weight of edge between vertices that do not exist");
        for(Edge e : sourceVertex.edgesLeaving)
            if(e.target == targetVertex)
                return e.weight;
        throw new NoSuchElementException("No directed edge found between these vertices");
    }
    
    /**
     * Return the number of edges in the graph.
     * 
     * @return the number of edges in the graph
     */
    public int getEdgeCount() {
        int edgeCount = 0;
        for(Vertex v : vertices.values())
            edgeCount += v.edgesLeaving.size();
        return edgeCount;
    }
    
    /**
     * Return the number of vertices in the graph
     * 
     * @return the number of vertices in the graph
     */
    public int getVertexCount() {
        return vertices.size();
    }

    /**
     * Check if the graph is empty (does not contain any vertices or edges).
     * 
     * @return true if the graph does not contain any vertices or edges, false otherwise
     */
    public boolean isEmpty() {
        return vertices.size() == 0;
    }

    /**
     * Path objects store a discovered path of vertices and the overal distance of cost
     * of the weighted directed edges along this path. Path objects can be copied and extended
     * to include new edges and verticies using the extend constructor. In comparison to a
     * predecessor table which is sometimes used to implement Dijkstra's algorithm, this
     * eliminates the need for tracing paths backwards from the destination vertex to the
     * starting vertex at the end of the algorithm.
     */
    protected class Path implements Comparable<Path> {
        public Vertex start; // first vertex within path
        public int distance; // sumed weight of all edges in path
        public List<T> dataSequence; // ordered sequence of data from vertices in path
        public Vertex end; // last vertex within path

        /**
         * Creates a new path containing a single vertex.  Since this vertex is both
         * the start and end of the path, it's initial distance is zero.
         * @param start is the first vertex on this path
         */
        public Path(Vertex start) {
            this.start = start;
            this.distance = 0;
            this.dataSequence = new LinkedList<>();
            this.dataSequence.add(start.data);
            this.end = start;
        }

        /**
         * This extension constructor makes a copy of the path passed into it as an argument
         * without affecting the original path object (copyPath). The path is then extended
         * by the Edge object extendBy.
         * @param copyPath is the path that is being copied
         * @param extendBy is the edge the copied path is extended by
         */
        public Path(Path copyPath, Edge extendBy) {
            // TODO: Implement this constructor in Step 5.
            // This constructor should copy the path passed to it, 
              // and extend it by a single edge passed as the second parameter.
            this.start = copyPath.start;

            // The final node of the new path will be the target vertex of this edge.
            this.end = extendBy.target;
            
            // This extension will require to update the path's overall costs too. 
            this.distance = copyPath.distance+extendBy.weight;
            
            // Make sure that the dataSequence field in the newly constructed instance 
              // references a different list object than the source path did. 
            this.dataSequence = (List<T>) ((LinkedList) copyPath.dataSequence).clone();
            
            this.dataSequence.add((T) extendBy.target.data); // add target of edge to linkedlist 
        }

        /**
         * Allows the natural ordering of paths to be increasing with path distance.
         * When path distance is equal, the string comparison of end vertex data is used to break ties.
         * @param other is the other path that is being compared to this one
         * @return -1 when this path has a smaller distance than the other,
         *         +1 when this path has a larger distance that the other,
         *         and the comparison of end vertex data in string form when these distances are tied
         */
        public int compareTo(Path other) {
            int cmp = this.distance - other.distance;
            if(cmp != 0) return cmp; // use path distance as the natural ordering
            // when path distances are equal, break ties by comparing the string
            // representation of data in the end vertex of each path

            return this.end.data.toString().compareTo(other.end.data.toString());
        }
    }

    /**
     * Uses Dijkstra's shortest path algorithm to find and return the shortest path 
     * between two vertices in this graph: start and end. This path contains an ordered list
     * of the data within each node on this path, and also the distance or cost of all edges
     * that are a part of this path.
     * @param start data item within first node in path
     * @param end data item within last node in path
     * @return the shortest path from start to end, as computed by Dijkstra's algorithm
     * @throws NoSuchElementException when no path from start to end can be found,
     *     including when no vertex containing start or end can be found
     */
    protected Path dijkstrasShortestPath(T start, T end) throws NoSuchElementException{  
        // If your search fails to find any path from the start to end vertices, then this method should throw a NoSuchElementException
      if (start==null | end==null) throw new NoSuchElementException("the argument is illegal");
      if (!containsVertex(start) | !containsVertex(end)) throw new NoSuchElementException("Cannot find provided vertex");
        
        Vertex Vstart = this.vertices.get(start); // use data from Hashtable vertices to create node
        Vertex Vend = this.vertices.get(end);
        
        // create an instance of PriorityQueue to hold the discovered paths 
        // that have not yet been determined to be the shortest possible 
        // paths to their given end vertex

        PriorityQueue<Path> q = new PriorityQueue<Path>(); // temp queue to hold new path
        ArrayList<Vertex> visited = new ArrayList<Vertex>(); // record visited node
        PriorityQueue<Path> settled = new PriorityQueue<Path>(); // record shortest path within each round
        Path curr_path = new Path(Vstart); // initial path is Vstart -> itself
        
        visited.add(Vstart); 
        settled.add(curr_path);
        
        while (curr_path.end.edgesLeaving.size()>0 | q.size()>0) {
          System.out.println("new round");
          System.out.print("visited contains ");
          for(Vertex v : visited) System.out.print(v.data + " ");
          System.out.print("| ");
          System.out.print("q contains ");
          for(Path p : q) System.out.print(p.dataSequence + " ");
          System.out.print("| ");
          System.out.print("settled contains ");
          for(Path p : settled) System.out.print(p.dataSequence + " ");
          System.out.print("| ");
          System.out.print("curr_path = " + curr_path.dataSequence);
          System.out.println();

            Vertex v = curr_path.end; // use end of current path to find new edges
            System.out.print("current vertex = " + v.data + ", current vertex.edgesLeaving = " + showEdges(v) + ", q = "); 
            System.out.print("{");
            for(Path p : q) System.out.print(p.dataSequence + " ");
            System.out.print("}");
            System.out.println();
            
            for(Edge i : v.edgesLeaving) { // v.edgesLeaving is linkedlist of edges
              if (this.containsEdge((T)curr_path.end.data, (T)i.target.data)) { // ensure the edge exists
                  Path new_path =  new Path(curr_path, i); // combine current path and this edge to create new path
                  if (!visited.contains(i.target) & !containsPath(q, new_path)
                      & !q.contains(new_path) & !settled.contains(new_path)
                      ) { // ensure target of edge has not been visited and path has not been found
                      q.add(new_path); // put new path to temp queue
                      System.out.println(new_path.dataSequence + " is added to q");
                  }
                }
              }
          
          Path shortestPath = evaluatePath(q); // find the shortest path in queue this round
          System.out.println("shortest this round = " + shortestPath.dataSequence + ", removed from q\n");
          settled.add(shortestPath); // put in settled 
          q.remove(shortestPath); // remove from queue 
          Vertex Vnew = shortestPath.end; 
          if (Vnew==Vend) return shortestPath; // if new end equals initial end then shortest path is found
          visited.add(Vnew); 
          curr_path = shortestPath; // otherwise put new end into visited and record current path to use it next round
        }
        // throw when all vertices are traversed but no path can find the way to Vend
        throw new NoSuchElementException("No directed path found between these vertices");
    }    
    
    private String showEdges(Vertex v){
      String res = "";
      for(Edge e : v.edgesLeaving) {
        res += "[";
        res += v.data;
        res += "->";
        res += e.target.data;
        res += "] ";
      }
      return res;
    }
    
    private Boolean containsPath(PriorityQueue<Path> q, Path target){
        for(Path p : q) {
          if (p.compareTo(target)==0) return true;
        }
        return false;
    }    
    
    private Path evaluatePath(PriorityQueue<Path> q){
        Path shortestPath = q.peek();
 
        for(Path p : q) {
          if (p!=q.peek()) {
            System.out.println(p.dataSequence + " " + p.distance + " vs " + shortestPath.dataSequence + " " + shortestPath.distance);
            if (p.compareTo(shortestPath)<0) shortestPath = p;
          }
        }
        return shortestPath;
    }    
    
    /**
     * Returns the shortest path between start and end.
     * Uses Dijkstra's shortest path algorithm to find the shortest path.
     * 
     * @param start the data item in the starting vertex for the path
     * @param end the data item in the destination vertex for the path
     * @return list of data item in vertices in order on the shortest path between vertex 
     * with data item start and vertex with data item end, including both start and end 
     * @throws NoSuchElementException when no path from start to end can be found
     *     including when no vertex containing start or end can be found
     */
    public List<T> shortestPath(T start, T end) throws NoSuchElementException{
        if (start==null | end==null) throw new NoSuchElementException("No directed path found between these vertices");
        return dijkstrasShortestPath(start,end).dataSequence;
    }
    
    /**
     * Returns the cost of the path (sum over edge weights) between start and end.
     * Uses Dijkstra's shortest path algorithm to find the shortest path.
     * 
     * @param start the data item in the starting vertex for the path
     * @param end the data item in the end vertex for the path
     * @return the cost of the shortest path between vertex with data item start 
     * and vertex with data item end, including all edges between start and end
     * @throws NoSuchElementException when no path from start to end can be found
     *     including when no vertex containing start or end can be found
     */
    public int getPathCost(T start, T end) throws NoSuchElementException{
        if (start==null | end==null) throw new NoSuchElementException("No directed path found between these vertices");
        return dijkstrasShortestPath(start, end).distance;
    }   
    
    
    
    public static void main(String[] args) {
      // TODO Auto-generated method stub
      CS400Graph<String> x = new CS400Graph();
      
//      CS400Graph.Vertex a = x.new Vertex(0);
//      CS400Graph.Vertex b = x.new Vertex(1);
    
      x.insertVertex("A");
      x.insertVertex("B");
      x.insertVertex("C");
      x.insertVertex("D");
      x.insertVertex("E");
      x.insertVertex("F");
      
      x.insertEdge("A", "B", 5);
      x.insertEdge("A", "C", 10);
      x.insertEdge("A", "E", 2);
      x.insertEdge("B", "C", 2);
      x.insertEdge("B", "D", 4);
      x.insertEdge("C", "D", 7);
      x.insertEdge("D", "F", 40);
      x.insertEdge("C", "E", 3);   
      
      CS400Graph.Path p = x.dijkstrasShortestPath("A", "D");
      System.out.println(p.start.data + " -> " + p.distance + " -> " + p.end.data 
          + ", dataSequence = " + p.dataSequence);
    }
}