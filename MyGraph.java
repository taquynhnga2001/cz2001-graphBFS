import java.io.*;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Stack;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.generator.*;
import org.graphstream.ui.view.*;

public class MyGraph {
    private int numNodes;
    private int numEdges;
    private int numHos;
    private LinkedList<Integer> adjList[];  // adjacency list
    private int[] hospitals;
    private int[] mark;                     // mark visited nodes

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
                        mark = new int[numNodes];
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
            System.out.println("Error opening the input " + filePath + " " + e.getMessage());
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

        // READ AND CONSTRUCT HOSPITAL LIST
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
        mark[id] = 1;
    }
    public void unmarkNode(int id) {
        mark[id] = 0;
    }
    public int getMark(int id) {
        return mark[id];
    }
    public void resetMark(){
        // reset all mark[] = 0
        mark = new int[numNodes];
    }

    public LinkedList<Integer>[] getAdjcencyList() {
        return adjList;
    }
    public int[] getHospitalList() {
        return hospitals;
    }

    public boolean isHospital(int id) {
        for (int hosId : hospitals) {
            if (id == hosId)
                return true;
        }
        return false;
    }
}
