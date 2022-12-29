package com.molly.a20190305030.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.molly.a20190305030.Models.Car;
import com.molly.a20190305030.R;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

public class DialogAddCar extends Dialog {
    private final EditText modelEdit,yearEdit;
    private final Button okButton;
    private final DialogAddCarListener listener;
    private final Activity activity;
    private final ImageView imageView;
    private byte[] selectedByteArray = null;
    public DialogAddCar(@NonNull Activity activity, DialogAddCarListener listener) {
        super(activity);
        setContentView(R.layout.dialog_admin_add_car); //XML ile bağlıyoruz.
        this.listener = listener;
        this.activity = activity;
        modelEdit = findViewById(R.id.dialog_admin_add_car_modeledit); //Model edittext bağlanıyor.
        yearEdit = findViewById(R.id.dialog_admin_add_car_yearedit); //Yearedittext bağlanıyor.
        okButton = findViewById(R.id.dialog_admin_add_car_okbutton); //Okbutton text bağlanıyor.
        imageView = findViewById(R.id.dialog_admin_add_car_image); //Image bağlanıyor
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ok butonuna tıklandığında çalışan yer
                okAction();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                        selectedByteArray = stream.toByteArray();
                        imageView.setImageBitmap(r.getBitmap());
                        //Fotoğrafı byte arraye dönüştürdük.
                    }
                }).show((FragmentActivity) activity);
            }
        });



    }

    private void okAction() {
        if(edittextIsNotEmpty(modelEdit)){
            if(edittextIsNotEmpty(yearEdit)){
                if(selectedByteArray != null){
                    Car car = new Car();
                    car.setYear(yearEdit.getText().toString().trim());
                    car.setModel(modelEdit.getText().toString().trim());
                    car.setImage(selectedByteArray);
                    listener.okClicked(car);
                    dismiss();
                }else{
                    //Resim seçilmemiş
                    Toast.makeText(activity, "Please select picture", Toast.LENGTH_SHORT).show();
                }
            }else{
                //Year kısmı boş
                Toast.makeText(activity, "Please enter year", Toast.LENGTH_SHORT).show();
            }
        }else{
            //Model kısmı boş
            Toast.makeText(activity, "Please enter model", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean edittextIsNotEmpty(EditText editText){
        //Edittextin boş olup olmadığını kontrol eden method.
        return !editText.getText().toString().trim().isEmpty();
    }




    public interface DialogAddCarListener<T>{
        void okClicked(Car car);
    }
}
