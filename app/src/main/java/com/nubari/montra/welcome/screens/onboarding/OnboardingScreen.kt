package com.nubari.montra.welcome.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnBoardingScreen(page: Page) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 35.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(page.image),
            contentDescription = null,
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = page.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
            fontWeight = FontWeight.Bold,

        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = page.description,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(20.dp))


    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen(onBoardingPages[0])
}