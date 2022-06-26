package com.hadi.myapplication.ui.screens

import android.graphics.Color.parseColor
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hadi.myapplication.data.local.placeData
import com.hadi.myapplication.data.model.Place
import com.hadi.myapplication.utils.RequestStatus
import com.hadi.myapplication.utils.PaletteGenerator
import com.hadi.myapplication.utils.ParsedColor

@Composable
fun PlaceDetailsScreen(navController: NavController, placeId: String?) {

    var systemUiController = rememberSystemUiController()

    var place by remember {
        mutableStateOf<Place?>(null)
    }

    var colors by remember {
        mutableStateOf<Map<String, String>>(emptyMap())
    }

    var parsedColor = remember {
        mutableStateOf<ParsedColor>(ParsedColor())
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
            if (bitmap != null) {
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

    systemUiController.setStatusBarColor(Color(parseColor(colors["darkVibrant"] ?: "#000000")))

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {

        place?.let { selectedPlace ->

            when (imageResult) {

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
                            .background(color = Color(parseColor(colors["darkVibrant"]
                                ?: "#000000")))
                            .padding(all = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(horizontal = 12.dp, vertical = 12.dp)
                                    .background(color = Color(parseColor(colors["onDarkVibrant"]
                                        ?: "#000000"))),
                            ) {

                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "2022",
                                    textAlign = TextAlign.Center
                                )

                                Divider(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(4.dp)
                                        .background(Color.Black)
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "120"
                                )


                                Divider(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(4.dp)
                                        .background(Color.Black)
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "EN"
                                )

                                Divider(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(4.dp)
                                        .background(Color.Black)
                                )
                        }


                        Text(
                            text = selectedPlace.placeTitle,
                            color = Color(parseColor(colors["onDarkVibrant"]
                                ?: "#000000")),
                            fontSize = 28.sp)

                        Text(
                            text = selectedPlace.description,
                            color = Color(parseColor(colors["onDarkVibrant"]
                                ?: "#000000")),
                            fontSize = 16.sp,)

                    }
                }
            }


        }


    }


}