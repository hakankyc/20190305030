package com.molly.a20190305030.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class IntentHelper {
    public static void goToClass(Context context,Class c){
        //Burada bir sayfaya gidiyoruz. Context = Mevcut Sayfa, Class c = Gidilecek sayfa.
        Intent intent = new Intent(context,c); //Burada intent objemizi oluşturuyoruz.
        context.startActivity(intent); //Burada yeni aktiviteyi başlatıyoruz
    }
    public static void goToClassWithAllFinish(Activity activity, Class c){
        /* Burada da bir sayfaya gidiyoruz fakat tek farkla.
            Eğer bu fonksiyon çağrılırsa yeni sayfaya giderken önceden açılmış bütün sayfaları kapatarak gidiyoruz.
            Bu yüzden input olarak context yerine direkt aktivite istiyoruz.
         */
        Intent intent = new Intent(activity,c); //Burası yukarısıyla aynı tek fark context yerine aktivite giriyoruz.
        activity.startActivity(intent); //Burası da yukarısıyla aynı;
        activity.finishAffinity(); //Fark burada. Bu kod sayesinde önceden açılmış bütün sayfalar kapatılıyor.
    }
}
