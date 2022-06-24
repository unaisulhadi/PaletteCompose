package com.hadi.myapplication.data.model

data class Place(
    val placeId:Int,
    val placeTitle:String,
    val description:String,
    val placeCategory:String,
    val imageUrl:String,
    val climate:String,
    val rating:Double
)
