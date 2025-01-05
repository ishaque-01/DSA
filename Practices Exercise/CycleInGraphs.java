import java.util.ArrayList;

public class CycleInGraphs {

    static class Edge {
        int src, dest;

        public Edge(int s, int d) {
            src = s;
            dest = d;
        }
    }

    public static void createGaphUnDir(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        graph[0].add(new Edge(0, 1));
        graph[0].add(new Edge(0, 2));
        graph[0].add(new Edge(0, 3));

        graph[1].add(new Edge(1, 0));

        graph[2].add(new Edge(2, 0));
        graph[2].add(new Edge(2, 3));

        graph[3].add(new Edge(3, 0));
        graph[3].add(new Edge(3, 2));
    }

    public static void createGaphDir(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        graph[0].add(new Edge(0, 2));

        graph[1].add(new Edge(1, 0));

        graph[2].add(new Edge(2, 3));

        graph[3].add(new Edge(3, 0));
    }

    // DFS for detecting cycle in Directed Graph
    // If recursion stack has already true means there is a cycle
    public static boolean detectCycleDir(ArrayList<Edge>[] graph, boolean[] vis, int curr, boolean[] recStack) {
        vis[curr] = true;
        recStack[curr] = true;

        for (int i = 0; i < graph[curr].size(); i++) {
            Edge e = graph[curr].get(i);
            if (recStack[e.dest]) {
                return true;
            } else if (!vis[e.dest] && detectCycleDir(graph, vis, e.dest, recStack)) {
                return true;
            }
        }
        recStack[curr] = false;
        return false;
    }

    // DFS for detecting cycle in un directed graph
    public static boolean detectCycleUnDir(ArrayList<Edge>[] graph, boolean[] vis, int curr, int parent) {
        vis[curr] = true;

        for (int i = 0; i < graph[curr].size(); i++) {
            Edge e = graph[curr].get(i);
            if(!vis[e.dest]) {
                if (detectCycleUnDir(graph, vis, e.dest, curr)) {
                    return true;
                }
            } else if(e.dest != parent) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        int V = 4;

        ArrayList<Edge>[] graph1 = new ArrayList[V];

        createGaphDir(graph1);

        boolean[] vis = new boolean[V];
        boolean[] rec = new boolean[V];

        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                boolean isCycle = detectCycleDir(graph1, vis, i, rec);
                if (isCycle)
                    System.out.println("Cycle in directed graph: true");
                break;
            }
        }

        for(int i=0; i<V; i++) {
            vis[i] = false;
            rec[i] = false;
        }

        ArrayList<Edge>[] graph2 = new ArrayList[V];
        createGaphUnDir(graph2);

        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                boolean isCycle = detectCycleUnDir(graph2, vis, i, -1);
                if (isCycle)
                    System.out.println("Cycle in un-directed graph: true");
                break;
            }
        }
    }
}
