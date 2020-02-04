package View;
import Model.*;
import java.io.*;

public class TextView {

    private int tickNum = 1;

    public TextView(){

    }

    /**
     * View.
     *
     * @param p the petrolStation
     * @param s the shop
     * @param d the vehicleDatabase
     */
    public void view(PetrolStation p, Shop s, Database d){
        System.out.println(p.toString());
        System.out.println(s.toString());
        System.out.println(d.filledVehicleToString());
        System.out.println(d.lostVehicleToString());
        System.out.println("||------------------------------- tick " + tickNum + "-------------------------------||");
        tickNum++;
    }

    public void financialTotal(Database d){
        System.out.println(d.EarnedMoneyToString(true));
        System.out.println(d.LostMoneyToString());
    }
}