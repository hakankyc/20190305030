package com.molly.a20190305030.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.molly.a20190305030.Adapters.AdminCarRowAdapter;
import com.molly.a20190305030.Helpers.IntentHelper;
import com.molly.a20190305030.Helpers.SQLiteCars;
import com.molly.a20190305030.Helpers.SQLiteUser;
import com.molly.a20190305030.Helpers.SharedHelper;
import com.molly.a20190305030.Models.Car;
import com.molly.a20190305030.Models.User;
import com.molly.a20190305030.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AdminCarRowAdapter adapter;
    private ArrayList<Car> cars;
    private Activity mActivity;
    private boolean isMyCar = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindIDs();
    }



    private void bindIDs() {
        cars = new ArrayList<>();
        mActivity = this;

        RecyclerView recyclerView = findViewById(R.id.activity_main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new AdminCarRowAdapter(cars, new AdminCarRowAdapter.AdminCarRowAdapterListener() {
            @Override
            public void onLinearClicked(int position) {
                if(cars.get(position).getOwnerEmail() == null){
                    clickAction(position);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.rented_car){
                    isMyCar = !isMyCar;
                    if(isMyCar){
                        getMyCars();
                        Toast.makeText(mActivity, "My Cars", Toast.LENGTH_SHORT).show();
                        MenuItem menuItem = toolbar.getMenu().getItem(0);
                        menuItem.setTitle("All Cars");
                    }else{
                        getData();
                        Toast.makeText(mActivity, "All cars", Toast.LENGTH_SHORT).show();
                        MenuItem menuItem = toolbar.getMenu().getItem(0);
                        menuItem.setTitle("Rented Cars");

                    }
                }else if(item.getItemId() == R.id.sign_out){
                    SharedHelper.deleteSignedUserEmail(mActivity);
                    IntentHelper.goToClassWithAllFinish(mActivity,LoginActivity.class);
                    Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        getData();
    }

    private void getMyCars() {
        cars.clear();
        SQLiteCars sqLiteCars = new SQLiteCars(mActivity);
        String myEmail = SharedHelper.getSignedUserEmail(mActivity);
        List<Car> savedCars = sqLiteCars.getAllCars();
        for(Car c : savedCars){
            if(c.getOwnerEmail() != null){
                if(c.getOwnerEmail().equals(myEmail)){
                    cars.add(c);
                }
            }
        }
        adapter.notifyDataSetChanged();

    }

    private void clickAction(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("Rent the Car?");
        builder.setMessage("Are You Sure?");
        builder.setPositiveButton("Rent", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteCars sqLiteCars = new SQLiteCars(mActivity);
                Car c = cars.get(position);
                c.setOwnerEmail(SharedHelper.getSignedUserEmail(mActivity));
                sqLiteCars.updateCar(c);
                Toast.makeText(mActivity, "Rented!", Toast.LENGTH_SHORT).show();
                getData();
            }
        }).show();
    }

    private void getData() {
        SQLiteCars sqLiteCars = new SQLiteCars(mActivity);
        cars.clear();
        List<Car> databaseCar = sqLiteCars.getAllCars();

        for(Car c : databaseCar){
            if(c.getOwnerEmail() == null){
                cars.add(c);
            }
        }
        adapter.notifyDataSetChanged();
    }









}