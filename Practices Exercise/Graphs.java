import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class Graphs {
    static class Edge {
        char src, dest;

        public Edge(char s, char d) {
            src = s;
            dest = d;
        }
    }

    public static void createGaphUnDir(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        graph[0].add(new Edge('A', 'B'));
        graph[0].add(new Edge('A', 'C'));

        graph[1].add(new Edge('B', 'A'));
        graph[1].add(new Edge('B', 'C'));
        graph[1].add(new Edge('B', 'D'));

        graph[2].add(new Edge('C', 'A'));
        graph[2].add(new Edge('C', 'B'));
        graph[2].add(new Edge('C', 'E'));

        graph[3].add(new Edge('D', 'B'));
        graph[3].add(new Edge('D', 'E'));
        graph[3].add(new Edge('D', 'F'));

        graph[4].add(new Edge('E', 'D'));
        graph[4].add(new Edge('E', 'C'));
        graph[4].add(new Edge('E', 'F'));

        graph[5].add(new Edge('F', 'D'));
        graph[5].add(new Edge('F', 'E'));
    }

    public static void bfs(ArrayList<Edge>[] graph) {
        boolean[] vis = new boolean[graph.length];
        Queue<Character> q = new LinkedList<>();

        q.add(graph[0].get(0).src);

        while (!q.isEmpty()) {
            char curr = q.remove();
            if (!vis[curr - 'A']) {
                System.out.print(curr + " ");
                vis[curr - 'A'] = true;
                for (int i = 0; i < graph[curr - 'A'].size(); i++) {
                    Edge e = graph[curr - 'A'].get(i);
                    q.add(e.dest);
                }
            }
        }
        System.out.println();
    }

    public static void dfs(ArrayList<Edge>[] graph, boolean[] vis, char start) {
        System.out.print(start + " ");
        vis[start - 'A'] = true;

        for (int i = 0; i < graph[start - 'A'].size(); i++) {
            Edge e = graph[start - 'A'].get(i);
            if (!vis[e.dest - 'A']) {
                dfs(graph, vis, e.dest);
            }
        }
    }

    public static void printAllPath(ArrayList<Edge>[] graph, boolean[] vis, char src, char tar, String path) {
        if (src == tar) {
            System.out.println(path);
            return;
        }

        for (int i = 0; i < graph[src - 'A'].size(); i++) {
            Edge e = graph[src - 'A'].get(i);
            if(!vis[src - 'A']) {
                vis[src - 'A'] = true;
                printAllPath(graph, vis, e.dest, tar, path + e.dest);
                vis[src - 'A'] = false; 
            }
        }
    }

    public static void main(String[] args) {
        int V = 6;

        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] graph = new ArrayList[V];

        createGaphUnDir(graph);

        /*
         * a == 0
         * b == 1
         * c == 2
         * d == 3
         * e == 4
         * f == 5
         */

        // System.out.println("Neighbours of C:");
        // for (int i = 0; i < graph[2].size(); i++) {
        // Edge e = graph[2].get(i);
        // System.out.print(e.dest + " ");
        // }
        // System.out.println();

        System.out.println("BFS:");
        bfs(graph);

        System.out.println("DFS:");
        dfs(graph, new boolean[V], 'A');

        System.out.println("\nPrinting All Paths From A -> F");
        printAllPath(graph, new boolean[V], 'A', 'F', "A");
    }
}
