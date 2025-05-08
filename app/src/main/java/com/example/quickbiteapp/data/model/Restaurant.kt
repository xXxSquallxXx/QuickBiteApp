package com.example.quickbiteapp.data.model

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("rating") val rating: Float
)