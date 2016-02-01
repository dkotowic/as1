/*
This object facilitates the management of fuel purchases in a list. While it doesn't differ much from a pure arraylist,
the maintenance of the purchases inside a container object allows for methods that manipulate the data according to the app's needs.
I commented out the portions that would have sorted the fuel purchase objects by date, based on my realization that the sorting did not need
to occur, but felt that removing the sort entirely was unnecessary since the implementation was complete and worked effectively.
No issues are outstanding with this class.
*/

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