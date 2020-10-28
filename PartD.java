import java.util.*;
import java.io.*;

public class PartD {
    // private static Stack<Integer> path;
    private static HashMap<Integer, Integer[]> toHos = new HashMap<>();
    private static HashMap<Integer, Integer[]> distances = new HashMap<>();
    private static HashMap<Integer, Integer> numVisited = new HashMap<>();

    public static void main(String[] args) {
        int NUMBER_OF_HOSPITALS = 100000;
        String ROAD_FILE = "roadNet_CA.txt";
        String HOSPITAL_FILE = "fileHos.txt";
        int TOP_NEAREST_K = 4;
        // String roadFile = "file1.txt";
        RandHospital.writeRandHos(NUMBER_OF_HOSPITALS, 1965206);

        System.out.println("\n===== TOP-" + TOP_NEAREST_K + " NEAREST HOSPITALS =====");
        
        MyGraph graph = new MyGraph(ROAD_FILE, HOSPITAL_FILE);

        try {
            FileWriter fw = new FileWriter("path_output_D.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println("# FromNodeId\tToHospitalId\tTop\t\tDistance");
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        long fromTime = System.currentTimeMillis();
        toHos = bfs(graph, TOP_NEAREST_K);
        writeFile(toHos);
        long toTime = System.currentTimeMillis();
        long duration = toTime - fromTime;
        System.out.println(">>> Running time: " + duration + " milliseconds ~ " + duration/1000 + " seconds");
    }

    public static HashMap<Integer, Integer[]> bfs(MyGraph graph, int k) {
        HashSet<Integer> hospitals = graph.getHospitalList();
        HashMap<Integer, LinkedList<Integer>> adjList = graph.getAdjcencyList();
        HashMap<Integer, Integer[]> toHos = new HashMap<>();

        Queue<Integer> L = new LinkedList<>(); // empty queue L for visited
        int v = 0; // node in queue L
        int w; // neighbor of v
        LinkedList<Integer> neighbors; // adjacent nodes with v
        Iterator<Integer> iterator; // to iterate adjacency linked list

        for (int hosId : hospitals) {

            L.add(hosId);
            graph.markNode(hosId);
            toHos.put(hosId, new Integer[k]);
            distances.put(hosId, new Integer[k]);
            for (int count = 0; count < k; count++) {
                toHos.get(hosId)[count] = hosId;
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
                    toHos.put(w, new Integer[k]);
                }
                int round = numVisited.get(w);
                if (round < k-1 && round < numVisited.get(v)) {
                    round += 1;
                    numVisited.put(w, round);
                    L.add(w);
                    distances.get(w)[round] = 0;
                    distances.get(w)[round] = distances.get(v)[round] + 1;
                    toHos.get(w)[round] = toHos.get(v)[round];
                }
            }
        }
        return toHos;
    }

    public static void writeFile(HashMap<Integer, Integer[]> toHosMap) {
        try {
            FileWriter fw = new FileWriter("path_output_D.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = new String();
            int toHos;
            int distance;

            for (int fromId : toHosMap.keySet()) {
                int k = numVisited.get(fromId) + 1;
                for (int i = 0; i < k; i++) {
                    toHos = toHosMap.get(fromId)[i];
                    int top = i+1;
                    distance = distances.get(fromId)[i];

                    if (fromId >= 1000) {
                        line = fromId + "\t\t\t";
                        if (toHos >= 1000)
                            line += toHos + "\t\t\t" + top + "\t\t" + distance + "\n";
                        else
                            line += toHos + "\t\t\t\t" + top + "\t\t" + distance + "\n";
                    } else {
                        line = fromId + "\t\t\t\t";
                        if (toHos >= 1000)
                            line += toHos + "\t\t\t" + top + "\t\t" + distance + "\n";
                        else
                            line += toHos + "\t\t\t\t" + top + "\t\t" + distance + "\n";
                    }
                    // toNode = -1; // reset toNode
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
