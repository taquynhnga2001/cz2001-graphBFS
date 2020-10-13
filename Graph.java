import java.io.*;
import java.util.LinkedList;
import java.util.Iterator;

public class Graph {
    private int numNodes;
    private int numEdges;
    private int numHos;
    private LinkedList<Integer> adjList[]; // adjacency list
    private int[] hospitals;

    Graph(String filePath, String hosPath) {
        // read and construct adjacency list
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String inputline = br.readLine();

            while (inputline != null) {
                if (inputline.charAt(0) == '#') {
                    if (inputline.substring(2, 6).equals("Node")) {
                        numNodes = Integer.parseInt(inputline.split(" ")[2]);
                        numEdges = Integer.parseInt(inputline.split(" ")[4]);
                        System.out.println("Number of Nodes: " + numNodes);
                        System.out.println("Number of Edges: " + numEdges);
                        adjList = new LinkedList[numNodes];
                        for (int i = 0; i < numNodes; i++)
                            adjList[i] = new LinkedList<>();
                    }
                } else {
                    int node1 = Integer.parseInt(inputline.strip().split("\t")[0]);
                    int node2 = Integer.parseInt(inputline.strip().split("\t")[1]);
                    adjList[node1].add(node2);
                }
                inputline = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error opening the input file! " + e.getMessage());
            System.exit(0);
        }
        // print adjList
        // LinkedList<Integer> list;
        // Iterator<Integer> iterator;
        // for (int i = 0; i < numNodes; i++) {
        // System.out.print(i + " - ");
        // list = adjList[i];
        // iterator = list.iterator();
        // while (iterator.hasNext())
        // System.out.print(iterator.next() + " ");
        // System.out.println();
        // }

        // read and construct hospitals list
        try {
            FileReader frh = new FileReader(hosPath);       // file reader hospital
            BufferedReader brh = new BufferedReader(frh);   // buffered reader hospital
            String inputline = brh.readLine();
            numHos = Integer.parseInt(inputline.split(" ")[1]);
            inputline = brh.readLine();
            System.out.println("Number of Hospitals: " + numHos);
            hospitals = new int[numHos];

            int i=0;
            while (inputline != null) {
                hospitals[i] = Integer.parseInt(inputline.strip());
                inputline = brh.readLine();
                i++;
            }
            brh.close();

        } catch (IOException e) {
            System.out.println("Error opening the input file! " + e.getMessage());
            System.exit(0);
        }
    }
}
