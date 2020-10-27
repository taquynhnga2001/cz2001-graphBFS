import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Iterator;
import java.io.*;

public class BFSRandomApp {
    static int distance;
    static boolean findHos = false;

    public static void main(String[] args) {
        RandomGraph graph = new RandomGraph(100, 3);
        graph.markRandHospitals(5);

        // print adjacency list
        LinkedList<Integer> adjList[] = graph.adjacencyList();
        LinkedList<Integer> list;
        Iterator<Integer> iterator;
        for (int i = 0; i < graph.getNodeCount(); i++) {
            System.out.print(i + " - ");
            list = adjList[i];
            iterator = list.iterator();
            while (iterator.hasNext())
                System.out.print(iterator.next() + " ");
            System.out.println();
        }

        // write file1: graph structure and file2: hospital Id
        graph.writeGraphStructure(adjList);
        graph.writeHospitalFile();

        // algorithm application
        int sourceVertex = 0;
        Stack<Integer> path = breathFS(graph, sourceVertex);
        // clone the path for writing output step
        Stack<Integer> copiedPath = new Stack<Integer>();   
        copiedPath.addAll(path);

        graph.display(path, sourceVertex);
        // write output to a file
        BFSRandomApp.writeFile(copiedPath);
    }

    public static Stack<Integer> breathFS(RandomGraph graph, int source) {
        Queue<Integer> L = new LinkedList<>();  // empty queue L for visited nodes
        graph.mark(source); // mark visited source nodes
        L.add(source);
        int v = 0;          // vertex in queue L
        int w;              // neighbor of v
        LinkedList<Integer> neighbors;      // adjacent nodes with v
        Iterator<Integer> iterator;         // to iterate adjacency linked list
        int preNode[] = new int[graph.getNodeCount()]; // record pre-incident node (same as Tree)
        preNode[source] = source;           // pre-incident node of source node is source node
        Stack<Integer> path = new Stack<>();  // trace path to hospital

        while (L.size() != 0) {
            v = L.remove();
            if (graph.isHospital(v)) {
                findHos = true;
                break;
            }
            neighbors = graph.adjacencyList()[v];
            iterator = neighbors.iterator();
            while (iterator.hasNext()) {
                w = iterator.next();
                if (graph.mark[w] == 0) {   // vertex w is unvisited
                    graph.mark(w);          // mark w as visited
                    L.add(w);
                    preNode[w] = v;         // mark the edge vw
                }
            }
        }
        if (findHos) {
            distance = 0;
            // now the hospital is v
            while (preNode[v] != v) { // v is not a source node
                path.push(v);
                v = preNode[v]; // v is now its pre-incident node
                distance++;
            }
            System.out.println("The distance is " + distance);
            path.push(v); // push the source node
        } else
            System.out.println("No way to hospitals!");
        return path;
    }

    public static void writeFile(Stack<Integer> path) {
        try {
            FileWriter fw = new FileWriter("output.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            
            if (findHos) {
                pw.println("The distance is " + distance);
                int fromNode = path.pop();
                int toNode;
                pw.print("BFS Path: " + fromNode);
                while (!path.empty()) {
                    toNode = path.pop();
                    pw.print(" -> " + toNode);
                    fromNode = toNode;
                }
            } else pw.println("No way to hospitals!");
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}
