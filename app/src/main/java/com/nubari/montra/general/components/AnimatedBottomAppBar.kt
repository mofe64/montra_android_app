package com.nubari.montra.general.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.LocalWindowInsets

@Composable
fun AnimatedBottomAppBar(
    navController: NavController,
    barState: Boolean
) {
    val insets = LocalWindowInsets.current
    val systemNavBarHeight = with(LocalDensity.current) {
        insets.navigationBars.bottom.toDp()
    }
    val defaultAppBarHeight = 56.dp
    val heightValue = systemNavBarHeight + defaultAppBarHeight
    AnimatedVisibility(
        visible = barState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomAppBar(
            modifier = androidx.compose.ui.Modifier
                .height(heightValue)
                .fillMaxWidth(),
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
        ) {
            BottomNavBar(navController = navController)
        }
    }
}