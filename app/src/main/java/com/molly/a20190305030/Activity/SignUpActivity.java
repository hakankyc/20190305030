package com.molly.a20190305030.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.molly.a20190305030.Helpers.IntentHelper;
import com.molly.a20190305030.Helpers.SQLiteUser;
import com.molly.a20190305030.Helpers.SharedHelper;
import com.molly.a20190305030.Models.User;
import com.molly.a20190305030.R;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailEdit,passwordEdit,phoneNumberEdit;
    private Button signUpButton;
    private Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Sayfa ilk açıldığında çalışan fonksiyon.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        bindIDs(); //Burada XML'deki ID'leri bağlıyoruz.
        setOnClicks(); //Butonlara tıklandığında olacakları belirliyoruz.
    }



    private void bindIDs() {
        mActivity = this; //Kolaylık olması için mevcut aktivitemizi değişkene atıyoruz.
        emailEdit = findViewById(R.id.activity_signup_emailedit); //Email edittext bağlanıyor.
        passwordEdit = findViewById(R.id.activity_signup_passwordedit); //Password edittext bağlanıyor.
        phoneNumberEdit = findViewById(R.id.activity_signup_phonenumberedit); //Telefon No Edittext bağlanıyor.
        signUpButton = findViewById(R.id.activity_signup_button); //Kayıt ol butonu bağlanıyor.
        Toolbar toolbar = findViewById(R.id.activity_signup_toolbar); //Toolbar bağlanıyor.
        setSupportActionBar(toolbar); //Sayfanın toolbarı olduğunu burada belirtiyoruz. Bir nevi sayfayla bağlanmış oluyor.

    }

    private void setOnClicks() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sign up butonuna tıklandığında çalışan yer.
                signUpAction(); //Derli toplu görünmesi için ayrı fonksiyona alıyorum.
            }
        });
    }

    private void signUpAction() {
        if(checkIsNotEmpty(emailEdit)){ //Emailedit kontrolü
            if(checkIsNotEmpty(passwordEdit)){ //Passwordedit kontrolü
                if(checkIsNotEmpty(phoneNumberEdit)){ //Phonenumberedit kontrolü
                    //Bütün boşluklar doldurulmuş. Kayıt yapılabilir.
                    checkEmail();//Bu email daha önce kaydedilmiş mi kontrol ediyoruz.
                }else{
                    //Telefon numarası boş
                    Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                }
            }else{
                //Parola boş
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            }
        }else{
            //Email edittext boş
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show(); //Tost yazısı gösteriyor.
        }
    }

    private void checkEmail() {
        //Email adresleri kontrolü
        SQLiteUser sqLiteHelper = new SQLiteUser(mActivity); //SQLite helper sınıfımızdan bir obje oluşturuyoruz.
        List<User> allUsers = sqLiteHelper.getAllUsers(); //Bütün kullanıcıları veritabanından çekiyoruz.
        ArrayList<String> emails = new ArrayList<>(); //Emails adında yeni bir dizi oluşturuyoruz.
        for(User u : allUsers){
            /*
                For döngüsü içinde yerel veritabanındaki bütün kullanıcıların emaillerini emails adlı dizinin içine koyuyoruz.
             */
            emails.add(u.getEmail());
        }
        if(!emails.contains(emailEdit.getText().toString().trim())){ //Emails dizisi doldurulduktan sonra,
            //girilen emailin mevcut olup olmadığını kontrol ediyoruz. -> contains = içeriyorMu
            signUp(); //Email adresi ilk kez kullanılıyor. Kayıt olabilir.
        }else{
            //Bu email adresi daha önce kullanılmış
            Toast.makeText(mActivity, "This email address already exists", Toast.LENGTH_SHORT).show();
        }
        
    }

    private void signUp() {
        SQLiteUser sqLiteHelper = new SQLiteUser(mActivity); //Yerel veritabanı için helper sınıfından bir obje oluşturduk.
        User user = new User(); //Yeni kullanıcı objesi oluşturduk;
        user.setEmail(emailEdit.getText().toString().trim()); //Kullanıcının emailini belirledik.
        user.setPassword(passwordEdit.getText().toString().trim()); //Kullanıcının parolasını belirledik.
        user.setPhoneNumber(phoneNumberEdit.getText().toString().trim()); //Kullanıcının telefon numarasını belirledik.
        sqLiteHelper.addUser(user); //Yerel veritabanına kaydettik.
        SharedHelper.setSignedUserEmail(mActivity,emailEdit.getText().toString().trim()); /*Bu email adresi giriş yaptığı için
        sisteme giriş yapmış email olarak kaydediyoruz.
        */
        Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show(); //Kullanıcıya tost mesajı gösteriyoruz.
        IntentHelper.goToClassWithAllFinish(mActivity,MainActivity.class); //Kayıt olduk. Artık Ana sayfaya gidebiliriz.
        //Arkada sayfa kalmaması gerektiği için allFinish methodunu kullanıyoruz.
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //Toolbardaki geri butonuna basıldığında geri gitmesi için bu kod yazılıyor.
        return super.onSupportNavigateUp();
    }

    private boolean checkIsNotEmpty(EditText editText){
        //Edittextin içinin boş olup olmadığını kontrol eden method.
        return !editText.getText().toString().trim().isEmpty();
        //getText -> İçindeki texti getir
        //toString -> İçindeki texti stringe çevir
        //trim -> Stringin sağında ve solunda boşluk varsa bunları temizle.
        //isEmpty -> Bu işlemler sonucunda oluşan string boş ise true, dolu ise false döndrürür.
    }
}