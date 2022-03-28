package com.nubari.montra.profile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.R
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.general.components.input.SelectInput
import com.nubari.montra.navigation.destinations.Destination

@ExperimentalMaterialApi
@Composable
fun ExportDataForm(
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
                        text = "Export Data",
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
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(.8f)
                    .padding(top = 20.dp, start = 15.dp, end = 15.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "What data do you want to export ?",
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    SelectInput(
                        options = listOf("All", "Income", "Expense"),
                        onSelect = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "What date range ?",
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    SelectInput(
                        options = listOf("Last 30 days", "Last 120 days", "Last year"),
                        onSelect = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Button(
                onClick = {
                    navController.navigate(
                        route = Destination.ExportDataSuccess.route
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
                Icon(
                    painter = painterResource(id = R.drawable.download),
                    contentDescription = "",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(text = "Export")
            }
        }
    }
}