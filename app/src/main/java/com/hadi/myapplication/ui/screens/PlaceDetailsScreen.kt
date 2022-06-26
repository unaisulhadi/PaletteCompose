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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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

    var parsedColor by remember {
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
                parsedColor = PaletteGenerator.extractColorsFromBitmapToParsedColors(
                    bitmap = bitmap
                )
            }
            imageResult = RequestStatus.Success
        }

        if (result is ErrorResult) {
            imageResult = RequestStatus.Error
        }

    }

    systemUiController.setStatusBarColor(Color(parseColor(parsedColor.darkVibrant)))

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {

        place?.let { selectedPlace ->

            when (imageResult) {

                RequestStatus.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Failure")
                    }
                }
                RequestStatus.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Loading")
                    }

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
                            .background(color = Color(parseColor(parsedColor.darkVibrant)))
                            .padding(all = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                                .background(color = Color(parseColor(parsedColor.muted)),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            Text(
                                modifier = Modifier.weight(1f),
                                text = selectedPlace.placeCategory,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily.Monospace
                            )

                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                                    .background(Color(parseColor(parsedColor.darkVibrant)))
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = selectedPlace.climate,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily.Monospace
                            )


                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                                    .background(Color(parseColor(parsedColor.darkVibrant)))
                            )

                            Text(
                                modifier = Modifier.weight(1f),
                                text = selectedPlace.rating.toString(),
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily.Monospace
                            )

                        }


                        Text(
                            modifier = Modifier.padding(bottom = 12.dp),
                            text = selectedPlace.placeTitle,
                            color = Color(parseColor(parsedColor.onDarkVibrant)),
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Cursive)

                        Text(
                            text = selectedPlace.description,
                            color = Color(parseColor(parsedColor.onDarkVibrant)),
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Monospace
                        )

                    }
                }
            }


        }


    }


}