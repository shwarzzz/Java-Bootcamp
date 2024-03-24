package edu.school21.classes;

import java.util.StringJoiner;

public class Car {
    private final String brand;
    private Double mileage;
    private Integer fuel;

    public Car() {
        this.brand = "Default brand";
        this.mileage = 0.0;
        this.fuel = 3;
    }

    public Car(String brand, double mileage, int fuel) {
        this.brand = brand;
        this.mileage = mileage;
        this.fuel = fuel;
    }

    public Double drive(double distance) {
        mileage += distance;
        return mileage;
    }

    public Integer fill(int value) {
        this.fuel += value;
        return fuel;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("brand = '" + brand + "'")
                .add("mileage = '" + mileage + "'")
                .add("fuel = " + fuel)
                .toString();
    }
}
