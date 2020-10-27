import java.util.*;
import java.io.*;

public class PartD {
    private static Stack<Integer> path;
    private static HashMap<Integer, Stack<Integer>[]> paths = new HashMap<>();
    private static HashMap<Integer, Integer[]> distances = new HashMap<>();
    private static HashMap<Integer, Integer> numVisited = new HashMap<>();

    public static void main(String[] args) {
        int number_of_hospital = 20000;
        String roadFile = "roadNet_CA.txt";
        String hosFile = "fileHos.txt";
        RandHospital.writeRandHos(number_of_hospital, 1965206);
        int topk = 2;
        MyGraph graph = new MyGraph(roadFile, hosFile);

        try {
            FileWriter fw = new FileWriter("path_output_D.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println("# FromNodeId\tToHospitalId\tTop\t\tDistance\tPath");
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        long fromTime = System.currentTimeMillis();
        paths = bfs(graph, topk);
        writeFile(paths);
        long toTime = System.currentTimeMillis();
        long duration = toTime - fromTime;
        System.out.println(">>> Running time: " + duration + " milliseconds ~ " + duration/1000 + " seconds");
    }

    public static HashMap<Integer, Stack<Integer>[]> bfs(MyGraph graph, int k) {
        HashSet<Integer> hospitals = graph.getHospitalList();
        HashMap<Integer, LinkedList<Integer>> adjList = graph.getAdjcencyList();
        HashMap<Integer, Stack<Integer>[]> paths = new HashMap<>();

        Queue<Integer> L = new LinkedList<>(); // empty queue L for visited
        int v = 0; // node in queue L
        int w; // neighbor of v
        LinkedList<Integer> neighbors; // adjacent nodes with v
        Iterator<Integer> iterator; // to iterate adjacency linked list
        Stack<Integer> path = new Stack<>(); // trace path to hospital

        for (int hosId : hospitals) {

            L.add(hosId);
            graph.markNode(hosId);
            paths.put(hosId, new Stack[k]);
            distances.put(hosId, new Integer[k]);
            for (int count = 0; count < k; count++) {
                paths.get(hosId)[count] = new Stack<>();
                paths.get(hosId)[count].add(hosId);
                // distances.get(hosId);
                distances.get(hosId)[count] = 0;
            }
            numVisited.put(hosId, k-1);
        }

        while (L.size() != 0) {
            v = L.remove();

            neighbors = adjList.get(v);
            iterator = neighbors.iterator();
            while (iterator.hasNext()) {
                w = iterator.next();

                if (!numVisited.containsKey(w)) {
                    numVisited.put(w, -1); // first round as 0

                    distances.put(w, new Integer[k]);
                    paths.put(w, new Stack[k]);
                }
                int round = numVisited.get(w);
                if (round < k-1 && round < numVisited.get(v)) {
                    // graph.markNode(w); // mark w as visited
                    round += 1;
                    numVisited.put(w, round);
                    L.add(w);
                    distances.get(w)[round] = 0;
                    // System.out.println("w " + w + " - v " + v + " - distances.get(v)[round] " + distances.get(v)[round] + " - visited v " + numVisited.get(v));
                    distances.get(w)[round] = distances.get(v)[round] + 1;
                    paths.get(w)[round] = new Stack<>();
                    path = new Stack<>();
                    path = (Stack<Integer>) paths.get(v)[round].clone();
                    path.add(w);
                    paths.get(w)[round] = path;
                }
            }
        }
        return paths;
    }

    public static void writeFile(HashMap<Integer, Stack<Integer>[]> paths) {
        try {
            FileWriter fw = new FileWriter("path_output_D.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = new String();
            String pathStr = new String();
            int toNode = -1; // just initialize to compile
            Stack<Integer> path = new Stack<>();
            int distance;

            for (int fromId : paths.keySet()) {
                int k = numVisited.get(fromId) + 1;
                for (int i = 0; i < k; i++) {
                    path = paths.get(fromId)[i];
                    int top = i+1;
                    distance = distances.get(fromId)[i];
                    int fromNode = path.pop();
                    pathStr = String.valueOf(fromNode);
                    while (!path.empty()) {
                        toNode = path.pop();
                        pathStr += " -> " + String.valueOf(toNode);
                        fromNode = toNode;
                    }
                    if (toNode == -1) {
                        toNode = fromNode;
                    }

                    if (fromId >= 1000) {
                        line = fromId + "\t\t\t";
                        if (toNode >= 1000)
                            line += toNode + "\t\t\t" + top + "\t\t" + distance + "\t\t\t" + pathStr + "\n";
                        else
                            line += toNode + "\t\t\t\t" + top + "\t\t" + distance + "\t\t\t" + pathStr + "\n";
                    } else {
                        line = fromId + "\t\t\t\t";
                        if (toNode >= 1000)
                            line += toNode + "\t\t\t" + top + "\t\t" + distance + "\t\t\t" + pathStr + "\n";
                        else
                            line += toNode + "\t\t\t\t" + top + "\t\t" + distance + "\t\t\t" + pathStr + "\n";
                    }
                    toNode = -1; // reset toNode
                    pw.append(line);
                }
            }

            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}
