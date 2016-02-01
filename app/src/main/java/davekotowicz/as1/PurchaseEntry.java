/*
This view allows the user to enter data which will eventually serve as the attributes for a new fuel purchase object,
which will be created on the submit button and added to the purchase log object to be displayed in the log.
An additional function is included for when exceptions occur where data entered is invalid, to allow a clear message to the user
about the nature of their error (checks for numeric value of entered string).
The purchase entry and purchase edit classes appear to be relatively similar but I have opted to differentiate them entirely;
this decision allows for subtle difference in operation (e.g., new entry resets the fields and stays on page for additional entries,
while an edit results in the immediate return to the log) and also allows for future implementations of the app to have flexibility in
specializing the look of the views -- they're not intended to be considered similar despite their current appearance similarities.
This class does not have any outstanding issues.
*/

package davekotowicz.as1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PurchaseEntry extends AppCompatActivity {


    private static final String FILENAME = "file.sav";
    private ArrayList<FuelPurchase> FuelPurchases = new ArrayList<FuelPurchase>();
    private PurchaseLog PurchLog;

    private EditText Station;
    private EditText Date_Fuelled;
    private EditText Odometer;
    private EditText Grade;
    private EditText Amount;
    private EditText UnitCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_entry);

        String fuel_purchase_list = getIntent().getStringExtra("list_as_string");
        Gson gson = new Gson();
        //Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html on Jan 21 2016
        Type listType = new TypeToken<ArrayList<FuelPurchase>>() {}.getType();

        FuelPurchases = gson.fromJson(fuel_purchase_list, listType);
        PurchLog = new PurchaseLog(FuelPurchases);

        Station = (EditText) findViewById(R.id.station);
        Date_Fuelled = (EditText) findViewById(R.id.date_fuelled);
        Odometer = (EditText) findViewById(R.id.odometer);
        Grade = (EditText) findViewById(R.id.fuel_grade);
        Amount = (EditText) findViewById(R.id.fuel_amt);
        UnitCost = (EditText) findViewById(R.id.fuel_unit_cost);

        Button submitButton = (Button) findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                try {
                    // Used http://stackoverflow.com/questions/7552660/java-convert-float-to-string-and-string-to-float on 2016/01/24 to identify how to convert string to float and back
                    // Accessed http://stackoverflow.com/questions/4216745/java-string-to-date-conversion on 2016/01/24 for date format
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    // Accessed http://stackoverflow.com/questions/4528047/checking-the-validity-of-a-date to check for valid dates, as no exception occurred
                    // when the date was, for example, 2016-01-50 because it was correct format.
                    format.setLenient(false);
                    String station = Station.getText().toString();
                    Date date_fuelled = format.parse(Date_Fuelled.getText().toString());
                    float odometer = Float.parseFloat(Odometer.getText().toString());
                    String grade = Grade.getText().toString();
                    float amount = Float.parseFloat(Amount.getText().toString());
                    float unitcost = Float.parseFloat(UnitCost.getText().toString());
                    FuelPurchase fuelpurchase = new FuelPurchase(date_fuelled, station, odometer, grade, amount, unitcost);
                    PurchLog.AddPurchase(fuelpurchase);
                    saveInFile();
                    // Accessed http://developer.android.com/guide/topics/ui/notifiers/toasts.html on 2016-01-30 for help with pop up messages
                    Toast toast = Toast.makeText(getApplicationContext(), "Entry Successful, please add another or Cancel to return to the Log", Toast.LENGTH_LONG );
                    toast.show();
                    Station.setText("");
                    Date_Fuelled.setText("");
                    Odometer.setText("");
                    Grade.setText("");
                    Amount.setText("");
                    UnitCost.setText("");
                } catch (ParseException e) {
                    // Accessed http://developer.android.com/guide/topics/ui/notifiers/toasts.html on 2016-01-30 for help with pop up messages
                    Toast toast = Toast.makeText(getApplicationContext(), "Must Enter A Valid Date", Toast.LENGTH_LONG );
                    toast.show();
                } catch (NumberFormatException e){
                    // Accessed http://developer.android.com/guide/topics/ui/notifiers/toasts.html on 2016-01-30 for help with pop up messages
                    String text = "Must Enter a Valid Number in ";
                    if(IsNotNumeric(Odometer.getText().toString())){text = text + "Odometer / ";}
                    if(IsNotNumeric(Amount.getText().toString())){text = text + "Amount / ";}
                    if(IsNotNumeric(UnitCost.getText().toString())){text = text + "Unit Cost / ";}
                    text = text.substring(0,text.length()-3);
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG );
                    toast.show();
                }
            }
        });


        Button cancelButton = (Button) findViewById(R.id.cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(PurchaseEntry.this, ListPurchases.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,0);

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


    private Boolean IsNotNumeric(String s){
        try{
            Float.parseFloat(s);
            return false;
        } catch (NumberFormatException e){
            return true;
        }
    }
}
