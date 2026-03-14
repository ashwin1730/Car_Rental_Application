package com.ltimindtree.carrentalapplication.model

data class Car(
    val id: Int,
    val brand: String,
    val model: String,
    val pricePerDay: Double,
    val transmission: String,
    val fuelType: String,
    val seats: Int,
    val category: String, // Added category
    val imageRes: Int? = null,
    val isAvailable: Boolean = true
)
