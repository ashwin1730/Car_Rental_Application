package com.ltimindtree.carrentalapplication.repository

import com.ltimindtree.carrentalapplication.R
import com.ltimindtree.carrentalapplication.model.Car

class CarRepository {
    fun getCars(): List<Car> {
        return listOf(
            Car(1, "Honda", "Civic", 45.0, "Automatic", "Hybrid", 5, "Sedan", R.drawable.civic),
            Car(2, "Mercedes", "S-Class", 150.0, "Automatic", "Petrol", 5, "Luxury", R.drawable.mercedies),
            Car(3, "Ford", "Mustang", 80.0, "Manual", "Petrol", 4, "Sports", R.drawable.mustangg),
            Car(4, "Honda", "City", 40.0, "Automatic", "Petrol", 5, "Sedan", R.drawable.hondacity),
            Car(5, "Tesla", "Model 3", 100.0, "Automatic", "Electric", 5, "Electric"),
            Car(6, "BMW", "X5", 120.0, "Automatic", "Diesel", 7, "SUV")
        )
    }
}
