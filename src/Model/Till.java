package Model;
import java.math.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
public class Till {
    private Queue <Customer> customers;
    private double gallonPrice;
    private double shopTotal;
    private Random rng;
    private double tillMoney;
    /**
     * Instantiates a new till.
     *
     * @param gallonPrice the gallonPrice
     */
    public Till(double gallonPrice, Random rng){
        this.gallonPrice = gallonPrice;
        this.rng = rng;
        tillMoney = 0;
        customers = new LinkedList<Customer>();
    }
    /**
     * Adds the customer to the till.
     *
     * @param customer the customer
     */
    public void addCustomer(Customer customer){
        customers.add(customer);
    }
    /**
     * Calculates the payment for the gallons filled.
     */
    public double pay(double shopSpend){
        Customer customer = customers.peek();
        double total = (gallonPrice*customer.getGallonsTaken()) + shopSpend;
        total = round(total, 2);
        tillMoney = tillMoney + total;
        return total;
    }
    public double browseShop(Customer customer){
        double shopSpend = 0.0;
        if(customer.getFillTime() > customer.getToleranceTime()){
            customer.setHappy(false);
            return 0.0;
        }
        else{
            double shopProb = customer.getShopChance();
            double money = customer.getSpendingMoney();
            double randomNumber = rng.nextInt(100);
            randomNumber = randomNumber/100;
            if(randomNumber < shopProb){
                shopSpend = money;
                shopTotal = shopTotal + money;
                return shopSpend;
            }
            else{
                return 0.0;
            }
        }
    }
    /**
     * Returns a rounded double.
     *
     * @param value double to be rounded
     * @param places num. of dp.
     * @return rounded value.
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public void removeCustomer(){
        customers.remove();
    }
//-------------------------------------------------------------------//
    /**
     * Gets the price of a gallon.
     *
     * @return the price
     */
    public double getPrice() {
        return gallonPrice;
    }
    /**
     * Gets the customer.
     *
     * @return the customer
     */
    public Customer getCustomer(){
        return customers.peek();
    }
    /**
     * Gets the size.
     *
     * @return the size
     */
    public int getSize(){
        int size = customers.size();
        return size;
    }
    /** makes a string format of till informatino
     * @returns the string
     */
    public String toString() {
        String str = "" ;
        str = "Total money " + tillMoney + customers.toString() + " | Total of £" + shopTotal + " spent at the shop.";
        return str ;
    }
    /**
     * To string tills for GUI purpose.
     *
     * @return the string
     */
    public String toStringGUI() {
        String str = "" ;
        str = "Total money " + tillMoney + customers.toString();
        return str ;
    }
}