package com.nubari.montra.profile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.ui.theme.light20
import com.nubari.montra.ui.theme.violet100

@Composable
fun NotificationSettings(
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    Text(
                        text = "Notifications",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                },
                backIconColor = Color.Black,
                backgroundColor = Color.White,
                elevation = 2.dp
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(.7f)
                ) {
                    Text(text = "Budget Alerts")
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Get Notifications when you're about" +
                                " to exceed your budget limits",
                        fontSize = 13.sp,
                        color = light20
                    )
                }
                Switch(
                    checked = true,
                    onCheckedChange = {},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = violet100
                    ),
                )

            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(.7f)
                ) {
                    Text(text = "Subscriptions Alerts")
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Get Notifications when your subs" +
                                " are almost due",
                        fontSize = 13.sp,
                        color = light20
                    )
                }
                Switch(
                    checked = true,
                    onCheckedChange = {},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = violet100
                    ),
                )

            }
        }
    }
}