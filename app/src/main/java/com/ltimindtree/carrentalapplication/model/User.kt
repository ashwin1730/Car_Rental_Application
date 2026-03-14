package com.ltimindtree.carrentalapplication.model

data class User(
    val name: String,
    val email: String,
    val phone: String = "",
    val profileImage: String? = null
)
