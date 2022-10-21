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
        System.out.println("Gale-Shapley Time: " + (endTime - startTime) / 1000000 + "ms");

        BruteForce2 bf = new BruteForce2(m, w, mp, wp);
        startTime = System.nanoTime();
        String[] result = bf.isStableSolution();
        endTime = System.nanoTime();
        System.out.println("Brute Force Time: " + (endTime - startTime) / 1000000 + "ms");
        if (result != null) {
            System.out.println("Brute Force: Stable solution found.");
        } else {
            System.out.println("Brute Force: No stable solution found.");
        }

        // if (result != null) {
        //     System.out.println("Stable solution found:");
        //     for (int i = 0; i < result.length; i++) {
        //         System.out.println("W" + (i + 1) + " - " + result[i]);
        //     }
        // } else {
        //     System.out.println("No stable solution found");
        // }
    }
}
