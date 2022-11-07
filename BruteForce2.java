import java.util.*;

public class BruteForce2 {
    private final int N;
    private String[] listOfMen;
    private String[] listOfMen2;
    private String[] listOfWomen;
    private String[] womanPartners;
    private String[][] menPrefs;
    private String[][] womenPrefs;

    ArrayList<String[]> allSolutionSets;

    /**
     * Constructor for the BruteForce class
     * @param listOfMen - list of male names
     * @param listOfWomen - list of female names
     * @param menPrefs - preference list containing each woman in descending order per man
     * @param womenPrefs - preference list containing each man in descending order per woman
     */
    public BruteForce2(String[] listOfMen, String[] listOfWomen, String[][] menPrefs, String[][] womenPrefs) {
        this.N = listOfMen.length;
        this.listOfMen = listOfMen;
        this.listOfMen2 = listOfMen.clone();
        this.listOfWomen = listOfWomen;
        this.womanPartners = new String[listOfWomen.length];
        this.menPrefs = menPrefs;
        this.womenPrefs = womenPrefs;
        this.allSolutionSets = new ArrayList<String[]>();
    }

    /**
     * Generates every permutation of stable sets of matches and checks if they are stable.
     * Does not store in memory.
     * @return true if a stable set of matches is found, false otherwise
     */
    public boolean generateAllSolutionSets() {
        // use heap's algorithm to generate all permutations

        // initialize the array
        int[] c = new int[N];
        for (int i = 0; i < N; i++) {
            c[i] = 0;
        }

        // check that the 1:1 mapping is stable
        if (isStable(listOfMen.clone())) {
            return true;
        }

        // generate each permutation and check if it is stable
        int i = 1;
        while (i < N) {
            if (c[i] < i) {
                if (i % 2 == 0) {
                    swap(listOfMen, 0, i);
                } else {
                    swap(listOfMen, c[i], i);
                }
                
                // check that the current permutation is stable
                if (isStable(listOfMen.clone())) {
                    return true;
                }

                // basically a stack pointer
                c[i]++;
                i = 1;
            } else {
                c[i] = 0;
                i++;
            }
        }
        return false;
    }

    /**
     * Checks if the solution set containing matches between men and woman is stable
     * @param solutionSet - list of men paired with the woman represented by the index (e.g. solutionSet[0] = "A" means that "A" is paired with "W1")
     * @return true if the solution set is stable, false otherwise
     */
    public boolean isStable(String[] solutionSet) {
        String currentMan;
        String currentWoman;
        String[] womanLikes;
        String[] womanLikesBetter;
        String[] manLikes;
        String[] manLikesBetter;

        for (int i = 0; i < N; i++) {
            // get the current couple's information
            currentMan = solutionSet[i];
            currentWoman = "W" + (i + 1);

            // get the woman's prefs and see who she likes more than current fiance
            womanLikes = womenPrefs[i];
            womanLikesBetter = Arrays.copyOfRange(womanLikes, 0, Arrays.asList(womanLikes).indexOf(currentMan));

            // get the man's prefs and see who he likes more than current fiancee
            int idx = Arrays.asList(listOfMen2).indexOf(currentMan);
            manLikes = menPrefs[idx];
            manLikesBetter = Arrays.copyOfRange(manLikes, 0, Arrays.asList(manLikes).indexOf(currentWoman));

            // if the guy does not like the woman of the current couple more than his current fiancee, return false
            for (String guy : womanLikesBetter) {
                String guysGirl = "W" + (Arrays.asList(solutionSet).indexOf(guy) + 1);
                String[] guyLikes = menPrefs[getMenIndex(guy)];
                if (Arrays.asList(guyLikes).indexOf(guysGirl) > Arrays.asList(guyLikes).indexOf(currentWoman)) {
                    return false;
                }
            }

            // if the woman does not like the man of the current couple more than her current fiance, return false
            for (String gal : manLikesBetter) {
                String galsGuy = solutionSet[getWomenIndex(gal)];
                String[] galLikes = womenPrefs[getWomenIndex(gal)];
                if (Arrays.asList(galLikes).indexOf(galsGuy) > Arrays.asList(galLikes).indexOf(currentMan)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Swaps the elements at indices i and j in the array.
     * @param arr the array
     * @param i the first index
     * @param j the second index
     */
    private static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Finds the index of a woman based on their name
     * 
     * @param name - the name of the woman
     * @return index of woman based on their name
     */
    private int getWomenIndex(String name) {
        for (int i = 0; i < N; i++) {
            if (listOfWomen[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the index of a man based on their name
     * 
     * @param name - the name of the man
     * @return index of man based on their name
     */
    private int getMenIndex(String name) {
        for (int i = 0; i < N; i++) {
            if (listOfMen2[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("Brute-Force Marriage Algorithm\n");
        /** list of men **/
        String[] m = { "M1", "M2" };
        /** list of women **/
        String[] w = { "W1", "W2" };

        /** men preference **/
        String[][] mp = { { "W1", "W2" },
                { "W2", "W1" } };
        /** women preference **/
        String[][] wp = { { "M2", "M1" },
                { "M1", "M2" } };

        // String[] m = {"M1", "M2", "M3", "M4", "M5"};
        // /** list of women **/
        // String[] w = {"W1", "W2", "W3", "W4", "W5"};

        // /** men preference **/
        // String[][] mp = {{"W5", "W2", "W3", "W4", "W1"},
        // {"W2", "W5", "W1", "W3", "W4"},
        // {"W4", "W3", "W2", "W1", "W5"},
        // {"W1", "W2", "W3", "W4", "W5"},
        // {"W5", "W2", "W3", "W4", "W1"}};
        // /** women preference **/
        // String[][] wp = {{"M5", "M3", "M4", "M1", "M2"},
        // {"M1", "M2", "M3", "M5", "M4"},
        // {"M4", "M5", "M3", "M2", "M1"},
        // {"M5", "M2", "M1", "M4", "M3"},
        // {"M2", "M1", "M4", "M3", "M5"}};

        // You can use the above example problem to test your code if you
        // do not use RunAlgorithms.java
        BruteForce2 bf = new BruteForce2(m, w, mp, wp);

        long startTime = System.currentTimeMillis();
        boolean result = bf.generateAllSolutionSets();
        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");

        // String[] result = bf.isStableSolution();
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
