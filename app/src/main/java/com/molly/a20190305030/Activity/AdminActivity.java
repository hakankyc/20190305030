package com.molly.a20190305030.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.molly.a20190305030.Adapters.AdminCarRowAdapter;
import com.molly.a20190305030.Dialog.DialogAddCar;
import com.molly.a20190305030.Helpers.SQLiteCars;
import com.molly.a20190305030.Models.Car;
import com.molly.a20190305030.R;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private Activity mActivity;
    private ArrayList<Car> cars;
    private AdminCarRowAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bindIDs(); //ID'leri bağlayan fonksiyon
    }

    private void bindIDs() {
        cars = new ArrayList<>();
        fab = findViewById(R.id.activity_admin_fab); //Sağ alttaki yuvarlak butonu tanımlıyoruz.
        mActivity = this; //Aktiviteyi değişkene atadık.
        Toolbar toolbar = findViewById(R.id.activity_admin_toolbar); //Toolbar tanımlanıyor.
        setSupportActionBar(toolbar); //Sayfanın toolbarı atanıyor.

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sağ alttaki butona tıklanınca çağrılan fonksiyon.
                fabAction();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.activity_admin_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new AdminCarRowAdapter(cars, new AdminCarRowAdapter.AdminCarRowAdapterListener() {
            @Override
            public void onLinearClicked(int position) {
                deleteAction(position);
            }
        });
        recyclerView.setAdapter(adapter);

        getData();
    }

    private void deleteAction(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("Delete Car");
        builder.setMessage("Are You Sure?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteCars sqLiteCars = new SQLiteCars(mActivity);
                sqLiteCars.deleteCar(cars.get(position));
                getData();
            }
        }).show();
    }

    private void fabAction() {
        DialogAddCar dialogAddCar = new DialogAddCar(mActivity, new DialogAddCar.DialogAddCarListener() {
            @Override
            public void okClicked(Car car) {
               //Araba özellikleri girilip ok'a tıklandı.
                SQLiteCars sqLiteCars = new SQLiteCars(mActivity); //Yerel veritabanı için SQLite objesi oluşturuyoruz.
                sqLiteCars.addCar(car);
                Toast.makeText(mActivity, "Araç eklendi", Toast.LENGTH_SHORT).show();
                getData(); //Verileri tekrar getir
            }
        });
        dialogAddCar.show(); //Dialog gösteriliyor.
    }

    private void getData() {
        cars.clear();
        SQLiteCars sqLiteCars = new SQLiteCars(mActivity);
        cars.addAll(sqLiteCars.getAllCars());
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //Toolbardaki geri ok'a tıklandığında geri gitmesini söyleyen fonksiyon.
        return super.onSupportNavigateUp();
    }





}