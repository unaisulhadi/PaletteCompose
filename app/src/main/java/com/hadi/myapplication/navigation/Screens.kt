package com.hadi.myapplication.navigation

sealed class Screens (val route:String) {
    object PlacesListScreen : Screens("/places_list_screen")
    object PlaceDetailsScreen : Screens("/place_details_screen")
}