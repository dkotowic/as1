package davekotowicz.as1;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dave on 2016-01-24.
 */
public class FuelPurchase implements Comparable{
    private Date date;
    private String station;
    private float odometer;
    private String grade;
    private float amount;
    private float unitcost;

    public FuelPurchase( Date date, String station, float odometer, String grade, float amount, float unitcost) {
            this.date = date;
            this.station = station;
            this.odometer = odometer;
            this.grade = grade;
            this.amount = amount;
            this.unitcost = unitcost;
    }

    public float getTotalCost(){
        float totalcost = this.amount*this.unitcost/100;
        return totalcost;
    }

    public Date getDate(){return this.date;}

    public String getStation(){
        return this.station;
    }

    public float getOdometer(){return this.odometer;}

    public String getGrade(){
        return this.grade;
    }

    public float getAmount(){return this.amount;}

    public float getUnitcost(){return this.unitcost;}

    public void setDate(Date d){
        this.date = d;
    }

    public void setStation(String s){
        this.station = s;
    }

    public void setOdometer(float o){
        this.odometer = o;
    }

    public void setGrade(String g){
        this.grade = g;
    }

    public void setAmount(float a){
        this.amount = a;
    }

    public void setUnitcost(float uc){
        this.unitcost = uc;
    }

    public String toString(){
        return new SimpleDateFormat("yyyy-MM-dd").format(this.date) + " - " + this.getStation()
                + " - " + String.format("%.1f",this.odometer) + " km - "
                + this.getGrade() + " - " + String.format("%.3f", this.amount)
                + " L - " + String.format("%.1f", this.unitcost) + " cents/L - $" + String.format("%.2f",this.getTotalCost());
    }

    @Override
    public int compareTo(Object fp) {
        // Accessed http://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/ on 2016/01/24 for syntax on comparable
        // Accessed http://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date on 2016-01-28 for further details on comparing dates
        return this.date.compareTo(((FuelPurchase) fp).date);
    }

}
