import java.util.*;

public class RunAlgorithms {
    public static void main(String[] args) {
        // check that only 1 argument is passed
        if (args.length != 1) {
            System.out.println("Usage: java GaleShapley <size of random input>");
            System.exit(1);
        }

        // convert argument to int and catch error
        int size = 0;
        try {
            size = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Usage: java GaleShapley <size of random input>");
            System.exit(1);
        }

        // for debugging
        // size = 4;

        System.out.println("Generating random input of size " + size + "...");
        String[] m = new String[size];
        String[] w = new String[size];
        for (int i = 0; i < size; i++) {
            m[i] = "M" + (i + 1);
            w[i] = "W" + (i + 1);
        }

        String[][] mp = new String[size][size];
        String[][] wp = new String[size][size];
        for (int i = 0; i < size; i++) {
            String[] subMP = new String[size];
            String[] subWP = new String[size];
            ArrayList<Integer> randM = new ArrayList<Integer>();
            ArrayList<Integer> randW = new ArrayList<Integer>();
            for (int k = 0; k < size; k++) {
                randM.add(k, k);
                randW.add(k, k);
            }
            Collections.shuffle(randW);
            Collections.shuffle(randM);
            for (int j = 0; j < size; j++) {
                subMP[j] = w[randM.get(j)];
                subWP[j] = m[randW.get(j)];
            }
            
            mp[i] = subMP;
            wp[i] = subWP;
        }    

        GaleShapley gs = new GaleShapley(m, w, mp, wp);
        long startTime = System.nanoTime();
        gs.findStableMatches();
        long endTime = System.nanoTime();
        System.out.println("Gale-Shapley Time: " + (endTime - startTime) + " ns (" + (endTime - startTime) / 1000000 + " ms)");

        BruteForce2 bf = new BruteForce2(m, w, mp, wp);
        startTime = System.nanoTime();
        String[] result = bf.findStableSolution();
        endTime = System.nanoTime();
        System.out.println("Brute Force Time: " + (endTime - startTime) + " ns (" + (endTime - startTime) / 1000000 + " ms)");

        if (result != null) {
            System.out.println("\u001B[32mBrute Force: stable solution found:\u001B[0m");
            for (int i = 0; i < result.length; i++) {
                System.out.println("(" + result[i] + ", " + "W" + (i + 1) + ")");
            }
        } else {
            System.out.println("\u001B[31mBrute Force: no stable solution found\u001B[0m");
        }

        // print preference matrix mp
        // System.out.println("Preference matrix mp:");
        // for (int i = 0; i < size; i++) {
        //     for (int j = 0; j < size; j++) {
        //         System.out.print(mp[i][j] + " ");
        //     }
        //     System.out.println();
        // }

        // // print preference matrix wp
        // System.out.println("Preference matrix wp:");
        // for (int i = 0; i < size; i++) {
        //     for (int j = 0; j < size; j++) {
        //         System.out.print(wp[i][j] + " ");
        //     }
        //     System.out.println();
        // }
    }
}
