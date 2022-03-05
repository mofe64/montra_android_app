package com.nubari.montra.application


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nubari.montra.general.components.app.AnimatedBottomAppBar
import com.nubari.montra.general.components.MultiActionFAB
import com.nubari.montra.navigation.navhosts.NavigationHost

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@Composable
fun Montra() {
    val navController = rememberNavController()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    Scaffold(
        bottomBar = {
            AnimatedBottomAppBar(navController = navController, barState = bottomBarState.value)
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            if (bottomBarState.value) {
                MultiActionFAB(navController = navController)
            }
        }
    ) {
        NavigationHost(
            navController = navController,
            bottomBarState = bottomBarState
        )
    }
}