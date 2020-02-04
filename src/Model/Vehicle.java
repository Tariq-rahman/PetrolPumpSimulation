package Model;
import java.util.Random;

public class Vehicle {
    protected int tankSize;
    protected double vehicleSize;
    protected Random rng;
    protected int fuel;
    protected double p;
    protected double q;
    protected Customer customer;
    /**
     * Instantiates a new vehicle.
     */
    public Vehicle(Random rng){
        this.rng = rng;
        tankSize = 0;
        vehicleSize = 0.0;
        fuel = 0;
        customer = new Customer(rng);
    }
    /**
     * sets the tankSize based on numbers given to RNG.
     * @param min = min tankSize
     * @param max = max tankSize
     *
     */
    public void setTank(int min, int max){
        int c = (max - min) + 1;
        tankSize = min + rng.nextInt(c);
    }
    /**
     * Sets the size.
     *
     * @param newSize the new size
     */
    /*
     * sets the size of vehicle
     */
    public void setSize(double newSize){
        vehicleSize = newSize;
    }
/**
 * Sets the truck probability.
 *
 * @param customer the new truck probability
 */
    /**
     * Sets the initial fuel.
     */
    public void setInitialFuel(){
        int i = getTankSize() / 2;
        fuel = rng.nextInt(Math.round(i));
    }
    /**
     * Fill tank.
     *
     * @param addedFuel the added fuel
     */
    public void fillTank(int addedFuel){
        fuel = fuel + addedFuel;
        customer.buyGallons(addedFuel);
    }
    /**
     * Sets the probability chance of p.
     *
     * @param p the new pChance
     */
    public void setP(double p){
        this.p = p;
    }
    /**
     * Sets the probability of q.
     *
     * @param q the new qChance
     */
    public void setQ(double q){
        this.q = q;
    }
    /**
     * Check if tank is full.
     *
     * @return true, if successful
     */
    public boolean checkIsFull(){
        if(tankSize == fuel){
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * Gets the tank size.
     *
     * @return the tank size as int.
     */
    public int getTankSize(){
        return tankSize;
    }
    /**
     * Gets the fuel.
     *
     * @return the fuel as int.
     */
    public int getFuel(){
        return fuel;
    }
    /**
     * Gets the vehicle size.
     *
     * @return the vehicle size
     */
    public double getVehicleSize(){
        return vehicleSize;
    }
    /**
     * Gets the customer.
     *
     * @return the customer
     */
    public Customer getCustomer(){
        return customer;
    }
}