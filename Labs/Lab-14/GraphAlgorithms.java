import java.util.ArrayList;

public class GraphAlgorithms {

    private int numVertices;
    private ArrayList<Pair> graph[];

    GraphAlgorithms(int vertices) {
        this.numVertices = vertices;
        graph = new ArrayList[numVertices];
    }

    class Pair implements Comparable<Pair> {

        public int node;
        public int weight;
        
        public Pair(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    
        @Override
        public int compareTo(Pair node) {
            return this.weight - node.weight;    
        }
    
        public void addEdge(int src, int dest, int weight) {
            graph[src].add(new Pair(dest, weight));
            graph[dest].add(new Pair(src, weight));
        }
    
        public void getAllNeighbour() {
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph[i].size(); j++) {
                    System.out.println("Src: " + graph[i] + " dest/neighbour: " + graph[i].get(j));
                }
            }
        }
    }

    public static void main(String[] args) {
        GraphAlgorithms ga = new GraphAlgorithms(8);
        Pair p = new Pair(0, 0);
    }



}
