package Model;
import java.util.*;
public class SmallCar extends Vehicle {
    private double shopChance = 0.3;
    private int toleranceTime = 30;
    private int browseMin = 12;
    private int browseMax = 24;
    private int shopMin = 5;
    private int shopMax = 10;
    /**
     * Instantiates a new small car.
     */
    public SmallCar(Random rng){
        super(rng);
        setTank(7,9);
        setSize(1.0);
        setInitialFuel();
        customer.carIdentifier(toleranceTime, browseMin, browseMax, shopMin, shopMax);
        customer.setShopChance(shopChance);
        customer.setDriver("smallCar");
    }
    /** To String
     * @return SmallCar class as string form
     */
    public String toString(){
        String str = "";
        str = str + "SmallCar " + " |tankSize: " + tankSize + " || fuel: " + fuel + "|";
        return str;
    }
}