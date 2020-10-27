import java.io.*;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

public class MyGraph {
    private int numNodes;
    private int numEdges;
    private int numHos;
    private HashMap<Integer, LinkedList<Integer>>  adjList;  // adjacency list
    private HashSet<Integer> hospitals;
    private HashMap<Integer, Integer> mark;                  // mark visited nodes

    MyGraph(String filePath, String hosPath) {
        // READ AND CONSTRUCT ADJACENCY LIST
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String inputline = br.readLine();

            while (inputline != null) {
                if (inputline.charAt(0) == '#') {
                    if (inputline.substring(2, 6).equals("Node")) {
                        numNodes = Integer.parseInt(inputline.split(" ")[2]);
                        numEdges = Integer.parseInt(inputline.split(" ")[4]);
                        mark = new HashMap<>((int)(numNodes/0.75));
                        System.out.println("Number of Nodes: " + numNodes);
                        System.out.println("Number of Edges: " + numEdges);
                        adjList = new HashMap<>( (int)(numNodes/0.75) );   // construct capacity for HashMap
                    }
                } else {
                    int node1 = Integer.parseInt(inputline.strip().split("\t")[0]);
                    int node2 = Integer.parseInt(inputline.strip().split("\t")[1]);
                    if (adjList.get(node1) == null) adjList.put(node1, new LinkedList<>());  // construct Linkedlist if not constructed
                    adjList.get(node1).add(node2); // add node2 to the linkedlist of node1
                }
                inputline = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error opening the input " + filePath + " " + e.getMessage());
            System.exit(0);
        }
        
        // READ AND CONSTRUCT HOSPITAL LIST
        try {
            FileReader frh = new FileReader(hosPath);       // file reader hospital
            BufferedReader brh = new BufferedReader(frh);   // buffered reader hospital
            String inputline = brh.readLine();
            numHos = Integer.parseInt(inputline.split(" ")[1]); // first line contains the number of hospitals
            inputline = brh.readLine();

            System.out.println("Number of Hospitals: " + numHos);
            hospitals = new HashSet<>((int) (numHos / 0.75));

            int i=0;
            while (inputline != null) {
                hospitals.add(Integer.parseInt(inputline.strip()));     // add HosId to the HashSet
                inputline = brh.readLine();
                i++;
            }
            brh.close();

        } catch (IOException e) {
            System.out.println("Error opening the input " + hosPath + " " + e.getMessage());
            System.exit(0);
        }
    }

    public int getNodeCount() {
        return numNodes;
    }
    public int getEdgeCount() {
        return numEdges;
    }
    public int getHosCount() {
        return numHos;
    }

    public void markNode(int id) {
        mark.put(id, 1);
    }
    public boolean isVisited(int id) {
        return mark.containsKey(id);
    }
    public void resetMark(){
        mark = new HashMap<>();
    }

    public HashMap<Integer, LinkedList<Integer>> getAdjcencyList() {
        return adjList;
    }
    public HashSet<Integer> getHospitalList() {
        return hospitals;
    }

    public boolean isHospital(int id) {
        return hospitals.contains(id) ? true : false;
    }
}
