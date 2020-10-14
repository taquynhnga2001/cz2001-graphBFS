import java.util.*;
import java.io.*;

public class App {
    private static int distance = 0;
    private static boolean findHos = false;
    private static LinkedList<Integer>[] adjList;
    private static int[] hospitals;
    private static Stack<Integer> path;
    public static void main(String[] args){
        MyGraph graph = new MyGraph("file1.txt", "file2.txt");
        adjList = graph.getAdjcencyList();
        hospitals = graph.getHospitalList();

        try {
            FileWriter fw = new FileWriter("path_output.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println("# FromNodeId\tToHospitalId\tDistance\tPath");
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        for (int id=0; id<graph.getNodeCount(); id++) {
            path = bfs(graph, id);
            App.writeFile(path, id);
            graph.resetMark();
            App.reset();
        }
    }

    public static Stack<Integer> bfs(MyGraph graph, int source) {
        Queue<Integer> L = new LinkedList<>();  // empty queue L for visited
        graph.markNode(source); // mark source node visited
        L.add(source);
        int v = 0;          // node in queue L
        int w;              // neighbor of v
        LinkedList<Integer> neighbors;      // adjacent nodes with v
        Iterator<Integer> iterator;         // to iterate adjacency linked list
        int preNode[] = new int[graph.getNodeCount()]; // record pre-incident node (same as Tree)
        preNode[source] = source;           // pre-incident node of source node is source node
        Stack<Integer> path = new Stack();  // trace path to hospital

        while (L.size() != 0) {
            v = L.remove();
            if (graph.isHospital(v)) {
                findHos = true;
                break;
            }
            neighbors = graph.getAdjcencyList()[v];
            iterator = neighbors.iterator();
            while (iterator.hasNext()) {
                w = iterator.next();
                if (graph.getMark(w) == 0) {    // vertex w is unvisited
                    graph.markNode(w);          // mark w as visited
                    L.add(w);
                    preNode[w] = v;             // mark the edge vw
                }
            }
        }
        if (findHos) {
            distance = 0;
            // now the hospital is v
            while (preNode[v] != v) {   // v is not a source node
                path.push(v);
                v = preNode[v];         // v is now its pre-incident node
                distance++;
            }
            // System.out.println("The distance is " + distance);
            path.push(v); // push the source node
        }
            // System.out.println("No way to hospitals!");
        return path;
    }

    public static void reset() {
        distance = 0;
        findHos = false;
    }

    public static void writeFile(Stack<Integer> path, int nodeId) {
        try {
            FileWriter fw = new FileWriter("path_output.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = new String();
            String pathStr = new String();
            int toNode=-1;   // just initialize to compile
            
            if (findHos) {
                int fromNode = path.pop();
                pathStr += String.valueOf(fromNode);
                while (!path.empty()) {
                    toNode = path.pop();
                    pathStr += " -> " + String.valueOf(toNode);
                    fromNode = toNode;
                }
                if (toNode==-1) {
                    // when fromNode = hospital, toNode = -1
                    toNode = fromNode;
                }
                line = nodeId + "\t\t\t\t" + toNode + "\t\t\t\t" + distance + "\t\t\t" + pathStr + "\n";
            } else {
                pathStr = "Null";
                line = nodeId + "\t\t\t\t" + "Null" + "\t\t\t" + "Null" + "\t\t" + pathStr + "\n";
            }
            pw.append(line);
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}
