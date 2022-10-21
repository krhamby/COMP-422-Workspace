import java.util.*;

public class BruteForce2 {
    private final int N;
    private String[] listOfMen;
    private String[] listOfWomen;
    private String[] womanPartners;
    private String[][] menPrefs;
    private String[][] womenPrefs;

    ArrayList<String[]> allSolutionSets;

    public BruteForce2(String[] listOfMen, String[] listOfWomen, String[][] menPrefs, String[][] womenPrefs) {
        this.N = listOfMen.length;
        this.listOfMen = listOfMen;
        this.listOfWomen = listOfWomen;
        this.womanPartners = new String[listOfWomen.length];
        this.menPrefs = menPrefs;
        this.womenPrefs = womenPrefs;
        this.allSolutionSets = new ArrayList<String[]>();
    }

    private void generateAllSolutionSets() {
        // use heap's algorithm to generate all permutations

        // initialize the array
        int[] c = new int[N];
        for (int i = 0; i < N; i++) {
            c[i] = 0;
        }

        // add the first permutation
        allSolutionSets.add(listOfMen.clone());

        int i = 1;
        while (i < N) {
            if (c[i] < i) {
                if (i % 2 == 0) {
                    swap(listOfMen, 0, i);
                } else {
                    swap(listOfMen, c[i], i);
                }
                // add the current permutation to the list of all solution sets
                allSolutionSets.add(listOfMen.clone());
                c[i]++;
                i = 1;
            } else {
                c[i] = 0;
                i++;
            } 
        }
    }


    public String[] isStableSolution() {
        generateAllSolutionSets();
        // Collections.reverse(allSolutionSets);
        // System.out.println("Number of solution sets: " + allSolutionSets.size()); // debugging
        for (String[] solutionSet: allSolutionSets) {
            // for a given solutionSet, if a man prefers another woman more than his
            // current partner, and a woman prefers him more than her current partner,
            // then the solutionSet is not stable

            // print current pairings for debugging
            // for (int i = 0; i < N; i++) {
            //     System.out.println("W" + (i + 1) + " - " + solutionSet[i]);
            // }
            
            boolean isStable = true;
            thisCouple: for (int i = 0; i < N; i++) {
                for (String man : womenPrefs[i]) {
                    if (getWomanPreference(i, man) < getWomanPreference(i, solutionSet[i])) {
                        int mansFianceIndex = getFemalePartnerIndex(man, solutionSet);
                        String femaleName = "W" + (mansFianceIndex + 1); // current fiance
                        String competingFemaleName = "W" + (i + 1); // current female in couple
                        int manIndex = Character.getNumericValue(man.toCharArray()[1]);
                        if (getManPreference(manIndex - 1, femaleName) > getManPreference(manIndex - 1,
                                competingFemaleName)) {
                            isStable = false;
                            break thisCouple;
                        }
                    }
                }

                for (String woman : menPrefs[getMenIndex(solutionSet[i])]) {
                    int manIndex = getMenIndex(solutionSet[i]);
                    if (getManPreference(manIndex, woman) < getManPreference(manIndex, "W" + (i + 1))) {
                        int index = Character.getNumericValue(woman.toCharArray()[1]);
                        String maleName = solutionSet[index - 1];
                        String competingMaleName = solutionSet[i];
                        if (getWomanPreference(i, maleName) > getWomanPreference(i, competingMaleName)) {
                            isStable = false;
                            break thisCouple;
                        }
                    }
                }
            }
            if (isStable) {
                return solutionSet;
            }
        }
        return null;
    }
        
private static void  swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

   /**
     * Returns a given woman's preference ranking (index) 
     * for a given man
     * @param index - the index value of the woman
     * @param name - name of the man whose preference ranking is being found
     * @return index representing the index (and therefore ranking) of the man
     */
    private int getWomanPreference(int index, String name) {
        for (int i = 0; i < womenPrefs[index][0].length(); i++) {
            if (womenPrefs[index][i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private int getManPreference(int index, String name) {
        for (int i = 0; i < menPrefs[index][0].length(); i++) {
            if (menPrefs[index][i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private int getFemalePartnerIndex(String name, String[] solutionSet) {
        for (int i = 0; i < N; i++) {
            if (solutionSet[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the index of a woman based on their name
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
     * @param name - the name of the man
     * @return index of man based on their name
     */
    private int getMenIndex(String name) {
        for (int i = 0; i < N; i++) {
            if (listOfMen[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("Brute-Force Marriage Algorithm\n");
        /** list of men **/
        String[] m = {"M1", "M2"};
        /** list of women **/
        String[] w = {"W1", "W2"};
 
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
        //                  {"W2", "W5", "W1", "W3", "W4"}, 
        //                  {"W4", "W3", "W2", "W1", "W5"}, 
        //                  {"W1", "W2", "W3", "W4", "W5"},
        //                  {"W5", "W2", "W3", "W4", "W1"}};
        // /** women preference **/                      
        // String[][] wp = {{"M5", "M3", "M4", "M1", "M2"}, 
        //                  {"M1", "M2", "M3", "M5", "M4"}, 
        //                  {"M4", "M5", "M3", "M2", "M1"},
        //                  {"M5", "M2", "M1", "M4", "M3"}, 
        //                  {"M2", "M1", "M4", "M3", "M5"}};

        BruteForce2 bf = new BruteForce2(m, w, mp, wp);

        long startTime = System.currentTimeMillis();
        String[] result = bf.isStableSolution();
        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");

        // String[] result = bf.isStableSolution();
        if (result != null) {
            System.out.println("Stable solution found:");
            for (int i = 0; i < result.length; i++) {
                System.out.println("W" + (i + 1) + " - " + result[i]);
            }
        } else {
            System.out.println("No stable solution found");
        }
    }
}
