import java.util.ArrayList;

public class BruteForce {
    // private ArrayList<String> listOfMen;
    // private ArrayList<String> listOfWomen;
    // private ArrayList<String> womanPartners;

    // public BruteForce(ArrayList<String> listOfMen, ArrayList<String> listOfWomen) {
    //     this.listOfMen = listOfMen;
    //     this.listOfWomen = listOfWomen;
    //     this.womanPartners = new ArrayList<String>();
    // }

    // generate all pairings using the Cartesian product
    // https://blogs.ubc.ca/cpsc3202016w2/files/2017/01/2016-01-04-notes-smp-intro-sample-soln.pdf
    // NOTE: this starting code may be incorrect; please do not get stuck on it
    // You may need to use a separate data structure to store all of the possible pairs

    private class Match {
        public int woman;
        public int man;

        public Match(int woman, int man) {
            this.woman = woman;
            this.man = man;
        }
    }

    private int size; //The number of women, and also the number of men.
    private ArrayList<Integer> allMen; //A list of every woman.
    private ArrayList<Integer> allWomen; //A list of every man.
    private int[][] womenPreferences; //The preferences of every woman. If w is the woman, womenPreferences[w][i] returns which man is their (i + 1)th preference. i = 0 is their favorite.
    private int[][] menPreferences; //The preferences of every man. If m is the man, menPreferences[m][i] returns which woman is their (i + 1)th preference. i = 0 is their favorite.

    private boolean generatedData = false;

    public BruteForce(int size) {
        this.size = size;
        allWomen = new ArrayList<>();
        allMen = new ArrayList<>();
        womenPreferences = new int[size][size];
        menPreferences = new int[size][size];
    }

    public void generateData() {

        //Clear the current list of men and women.
        allWomen.clear();
        allMen.clear();

        //Generate every man and woman.
        for (int i = 0; i < size; i++) {
            allWomen.add(i);
            allMen.add(i);
        }

        //Generate their preferences.
        //TODO: Generate preferences.

        generatedData = true;
    }

    public void findStableMatches() {
        //If the men and women have not yet been generated, do not run.
        if (!generatedData) {
            System.out.println("Please run \".generateData()\" before running \".findStableMatches()\".");
            return;
        }

        //Generate every possible valid solution.
        ArrayList<ArrayList<Match>> solutions = findAllSolutions(allWomen, allMen);

        //DEBUG
        printSolutions(solutions);
    }

    /** DEBUG
     * Prints out all of the solutions in a list of solutions.
     */
    private void printSolutions(ArrayList<ArrayList<Match>> solutions) {
        for (int i = 0; i < solutions.size(); i++) {
            System.out.print("Solution " + (i+1) + ": ");

            for (int j = 0; j < solutions.get(i).size(); j++) {
                System.out.print("{" + solutions.get(i).get(j).woman + "+" + solutions.get(i).get(j).man + "} ");
            }

            System.out.println("");
        }
    }

    private ArrayList<ArrayList<Match>> findAllSolutions(ArrayList<Integer> women, ArrayList<Integer> men) {
        //Base Case
        if (women.size() == 0) {
            //If there are no people to match, return an empty set of solutions.
            return new ArrayList<ArrayList<Match>>();
        }
        else if (women.size() == 1) {
            //If there is only one man and one woman, that is the only solution.
            ArrayList<ArrayList<Match>> toReturn = new ArrayList<ArrayList<Match>>();
            ArrayList<Match> solution = new ArrayList<Match>();
            solution.add(new Match(women.get(0), men.get(0)));
            toReturn.add(solution);
            return toReturn;
        }
        else
        {
            //Set aside the first woman in the list.
            int w = women.remove(0);

            int menSize = men.size();
            int m;
            ArrayList<ArrayList<Match>> toReturn = new ArrayList<ArrayList<Match>>();

            //Iterate through every man in the list.
            for (int i = 0; i < menSize; i++) {
                //Set aside the first man in the list.
                m = men.remove(0);

                //Use a recursive call on the subproblem of all men and women except for this pair.
                //The call returns every solution for that subproblem.
                //Add this pairing to each solution.
                ArrayList<ArrayList<Match>> subsolutions = findAllSolutions(women, men);

                for (int j = 0; j < subsolutions.size(); j++) {
                    subsolutions.get(j).add(new Match(w, m));
                }

                toReturn.addAll(subsolutions);

                men.add(m);
            }

            women.add(w);

            return toReturn;
        }
    }

    public static void main(String[] args) {
        //DEBUG
        BruteForce bf = new BruteForce(3);
        bf.generateData();
        bf.findStableMatches();
    }
}
