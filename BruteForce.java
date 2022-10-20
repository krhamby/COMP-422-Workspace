import java.util.ArrayList;

public class BruteForce {
    private ArrayList<String> listOfMen;
    private ArrayList<String> listOfWomen;
    private ArrayList<String> womanPartners;

    public BruteForce(ArrayList<String> listOfMen, ArrayList<String> listOfWomen) {
        this.listOfMen = listOfMen;
        this.listOfWomen = listOfWomen;
        this.womanPartners = new ArrayList<String>();
    }

    // generate all pairings using the Cartesian product
    // https://blogs.ubc.ca/cpsc3202016w2/files/2017/01/2016-01-04-notes-smp-intro-sample-soln.pdf
    // NOTE: this starting code may be incorrect; please do not get stuck on it
    // You may need to use a separate data structure to store all of the possible pairs
}
