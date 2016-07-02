package com.example.souvik.prescriptionformnew.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by intel on 03-01-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "medicineList";

    // Contacts table name
    public static final String TABLE_MEDICINE = "medicineReminders";

    // Contacts Table Columns names
    public static final String MEDICINE_NAME = "medicineName";

    public static final String MORNING = "isMorning";
    public static final String MORNING_AFTER = "isMorningAfter";
    public static final String MORNING_BEFORE = "isMorningBefore";

    public static final String AFTERNOON = "isAfternoon";
    public static final String AFTERNOON_AFTER = "isAfternoonAfter";
    public static final String AFTERNOON_BEFORE = "isAfternoonBefore";

    public static final String EVENING = "isEvening";
    public static final String EVENING_AFTER = "isEveningAfter";
    public static final String EVENING_BEFORE = "isEveningBefore";

    public static final String NIGHT = "isNight";
    public static final String NIGHT_AFTER = "isNightAfter";
    public static final String NIGHT_BEFORE = "isNightBefore";

    public static final String MORNING_QUANTITY = "mQuantity";
    public static final String AFTERNOON_QUANTITY = "aQuantity";
    public static final String EVENING_QUANTITY = "eQuantity";
    public static final String NIGHT_QUANTITY = "nQuantity";


    public static final String NUM_OF_DAYS = "numOfDays";

    public static final String KEY_ID = "_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEDICINE_TABLE = "CREATE TABLE " + TABLE_MEDICINE + "("
        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + MEDICINE_NAME + " TEXT,"
        + MORNING + " INTEGER,"         + MORNING_AFTER + " INTEGER,"          + MORNING_BEFORE + " INTEGER,"   + MORNING_QUANTITY + " INTEGER,"
        + AFTERNOON + " INTEGER,"       + AFTERNOON_AFTER + " INTEGER,"        + AFTERNOON_BEFORE + " INTEGER," + AFTERNOON_QUANTITY + " INTEGER,"
        + EVENING + " INTEGER,"         + EVENING_AFTER + " INTEGER,"          + EVENING_BEFORE + " INTEGER,"   + EVENING_QUANTITY + " INTEGER,"
        + NIGHT + " INTEGER,"           + NIGHT_AFTER + " INTEGER,"            + NIGHT_BEFORE + " INTEGER,"      + NIGHT_QUANTITY + " INTEGER,"
        + NUM_OF_DAYS + " INTEGER"+")";


        db.execSQL(CREATE_MEDICINE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINE);

        // Create tables again
        onCreate(db);
    }


    public void add(Prescription prescription) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEDICINE_NAME, prescription.getMedicineName());

        values.put(MORNING, prescription.getIsMorning());
        values.put(MORNING_AFTER, prescription.getIsMorningAfter());
        values.put(MORNING_BEFORE, prescription.getIsMorningBefore());

        values.put(AFTERNOON, prescription.getIsAfternoon());
        values.put(AFTERNOON_AFTER, prescription.getIsAfternoonAfter());
        values.put(AFTERNOON_BEFORE, prescription.getIsAfternoonBefore());

        values.put(EVENING, prescription.getIsEvening());
        values.put(EVENING_AFTER, prescription.getIsEveningAfter());
        values.put(EVENING_BEFORE, prescription.getIsEveningBefore());

        values.put(NIGHT, prescription.getIsNight());
        values.put(NIGHT_AFTER, prescription.getIsNightAfter());
        values.put(NIGHT_BEFORE, prescription.getIsNightBefore());

        values.put(MORNING_QUANTITY,prescription.getQuantity(MORNING_QUANTITY));
        values.put(AFTERNOON_QUANTITY,prescription.getQuantity(AFTERNOON_QUANTITY));
        values.put(EVENING_QUANTITY,prescription.getQuantity(EVENING_QUANTITY));
        values.put(NIGHT_QUANTITY,prescription.getQuantity(NIGHT_QUANTITY));

        values.put(NUM_OF_DAYS,prescription.getNumOfDays());

        // Inserting Row
        db.insert(TABLE_MEDICINE, null, values);
        db.close(); // Closing database connection
    }


    public List<Prescription> get() {
        List<Prescription> medicineList = new ArrayList<Prescription>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEDICINE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);



        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Prescription prescription = new Prescription();

                prescription.set_id(cursor.getString(0));
                prescription.setMedicineName(cursor.getString(1));

                prescription.setIsMorning(Integer.parseInt(cursor.getString(2)));
                prescription.setIsMorningAfter(Integer.parseInt(cursor.getString(3)));
                prescription.setIsMorningBefore(Integer.parseInt(cursor.getString(4)));
                prescription.setQuantity(Integer.parseInt(cursor.getString(5)), MORNING_QUANTITY);

                prescription.setIsAfternoon(Integer.parseInt(cursor.getString(6)));
                prescription.setIsAfternoonAfter(Integer.parseInt(cursor.getString(7)));
                prescription.setIsAfternoonBefore(Integer.parseInt(cursor.getString(8)));
                prescription.setQuantity(Integer.parseInt(cursor.getString(9)), AFTERNOON_QUANTITY);

                prescription.setIsEvening(Integer.parseInt(cursor.getString(10)));
                prescription.setIsEveningAfter(Integer.parseInt(cursor.getString(11)));
                prescription.setIsEveningBefore(Integer.parseInt(cursor.getString(12)));
                prescription.setQuantity(Integer.parseInt(cursor.getString(13)), EVENING_QUANTITY);

                prescription.setIsNight(Integer.parseInt(cursor.getString(14)));
                prescription.setIsNightAfter(Integer.parseInt(cursor.getString(15)));
                prescription.setIsNightBefore(Integer.parseInt(cursor.getString(16)));
                prescription.setQuantity(Integer.parseInt(cursor.getString(17)), NIGHT_QUANTITY);

                prescription.setNumOfDays(Integer.parseInt(cursor.getString(18)));



                medicineList.add(prescription);
            } while (cursor.moveToNext());
        }

        // return contact list
        return medicineList;
    }


    public Prescription get(String ID) {

        String selectQuery = "SELECT  * FROM " + TABLE_MEDICINE + " WHERE " + KEY_ID + " = " + ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        Prescription prescription = new Prescription();

        prescription.set_id(cursor.getString(0));
        prescription.setMedicineName(cursor.getString(1));

        prescription.setIsMorning(Integer.parseInt(cursor.getString(2)));
        prescription.setIsMorningAfter(Integer.parseInt(cursor.getString(3)));
        prescription.setIsMorningBefore(Integer.parseInt(cursor.getString(4)));
        prescription.setQuantity(Integer.parseInt(cursor.getString(5)), MORNING_QUANTITY);

        prescription.setIsAfternoon(Integer.parseInt(cursor.getString(6)));
        prescription.setIsAfternoonAfter(Integer.parseInt(cursor.getString(7)));
        prescription.setIsAfternoonBefore(Integer.parseInt(cursor.getString(8)));
        prescription.setQuantity(Integer.parseInt(cursor.getString(9)), AFTERNOON_QUANTITY);

        prescription.setIsEvening(Integer.parseInt(cursor.getString(10)));
        prescription.setIsEveningAfter(Integer.parseInt(cursor.getString(11)));
        prescription.setIsEveningBefore(Integer.parseInt(cursor.getString(12)));
        prescription.setQuantity(Integer.parseInt(cursor.getString(13)), EVENING_QUANTITY);

        prescription.setIsNight(Integer.parseInt(cursor.getString(14)));
        prescription.setIsNightAfter(Integer.parseInt(cursor.getString(15)));
        prescription.setIsNightBefore(Integer.parseInt(cursor.getString(16)));
        prescription.setQuantity(Integer.parseInt(cursor.getString(17)), NIGHT_QUANTITY);

        prescription.setNumOfDays(Integer.parseInt(cursor.getString(18)));

        return prescription;
    }

    public Cursor getAllRows(){

        String selectQuery = "SELECT  * FROM " + TABLE_MEDICINE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
       return  cursor;
    }


    public void delete(String ID){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MEDICINE +" WHERE " + KEY_ID +" = " +ID);

    }


    public void delete(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MEDICINE);

    }

}
