package Model;
import java.util.*;

public class Customer {
    private String driver;
    private int gallonsTaken;
    private boolean inVehicle;
    private int payPeriod;
    private Random rng;
    private int fillTime;
    private boolean isHappy;
    private int toleranceTime;
    private int browseTime;
    private double shopChance;
    private double spendingMoney;
    private int pumpNo;
    /**
     * Instantiates a new customer.
     */
    public Customer(Random rng){
        this.rng = rng;
        gallonsTaken = 0;
        inVehicle = true;
        generateTillTime(12, 18);
        fillTime = 0;
        isHappy = true;
        shopChance = 0;
    }
    /**
     * Car identifier. sets tolerance time, browse time and shop spend.
     *
     * @param time the time
     * @param min the min
     * @param max the max
     * @param shopMin the shop min
     * @param shopMax the shop max
     */
    public void carIdentifier(int time, int min, int max, int shopMin, int shopMax){
        setToleranceTime(time);
        generateBrowseTime(min, max);
        updatePayPeriod(getBrowseTime());
        spenShopMoney(shopMin, shopMax);
    }
    /**
     * Spend shop money.
     *
     * @param min the min
     * @param max the max
     */
    public void spenShopMoney(int min, int max){
        int diff = (max - min) +1 ;
        spendingMoney = min + rng.nextInt(diff);
    }
    /**
     * Sets the pump no.
     *
     * @param number the new pump no
     */
    public void setPumpNo(int number){
        pumpNo = number;
    }
    /**
     * Buy gallons.
     *
     * @param gallonNum the number of gallons
     */
    public void buyGallons(int gallonNum){
        gallonsTaken = gallonsTaken + gallonNum;
    }
    /**
     * Decrement wait period.
     */
    public void decrementWaitPeriod(){
        if(payPeriod != 0){
            payPeriod--;
        }
    }
    /**
     * Sets the in vehicle to false. so customer is no longer in the vehicle
     */
    public void setInVehicle(){
        inVehicle = false;
    }
    /**
     * In vehicle.
     *
     * @return true, if successful
     */
    public boolean inVehicle(){
        if(inVehicle == true){
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * Generate browse time.
     */
    public void generateBrowseTime(int min, int max){
        int diff = (max - min) + 1;
        browseTime = min + rng.nextInt(diff);
    }
    public void generateTillTime(int min, int max){//probably unused
        int diff = (max - min) + 1;
        payPeriod = min + rng.nextInt(diff);
    }
    public void setShopChance( double var){
        shopChance = var;
    }
    /**
     * Increment fill time.
     */
    public void incrementFillTime(){
        fillTime = fillTime + 1;
    }
    /**
     * Sets the isHappy boolean.
     *
     * @param a is a boolean to set isHappy
     */
    public void setHappy(boolean a){
        isHappy = a;
    }
    /**
     * Sets the tolerance time.
     *
     * @param time the new tolerance
     */
    public void setToleranceTime(int time){
        toleranceTime = time;
    }
    /**
     * Update pay period.
     *
     * @param var the var
     */
    public void updatePayPeriod(int var){
        payPeriod = payPeriod + var;
    }
    /**
     * Sets the driver.
     *
     * @param name the new driver
     */
    public void setDriver( String name){
        driver = name;
    }
    //--------------------------------------------------------------------//
    public String getDriverType(){
        return driver;
    }
    /**
     * Gets the gallons taken.
     *
     * @return the gallons taken
     */
    public int getGallonsTaken(){
        return gallonsTaken;
    }
    /**
     * Gets the wait period.
     *
     * @return the wait period as int.
     */
    public int getWaitPeriod(){
        return payPeriod;
    }
    /**
     * Gets the fill time.
     *
     * @return the fill time
     */
    public int getFillTime(){
        return fillTime;
    }
    /**
     * Gets isHappy boolean.
     *
     * @return isHappy boolean
     */
    public boolean getIsHappy(){
        return isHappy;
    }
    /**
     * Gets the tolerance time.
     *
     * @return the tolerance time
     */
    public int getToleranceTime(){
        return toleranceTime;
    }
    /**
     * Gets the browse time.
     *
     * @return the browse time
     */
    public int getBrowseTime(){
        return browseTime;
    }
    /**
     * Gets the pump no.
     *
     * @return the pump no
     */
    public int getPumpNo(){
        return pumpNo;
    }
    /**
     * Gets the shop chance.
     *
     * @return the shop chance
     */
    public double getShopChance(){
        return shopChance;
    }
    /**
     * Gets the spending money.
     *
     * @return the spending money
     */
    public double getSpendingMoney(){
        return spendingMoney;
    }
    /**
     * To String .
     *
     * @return customer plus gallons filled
     */
    public String toString(){
        String str = "";
        str = "customer filled " + gallonsTaken + " gallons";
        return str;
    }
}