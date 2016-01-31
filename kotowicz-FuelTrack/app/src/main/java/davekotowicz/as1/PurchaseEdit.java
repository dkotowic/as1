package davekotowicz.as1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PurchaseEdit extends AppCompatActivity {


    private static final String FILENAME = "file.sav";
    private ArrayList<FuelPurchase> FuelPurchases = new ArrayList<FuelPurchase>();
    private PurchaseLog PurchLog;
    private FuelPurchase fp;

    private EditText Station;
    private EditText Date_Fuelled;
    private EditText Odometer;
    private EditText Grade;
    private EditText Amount;
    private EditText UnitCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_edit);

        String fuel_purchase_list = getIntent().getStringExtra("list_as_string");
        Gson gson = new Gson();
        //Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html on Jan 21 2016
        Type listType = new TypeToken<ArrayList<FuelPurchase>>() {}.getType();

        FuelPurchases = gson.fromJson(fuel_purchase_list, listType);
        PurchLog = new PurchaseLog(FuelPurchases);

        String purchase_pos = getIntent().getStringExtra("position_as_string");
        final int pos = Integer.parseInt(purchase_pos);

        fp = PurchLog.get(pos);

        Station = (EditText) findViewById(R.id.station);
        Date_Fuelled = (EditText) findViewById(R.id.date_fuelled);
        Odometer = (EditText) findViewById(R.id.odometer);
        Grade = (EditText) findViewById(R.id.fuel_grade);
        Amount = (EditText) findViewById(R.id.fuel_amt);
        UnitCost = (EditText) findViewById(R.id.fuel_unit_cost);

        Station.setText(fp.getStation());
        String date = new SimpleDateFormat("yyyy-MM-dd").format(fp.getDate());
        Date_Fuelled.setText(date);
        Odometer.setText(String.valueOf(fp.getOdometer()));
        Grade.setText(fp.getGrade());
        Amount.setText(String.valueOf(fp.getAmount()));
        UnitCost.setText(String.valueOf(fp.getUnitcost()));

        Button submiteditButton = (Button) findViewById(R.id.submit_edit);

        submiteditButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                try {
                    // Used http://stackoverflow.com/questions/7552660/java-convert-float-to-string-and-string-to-float on 2016/01/24 to identify how to convert string to float and back
                    // Accessed http://stackoverflow.com/questions/4216745/java-string-to-date-conversion on 2016/01/24 for date format
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    // Accessed http://stackoverflow.com/questions/4528047/checking-the-validity-of-a-date to check for valid dates, as no exception occurred
                    // when the date was, for example, 2016-01-50 because it was correct format.
                    format.setLenient(false);
                    Date date_fuelled = format.parse(Date_Fuelled.getText().toString());
                    String station = Station.getText().toString();
                    float odometer = Float.parseFloat(Odometer.getText().toString());
                    String grade = Grade.getText().toString();
                    float amount = Float.parseFloat(Amount.getText().toString());
                    float unitcost = Float.parseFloat(UnitCost.getText().toString());
                    fp.setDate(date_fuelled);
                    fp.setStation(station);
                    fp.setOdometer(odometer);
                    fp.setGrade(grade);
                    fp.setAmount(amount);
                    fp.setUnitcost(unitcost);
                    saveInFile();
                    Intent intent = new Intent(PurchaseEdit.this, ListPurchases.class);
                    startActivity(intent);
                    // Accessed http://developer.android.com/guide/topics/ui/notifiers/toasts.html on 2016-01-30 for help with pop up messages
                    Toast toast = Toast.makeText(getApplicationContext(), "Edit Successful", Toast.LENGTH_LONG );
                    toast.show();
                    finish();
                } catch (ParseException e) {
                    // Accessed http://developer.android.com/guide/topics/ui/notifiers/toasts.html on 2016-01-30 for help with pop up messages
                    Toast toast = Toast.makeText(getApplicationContext(), "Must Enter A Valid Date", Toast.LENGTH_LONG );
                    toast.show();
                } catch (NumberFormatException e) {
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
                Intent intent = new Intent(PurchaseEdit.this, ListPurchases.class);
                Gson gson = new Gson();
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

    public Boolean IsNotNumeric(String s){
        try{
            Float.parseFloat(s);
            return false;
        } catch (NumberFormatException e){
            return true;
        }
    }
}
