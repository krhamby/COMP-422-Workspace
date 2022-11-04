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

    public String[] findStableSolution() {
        generateAllSolutionSets();
        for (String[] solutionSet : allSolutionSets) {

            // System.out.println("Current solution set: ");
            // for (int i = 0; i < N; i++) {
            //     System.out.println("(" + solutionSet[i] + ", " + "W" + (i + 1) + ")");
            // }

            if (isStable(solutionSet)) {
                return solutionSet;
            }
        }
        return null;
    }

    public boolean isStable(String[] solutionSet) {
        String currentMan;
        String currentWoman;
        String[] womanLikes;
        String[] womanLikesBetter;
        String[] manLikes;
        String[] manLikesBetter;

        for (int i = 0; i < N; i++) {
            currentMan = solutionSet[i];
            currentWoman = "W" + (i + 1);

            womanLikes = womenPrefs[i];
            womanLikesBetter = Arrays.copyOfRange(womanLikes, 0, Arrays.asList(womanLikes).indexOf(currentMan));

            int idx = Arrays.asList(listOfMen2).indexOf(currentMan);
            manLikes = menPrefs[idx];
            manLikesBetter = Arrays.copyOfRange(manLikes, 0, Arrays.asList(manLikes).indexOf(currentWoman));

            for (String guy : womanLikesBetter) {
                String guysGirl = "W" + (Arrays.asList(solutionSet).indexOf(guy) + 1);
                String[] guyLikes = menPrefs[getMenIndex(guy)];
                if (Arrays.asList(guyLikes).indexOf(guysGirl) > Arrays.asList(guyLikes).indexOf(currentWoman)) {
                    return false;
                }
            }

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


    public String[] isStableSolution() {
        generateAllSolutionSets();

        for (String[] solutionSet: allSolutionSets) {
        
            // DEBUGGING: print out the current solution set of pairings
            // System.out.println("Current solution set: ");
            // for (int i = 0; i < N; i++) {
            //     System.out.println("W" + (i + 1) + " - " + solutionSet[i]);
            // }

            boolean isStable = true;
            thisCouple: for (int i = 0; i < N; i++) {
                String[] currentWomanPrefs = womenPrefs[i];
                int currentWomanPrefIndex = getWomanPreference(i, solutionSet[i]);

                String[] currentWomanLikesMore = new String[N - currentWomanPrefIndex];

                // add men who current woman prefers to current partner
                for (int j = 0; j < N; j++) {
                    if (j > currentWomanPrefIndex) {
                        currentWomanLikesMore[j - currentWomanPrefIndex - 1] = currentWomanPrefs[j];
                    }
                }
                


                for (String competingMan : currentWomanLikesMore) {
                    String currentFemaleInCouple = "W" + (i + 1);
                    String currentMaleInCouple = solutionSet[i];

                    // if the woman likes another man over the current, check if the man likes someone
                    // else over his
                    if (getWomanPreference(i, competingMan) > getWomanPreference(i, currentMaleInCouple)) {
                        int competingWomanIndex = getFemalePartnerIndex(competingMan, solutionSet);
                        String competingWoman = "W" + (competingWomanIndex + 1);
                        int competingManIndex = Character.getNumericValue(competingMan.toCharArray()[1]);
                        if (getManPreference(competingManIndex - 1, competingWoman) > getManPreference(competingManIndex - 1, currentFemaleInCouple)) {
                            isStable = false;
                            break thisCouple;
                        }
                    }
                }

                // vice versa
                String[] currentManPrefs = menPrefs[getMenIndex(solutionSet[i])];
                int currentManPrefIndex = getManPreference(getMenIndex(solutionSet[i]), "W" + (i + 1));
                String[] currentManLikesMore = new String[N - currentManPrefIndex];

                for (int j = 0; j < N; j++) {
                    if (j > currentManPrefIndex) {
                        currentManLikesMore[j - currentManPrefIndex - 1] = currentManPrefs[j];
                    }
                }


                for (String competingWoman : currentManLikesMore) {
                    String currentFemaleInCouple = "W" + (i + 1);
                    String currentMaleInCouple = solutionSet[i];

                    if (getManPreference(i, competingWoman) > getManPreference(i, currentFemaleInCouple)) {
                        int competingWomanIndex = Character.getNumericValue(competingWoman.toCharArray()[1]) - 1;
                        String competingMan = solutionSet[competingWomanIndex];

                        if (getWomanPreference(i, competingMan) > getWomanPreference(i, currentMaleInCouple)) {
                            isStable = false;
                            break thisCouple;
                        }
                    }
                }

                // for (String woman : menPrefs[getMenIndex(solutionSet[i])]) {
                //     int manIndex = getMenIndex(solutionSet[i]);
                //     if (getManPreference(manIndex, woman) < getManPreference(manIndex, "W" + (i + 1))) {
                //         int index = Character.getNumericValue(woman.toCharArray()[1]);
                //         String maleName = solutionSet[index - 1];
                //         String competingMaleName = solutionSet[i];
                //         if (getWomanPreference(i, maleName) > getWomanPreference(i, competingMaleName)) {
                //             isStable = false;
                //             break thisCouple;
                //         }
                //     }
                // }
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
            if (listOfMen2[i].equals(name)) {
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
        String[] result = bf.findStableSolution();
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
