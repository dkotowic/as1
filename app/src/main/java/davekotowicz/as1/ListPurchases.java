/*
This view displays in a listview on screen a list of all of the fuel purchase objects that are contained in the
fuel purchase log object's attribute arraylist. These are currently sorted by the order in which they were added to the log,
but future implementations may include a sort by date, a feature that is already built into this version but is not implemented.
Additionally, a clear button is included on screen even though it wasn't in the spec, which allows a user to clear out the log in the event
they wanted to start from new. It should be noted that the clear button does not update the page, so the log will appear populated while
user remains on screen; the intention of this button was for testing and the app would be closed and reopened each time it was utilized.
In future implementations, this feature would be more fleshed out to provide the user a satisfying and immediate experience.
I would also like to include a delete purchase functionality in a future version of the app, as I think it would serve the end user well.
This class does not have any outstanding issues.
*/

package davekotowicz.as1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class ListPurchases extends AppCompatActivity {

    private ArrayAdapter<FuelPurchase> adapter;
    private static final String FILENAME = "file.sav";
    private ListView FuelPurchaseLog;
    private ArrayList<FuelPurchase> FuelPurchases = new ArrayList<FuelPurchase>();
    private PurchaseLog PurchLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_purchases);

        loadFromFile();

        adapter = new ArrayAdapter<FuelPurchase>(this, R.layout.list_view, PurchLog.getFuelPurchases());
        adapter.notifyDataSetChanged();

        Button clearButton = (Button) findViewById(R.id.clear);

        clearButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                PurchLog.clear_log();
                saveInFile();
            }
        });

        Button newButton = (Button) findViewById(R.id.new_purch);

        newButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(ListPurchases.this, PurchaseEntry.class);
                Gson gson = new Gson();
                String fuel_purchase_list = gson.toJson(PurchLog.getFuelPurchases());
                intent.putExtra("list_as_string", fuel_purchase_list);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FuelPurchaseLog = (ListView) findViewById(R.id.fuel_log);
        FuelPurchaseLog.setAdapter(adapter);
        // Accessed http://stackoverflow.com/questions/8565999/how-to-highlight-selected-item-in-listview on 2016-01-28 for listview choice.
        FuelPurchaseLog.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // Accessed http://stackoverflow.com/questions/8615417/how-can-i-set-onclicklistener-on-arrayadapter on 2016-01-28
        // Accessed http://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener.html on 2016-01-28
        FuelPurchaseLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListPurchases.this, PurchaseEdit.class);
                Gson gson = new Gson();
                String fuel_purchase_list = gson.toJson(FuelPurchases);
                intent.putExtra("list_as_string", fuel_purchase_list);
                String purchase_pos = String.valueOf(position);
                intent.putExtra("position_as_string", purchase_pos);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(PurchLog.getFuelPurchases(), out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            //Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html on Jan 21 2016
            Type listType = new TypeToken<ArrayList<FuelPurchase>>() {
            }.getType();
            FuelPurchases = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            FuelPurchases = new ArrayList<FuelPurchase>();
        } catch (NumberFormatException e) {
            FuelPurchases = new ArrayList<FuelPurchase>();
        } finally {
            PurchLog = new PurchaseLog(FuelPurchases);
        }
    }
}
