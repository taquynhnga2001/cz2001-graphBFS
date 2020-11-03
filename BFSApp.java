import java.util.*;
import java.io.*;

public class BFSApp {
    private static int distance = 0;
    private static boolean findHos = false;
    private static Stack<Integer> path;
    public static void main(String[] args){
        MyGraph graph = new MyGraph("file1.txt", "file2.txt");

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
        long fromTime = System.currentTimeMillis();
        for (int id=0; id<graph.getNodeCount(); id++) {
            path = bfs(graph, id);
            BFSApp.writeFile(path, id);
            graph.resetMark();
            BFSApp.reset();
        }
        long toTime = System.currentTimeMillis();
        long duration = toTime - fromTime;
        System.out.println(">>> Running time: " + duration + " milliseconds");
    }

    public static Stack<Integer> bfs(MyGraph graph, int source) {
        Queue<Integer> L = new LinkedList<>();  // empty queue L for visited
        graph.markNode(source); // mark source node visited
        L.add(source);
        int v = 0;          // node in queue L
        int w;              // neighbor of v
        LinkedList<Integer> neighbors;      // adjacent nodes with v
        Iterator<Integer> iterator;         // to iterate adjacency linked list
        HashMap<Integer, Integer> preNode = new HashMap<>(); // record pre-incident node (same as Tree)
        preNode.put(source, source);           // pre-incident node of source node is source node
        Stack<Integer> path = new Stack<>();  // trace path to hospital
        boolean fromHos = false;
        if (graph.isHospital(source)) fromHos = true;

        while (L.size() != 0) {
            v = L.remove();
            if (graph.isHospital(v) && !fromHos) {
                findHos = true;
                break;
            } else fromHos = false;
            if (!graph.getAdjcencyList().containsKey(v)) break;  // the node doesnt connect to any other nodes

            neighbors = graph.getAdjcencyList().get(v);
            iterator = neighbors.iterator();
            while (iterator.hasNext()) {
                w = iterator.next();
                if (!graph.isVisited(w)) {    // vertex w is unvisited
                    graph.markNode(w);          // mark w as visited
                    L.add(w);
                    preNode.put(w, v);             // mark the edge vw
                }
            }
        }
        if (findHos) {
            distance = 0;
            // now the hospital is v
            while (preNode.get(v) != v) {   // v is not a source node
                path.push(v);
                v = preNode.get(v);         // v is now its pre-incident node
                distance++;
            }
            path.push(v);               // push the source node
        }
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

                // write to file
                if (nodeId >= 1000) {
                    line = nodeId + "\t\t\t";
                    if (toNode >= 1000) line += toNode + "\t\t\t" + distance + "\t\t\t" + pathStr + "\n";
                    else line += toNode + "\t\t\t\t" + distance + "\t\t\t" + pathStr + "\n";
                }
                else {
                    line = nodeId + "\t\t\t\t";
                    if (toNode >= 1000) line += toNode + "\t\t\t" + distance + "\t\t\t" + pathStr + "\n";
                    else line += toNode + "\t\t\t\t" + distance + "\t\t\t" + pathStr + "\n";
                }
            } else {
                // findHos = false
                pathStr = "---";
                if (nodeId >= 1000) line = nodeId + "\t\t\t" + "---" + "\t\t\t\t" + "---" + "\t\t\t" + pathStr + "\n";
                else line = nodeId + "\t\t\t\t" + "---" + "\t\t\t\t" + "---" + "\t\t\t" + pathStr + "\n";
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
