package com.example.dz_7.repository;

import com.example.dz_7.R;
import com.example.dz_7.models.Car;

import java.util.ArrayList;
import java.util.List;

public class CarRepository {
    private static CarRepository instance;
    private List<Car> carList;

    private CarRepository() {
        carList = new ArrayList<>();
        initCarList();
    }

    public static CarRepository getInstance() {
        if (instance == null) {
            instance = new CarRepository();
        }
        return instance;
    }

    public List<Car> getCarList() {
        return carList;
    }

    private void initCarList() {
        carList.add(new Car("Toyota", "Corolla", 2010, "Well maintained", 5000, R.drawable.toyota_corolla));
        carList.add(new Car("Honda", "Civic", 2012, "Sporty model", 7000, R.drawable.honda_civic));
        // Add other cars here as needed
    }
}
