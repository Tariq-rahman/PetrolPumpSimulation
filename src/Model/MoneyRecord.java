package Model;
import java.math.BigDecimal;
import java.math.RoundingMode;
// TODO: Auto-generated Javadoc
/**
 * The Class MoneyRecord.
 */
public class MoneyRecord {
    /** The money. */
    private double money;
    /** The is earned. */
    private boolean isEarned;
    /**
     * Instantiates a new money record.
     *
     * @param money the money
     * @param isEarned the is earned
     */
    public MoneyRecord(double money, boolean isEarned){
        this.money = money;
        this.isEarned = isEarned;
    }
//-------------------------------------------------------------------//
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
     * Gets the money.
     *
     * @return the money
     */
    public double getMoney(){
        return money;
    }
    /**
     * Gets the checks if is earned.
     *
     * @return the checks if is earned
     */
    public boolean getIsEarned(){
        return isEarned;
    }
    /** returns a string of money record
     * @return the string
     */
    public String toString(){
        String str = "";
        if(isEarned){
            str = "Payed £ " + round(money,2);
            return str;
        }
        else{
            str = "lost £ " + round(money,2);
            return str;
        }
    }
}