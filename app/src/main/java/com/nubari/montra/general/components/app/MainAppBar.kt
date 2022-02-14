package com.nubari.montra.general.components.app


import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import com.nubari.montra.R

@Composable
fun MainAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    backgroundColor: Color = Color.White,
    backIconColor: Color = Color.White,
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            title()
        },
        navigationIcon = {
            Box(modifier = Modifier.width(70.dp)) {
                if (navController.previousBackStackEntry != null) {

                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "Back Button",
                            tint = backIconColor
                        )
                    }

                }
            }
        },
        actions = {
            Row(modifier = Modifier.width(70.dp)) {
                actions()
            }
        },
        backgroundColor = backgroundColor,
        elevation = 0.dp,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyBottom = false,
        )
    )
}