package com.molly.a20190305030.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.molly.a20190305030.Dialog.DialogAdminLogin;
import com.molly.a20190305030.Helpers.IntentHelper;
import com.molly.a20190305030.Helpers.SQLiteUser;
import com.molly.a20190305030.Helpers.SharedHelper;
import com.molly.a20190305030.Models.User;
import com.molly.a20190305030.R;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEdit,passwordEdit;
    private Button loginButton,signUpButton,adminButton;
    private Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        bindIDs(); //Bağlama fonksiyonu
        setOnClicks(); //Tıklandığında yapılacak şeyi belirlemek için burada onClick fonksiyonlarını belirliyoruz.
        checkUser(); //Kullanıcı kontrolü
    }

    private void checkUser() {
        //Giriş yapmış kullanıcının olup olmadığını kontrol ediyoruz.
        if(SharedHelper.getSignedUserEmail(mActivity) != null){
            //Giriş yapmış kullanıcı mevcut. Direkt MainActivity'e yönlendiriyoruz.
            IntentHelper.goToClassWithAllFinish(mActivity,MainActivity.class);
            //AllFinish -> Arkadaki sayfaları kapatır.
        }else{
            //Giriş yapmış kullanıcı yok
        }
    }


    private void bindIDs() {
        //Burada XML'deki görünümleri koddaki değişkenlere bağlıyoruz.
        mActivity = this; //Burada kolaylık olması için mevcut aktivitemizi bir değişkene atıyoruz.
        emailEdit = findViewById(R.id.login_email_edit); //Email edit bağlanıyor.
        passwordEdit = findViewById(R.id.login_password_edit); //Password edit bağlanıyor.
        loginButton = findViewById(R.id.login_button); //Login butonu bağlanıyor
        signUpButton = findViewById(R.id.login_signup_button); //Kayıt Ol butonu bağlanıyor.
        adminButton = findViewById(R.id.login_admin_button); //Admin butonu bağlanıyor.


    }
    private void setOnClicks() {
        //Butonların tıklamalarının belirlenmesi.

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Login butonuna tıklanınca yapılacaklar
                loginAction(); //Giriş yapmayı ayrı bir fonksiyona taşıyorum. 
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kayıt ol butonuna tıklanınca yapılacaklar
                //Burada kayıt aktivitesine gideceğiz.
                IntentHelper.goToClass(mActivity,SignUpActivity.class); //Kolaylık olması için yazdığımız helper classımızdan yararlanıyoruz.
                //Intent helper class'ını görmek için ctrl'ye basılı tutarak IntentHelper yazan yerin üstüne tıklanabilir.
            }
        });
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Admin butonuna tıklanınca yapılacaklar
                DialogAdminLogin adminLoginDialog = new DialogAdminLogin(mActivity, new DialogAdminLogin.DialogAdminLoginListener() {
                    @Override
                    public void okClicked(boolean truePassword) {
                        //Dialogta ok'a basınca çalışan yer. (Listener)
                        if(truePassword){
                            //Parola doğru
                            IntentHelper.goToClass(mActivity,AdminActivity.class); //Admin sayfasına gidiş
                        }else{
                            //Parola hatalı
                            Toast.makeText(mActivity, "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                adminLoginDialog.show(); //Dialogu gösterir.
                
            }
        });
    }

    private void loginAction() {
        SQLiteUser sqLiteHelper = new SQLiteUser(mActivity); //SQLite helper sınıfından bir obje oluşturduk (Veritabanı Sınıfı).
        List<User> users = sqLiteHelper.getAllUsers(); //Veritabanından bütün kullanıcıları getirdik.
        ArrayList<String> emails = new ArrayList<>(); //Emails adında yeni bir dizi oluşturduk.
        for(User u : users){
            emails.add(u.getEmail()); //Döngü sayesinde bütün emailleri oluşturduğumuz diziye aktardık.
        }
        String userEmail = emailEdit.getText().toString().trim(); //Kullanıcının girdiği email adresi
        String userPassword = passwordEdit.getText().toString().trim(); //Kullanıcının girdiği parola
        if(emails.contains(userEmail)){ //Eğer bu emailin kaydı varsa -> kontrol
            //Kullanıcı kaydı mevcut. Parola kontrolü yapılacak.
            User user = sqLiteHelper.getUserFromEmail(userEmail); //Kullanıcıyı sistemden çektik.
            if(user.getPassword().equals(userPassword)){
                //Kullanıcının girdiği parola sistemdeki parolayla eşleşiyor. Giriş yapılabilir
                IntentHelper.goToClassWithAllFinish(mActivity,MainActivity.class); //MainActiviy'e git
                //allFinish -> Arkadaki sayfaların hepsini kapat.
                SharedHelper.setSignedUserEmail(mActivity,userEmail); //Cihaz hafızasına giriş yapan email adresini tanımladık.
            }else{
                //Parola Yanlış
                Toast.makeText(mActivity, "Password incorrect", Toast.LENGTH_SHORT).show();
            }

        }else{
            //Kullanıcının kaydı yok
            Toast.makeText(mActivity, "This email address has not been registered in the system", Toast.LENGTH_SHORT).show();
        }
        
        
    }


}