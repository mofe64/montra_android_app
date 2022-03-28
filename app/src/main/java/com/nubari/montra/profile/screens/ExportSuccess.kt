package com.nubari.montra.profile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.ui.theme.light
import com.skydoves.landscapist.glide.GlideImage
import com.nubari.montra.R
import com.nubari.montra.navigation.destinations.Destination

@Composable
fun ExportSuccess(
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(top = 60.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxHeight(.8f)
            ) {
                GlideImage(
                    imageModel = R.drawable.exportilustration,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.6f)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Check your email, we sent you the financial report." +
                            " In certain cases, it might take a while, " +
                            "depending on the time period and the volume of activity.",
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = {
                    navController.navigate(
                        route = Destination.Home.route
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 20.dp, end = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White,
                ),
                shape = RoundedCornerShape(30),
            ) {
                Text(text = "Back to home")
            }
        }
    }
}