package com.google.android.gms.anpr.vision.ocrreader;

public class DataModel {
    public String Id;
    public String Number_plate;
    public String Date;
    public String Time;
    public String Driver;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }



    public String getNumber_plate() {
        return Number_plate;
    }

    public void setNumber_plate(String number_plate) {
        Number_plate = number_plate;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }
}
