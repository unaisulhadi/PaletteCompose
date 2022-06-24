package com.hadi.myapplication.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter
import com.hadi.myapplication.data.local.placeData
import com.hadi.myapplication.data.model.Place
import com.hadi.myapplication.navigation.Screens

@Composable
fun PlacesListScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 12.dp)
                .align(Alignment.CenterHorizontally),
            text = "Places",
            style = TextStyle(
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive
            ),
            textAlign = TextAlign.Center
        )


        placeData.forEach { place ->
            PlaceItem(place = place) {
                navController.navigate(Screens.PlaceDetailsScreen.route+"/${place.placeId}")
            }
        }

    }

}

@Composable
fun PlaceItem(place: Place,onItemClick : (Int) -> Unit) {

    val painter = rememberImagePainter(data = place.imageUrl)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .background(
                shape = RoundedCornerShape(size = 8.dp),
                color = Color.White
            )
            .clickable {
                onItemClick(place.placeId)
            },
        contentAlignment = Alignment.BottomCenter
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(shape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp)),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = place.placeTitle,
            style = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive
            )
        )

    }


}