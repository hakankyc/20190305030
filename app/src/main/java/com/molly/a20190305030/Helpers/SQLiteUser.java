package com.molly.a20190305030.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.molly.a20190305030.Models.User;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUser extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserManager";
    private static final String TABLE_CONTACTS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PH_NO = "phone_number";
    public SQLiteUser(@Nullable Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Sınıf oluşturulduğunda Yerel veritabanındaki veri tablosu oluşturuluyor.
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Veritabanında herhangi bir değişim olduğunda çağrılan yer.

    }

    public void addUser(User user) {
        //Kullanıcı ekleyen fonksiyon
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, user.getEmail()); // Email ekle
        values.put(KEY_PASSWORD,user.getPassword()); //Password ekle
        values.put(KEY_PH_NO, user.getPhoneNumber()); //  //Telefon numarası ekle
        db.insert(TABLE_CONTACTS, null, values); //Kaydet
        db.close(); // Veritabanı bağlantısı kapanıyor.
    }

    public User getUserFromEmail(String email) {
        //Email ile kullanıcının bütün bilglerini sorgulama
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_EMAIL,KEY_PASSWORD ,KEY_PH_NO }, KEY_EMAIL + "=?",
                new String[] {email}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2), cursor.getString(3));
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        // Bütün kullanıcıları getiren fonksiyon
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setEmail(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setPhoneNumber(cursor.getString(3));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }



}
