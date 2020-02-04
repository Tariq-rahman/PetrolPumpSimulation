package Model;
import java.util.LinkedList;
import java.util.Queue;
// TODO: Auto-generated Javadoc
/**
 * The Class Pump.
 */
public class Pump {
    /** The space. */
    private double space;
    /** The Constant GALLON_PER_TICK. */
    private static final int GALLON_PER_TICK = 1;
    /** The vehicles. */
    private Queue <Vehicle> vehicles;
    /**
     * Instantiates a new pump.
     */
    public Pump() {
        vehicles = new LinkedList<Vehicle>();
        this.space = 3.0;
    }
    /**
     * Adds the vehicle.
     *
     * @param v the vehicle
     */
    public void addVehicle(Vehicle v){
        space = space - v.getVehicleSize();
        vehicles.add(v);
    }
    /**
     * Removes the vehicle.
     */
    public void removeVehicle(){
        space = space + vehicles.peek().getVehicleSize();
        vehicles.remove();
    }
    /**
     * Check occupied.
     *
     * @return true, if successful
     */
    public boolean checkOccupied(){
        if(vehicles.size() == 0){
            return false;
        }
        else{
            return true;
        }
    }
    /**
     * Fill car.
     */
    public void fillCar(){
        Vehicle v = vehicles.peek();
        if(v.getTankSize() > v.getFuel()){
            v.fillTank(GALLON_PER_TICK);
        }
    }
    /**
     * Gets the space.
     *
     * @return the space
     */
    public double getSpace() {
        return space;
    }
    /**
     * Gets the vehicle.
     *
     * @return the vehicle
     */
    public Vehicle getVehicle(){
        return vehicles.peek();
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){
        String str = "Queue of ";
        Object[] arr = vehicles.toArray();
        for(int i = 0; i < arr.length; i++){
            str = str + arr[i].getClass().getSimpleName() + "| ";
        }
        return str = str + "remaining space: " + space;
    }
    /**
     * To string GUI.
     *
     * @return the string
     */
    public String toStringGUI(){
        String str = "";
        Object[] arr = vehicles.toArray();
        for(int i = 0; i < arr.length; i++){
            str = str + arr[i].getClass().getSimpleName() + "| ";
        }
        return str ;
    }
    /**
     * Show remaining space.
     *
     * @return the string
     */
    public String showRemainingSpace(){
        return "remaining space: " + space;
    }
    /**
     * Gets the queue size.
     *
     * @return the queue size
     */
    public int getQueueSize(){
        return vehicles.size();
    }
}