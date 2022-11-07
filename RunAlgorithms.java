import java.util.*;

public class RunAlgorithms {

    String[] listOfMen;
    String[] listOfWomen;
    String[][] menPrefs;
    String[][] womenPrefs;

    int size;

    public RunAlgorithms(int size) {
        this.size = size;
    }

    /**
     * Generate a random list of men and women, and two matrices of their preferences
     */
    public void generateRandomInput() {
        listOfMen = new String[size];
        listOfWomen = new String[size];
        for (int i = 0; i < size; i++) {
            listOfMen[i] = "M" + (i + 1);
            listOfWomen[i] = "W" + (i + 1);
        }

        menPrefs = new String[size][size];
        womenPrefs = new String[size][size];
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
                subMP[j] = listOfWomen[randM.get(j)];
                subWP[j] = listOfMen[randW.get(j)];
            }

            menPrefs[i] = subMP;
            womenPrefs[i] = subWP;
        }
    }

    public static void main(String[] args) {
        boolean exludeBruteForce = false;

        // check that only 1 or 2 arguments are passed
        if (args.length != 1 && args.length != 2) {
            System.out.println("Usage: java RunAlgorithms <size of random input>");
            System.out.println("OR");
            System.out.println("Usage: java RunAlgorithms --exclude <size of random input>");
            System.exit(1);
        }

        // convert argument to int and catch error
        int size = 0;
        if (args.length == 1) {
            try {
                size = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Usage: java RunAlgorithms <size of random input>");
                System.exit(1);
            }
        } else {
            try {
                size = Integer.parseInt(args[1]);
                if (args[0].equals("--exclude")) {
                    exludeBruteForce = true;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Usage: java RunAlgorithms --exclude <size of random input>");
                System.exit(1);
            }
        }

        // for debugging
        // size = 4;

        RunAlgorithms run = new RunAlgorithms(size);

        long totalTimeGS = 0;
        long totalTimeBF = 0;
        for (int i = 0; i < 10; i++) {
            run.generateRandomInput();

            // initialize Gale-Shapley object and run algorithm
            GaleShapley gs = new GaleShapley(run.listOfMen, run.listOfWomen, run.menPrefs, run.womenPrefs);
            long startTime = System.nanoTime();
            gs.findStableMatches();
            long endTime = System.nanoTime();
            totalTimeGS += endTime - startTime;
            System.out.println("Iteration " + (i + 1) + " (GS): " + (endTime - startTime) + " ns");

            // if not excluding brute force, initialize BruteForce object and run algorithm
            if (!exludeBruteForce) {
                BruteForce2 bf = new BruteForce2(run.listOfMen, run.listOfWomen, run.menPrefs, run.womenPrefs);
                startTime = System.nanoTime();
                // String[] result = bf.findStableSolution();
                boolean result = bf.generateAllSolutionSets();
                endTime = System.nanoTime();
                totalTimeBF += endTime - startTime;
                System.out.println("Iteration " + (i + 1) + " (BF): " + (endTime - startTime) + " ns");

                // check that results exist because of previous issues (should be fixed)
                if (result) {
                    System.out.println("Brute Force: \u001B[32mstable solution found\u001B[0m");
                } else {
                    System.out.println("\u001B[31mBrute Force: no stable solution found\u001B[0m");
                }
            }
        }
        System.out.println("Avg. time for 10 iterations of Gale-Shapley for problem size " + size + ": " + totalTimeGS / 10 + " ns");
        if (!exludeBruteForce) {
            System.out.println("Avg. time for 10 iterations of Brute Force for problem size " + size + ": " + totalTimeBF / 10 + " ns");
        }
    }
}
