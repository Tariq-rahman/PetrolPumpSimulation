package Controller;
import Model.*;
import java.util.*;
import View.*;
public class Simulation {
    private Shop shop;
    private PetrolStation station;
    private Database data;
    private int ticks;
    private double pChance;
    private double qChance;
    private int seed = 42;
    private double gallonPrice;
    private int noOfPumps;
    private int noOfTills;
    private double truckProb = 0.02;
    private boolean withTrucks;
    private Random rng = new Random(seed);
    private TextView view;
/**
 * The main method.
 *
 * @param args the arguments
 */
    /*
     * Args passed in(IN ORDER):
     * GallonPrice, pchance, qchance, no.ofPumps, no.ofTills,
     * no.OfTicks, seed.
     */
    /**
     * Instantiates a new simulation.
     *
     * @param seed the seed
     */
    public Simulation(int seed, double gallonPrice, double pChance, double qChance, int noOfPumps, int noOfTills, int ticks, boolean withTrucks){
        this.seed = seed;
        this.gallonPrice = gallonPrice;
        this.pChance = pChance;
        this.qChance = qChance;
        this.noOfPumps = noOfPumps;
        this.noOfTills = noOfTills;
        this.ticks = ticks;
        this.withTrucks = withTrucks;
        shop = new Shop(noOfTills, gallonPrice, rng);
        station = new PetrolStation(noOfPumps);
        data = new Database();
        view = new TextView();
    }
    /**
     * Sets the ticks.
     *
     * @param t the new ticks
     */
    public void setTicks(int t){
        ticks = t;
    }
    public void setTrucks(boolean isTruck) {
        if (isTruck == true) {
            withTrucks = true;
        }
        else {
            withTrucks = false;
        }
    }
    public int getTick() {
        return ticks;
    }
    public boolean getTruck() {
        return withTrucks;
    }
    /**
     * Simulate.
     */
    public void simulate(){
        for(int i = 0; i<ticks; i++){
            simulateOneTick();
            view.view(station, shop, data);
/* try {
Thread.sleep(50);
} catch (InterruptedException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} */
        }
        view.financialTotal(data);
    }
    /**
     * Simulate one tick.
     */
    public void simulateOneTick(){
        pumpFill();
        spawnCar();
        sendCustomer();
        customersWaiting();
    }
    /**
     * Spawn a car.
     * adds the car to filledCars if successful.
     * adds the car to lostcars if unsuccessful.
     */
    public void spawnCar(){
        double randomNumber = rng.nextDouble();
        double twoP = pChance*2;
        double twoQ = qChance + twoP;
        if(randomNumber <= pChance){
            spawnSmallCar();
        }
        else if(pChance < randomNumber && randomNumber < twoP){
            spawnMotorcycle();
        }
        else if( twoP < randomNumber && randomNumber < (twoQ)){
            spawnFamilySedan();
        }
        if(withTrucks && (twoQ) < randomNumber && randomNumber < (twoQ + truckProb)){
            spawnTruck();
        }
    }
    /**
     * Spawn small car.
     */
    public void spawnSmallCar(){
        SmallCar smallCar = new SmallCar(rng);
        boolean enter = station.allocateCar(smallCar);
        if(enter){
            data.logFilledCar(smallCar);
        }
        else{
            data.addLostCar(smallCar);
            logLostMoney(smallCar);
            logLostShopMoney(smallCar);
        }
    }
    /**
     * Spawn motorcycle.
     * adds the motorcycle to filledCars if successful.
     * adds the motorcycle to lostCars if unsuccessful.
     */
    public void spawnMotorcycle(){
        Motorcycle motorcycle = new Motorcycle(rng);
        boolean enter = station.allocateCar(motorcycle);
        if(enter){
            data.logFilledCar(motorcycle);
        }
        else{
            data.addLostCar(motorcycle);
            logLostMoney(motorcycle);
            logLostShopMoney(motorcycle);//redundant but placed anyway. May remove later
        }
    }
    /**
     * Spawn family Sedan.
     * adds the family sedan to filledCars if successful.
     * adds the family sedan to lostCars if unsuccessful.
     */
    public void spawnFamilySedan(){
        FamilySedan familySedan = new FamilySedan(rng);
        boolean enter = station.allocateCar(familySedan);
        if(enter){
            data.logFilledCar(familySedan);
        }
        else{
            data.addLostCar(familySedan);
            logLostMoney(familySedan);
            logLostShopMoney(familySedan);
        }
    }
    /**
     * Spawn Truck.
     * adds the Truck to filledCars if successful.
     * adds the Truck to lostCars if unsuccessful.
     */
    public void spawnTruck(){
        if(withTrucks == true){
            Truck truck = new Truck(rng);
            boolean enter = station.allocateCar(truck);
            if(enter){
                data.logFilledCar(truck);
            }
            else{
                data.addLostCar(truck);
                logLostMoney(truck);
                logLostShopMoney(truck);
            }
        }
    }
    /**
     * Pump fill.
     * checks if pump has a car.
     * if successful, fills car.
     */
    public void pumpFill(){
        for(int i = 0; i < noOfPumps; i++){
            if(station.getPump(i).checkOccupied()){
                station.getPump(i).fillCar();
                station.getPump(i).getVehicle().getCustomer().incrementFillTime();
            }
        }
    }
    /**
     * Send customer.
     */
    public void sendCustomer(){
        for(int i = 0; i < noOfPumps; i++){
            if(station.getPump(i).checkOccupied()){
                if(station.getPump(i).getVehicle().getCustomer().inVehicle()){
                    if(station.getPump(i).getVehicle().checkIsFull()){
                        Customer c = station.getPump(i).getVehicle().getCustomer();
                        shop.allocateCustomer(c);
                        station.getPump(i).getVehicle().getCustomer().setInVehicle();
                    }
                }
            }
        }
    }
    /**
     * Customers waiting.
     */
    public void customersWaiting(){
        for(int i = 0; i < noOfTills; i++){
            if(shop.getTill(i).getSize() != 0){
                if(shop.getTill(i).getCustomer().getWaitPeriod() != 0){
                    shop.getTill(i).getCustomer().decrementWaitPeriod();
                }
                else if(station.getPump(i).checkOccupied() == true){
                    payAndRemove(i);
                }
            }
        }
    }
    /**
     * Log lost money.
     *
     * @param vehicle the vehicle
     */
    public void logLostMoney(Vehicle vehicle){
        double gallons = vehicle.getTankSize() - vehicle.getFuel();
        gallons = gallons*gallonPrice;
        MoneyRecord record = new MoneyRecord(gallons, false);
        data.addLostMoney(record);
    }
    /**
     * Log payed money.
     *
     * @param money the money
     */
    public void logPayedMoney(double money){
        MoneyRecord record = new MoneyRecord(money, true);
        data.addEarnedMoney(record);
    }
    /**
     * Sets the truck probability.
     *
     * @param customer the new truck probability
     */
    public void setTruckProbability(Customer customer){
        if(customer.getIsHappy()){
            truckProb = truckProb * 1.05;
        }
        else{
            truckProb = truckProb * 0.8;
        }
    }
    /**
     * Pay and remove.
     *
     * @param i the i
     */
    public void payAndRemove(int i){
        Customer customer = shop.getTill(i).getCustomer();
        double shopReceipt = shop.getTill(i).browseShop(customer);
        double receipt = shop.getTill(i).pay(shopReceipt);
        logEarnedShopMoney(shopReceipt);
        logPayedMoney(receipt);
        if(shop.getTill(i).getCustomer().getDriverType().equals("truck")){
            setTruckProbability(shop.getTill(i).getCustomer());
        }
        shop.getTill(i).removeCustomer();
        station.getPump(i).removeVehicle();
    }
    /**
     * Log lost shop money.
     *
     * @param vehicle the vehicle
     */
    public void logLostShopMoney(Vehicle vehicle){
        double money = vehicle.getCustomer().getSpendingMoney();
        MoneyRecord lostShopRecord = new MoneyRecord(money, false);
        data.addLostShopMoney(lostShopRecord);
    }
    /**
     * Log earned shop money.
     *
     * @param money the money
     */
    public void logEarnedShopMoney(double money){
        MoneyRecord earnedShopRecord = new MoneyRecord(money, true);
        data.addEarnedShopMoney(earnedShopRecord);
    }
    /**
     * Gets the petrol station.
     *
     * @return the petrol station
     */
    public PetrolStation getPetrolStation(){
        return station;
    }
    /**
     * Gets the shop.
     *
     * @return the shop
     */
    public Shop getShop(){
        return shop;
    }
    /**
     * Gets the database.
     *
     * @return the database
     */
    public Database getDatabase(){
        return data;
    }
}