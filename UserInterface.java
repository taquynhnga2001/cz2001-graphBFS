import java.util.Scanner;

public class UserInterface {
    public static void main(String[] agrs) {
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.println("\n\n+=================================================+");
            System.out.println("|           PROJECT 2: GRAPH ALGORITHM            |");
            System.out.println("+=================================================+");
            System.out.println("Problems:");
            System.out.println("\t(a) : Find the shortest path from all nodes");
            System.out.println("\t(b) : Find the top-k shortest paths from all nodes");
            System.out.println("\t(x) : Exit project");
            System.out.print("Choose problem: ");
            choice = sc.nextLine().toLowerCase();

            switch (choice) {
                case "a": {
                    PartA();
                    break;
                }
                case "b": {
                    PartB();
                    break;
                }
            }
        } while (!choice.equals("x"));
    }
    public static void PartA() {
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.println("\n\n+-------------------------------------------------+");
            System.out.println("|            a) Find the shortest path            |");
            System.out.println("+-------------------------------------------------+");
            System.out.println("Data file:");
            System.out.println("\t(a) : 'file1-1k.txt' (1.006 nodes, 2.461 edges) and 'file2-1k.txt' (10 hospitals)");
            System.out.println("\t(b) : 'file1-10k.txt' (10.006 nodes, 25.231 edges) and 'file2-10k.txt' (100 hospitals)");
            System.out.println("\t(c) : Real road network data (1.965.206 nodes, 5.533.214 edges)");
            System.out.println("\t(d) : Test your own file");
            System.out.println("\t(x) : Exit problem a)");
            System.out.print("Choose data file: ");
            choice = sc.nextLine().toLowerCase();
            switch (choice) {
                case "a": {
                    String[] args = new String[4];
                    String roadFile = "file1-1k.txt";
                    String hosFile = "file2-1k.txt";
                    String outputFile = "output-a-1k.txt";
                    String makeRandHos = "false";
                    args[0] = roadFile;
                    args[1] = hosFile;
                    args[2] = outputFile;
                    args[3] = makeRandHos;
                    OptimizedBFSApp.main(args);
                    System.out.println(">>> Output file is in '" + outputFile + "'");
                    break;
                }
                case "b": {
                    String[] args = new String[4];
                    String roadFile = "file1-10k.txt";
                    String hosFile = "file2-10k.txt";
                    String outputFile = "output-a-10k.txt";
                    String makeRandHos = "false";
                    args[0] = roadFile;
                    args[1] = hosFile;
                    args[2] = outputFile;
                    args[3] = makeRandHos;
                    OptimizedBFSApp.main(args);
                    System.out.println(">>> Output file is in '" + outputFile + "'");
                    break;
                }
                case "c": {
                    String[] args = new String[4];
                    String roadFile = "roadNet_CA.txt";
                    String hosFile = "fileHos.txt";
                    String outputFile = "output-a-real-data.txt";
                    String makeRandHos = "true";
                    args[0] = roadFile;
                    args[1] = hosFile;
                    args[2] = outputFile;
                    args[3] = makeRandHos;
                    OptimizedBFSApp.main(args);
                    System.out.println(">>> Output file is in '" + outputFile + "'");
                    break;
                }
                case "d": {
                    String[] args = new String[4];
                    System.out.print("Absolute file path of graph structure: ");
                    String roadFile = sc.nextLine();
                    System.out.print("Absolute file path of hospitals: ");
                    String hosFile = sc.nextLine();
                    String outputFile = "output-a-own-data.txt";
                    String makeRandHos = "false";
                    args[0] = roadFile;
                    args[1] = hosFile;
                    args[2] = outputFile;
                    args[3] = makeRandHos;
                    OptimizedBFSApp.main(args);
                    System.out.println(">>> Output file is in '" + outputFile + "'");
                    break;
                }
            }
        } while (!choice.equals("x"));
        
    }
    public static void PartB() {
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.println("\n\n+-------------------------------------------------+");
            System.out.println("|         b) Find the top-k shortest paths        |");
            System.out.println("+-------------------------------------------------+");
            System.out.println("Data file:");
            System.out.println("\t(a) : 'file1-1k.txt' (1.006 nodes, 2.461 edges) and 'file2-1k.txt' (10 hospitals)");
            System.out.println("\t(b) : 'file1-10k.txt' (10.006 nodes, 25.231 edges) and 'file2-10k.txt' (100 hospitals)");
            System.out.println("\t(c) : Real road network data (1.965.206 nodes, 5.533.214 edges)");
            System.out.println("\t(d) : Test your own file");
            System.out.println("\t(x) : Exit problem b)");
            System.out.print("Choose data file: ");
            choice = sc.nextLine().toLowerCase();
            switch (choice) {
                case "a": {
                    String[] args = new String[5];
                    String roadFile = "file1-1k.txt";
                    String hosFile = "file2-1k.txt";
                    String outputFile = "output-b-1k.txt";
                    String makeRandHos = "false";
                    System.out.print("Top-k nearest paths. k = ");
                    String k = sc.nextLine();
                    args[0] = roadFile;
                    args[1] = hosFile;
                    args[2] = outputFile;
                    args[3] = makeRandHos;
                    args[4] = k;
                    PartD.main(args);
                    System.out.println(">>> Output file is in '" + outputFile + "'");
                    break;
                }
                case "b": {
                    String[] args = new String[5];
                    String roadFile = "file1-10k.txt";
                    String hosFile = "file2-10k.txt";
                    String outputFile = "output-b-10k.txt";
                    String makeRandHos = "false";
                    System.out.print("Top-k nearest paths. k = ");
                    String k = sc.nextLine();
                    args[0] = roadFile;
                    args[1] = hosFile;
                    args[2] = outputFile;
                    args[3] = makeRandHos;
                    args[4] = k;
                    PartD.main(args);
                    System.out.println(">>> Output file is in '" + outputFile + "'");
                    break;
                }
                case "c": {
                    String[] args = new String[5];
                    String roadFile = "roadNet_CA.txt";
                    String hosFile = "fileHos.txt";
                    String outputFile = "output-b-real-data.txt";
                    String makeRandHos = "true";
                    System.out.print("Top-k nearest paths. k = ");
                    String k = sc.nextLine();
                    args[0] = roadFile;
                    args[1] = hosFile;
                    args[2] = outputFile;
                    args[3] = makeRandHos;
                    args[4] = k;
                    PartD.main(args);
                    System.out.println(">>> Output file is in '" + outputFile + "'");
                    break;
                }
                case "d": {
                    String[] args = new String[5];
                    System.out.print("Absolute file path of graph structure: ");
                    String roadFile = sc.nextLine();
                    System.out.print("Absolute file path of hospitals: ");
                    String hosFile = sc.nextLine();
                    String outputFile = "output-b-own-data.txt";
                    String makeRandHos = "false";
                    System.out.print("Top-k nearest paths. k = ");
                    String k = sc.nextLine();
                    args[0] = roadFile;
                    args[1] = hosFile;
                    args[2] = outputFile;
                    args[3] = makeRandHos;
                    args[4] = k;
                    PartD.main(args);
                    System.out.println(">>> Output file is in '" + outputFile + "'");
                    break;
                }
            }
        } while (!choice.equals("x"));
    }
}
