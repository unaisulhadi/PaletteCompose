package com.hadi.myapplication.ui.screens

import android.graphics.Color.parseColor
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.hadi.myapplication.data.local.placeData
import com.hadi.myapplication.data.model.Place
import com.hadi.myapplication.utils.RequestStatus
import com.hadi.myapplication.utils.PaletteGenerator

@Composable
fun PlaceDetailsScreen(navController: NavController, placeId: String?) {

    var place by remember {
        mutableStateOf<Place?>(null)
    }

    var colors by remember {
        mutableStateOf<Map<String, String>>(emptyMap())
    }

    place = placeData.first { it.placeId == placeId?.toInt() }
    Log.d("PLACE_IDD", placeId ?: "NULL")


    val context = LocalContext.current

    val painter = rememberImagePainter(data = place?.imageUrl)
    var imageResult by remember {
        mutableStateOf<RequestStatus>(RequestStatus.Loading)
    }

    LaunchedEffect(key1 = painter) {
        imageResult = RequestStatus.Loading
        val result = PaletteGenerator.convertImageUrlToBitmap(
            place?.imageUrl ?: "",
            context
        )
        if (result is SuccessResult) {
            val bitmap = (result.drawable as BitmapDrawable).bitmap
            if(bitmap != null){
                colors = PaletteGenerator.extractColorsFromBitmap(
                    bitmap = bitmap
                )
            }
            imageResult = RequestStatus.Success
        }

        if (result is ErrorResult) {
            imageResult = RequestStatus.Error
        }

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {

        place?.let { selectedPlace ->

            when(imageResult){

                RequestStatus.Error -> {
                    Text(text = "Failure")
                }
                RequestStatus.Loading -> {
                    Text(text = "Loading")
                }
                RequestStatus.Success -> {

                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        painter = painter,
                        contentScale = ContentScale.Crop,
                        contentDescription = selectedPlace.placeTitle
                    )


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(parseColor(colors["darkVibrant"] ?: "#000000")))
                            .padding(all = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(text = selectedPlace.placeTitle)


                    }
                }
            }




        }


    }


}