package Model;
import java.util.ArrayList;
public class PetrolStation {
    /** The pumps. */
    private ArrayList<Pump> pumps;
    /**
     * Instantiates a new petrol station.
     *
     * @param numPumps the number of pumps
     */
    public PetrolStation(int numPumps) {
        pumps = new ArrayList<Pump>();
        createPumps(numPumps);
    }
    /**
     * Adds the pumps.
     *
     * @param p the p
     */
    public void addPumps(Pump p){
        pumps.add(p);
    }
    /**
     * Allocate car.
     *
     * @param vehicle the vehicle
     * @return true, if successful
     */
    public boolean allocateCar(Vehicle vehicle){
        int index = getLeastOccupiedPump();
        double space = pumps.get(index).getSpace();
        double car = vehicle.getVehicleSize();
        if( (space - car) > 0){
            pumps.get(index).addVehicle(vehicle);
            pumps.get(index).getVehicle().getCustomer().setPumpNo(index);
            return true;
        }
        else{
            return false;
        }
//adds vehicle to least full pump if space is available,
//it will return true.
//else it will return false and do nothing else.
    }
    /**
     * Gets the least occupied pump.
     *
     * @return the least occupied pump
     */
    public int getLeastOccupiedPump(){
        double x = pumps.get(0).getSpace();
        int index = 0;
        double y = 0.0;
        for(int i = 1; i < pumps.size(); i++){
            y = pumps.get(i).getSpace();
            if(y > x){
                x = y;
                index = i;
            }
        }
        return index;
    }
    /**
     * Creates the pumps.
     *
     * @param numPumps the number of pumps
     */
    public void createPumps(int numPumps){
        for(int i = 0; i < numPumps; i++){
            Pump p = new Pump();
            pumps.add(p);
//create pump objects
//add pump object to array list
        }
    }
    /**
     * Gets the pump.
     *
     * @param index the index
     * @return the pump
     */
    public Pump getPump(int index){
        return pumps.get(index);
    }
    /**
     * Gets the pumps.
     *
     * @return the pumps
     */
    public ArrayList<Pump> getPumps() {
        return pumps;
    }
    /**
     * To String
     *
     * returns a string form of pumps and space at each pump.
     *
     * @return the string
     */
    public String toString(){
        String str = "";
        for(int i = 0; i < pumps.size(); i++){
            str = str + "pump " + i + " " + pumps.get(i).toString() + "\n" ;
        }
        return str;
    }
}
