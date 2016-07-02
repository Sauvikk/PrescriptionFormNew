package com.example.souvik.prescriptionformnew.DB;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by intel on 04-01-2016.
 */
public class Prescription  {



    String medicineName;

    int isMorning ;
    int isMorningAfter;
    int isMorningBefore;

    int isAfternoon;
    int isAfternoonAfter;
    int isAfternoonBefore;

    int isEvening;
    int isEveningAfter;
    int isEveningBefore;

    int isNight;
    int isNightAfter;
    int isNightBefore;



    int mQuantity;
    int aQuantity;
    int eQuantity;
    int nQuantity;



    int numOfDays;

    String _id;

    public static final String MORNING_QUANTITY ="mQuantity";
    public static final String AFTERNOON_QUANTITY ="aQuantity";
    public static final String EVENING_QUANTITY ="eQuantity";
    public static final String NIGHT_QUANTITY ="nQuantity";





    public Prescription() {

        isMorning = 0;
        isMorningAfter = 0;
        isMorningBefore = 0;

        isAfternoon = 0;
        isAfternoonAfter = 0;
        isAfternoonBefore = 0;

        isEvening = 0;
        isEveningAfter = 0;
        isEveningBefore = 0;

        isNight = 0;
        isNightAfter = 0;
        isNightBefore = 0;

        mQuantity=0;
        aQuantity=0;
        eQuantity=0;
        nQuantity=0;

        numOfDays =0;
    }


    public String get_id() {
        return _id;
    }


    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getQuantity(String timeOfDay) {
        if(timeOfDay.equals(MORNING_QUANTITY)) {
            Log.d("getting morning", mQuantity+"");
            return mQuantity;
        }else if(timeOfDay.equals(AFTERNOON_QUANTITY)) {
            Log.d("getting AFTERNOON_QUANTITY", aQuantity+"");
            return aQuantity;
        }else if(timeOfDay.equals(EVENING_QUANTITY)) {
            Log.d("getting EVENING_QUANTITY", eQuantity+"");
            return eQuantity;
        }else if(timeOfDay.equals(NIGHT_QUANTITY)) {
            Log.d("getting NIGHT_QUANTITY", nQuantity+"");
            return nQuantity;
        }else{
            Log.d("getting quantity", "bla bla");
            return 0;
        }

    }

    public void setQuantity(int quantity, String timeOfDay) {
        if(timeOfDay.equals(MORNING_QUANTITY)) {
            Log.d("setting morning",quantity+"");
            this.mQuantity = quantity;
        }else if(timeOfDay.equals(AFTERNOON_QUANTITY)) {
            this.aQuantity = quantity;
            Log.d("setting afternoon",quantity+"");
        }else if(timeOfDay.equals(EVENING_QUANTITY)) {
            this.eQuantity = quantity;
            Log.d("setting evening",quantity+"");
        }else if(timeOfDay.equals(NIGHT_QUANTITY)) {
            this.nQuantity = quantity;
            Log.d("setting night",quantity+"");
        }else{
            Log.d("setting morning","bla bla");
        }
    }




    public int getIsMorning() {
        return isMorning;
    }

    public void setIsMorning(int isMorning) {
        this.isMorning = isMorning;
    }

    public int getIsMorningAfter() {
        return isMorningAfter;
    }

    public void setIsMorningAfter(int isMorningAfter) {
        this.isMorningAfter = isMorningAfter;
    }

    public int getIsMorningBefore() {
        return isMorningBefore;
    }

    public void setIsMorningBefore(int isMorningBefore) {
        this.isMorningBefore = isMorningBefore;
    }

    public int getIsAfternoon() {
        return isAfternoon;
    }

    public void setIsAfternoon(int isAfternoon) {
        this.isAfternoon = isAfternoon;
    }

    public int getIsAfternoonAfter() {
        return isAfternoonAfter;
    }

    public void setIsAfternoonAfter(int isAfternoonAfter) {
        this.isAfternoonAfter = isAfternoonAfter;
    }

    public int getIsAfternoonBefore() {
        return isAfternoonBefore;
    }

    public void setIsAfternoonBefore(int isAfternoonBefore) {
        this.isAfternoonBefore = isAfternoonBefore;
    }

    public int getIsEvening() {
        return isEvening;
    }

    public void setIsEvening(int isEvening) {
        this.isEvening = isEvening;
    }

    public int getIsEveningAfter() {
        return isEveningAfter;
    }

    public void setIsEveningAfter(int isEveningAfter) {
        this.isEveningAfter = isEveningAfter;
    }

    public int getIsEveningBefore() {
        return isEveningBefore;
    }

    public void setIsEveningBefore(int isEveningBefore) {
        this.isEveningBefore = isEveningBefore;
    }

    public int getIsNight() {
        return isNight;
    }

    public void setIsNight(int isNight) {
        this.isNight = isNight;
    }

    public int getIsNightAfter() {
        return isNightAfter;
    }

    public void setIsNightAfter(int isNightAfter) {
        this.isNightAfter = isNightAfter;
    }

    public int getIsNightBefore() {
        return isNightBefore;
    }

    public void setIsNightBefore(int isNightBefore) {
        this.isNightBefore = isNightBefore;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public JSONObject getMorning() throws JSONException {
        Boolean take = false;
        Boolean with_food = false;
        String time_stamp = "morning";

        if(getIsMorning() == 1){
            take = true;
        }

        if(getIsMorningAfter() ==1){
            with_food = true;

        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Take", take);
        jsonObject.put("With_Food", with_food);
        jsonObject.put("time", time_stamp);
        jsonObject.put("Quantity", getQuantity(MORNING_QUANTITY));

        return jsonObject;
    }


    public JSONObject getAfternoon() throws JSONException {
        Boolean take = false;
        Boolean with_food = false;
        String time_stamp = "Afternoon";

        if(getIsAfternoon() == 1){
            take = true;
        }

        if(getIsAfternoonAfter() ==1){
            with_food = true;

        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Take", take);
        jsonObject.put("With_Food", with_food);
        jsonObject.put("time", time_stamp);
        jsonObject.put("Quantity", getQuantity(AFTERNOON_QUANTITY));

        return jsonObject;
    }


    public JSONObject getEvening() throws JSONException {
        Boolean take = false;
        Boolean with_food = false;
        String time_stamp = "evening";

        if(getIsEvening() == 1){
            take = true;
        }

        if(getIsEveningAfter() ==1){
            with_food = true;

        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Take", take);
        jsonObject.put("With_Food", with_food);
        jsonObject.put("time", time_stamp);
        jsonObject.put("Quantity", getQuantity(EVENING_QUANTITY));

        return jsonObject;
    }

    public JSONObject getNight() throws JSONException {
        Boolean take = false;
        Boolean with_food = false;
        String time_stamp = "night";

        if(getIsNight() == 1){
            take = true;
        }

        if(getIsNightAfter() ==1){
            with_food = true;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Take", take);
        jsonObject.put("With_Food", with_food);
        jsonObject.put("time", time_stamp);
        jsonObject.put("Quantity", getQuantity(NIGHT_QUANTITY));
        return jsonObject;
    }

    public int getTotalQuantity(){

        int totalQuantity = getQuantity(MORNING_QUANTITY)+getQuantity(AFTERNOON_QUANTITY)+getQuantity(EVENING_QUANTITY)+getQuantity(NIGHT_QUANTITY);
        return totalQuantity*getNumOfDays();
    }


}
