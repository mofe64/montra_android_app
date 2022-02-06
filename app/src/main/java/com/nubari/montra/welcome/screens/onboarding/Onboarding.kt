package com.nubari.montra.welcome.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.ui.theme.violet100
import com.nubari.montra.ui.theme.violet40

@ExperimentalPagerApi
@Composable
fun OnBoarding(
    navController: NavController
) {
    val pagerState = rememberPagerState(initialPage = 0)
    Column() {
        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) { index ->
            OnBoardingScreen(page = onBoardingPages[index])
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            activeColor = violet100
        )
        Column(modifier = Modifier.padding(20.dp)) {
            Button(
                onClick = {
                    navController.navigate(route = Destination.SignupDestination.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
                shape = RoundedCornerShape(20)
            ) {
                Text(text = "Sign Up", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = { navController.navigate(route = Destination.LoginDestination.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = violet40,
                    contentColor = violet100
                ),
                shape = RoundedCornerShape(20)
            ) {
                Text(text = "Login", fontSize = 18.sp)
            }
        }
    }
}