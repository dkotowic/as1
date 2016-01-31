package davekotowicz.as1;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dave on 2016-01-27.
 */
public class PurchaseLog extends AppCompatActivity {

    private ArrayList<FuelPurchase> FuelPurchases = new ArrayList<FuelPurchase>();

    public PurchaseLog(ArrayList<FuelPurchase> fuelPurchases) {
        FuelPurchases = fuelPurchases;
    }

    public void clear_log(){
        FuelPurchases = new ArrayList<FuelPurchase>();
    }

    public ArrayList<FuelPurchase> getFuelPurchases(){
        return this.FuelPurchases;
    }

    public void AddPurchase(FuelPurchase FP){
        this.FuelPurchases.add(FP);
        //Collections.sort(this.FuelPurchases);
    }

    public void EditPurchase(FuelPurchase FP, int position){
        this.FuelPurchases.set(position, FP);
        //Collections.sort(this.FuelPurchases);
    }

    public FuelPurchase get( int position ) {
        return this.FuelPurchases.get(position);
    }
}