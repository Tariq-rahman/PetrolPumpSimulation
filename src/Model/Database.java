package Model;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Database {
    private ArrayList<Vehicle> lostCars = new ArrayList<>();
    private ArrayList <Vehicle> filledCars = new ArrayList<>();
    private ArrayList<MoneyRecord> earnedMoney = new ArrayList<>();
    private ArrayList <MoneyRecord> lostMoney = new ArrayList<>();
    private ArrayList<MoneyRecord> earnedShopMoney = new ArrayList<>();
    private ArrayList <MoneyRecord> lostShopMoney = new ArrayList<>();
    private double totalLost;
    private double totalEarned;
    private double shopLost;
    private double shopEarned;
    /**
     * Adds the lost car.
     *
     * @param vehicle the vehicle
     */
    public void addLostCar(Vehicle vehicle){
        lostCars.add(vehicle);
    }
    /**
     * Adds the filled car.
     *
     * @param vehicle the vehicle
     */
    public void logFilledCar(Vehicle vehicle){
        filledCars.add(vehicle);
    }
    /**
     * Adds the earned money.
     *
     * @param earned the earned
     */
    public void addEarnedMoney(MoneyRecord earned){
        earnedMoney.add(earned);
    }
    /**
     * Adds the lost money.
     *
     * @param lost the lost
     */
    public void addLostMoney(MoneyRecord lost){
        lostMoney.add(lost);
    }
    /**
     * Adds the earned shop money.
     *
     * @param shopEarned the shop earned
     */
    public void addEarnedShopMoney(MoneyRecord shopEarned){
        earnedShopMoney.add(shopEarned);
    }
    /**
     * Adds the lost shop money.
     *
     * @param shopLost the shop lost
     */
    public void addLostShopMoney(MoneyRecord shopLost){
        lostShopMoney.add(shopLost);
    }
    public String filledVehicleToString(){
        String str = "";
        if (filledCars.size() > 0){
            for(int i = 0; i < filledCars.size(); i++){
                if(filledCars.get(i).checkIsFull() == true){
                    str = str + "Filled " + filledCars.get(i).toString() + "\n";
                }
                else{
                    str = str + "Queued " + filledCars.get(i).toString() + "\n";
                }
            }
            return str;
        }
        else {
            return str = "FilledCars empty";
        }
    }
    public String lostVehicleToString(){
        String str = "";
        if(lostCars.size() == lostMoney.size()){
            if (lostCars.size() > 0){
                for(int i = 0; i < lostCars.size(); i++){
                    str = str + "Lost " + lostCars.get(i).toString() + lostMoney.get(i).toString() + " shop " + lostShopMoney.get(i).toString() + "\n";
                }
                return str;
            }
            else {
                return str = "LostCars empty" + "\n";
            }
        }
        else{
            return "error, vehicle and money mismatch";
        }
    }
    public void calculateShopTotal(ArrayList<MoneyRecord> shopList, boolean isEarned){
        double lost = 0;
        double earned = 0;
        if(isEarned){
            for(int i = 0; i < shopList.size(); i++){
                earned = earned + shopList.get(i).getMoney();
            }
            shopEarned = earned;
        }
        else{
            for(int i = 0; i < shopList.size(); i++){
                lost = lost + shopList.get(i).getMoney();
            }
            shopLost = lost;
        }
    }
    public void calculateTotal(ArrayList<MoneyRecord> list, boolean isEarned){
        double earned = 0;
        double lost = 0;
        if(isEarned == true){
            for(int i = 0; i < list.size(); i++){
                earned = earned + list.get(i).getMoney();
            }
            totalEarned = earned;
        }
        else{
            for(int i = 0; i < list.size(); i++){
                lost = lost + list.get(i).getMoney();
            }
            totalLost = lost;
        }
    }
    public String LostMoneyToString(){
        calculateTotal(lostMoney, false);
        calculateShopTotal(lostShopMoney, false);
        totalLost = totalLost + shopLost;
        String str = "Total Lost Money = ";
        return str + "£" + round(totalLost,2) + lostShopToString();
    }
    public String EarnedMoneyToString(boolean withShopMoney){
        calculateTotal(earnedMoney, true);
        calculateShopTotal(earnedShopMoney, true);
        totalEarned = totalEarned + shopEarned;
        String str = "";
        if(withShopMoney){
            return str +"Total Earned Money = £" + round(totalEarned,2) + earnedShopToString();
        }
        else{
            return str + totalEarned;
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
    /**
     * returns Earned petrol money as a string.
     *
     * @return the string
     */
    public String earnedPetrolMoneyToString(){
        String str = "";
        calculateTotal(earnedMoney, true);
        return str;
    }
    /**
     * returns a string format of the earned shop money
     *
     * @return the string
     */
    public String earnedShopToString(){
        String str = " | Total Shop money: " +"£"+round(shopEarned,2);
        return str;
    }
    /**
     * returns a string format of lost shop money
     *
     * @return the string
     */
    public String lostShopToString(){
        String str = " | Total Lost Shop Money: " + round(shopLost,2);
        return str;
    }
    /**
     * Gets the shop earned.
     *
     * @return the shop earned
     */
    public double getShopEarned() {
        return shopEarned;
    }
    /**
     * Gets the petrol earned money.
     *
     * @return the petrol earned money
     */
    public double getPetrolEarnedMoney(){
        return totalEarned;
    }
    /**
     * Gets the overall money.
     *
     * @return the overall money
     */
    public double getOverallMoney(){
        return totalEarned + shopEarned;
    }
    /**
     * Gets the lost petrol money.
     *
     * @return the lost petrol money
     */
    public double getLostPetrolMoney(){
        return totalLost;
    }
    /**
     * Gets the lost shop money.
     *
     * @return the lost shop money
     */
    public double getLostShopMoney(){
        return shopLost;
    }
    /**
     * Gets the overall lost.
     *
     * @return the overall lost
     */
    public double getOverallLost(){
        return shopLost + totalLost;
    }
    /**
     * Gets the filled vehicle count.
     *
     * @return the filled vehicle count
     */
    public int getFilledVehicleCount(){
        return filledCars.size();
    }
    /**
     * Gets the lost vehicle count.
     *
     * @return the lost vehicle count
     */
    public int getLostVehicleCount(){
        return lostCars.size();
    }
}