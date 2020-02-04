package Model;
import java.util.*;

public class Truck extends Vehicle{
    private int toleranceTime = 42;
    private int browseMin = 24;
    private int browseMax = 36;
    private int shopMin = 15;
    private int shopMax = 20;
    private double shopChance = 1;
    /**
     * Instantiates a new truck.
     */
    public Truck(Random rng){
        super(rng);
        setSize(2.0);
        setTank(30,40);
        setInitialFuel();
        customer.carIdentifier(toleranceTime, browseMin, browseMax, shopMin, shopMax);
        customer.setShopChance(shopChance);
        customer.setDriver("truck");
    }
    /**
     * toString method.
     *
     * @return Truck class in String form
     */
    public String toString(){
        String str = "";
        str = str + "Truck " + " |tankSize: " + tankSize + "|| fuel: " + fuel + "|";
        return str;
    }
}