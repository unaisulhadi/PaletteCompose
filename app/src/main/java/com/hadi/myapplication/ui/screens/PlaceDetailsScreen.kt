package com.hadi.myapplication.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.hadi.myapplication.data.local.placeData
import com.hadi.myapplication.data.model.Place

@Composable
fun PlaceDetailsScreen(navController: NavController, placeId: String?) {

    var place by remember {
        mutableStateOf<Place?>(null)
    }

    Log.d("PLACE_IDD",placeId ?: "NULL")

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        place = placeData.first { it.placeId == placeId?.toInt() }
        Toast.makeText(context, "${place?.placeTitle}", Toast.LENGTH_SHORT).show()
    }

    val painter = rememberImagePainter(data = place?.imageUrl)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {

        place?.let { selectedPlace ->
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = selectedPlace.placeTitle
            )




        }


    }


}