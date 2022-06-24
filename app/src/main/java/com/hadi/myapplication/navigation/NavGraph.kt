package com.hadi.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hadi.myapplication.ui.screens.PlaceDetailsScreen
import com.hadi.myapplication.ui.screens.PlacesListScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.PlacesListScreen.route
    ) {

        composable(
            route = Screens.PlacesListScreen.route,
        ) {
            PlacesListScreen(navController = navController)
        }

        composable(route = Screens.PlaceDetailsScreen.route+"/{placeId}") {
            val placeId = it.arguments?.getString("placeId")
            PlaceDetailsScreen(navController = navController,placeId)
        }

    }


}