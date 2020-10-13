import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.generator.*;
import org.graphstream.ui.view.*;

import java.util.LinkedList;
import java.util.Stack;
import java.util.Iterator;
import java.lang.Math;

import java.io.*;

public class RandomGraph {
    private int numNodes;
    private int numEdges;
    private Graph graph;
    public int[] mark;
    private int[] hospitals;

    RandomGraph(int noNodes, int noDegree) {
        numNodes = noNodes;

        graph = new SingleGraph("Random");
        Generator gen = new RandomGenerator(noDegree);
        gen.addSink(graph);
        gen.begin();

        int i;
        for (i = 0; i < numNodes; i++) {
            gen.nextEvents();
        }
        gen.end();
        numNodes = graph.getNodeCount();
        numEdges = graph.getEdgeCount();
        mark = new int[numNodes];
    }

    public int getNodeCount() {
        return numNodes;
    }

    public int getEdgeCount() {
        return numEdges;
    }

    public void display(Stack<Integer> path, int sourceVertex) {
        System.setProperty("org.graphstream.ui", "swing");
        Node node;
        Node source = graph.getNode(sourceVertex);
        source.setAttribute("ui.class", "source");
        for (int i = 0; i < numNodes; i++) {
            node = graph.getNode(i);
            node.addAttribute("ui.style", "text-alignment: under; text-size: 15;");
            node.addAttribute("ui.label", String.valueOf(i));
        }

        if (!path.empty()) {
            int fromNode = path.pop();
            int toNode;
            System.out.print("BFS Path: " + fromNode);
            Edge edge;
            while (!path.empty()) {
                toNode = path.pop();
                System.out.print(" -> " + toNode);
                edge = graph.getNode(fromNode).getEdgeToward(toNode);   // get edge to fill its color to red
                edge.setAttribute("ui.class", "path");
                fromNode = toNode;
            }
            System.out.println();
        }
        graph.setAttribute("ui.stylesheet",
                "node.source { fill-color: blue; } node.hos { fill-color: red; }  edge.path { fill-color: red; }");
        graph.display();
    }

    public LinkedList<Integer>[] adjacencyList() {
        LinkedList<Integer> adjList[] = new LinkedList[numNodes];
        for (int i = 0; i < numNodes; i++) {
            adjList[i] = new LinkedList<>();
            for (int j = 0; j < numNodes; j++) {
                if (j != i && graph.getNode(i).hasEdgeBetween(j)) {
                    adjList[i].add(j);
                }
            }        }
        return adjList;
    }

    public void mark(int s) {
        mark[s] = 1;        // mark visited vertex
    }

    public void markRandHospitals(int numHos) {
        // param: numHos = number of random hospital id you want to generate
        hospitals = new int[numHos];
        for (int i = 0; i < numHos; i++)
            hospitals[i] = graph.getNodeCount(); // initialize all hosIDs to be different from 0, and we choose =
                                                 // numNodes
        int hosId;
        Node node;
        boolean loopMore = false;
        System.out.print("Hospital: ");

        for (int i = 0; i < numHos; i++) {
            do { // generate random hosId
                hosId = (int) (Math.random() * (graph.getNodeCount()));
                for (int h : hospitals) {
                    if (hosId == h) {
                        loopMore = true;
                        break;
                    } else
                        loopMore = false;
                }
            } while (loopMore); // generate another hosID if hospitals contain that hosId

            hospitals[i] = hosId;
            System.out.print(hosId + "\t");
            node = graph.getNode(hosId);
            System.setProperty("org.graphstream.ui", "swing");
            node.setAttribute("ui.class", "hos"); // add CSS class attribute for further display
        }
        System.out.println();
    }

    public boolean isHospital(int id) {
        for (int hosId : hospitals) {
            if (id == hosId)
                return true;
        }
        return false;
    }

    public void writeGraphStructure(LinkedList<Integer> adjList[]) {
        try {
            FileWriter fw = new FileWriter("file1.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println("# Undirected graph: file1.txt");
            pw.println("# Random graph generator");
            pw.println("# Nodes: " + numNodes + " Edges: " + numEdges);
            pw.println("# FromNodeId\tToNodeId");
            for (int i = 0; i < numNodes; i++) {
                LinkedList<Integer> list;
                Iterator<Integer> iterator;
                list = adjList[i];
                iterator = list.iterator();
                while (iterator.hasNext())
                    pw.println(i + "\t" + iterator.next());
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void writeHospitalFile () {
        try {
            FileWriter fw = new FileWriter("file2.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("# " + hospitals.length);
            for (int hosId: hospitals) pw.println(hosId);
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}
