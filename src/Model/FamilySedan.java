package Model;
import java.util.*;
public class FamilySedan extends Vehicle {
    private int toleranceTime = 60;
    private int browseMin = 12;
    private int browseMax = 30;
    private int shopMin = 8;
    private int shopMax = 16;
    private static double shopChance = 0.4;
    public FamilySedan(Random rng){
        super(rng);
        setTank(12, 18);
        setSize(1.5);
        setInitialFuel();
        customer.carIdentifier(toleranceTime, browseMin, browseMax, shopMin, shopMax);
        customer.setShopChance(shopChance);
        customer.setDriver("familySedan");
    }
    /** returns vehicle information as string
     * @return the string
     */
    public String toString(){
        String str = "";
        str = str + "FamilySedan " + " |tankSize: " + tankSize + "|| fuel: " + fuel + "|";
        return str;
    }
}