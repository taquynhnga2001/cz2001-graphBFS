import java.io.*;
import java.util.HashSet;

public class RandHospital {
    public static void writeRandHos(int numHos, int numNode) {
        HashSet<Integer> hospitals = new HashSet<>();
        int hosId;
        boolean loopMore = false;

        for (int i = 0; i < numHos; i++) {
            do { // generate random hosId
                hosId = (int) (Math.random() * (numNode));
                if (hospitals.contains(hosId)) {
                    loopMore = true;
                    // break;
                } else
                    loopMore = false;
            } while (loopMore); // generate another hosID if hospitals contain that hosId
            hospitals.add(hosId);
        }

        try {
            FileWriter fw = new FileWriter("fileHos.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("# " + hospitals.size());
            for (int id: hospitals) pw.println(id);
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}
