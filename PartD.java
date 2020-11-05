import java.util.*;
import java.io.*;

public class PartD {
    // private static Stack<Integer> path;
    private static HashMap<Integer, Integer[]> toHos = new HashMap<>();
    private static HashMap<Integer, Integer[]> distances = new HashMap<>();
    private static HashMap<Integer, Integer> numVisited = new HashMap<>();

    public static void main(String[] args) {
        // int NUMBER_OF_HOSPITALS = 10000;
        // String ROAD_FILE = "roadNet_CA.txt";
        // String HOSPITAL_FILE = "fileHos.txt";
        // int TOP_NEAREST_K = 8;
        // String outputFile = "";
        toHos = new HashMap<>();
        distances = new HashMap<>();
        numVisited = new HashMap<>();

        String ROAD_FILE = args[0];
        String HOSPITAL_FILE = args[1];
        String outputFile = args[2];
        String makeRandHos = args[3];


        // int TOP_NEAREST_K = Integer.parseInt(args[4]);

        Scanner sc = new Scanner(System.in);
        System.out.print("Top-k nearest paths. k = ");
        String k = sc.nextLine();
        int TOP_NEAREST_K = Integer.parseInt(k);

        if (makeRandHos.equals("true")) {
            sc = new Scanner(System.in);
            System.out.print("How many random hospitals that you want to generate (approximately)? ");
            int number_of_hospital = Integer.parseInt(sc.nextLine());
            RandHospital.writeRandHos(number_of_hospital, 1965206);
        }

        System.out.println("\n===== TOP-" + TOP_NEAREST_K + " NEAREST HOSPITALS =====");
        MyGraph graph = new MyGraph(ROAD_FILE, HOSPITAL_FILE);

        try {
            FileWriter fw = new FileWriter(outputFile);
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
        writeFile(toHos, TOP_NEAREST_K, outputFile);
        long toTime = System.currentTimeMillis();
        long duration = toTime - fromTime;
        System.out.println(">>> Running time: " + duration + " milliseconds ~ " + (double)duration/1000 + " seconds");
    }

    public static HashMap<Integer, Integer[]> bfs(MyGraph graph, int k) {
        HashSet<Integer> hospitals = graph.getHospitalList();
        HashMap<Integer, LinkedList<Integer>> adjList = graph.getAdjcencyList();
        HashMap<Integer, Integer[]> toHos = new HashMap<>();

        Queue<Integer[]> L = new LinkedList<>(); // empty queue L for visited
        Integer[] v = new Integer[2]; // node in queue L, store [nodeId, order-in-queue]
        int w; // neighbor of v
        LinkedList<Integer> neighbors; // adjacent nodes with v
        Iterator<Integer> iterator; // to iterate adjacency linked list

        for (int hosId : hospitals) {
            Integer[] addHos = new Integer[2];
            addHos[0] = hosId;
            // addHos[1] = k-1;
            addHos[1] = 0;
            L.add(addHos);
            graph.markNode(hosId);
            toHos.put(hosId, new Integer[k]);
            distances.put(hosId, new Integer[k]);
            // for (int count = 0; count < k; count++) {
            //     toHos.get(hosId)[count] = hosId;
            //     distances.get(hosId)[count] = 0;
            // }
            toHos.get(hosId)[0] = hosId;
            distances.get(hosId)[0] = 0;
            numVisited.put(hosId, 0);
        }

        while (L.size() != 0) {
            v = L.remove();     // v is Integer[2]

            neighbors = adjList.get(v[0]);
            iterator = neighbors.iterator();
            while (iterator.hasNext()) {
                w = iterator.next();  // w is an int

                if (!numVisited.containsKey(w)) {
                    numVisited.put(w, -1); // first round as 0

                    distances.put(w, new Integer[k]);
                    toHos.put(w, new Integer[k]);
                }
                int round = numVisited.get(w);
                // System.out.println("tohosw " + toHos.get(w) + "tohosvint " + toHos.get(v[0])[v[1]]);
                if (round < k-1 && !contains(toHos.get(w), toHos.get(v[0])[v[1]])) {
                    round += 1;
                    numVisited.put(w, round);
                    Integer[] addW = new Integer[2];
                    addW[0] = w;
                    addW[1] = round;
                    L.add(addW);
                    distances.get(w)[round] = distances.get(v[0])[v[1]] + 1;
                    toHos.get(w)[round] = toHos.get(v[0])[v[1]];
                }
            }
        }
        return toHos;
    }

    public static void writeFile(HashMap<Integer, Integer[]> toHosMap, int top_k, String outputFile) {
        try {
            FileWriter fw = new FileWriter(outputFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = new String();
            int toHos;
            int distance;

            String breakline = "";
            if (top_k>1) breakline = "\n";

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
                    pw.append(line);
                }
                pw.append(breakline);
            }

            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
    public static boolean contains(Integer[] array, int key) {
        for (Integer i : array) {
            if (i != null && key==(int)i) return true;
        }
        return false;
    }
}