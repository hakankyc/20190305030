package com.molly.a20190305030.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.molly.a20190305030.R;

public class DialogAdminLogin extends Dialog {
    //Admin butonuna tıklandığında şifre girilmesi gereken dialog.
    private final EditText passwordEdit;
    private final Button okButton;
    private final DialogAdminLoginListener listener;
    public DialogAdminLogin(@NonNull Context context,DialogAdminLoginListener listener) {
        super(context);
        this.listener = listener;
        setContentView(R.layout.dialog_admin_login); //XML ile bağlandığı yer.
        passwordEdit = findViewById(R.id.dialog_admin_password_edit);  //Passwordedit bağlanıyor.
        okButton = findViewById(R.id.dialog_admin_login_okbutton); //Okbutton bağlanıyor.

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ok buttonuna tıklandığında çalışan yer.
                okButtonAction();
            }
        });
    }

    private void okButtonAction() {
        String enteredPassword = passwordEdit.getText().toString().trim(); //Kullanıcının girdiği parola alındı.
        if(enteredPassword.equals("1010")){
            listener.okClicked(true); //Parola doğru
        }else{
            listener.okClicked(false); //Parola hatalı
        }
        dismiss();
    }


    public interface DialogAdminLoginListener<T>{
        void okClicked(boolean truePassword); //OK butonuna tıklandığını dinleyebilmek için yazdığımız interface
    }




}
