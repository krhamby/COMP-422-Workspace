import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
// import java.util.Random;

/**
 * Class containing everything to execute the Gale-Shapley algorithm. Call
 * findStableMatches on your instance of this class. 
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


    private int getWomenIndex(String name) {
        for (int i = 0; i < N; i++) {
            if (listOfWomen[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private int getMenIndex(String name) {
        for (int i = 0; i < N; i++) {
            if (listOfMen[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private int getPreference(int index, String name) {
        for (int i = 0; i < womenPrefs[index][0].length(); i++) {
            if (womenPrefs[index][i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private void printPartners() {
        for (int i = 0; i < N; i++) {
            System.out.println("(" + womenPartners[i] + ", " + listOfWomen[i] + ")");
        }
    }

    public static void main(String[] args) {
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

        String[] m = new String[10000];
        String[] w = new String[10000];
        for (int i = 0; i < 10000; i++) {
            m[i] = "M" + (i + 1);
            w[i] = "W" + (i + 1);
        }

        String[][] mp = new String[10000][10000];
        String[][] wp = new String[10000][10000];
        for (int i = 0; i < 10000; i++) {
            String[] subMP = new String[10000];
            String[] subWP = new String[10000];
            ArrayList<Integer> randM = new ArrayList<Integer>();
            ArrayList<Integer> randW = new ArrayList<Integer>();
            for (int k = 0; k < 10000; k++) {
                randM.add(k, k);
                randW.add(k, k);
            }
            Collections.shuffle(randW);
            Collections.shuffle(randM);
            // int[] randM = new Random().ints(1, 10000).distinct().limit(10000).toArray();
            // int[] randW = new Random().ints(1, 10000).distinct().limit(10000).toArray();
            for (int j = 0; j < 10000; j++) {
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
 