import java.util.*;
import java.io.*;

public class OptimizedBFSApp {
    private static int distance = 0;
    private static boolean findHos = false;
    private static LinkedList<Integer>[] adjList;
    private static int[] hospitals;
    private static Stack<Integer> path;
    // private static int distances[];
    // private static int preNode[];// record pre-incident node (same as Tree)
    public static void main(String[] args){
        MyGraph graph = new MyGraph("file1.txt", "file2.txt");
        adjList = graph.getAdjcencyList();
        hospitals = graph.getHospitalList();
        // distances = new int[graph.getNodeCount()];
        // preNode = new int[graph.getNodeCount()]; // record pre-incident node (same as Tree)

        // write file: store the result
        try {
            FileWriter fw = new FileWriter("path_output_optimized.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println("# FromNodeId\tDistance");
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        // for (int id=0; id<graph.getNodeCount(); id++) {
        //     bfs(graph, id);
            // graph.resetMark();
            // BFSApp.reset();
        bfs(graph);
        // OptimizedBFSApp.writeFile(distances);
    }

    public static void bfs(MyGraph graph) {
        // int i;
        Queue<Integer> L = new LinkedList<>();  // empty queue L for visited
        Queue<Integer> resetList = new LinkedList<>();  // store all nodes need to reset prenode after each loop
        // graph.markNode(source); // mark source node visited
        // L.add(source);
        int v = 0;          // node in queue L
        int w;              // neighbor of v
        LinkedList<Integer> neighbors;      // adjacent nodes with v
        Iterator<Integer> iterator;         // to iterate adjacency linked list
        int preNode[] = new int[graph.getNodeCount()]; // record pre-incident node (same as Tree)
        int distances[] = new int[graph.getNodeCount()]; // store the distance of each node to the nearest hospital

        // initialize distances to -1; except for hospital is 0
        for (int i = 0; i<graph.getNodeCount(); i++) {
            if (graph.isHospital(i)) distances[i] = 0;
            else distances[i] = -1;
            // preNode = -1 means doesnt have preNode yet
            preNode[i] = -1;
        }

        for (int i = 0; i<1; i++) {
            if (distances[i] > -1) continue;    // source has been visited and lies in the found path
            L = new LinkedList<>();
            L.add(i);
            graph.markNode(i);
            resetList.add(i);

            while (L.size() != 0) { 
                v = L.remove();
                // if (distances[v] > -1) break;
                // if (graph.isHospital(v)) {
                //     findHos = true;
                //     break;
                // }
                neighbors = graph.getAdjcencyList()[v];
                iterator = neighbors.iterator();
                while (iterator.hasNext()) {
                    w = iterator.next();
                    if (graph.isVisited(w) == 0) {    // vertex w is unvisited
                        graph.markNode(w);          // mark w as visited
                        L.add(w);
                        preNode[w] = v;             // mark the edge vw
                        if (distances[w] > -1) {
                            int temp = w;
                            while (distances[preNode[temp]]==-1) { // means temp has preNode and not have distance
                                distances[preNode[temp]] = distances[temp] + 1;
                                temp = preNode[temp];
                                if (preNode[temp]==-1) break;
                            }
                        }
                    }
                }
            }
        } 
        OptimizedBFSApp.writeFile(distances);
    }

    public static void reset() {
        distance = 0;
        findHos = false;
    }

    public static void writeFile(int[] distances) {
        try {
            FileWriter fw = new FileWriter("path_output_optimized.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = new String();

            for (int i=0; i<distances.length; i++) {
                line = i + "\t\t\t\t" + distances[i] + "\n";
                pw.append(line);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}
