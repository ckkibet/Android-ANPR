package com.google.android.gms.anpr.vision.ocrreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import java.util.ArrayList;
import java.util.List;


public class mydatabase extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "mynewdatabase";
    private static String TABLE_NAME = "mynewtable";
    private static String ID = "id";
    private static String NumberPlate="text";
    private static String Date="formattedDate";
    private static String Time ="formattedTime";
    private static String Driver="driver";


    public mydatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String myquery = String.format("CREATE TABLE %s(%s INTEGER AUTO INCREMENT ,%s VARCHAR ,%s VARCHAR , %s VARCHAR , %s INTEGER)", TABLE_NAME, ID, NumberPlate, Date, Time, Driver);
        db.execSQL(myquery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

    }




    public Object insertdata(String plate, String date, String time, String driver) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NumberPlate, plate);
        contentValues.put(Date, date);
        contentValues.put(Time, time);
        contentValues.put(Driver, driver);

        long result =db.insert(TABLE_NAME,null,contentValues);

        if (result== -1){
            return false;
        }
        else {

            return true;
        }

        }


    public List<DataModel> getdata(){
        // DataModel dataModel = new DataModel();
        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" ;",null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext()) {
            dataModel= new DataModel();
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String plate = cursor.getString(cursor.getColumnIndexOrThrow("text"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("formattedDate"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("formattedTime"));
            String driver = cursor.getString(cursor.getColumnIndexOrThrow("driver"));
            dataModel.setId(id);
            dataModel.setNumber_plate(plate);
            dataModel.setDate(date);
            dataModel.setTime(time);
            dataModel.setDriver(driver);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }


        return data;
    }

}