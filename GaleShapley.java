import java.util.ArrayList;
import java.util.Collections;

/**
 * Class containing everything to execute the Gale-Shapley algorithm. Call
 * findStableMatches on your instance of this class to find a set of stable matchings. 
 * Some ideaologies taken from the following:
 * https://www.sanfoundry.com/java-program-gale-shapley-algorithm/
 * @author Connor Felton, Kevin Hamby, Zachary O'Brien
 */
class GaleShapley {
    private final int N;
    private int numEngaged;
    private String[] listOfMen;
    private String[] listOfWomen;
    private String[][] menPrefs;
    private String[][] womenPrefs;
    private String[] womenPartners;
    private boolean[] isEngaged;

    /**
     * Constructor for GaleShapley class.
     * @param listOfMen - list of names
     * @param listOfWomen - list of names
     * @param menPrefs - 2D array of preferences where each row is a list of the i man's preferences
     * @param womenPrefs - 2D array of preferences where each row is a lits of the i woman's preferences
     */
    public GaleShapley(String[] listOfMen, String[] listOfWomen, String[][] menPrefs, String[][] womenPrefs) {
        this.N = menPrefs.length;
        this.numEngaged = 0;
        this.listOfMen = listOfMen;
        this.listOfWomen = listOfWomen;
        this.menPrefs = menPrefs;
        this.womenPrefs = womenPrefs;
        this.womenPartners = new String[N];
        this.isEngaged = new boolean[N];
    }

    /**
     * Finds a set of stable matchings using the Gale-Shapley algorithm.
     */
    public void findStableMatches() {
        while (numEngaged < N) {

            int freeMan;
            for (freeMan = 0; freeMan < N; freeMan++) {
                if (!isEngaged[freeMan]) {
                    break;
                }
            }

            for (int i = 0; i < N && !isEngaged[freeMan]; i++) {
                int index = getWomenIndex(menPrefs[freeMan][i]);
                if (womenPartners[index] != null) {
                    int currentPartnerPref = getPreference(index, womenPartners[index]);
                    if (getPreference(index, listOfMen[freeMan]) > currentPartnerPref) {
                        isEngaged[getMenIndex(womenPartners[index])] = false;
                        womenPartners[index] = listOfMen[freeMan];
                        isEngaged[freeMan] = true;
                    }
                } else {
                    womenPartners[index] = listOfMen[freeMan];
                    isEngaged[freeMan] = true;
                    numEngaged++;
                }
            }
        }
        // printPartners();
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

    /**
     * Returns a given woman's preference ranking (index) 
     * for a given man
     * @param index - the index value of the woman
     * @param name - name of the man whose preference ranking is being found
     * @return index representing the index (and therefore ranking) of the man
     */
    private int getPreference(int index, String name) {
        for (int i = 0; i < womenPrefs[index][0].length(); i++) {
            if (womenPrefs[index][i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Prints out each partner. Only used for testing purposes.
     */
    private void printPartners() {
        for (int i = 0; i < N; i++) {
            System.out.println("(" + womenPartners[i] + ", " + listOfWomen[i] + ")");
        }
    }

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
        double startTime = System.currentTimeMillis();
        gs.findStableMatches();
        double endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");
    }
}
 