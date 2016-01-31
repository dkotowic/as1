package davekotowicz.as1;

import android.test.ActivityInstrumentationTestCase2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dave on 2016-01-31.
 */
public class FuelTrackTest extends ActivityInstrumentationTestCase2 {

    public FuelTrackTest() {
        super(PurchaseEdit.class);
    }

    public void testFPSetDate() {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            format.setLenient(false);
            Date d = format.parse("2016-01-01");
            String s = "Esso";
            float o = Float.parseFloat("200123.5");
            String g = "regular";
            float a = Float.parseFloat("30.456");
            float uc = Float.parseFloat("79.9");
            FuelPurchase fp = new FuelPurchase(d, s, o, g, a, uc);

            Date newdate = format.parse("2016-02-02");
            assertNotSame(fp.getDate(), newdate);
            fp.setDate(newdate);
            assertEquals(fp.getDate(), newdate);
        } catch (ParseException e) {

        }
    }

    public void testFPSetStation(){
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            format.setLenient(false);
            Date d = format.parse("2016-01-01");
            String s = "Esso";
            float o = Float.parseFloat("200123.5");
            String g = "regular";
            float a = Float.parseFloat("30.456");
            float uc = Float.parseFloat("79.9");
            FuelPurchase fp = new FuelPurchase(d, s, o, g, a, uc);

            String newstation = "Costco";
            assertNotSame(fp.getStation(), newstation);
            fp.setStation(newstation);
            assertEquals(fp.getStation(), newstation);
        } catch (ParseException e) {

        }
    }

    public void testFPSetOdometer(){
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            format.setLenient(false);
            Date d = format.parse("2016-01-01");
            String s = "Esso";
            float o = Float.parseFloat("200123.5");
            String g = "regular";
            float a = Float.parseFloat("30.456");
            float uc = Float.parseFloat("79.9");
            FuelPurchase fp = new FuelPurchase(d, s, o, g, a, uc);

            float newodometer = Float.parseFloat("200125.6");
            assertNotSame(fp.getOdometer(), newodometer);
            fp.setOdometer(newodometer);
            assertEquals(fp.getOdometer(), newodometer);
        } catch (ParseException e) {

        }
    }

    public void testFPSetGrade(){
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            format.setLenient(false);
            Date d = format.parse("2016-01-01");
            String s = "Esso";
            float o = Float.parseFloat("200123.5");
            String g = "regular";
            float a = Float.parseFloat("30.456");
            float uc = Float.parseFloat("79.9");
            FuelPurchase fp = new FuelPurchase(d, s, o, g, a, uc);

            String newgrade = "extra";
            assertNotSame(fp.getGrade(),newgrade);
            fp.setGrade(newgrade);
            assertEquals(fp.getGrade(),newgrade);
        } catch (ParseException e) {

        }
    }

    public void testFPSetAmount(){
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            format.setLenient(false);
            Date d = format.parse("2016-01-01");
            String s = "Esso";
            float o = Float.parseFloat("200123.5");
            String g = "regular";
            float a = Float.parseFloat("30.456");
            float uc = Float.parseFloat("79.9");
            FuelPurchase fp = new FuelPurchase(d, s, o, g, a, uc);

            float newamount = Float.parseFloat("40.556");
            assertNotSame(fp.getAmount(),newamount);
            fp.setAmount(newamount);
            assertEquals(fp.getAmount(),newamount);
        } catch (ParseException e) {

        }
    }

    public void testFPSetUnitCost(){
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            format.setLenient(false);
            Date d = format.parse("2016-01-01");
            String s = "Esso";
            float o = Float.parseFloat("200123.5");
            String g = "regular";
            float a = Float.parseFloat("30.456");
            float uc = Float.parseFloat("79.9");
            FuelPurchase fp = new FuelPurchase(d, s, o, g, a, uc);

            float newunitcost = Float.parseFloat("100.9");
            assertNotSame(fp.getUnitcost(),newunitcost);
            fp.setUnitcost(newunitcost);
            assertEquals(fp.getUnitcost(),newunitcost);
        } catch (ParseException e) {

        }
    }

    public void testPLClear(){
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            format.setLenient(false);
            Date d = format.parse("2016-01-01");
            String s = "Esso";
            float o = Float.parseFloat("200123.5");
            String g = "regular";
            float a = Float.parseFloat("30.456");
            float uc = Float.parseFloat("79.9");
            FuelPurchase fp = new FuelPurchase(d, s, o, g, a, uc);
            ArrayList<FuelPurchase> fpa = new ArrayList<>();
            fpa.add(fp);
            PurchaseLog pl = new PurchaseLog(fpa);

            assertTrue(pl.getFuelPurchases().contains(fp));
            pl.clear_log();
            assertFalse(pl.getFuelPurchases().contains(fp));
            assertTrue(pl.getFuelPurchases().size() == 0);
        } catch (ParseException e) {

        }
    }

    public void testPLAdd(){
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            format.setLenient(false);
            Date d = format.parse("2016-01-01");
            String s = "Esso";
            float o = Float.parseFloat("200123.5");
            String g = "regular";
            float a = Float.parseFloat("30.456");
            float uc = Float.parseFloat("79.9");
            FuelPurchase fp = new FuelPurchase(d, s, o, g, a, uc);
            ArrayList<FuelPurchase> fpa = new ArrayList<>();
            PurchaseLog pl = new PurchaseLog(fpa);

            assertFalse(pl.getFuelPurchases().contains(fp));
            pl.AddPurchase(fp);
            assertTrue(pl.getFuelPurchases().contains(fp));
        } catch (ParseException e) {

        }
    }

    public void testPLEdit(){
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            format.setLenient(false);
            Date d = format.parse("2016-01-01");
            String s = "Esso";
            float o = Float.parseFloat("200123.5");
            String g = "regular";
            float a = Float.parseFloat("30.456");
            float uc = Float.parseFloat("79.9");
            FuelPurchase fp = new FuelPurchase(d, s, o, g, a, uc);
            ArrayList<FuelPurchase> fpa = new ArrayList<>();
            fpa.add(fp);
            PurchaseLog pl = new PurchaseLog(fpa);

            FuelPurchase newfp = new FuelPurchase(d,"Costco",o,"extra",a,uc);
            assertFalse(pl.getFuelPurchases().contains(newfp));
            assertTrue(pl.getFuelPurchases().contains(fp));
            pl.EditPurchase(newfp, 0);
            assertTrue(pl.getFuelPurchases().contains(newfp));
            assertFalse(pl.getFuelPurchases().contains(fp));
        } catch (ParseException e) {

        }
    }

}
