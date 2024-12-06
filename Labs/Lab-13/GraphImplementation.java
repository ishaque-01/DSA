import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GraphImplementation {

    static class Edge {
        int src, dest;

        public Edge(int src, int dest) {
            this.src = src;
            this.dest = dest;
        }
    }

    // Undirected && UuWeighted Graph
    public static void createUnDirectedGraph(ArrayList<Edge>[] graph) {

        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        graph[0].add(new Edge(0, 1));
        graph[0].add(new Edge(0, 2));

        graph[1].add(new Edge(1, 0));
        graph[1].add(new Edge(1, 3));

        graph[2].add(new Edge(2, 0));
        graph[2].add(new Edge(2, 4));

        graph[3].add(new Edge(3, 1));
        graph[3].add(new Edge(3, 4));
        graph[3].add(new Edge(3, 5));

        graph[4].add(new Edge(4, 2));
        graph[4].add(new Edge(4, 3));
        graph[4].add(new Edge(4, 5));

        graph[5].add(new Edge(5, 3));
        graph[5].add(new Edge(5, 4));
        graph[5].add(new Edge(5, 6));

        graph[6].add(new Edge(6, 5));

    }

    // Directed && UuWeighted Graph
    public static void createDirectedGraph(ArrayList<Edge>[] graph) {

        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        graph[0].add(new Edge(0, 1));
        graph[0].add(new Edge(0, 2));
        graph[0].add(new Edge(0, 3));

        graph[1].add(new Edge(1, 3));

        graph[2].add(new Edge(2, 0));
        graph[2].add(new Edge(2, 1));
    }

    public static void getAllNeighbours(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].size(); j++) {
                Edge e = graph[i].get(j);
                System.out.println("Src: " + e.src + " dest/neighbour: " + e.dest);
            }
        }
    }

    public static void dfs(ArrayList<Edge>[] graph, int startVertex, boolean vis[]) {
        System.out.print(startVertex + " ");
        vis[startVertex] = true;
        for (int i = 0; i < graph[startVertex].size(); i++) {
            Edge e = graph[startVertex].get(i);
            if (vis[e.dest] == false)
                dfs(graph, e.dest, vis);
        }
    }

    public static void bfs(ArrayList<Edge>[] graph, int vertices) {
        boolean[] vis = new boolean[vertices];

        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        while (!q.isEmpty()) {
            int curr = q.remove();
            if (!vis[curr]) {
                System.out.print(curr + " ");
                vis[curr] = true;
                for (int i = 0; i < graph[curr].size(); i++) {
                    Edge e = graph[curr].get(i);
                    q.add(e.dest);
                }
            }
        }

    }
    
  public static void printAllPath(ArrayList<Edge>[] graph, boolean[] vis, int curr, String path, int tar){
        if(curr == tar){
            System.out.println(path);
            return;
        }

        for(int i=0; i<graph[curr].size(); i++){
            Edge e = graph[curr].get(i);
            if(!vis[e.dest]){
                vis[curr] = true;
                printAllPath(graph, vis, e.dest, path+" "+e.dest, tar);
                vis[curr] = false;
            }
        }
    }
     

    public static void main(String[] args) {

        int vertices = 7;
        ArrayList<Edge>[] graph = new ArrayList[vertices];

        createUnDirectedGraph(graph);
        getAllNeighbours(graph);

        boolean[] vis = new boolean[vertices];
        System.out.println("Depth-first search");
        dfs(graph, 0, vis);

        System.out.println("\nBredth-first search");
        bfs(graph, vertices);
        
        for (int i = 0; i < vis.length; i++) {
            vis[i] = false;
        }

        int src = 0, tar = 5;
        System.out.println("\nPrinting path from 0-5");
        printAllPath(graph, vis, src, "0", tar);


        for (int i = 0; i < vis.length; i++) {
            vis[i] = false;
        }
        int vertices2 = 4;
        ArrayList<Edge>[] graph2 = new ArrayList[vertices2];
        createDirectedGraph(graph2);
        System.out.println("Printing all paths from 2-3: ");
        printAllPath(graph2, vis, 2, "2", 3);

    }
}
