package com.molly.a20190305030.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.molly.a20190305030.Models.Car;
import com.molly.a20190305030.Models.User;

import java.util.ArrayList;
import java.util.List;

public class SQLiteCars extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CarManager";
    private static final String TABLE_CONTACTS = "cars";
    private static final String KEY_ID = "id";
    private static final String KEY_OWNEREMAIL = "owneremail";
    private static final String KEY_MODEL = "model";
    private static final String KEY_YEAR = "year";
    private static final String KEY_IMAGE = "image";
    public SQLiteCars(@Nullable Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Sınıf oluşturulduğunda Yerel veritabanındaki veri tablosu oluşturuluyor.
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_OWNEREMAIL + " TEXT,"
                + KEY_MODEL + " TEXT,"
                + KEY_YEAR + " TEXT,"
                + KEY_IMAGE+ " BLOB" + ")"
                ;
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Veritabanında herhangi bir değişim olduğunda çağrılan yer.

    }
    public int updateCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_OWNEREMAIL, car.getOwnerEmail());

        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(car.getId()) });
    }

    public void addCar(Car car) {
        //Araba ekleyen fonksiyon
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_OWNEREMAIL, car.getOwnerEmail()); // Email ekle
        values.put(KEY_MODEL,car.getModel()); //Model ekle
        values.put(KEY_YEAR, car.getYear()); //  //Yıl ekle
        values.put(KEY_IMAGE,car.getImage());
        db.insert(TABLE_CONTACTS, null, values); //Kaydet
        db.close(); // Veritabanı bağlantısı kapanıyor.
    }



    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();
        // Bütün kullanıcıları getiren fonksiyon
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Car car = new Car();
                car.setId(Integer.parseInt(cursor.getString(0)));
                car.setOwnerEmail(cursor.getString(1));
                car.setModel(cursor.getString(2));
                car.setYear(cursor.getString(3));
                car.setImage(cursor.getBlob(4));
                carList.add(car);
            } while (cursor.moveToNext());
        }

        return carList;
    }
    public void deleteCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(car.getId()) });
        db.close();
    }



}
