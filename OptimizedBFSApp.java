import java.util.*;
import java.io.*;

public class OptimizedBFSApp {
    private static HashMap<Integer, Stack<Integer>> paths = new HashMap<>();
    private static HashMap<Integer, Integer> distances = new HashMap<>();
    public static void main(String[] args){
        String roadFile = args[0];
        String hosFile = args[1];
        String outputFile = args[2];
        String makeRandHos = args[3];
        if (makeRandHos.equals("true")) {
            Scanner sc = new Scanner(System.in);
            System.out.print("How many random hospitals that you want to generate (approximately)? ");
            int number_of_hospital = Integer.parseInt(sc.nextLine());
            RandHospital.writeRandHos(number_of_hospital, 1965206);
        }
        
        System.out.println("\n===== RESULT FINDING THE SHORTEST PATH =====");

        long fromTime = System.currentTimeMillis();
        MyGraph graph = new MyGraph(roadFile, hosFile);

        try {
            FileWriter fw = new FileWriter(outputFile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println("# FromNodeId\tToHospitalId\tDistance\tPath");
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        paths = bfs(graph);
        long toTime = System.currentTimeMillis();
        long duration = toTime - fromTime;
        writeFile(paths, outputFile);
        System.out.println(">>> Running time: " + duration + " milliseconds ~ " + (double)duration/1000 + " seconds");
    }

    public static HashMap<Integer, Stack<Integer>> bfs(MyGraph graph) {
        HashSet<Integer> hospitals = graph.getHospitalList();
        HashMap<Integer, LinkedList<Integer>> adjList = graph.getAdjcencyList();
        HashMap<Integer, Stack<Integer>> paths = new HashMap<>();

        Queue<Integer> L = new LinkedList<>();  // empty queue L for visited
        int v = 0;          // node in queue L
        int w;              // neighbor of v
        LinkedList<Integer> neighbors;      // adjacent nodes with v
        Iterator<Integer> iterator;         // to iterate adjacency linked list
        Stack<Integer> path = new Stack<>();  // trace path to hospital

        for (int hosId : hospitals) {
            L.add(hosId);
            graph.markNode(hosId);
            paths.put(hosId, new Stack<>());
            paths.get(hosId).add(hosId);
            distances.put(hosId, 0);
        }

        while (L.size() != 0) {
            v = L.remove();
            neighbors = adjList.get(v);
            iterator = neighbors.iterator();
            while (iterator.hasNext()) {
                w = iterator.next();
                if (!graph.isVisited(w)) {      // vertex w is unvisited
                    graph.markNode(w);          // mark w as visited
                    L.add(w);
                    distances.put(w, distances.get(v) + 1);
                    path = new Stack<>();
                    path = (Stack<Integer>) paths.get(v).clone();
                    path.add(w);
                    paths.put(w, path);
                }
            }
        }
        return paths;
    }

    public static void writeFile(HashMap<Integer, Stack<Integer>> paths, String outputFile) {
        try {
            FileWriter fw = new FileWriter(outputFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = new String();
            String pathStr = new String();
            int toNode=-1;   // just initialize to compile
            Stack<Integer> path = new Stack<>();
            int distance;

            for (int fromId : paths.keySet()) {
                path = paths.get(fromId);
                distance = distances.get(fromId);

                int fromNode = path.pop();
                pathStr = String.valueOf(fromNode);
                while (!path.empty()) {
                    toNode = path.pop();
                    pathStr += " -> " + String.valueOf(toNode);
                    fromNode = toNode;
                }
                if (toNode==-1) {
                    toNode = fromNode;
                }

                if (fromId >= 1000) {
                    line = fromId + "\t\t\t";
                    if (toNode >= 1000) line += toNode + "\t\t\t" + distance + "\t\t\t" + pathStr + "\n";
                    else line += toNode + "\t\t\t\t" + distance + "\t\t\t" + pathStr + "\n";
                }
                else {
                    line = fromId + "\t\t\t\t";
                    if (toNode >= 1000) line += toNode + "\t\t\t" + distance + "\t\t\t" + pathStr + "\n";
                    else line += toNode + "\t\t\t\t" + distance + "\t\t\t" + pathStr + "\n";
                }
                toNode = -1; //reset toNode
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
