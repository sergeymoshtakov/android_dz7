package com.example.dz_7.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dz_7.R;
import com.example.dz_7.models.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<Car> carList;

    public CarAdapter(List<Car> carList) {
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.bind(car);
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView brand, model, year, cost;
        ImageView imageView;

        public CarViewHolder(View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.car_brand);
            model = itemView.findViewById(R.id.car_model);
            year = itemView.findViewById(R.id.car_year);
            cost = itemView.findViewById(R.id.car_cost);
            imageView = itemView.findViewById(R.id.car_image);
        }

        public void bind(Car car) {
            brand.setText(car.getBrand());
            model.setText(car.getModel());
            year.setText(String.valueOf(car.getYear()));
            cost.setText("$" + car.getCost());
            imageView.setImageResource(car.getImageResId());
        }
    }
}
