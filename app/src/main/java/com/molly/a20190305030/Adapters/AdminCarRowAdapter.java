package com.molly.a20190305030.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molly.a20190305030.Models.Car;
import com.molly.a20190305030.R;

import java.util.ArrayList;

public class AdminCarRowAdapter extends RecyclerView.Adapter<AdminCarRowAdapter.PostHolder> {
    private final ArrayList<Car> cars;
    private final AdminCarRowAdapterListener listener;

    public AdminCarRowAdapter(ArrayList<Car> cars, AdminCarRowAdapterListener listener) {
        this.cars = cars;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminCarRowAdapter.PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.admin_car_row,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCarRowAdapter.PostHolder holder, int position) {
        Car c = cars.get(holder.getAdapterPosition());
        Bitmap b = BitmapFactory.decodeByteArray(c.getImage(),0,c.getImage().length);
        holder.imageView.setImageBitmap(b);
        holder.modelText.setText(c.getModel());
        holder.yearText.setText(c.getYear());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLinearClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    static class PostHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        ImageView imageView;
        TextView modelText,yearText;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.admin_car_row_linear);
            imageView = itemView.findViewById(R.id.admin_car_row_image);
            modelText = itemView.findViewById(R.id.admin_car_row_model);
            yearText = itemView.findViewById(R.id.admin_car_row_year);
        }
    }
    public interface AdminCarRowAdapterListener<T>{
        void onLinearClicked(int position);
    }
}
