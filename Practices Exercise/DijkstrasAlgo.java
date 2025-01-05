import java.util.ArrayList;
import java.util.PriorityQueue;

public class DijkstrasAlgo {

    static class Edge {
        int src, dest, wt;

        public Edge(int src, int dest, int wt) {
            this.src = src;
            this.dest = dest;
            this.wt = wt;
        }
    }

    public static void createGraph(ArrayList<Edge>[] graph) {
        for (int i=0; i<graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        // undirected graph
        // graph[0].add(new Edge(0, 2, 2));

        // graph[1].add(new Edge(1, 2, 10));
        // graph[1].add(new Edge(1, 3, 9));
        
        // graph[2].add(new Edge(2, 0, 2));
        // graph[2].add(new Edge(2, 1, 10));
        // graph[2].add(new Edge(2, 3, 12));

        // graph[3].add(new Edge(3, 1, 9));
        // graph[3].add(new Edge(3, 2, 12));


        // Directed Graph
        graph[0].add(new Edge(0, 1, 2));
        graph[0].add(new Edge(0, 2, 4));

        graph[1].add(new Edge(1, 2, 1));
        graph[1].add(new Edge(1, 3, 7));

        graph[2].add(new Edge(2, 4, 3));

        graph[3].add(new Edge(3, 5, 1));

        graph[4].add(new Edge(4, 3, 2));
        graph[4].add(new Edge(4, 5, 5));
    }


    static class Pair implements Comparable<Pair>{
        int node, dist;

        public Pair(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }

        @Override
        public int compareTo(Pair p2) {
            return this.dist - p2.dist; 
        }
    }

    public static void dijkstrasAlgorithm(ArrayList<Edge>[] graph, int src, int V) {

        boolean[] vis = new boolean[V];
        int[] distance = new int[V];
        for (int i = 0; i < V; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        distance[src] = 0;

        PriorityQueue<Pair> pq = new PriorityQueue<>();

        pq.add(new Pair(src, 0));

        while (!pq.isEmpty()) {
            Pair curr = pq.remove();
            if (!vis[curr.node]) {
                vis[curr.node] = true;
                for (int i = 0; i < graph[curr.node].size(); i++) {
                    Edge e = graph[curr.node].get(i);
                    int u = e.src, v = e.dest;
                    if (distance[u] + e.wt < distance[v]) { //Relexation
                        distance[v] = distance[u] + e.wt;
                        pq.add(new Pair(v, distance[v]));
                    }
                }
            }
        }

        for(int i = 0; i< V; i++) {
            System.out.println("At node: " + i + ": distance: " + distance[i] + " ");
        }
        System.out.println();

    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        
        // For directed graph
        int V = 6;

        // For undirected graph
        // int V = 4;

        ArrayList<Edge>[] graph = new ArrayList[V];

        createGraph(graph);

        dijkstrasAlgorithm(graph, 5, V);
    }
}
