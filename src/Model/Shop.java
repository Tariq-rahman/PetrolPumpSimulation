package Model;
import java.util.*;

public class Shop {
    /** The till. */
    private Till till;
    /** The tills. */
    private ArrayList<Till> tills;
    /** The gallon price. */
    private double gallonPrice;
    /**
     * Instantiates a new shop.
     *
     * @param numTills the number of tills
     * @param gp the gallonPrice
     * @param rng the rng
     */
    public Shop(int numTills, double gp,Random rng){
        tills = new ArrayList<Till>();
        gallonPrice = gp;
        createTills(numTills, rng);
    }
    /**
     * Creates the tills.
     *
     * @param num the number of tills to create.
     * @param rng the rng
     */
    public void createTills(int num, Random rng){
        for(int i = 0; i < num; i++){
            till = new Till(gallonPrice, rng);
            tills.add(till);
        }
    }
    /**
     * Allocate customer.
     * inserts customer into till.
     * @param customer the customer
     */
    public void allocateCustomer(Customer customer){
        int index = getLeastOccupiedTill();
        tills.get(index).addCustomer(customer);
    }
    /**
     * Gets the least occupied till.
     *
     * @return the index of the least occupied till
     */
    public int getLeastOccupiedTill(){
        int x = tills.get(0).getSize();
        int index = 0;
        int y = 0;
        for(int i = 1; i < tills.size(); i++){
            y = tills.get(i).getSize();
            if(y < x){
                x = y;
                index = i;
            } //returns index of least full till.
        }
        return index;
    }
    /**
     * Empty tills.
     */
    public void emptyTills(){
        tills.clear();
    }
    /**
     * Gets the till.
     *
     * @param i the index
     * @return the till
     */
    public Till getTill(int i){
        return tills.get(i);
    }
    /**
     * Gets the tills.
     *
     * @return the tills
     */
    public ArrayList<Till> getTills() {
        return tills;
    }
    /**
     * toString
     *
     * returns a string of tills and customer details.
     *
     * @return the string
     */
    public String toString(){
        String str = "";
        for(int i = 0; i < tills.size(); i++){
            str = str + "till " + i + " " + tills.get(i).toString() + "\n" ;
        }
        return str;
    }
}