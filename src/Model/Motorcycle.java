package Model;
import java.util.*;
public class Motorcycle extends Vehicle{
    private int toleranceTime = 999999999;
    private int browseMin = 0;
    private int browseMax = 0;
    private int shopMin = 0;
    private int shopMax = 0;
    public Motorcycle(Random rng){
        super(rng);
        setTank(5,5);
        setSize(0.75);
        setInitialFuel();
//Motercycles do not shop
        customer.carIdentifier(toleranceTime, browseMin, browseMax, shopMin, shopMax);
        customer.setDriver("motorcycle");
    }
    /** returns a vehicle as a string
     * @return a string
     */
    public String toString(){
        String str = "";
        str = str + "Motorcycle " + " |tankSize: " + tankSize + " || fuel: " + fuel + "|";
        return str;
    }
}
