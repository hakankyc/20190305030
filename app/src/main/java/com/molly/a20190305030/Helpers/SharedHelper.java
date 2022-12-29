package com.molly.a20190305030.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.molly.a20190305030.Models.User;

public class SharedHelper {
    private static final String USER_EMAIL = "USER_EMAIL";

    public static void setSignedUserEmail(Context context,String userEmail){
        getDefaulSharedPreferences(context).edit().putString(USER_EMAIL,userEmail).apply();
    }
    public static String getSignedUserEmail(Context context){
        return getDefaulSharedPreferences(context).getString(USER_EMAIL,null);
    }
    public static void deleteSignedUserEmail(Context context){
        getDefaulSharedPreferences(context).edit().remove(USER_EMAIL).apply();
    }

    private static SharedPreferences getDefaulSharedPreferences(Context context){
        return context.getSharedPreferences("com.molly.a20190305030",Context.MODE_PRIVATE);
    }

}
