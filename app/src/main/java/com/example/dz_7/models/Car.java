// Car.java
package com.example.dz_7.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {
    private String brand;
    private String model;
    private int year;
    private String description;
    private int cost;
    private int imageResId;

    public Car(String brand, String model, int year, String description, int cost, int imageResId) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.description = description;
        this.cost = cost;
        this.imageResId = imageResId;
    }

    // Getter methods
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getDescription() { return description; }
    public int getCost() { return cost; }
    public int getImageResId() { return imageResId; }

    // Parcelable implementation
    protected Car(Parcel in) {
        brand = in.readString();
        model = in.readString();
        year = in.readInt();
        description = in.readString();
        cost = in.readInt();
        imageResId = in.readInt();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeInt(year);
        dest.writeString(description);
        dest.writeInt(cost);
        dest.writeInt(imageResId);
    }
}
