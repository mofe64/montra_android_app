package com.nubari.montra.accountsetup.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.navigation.destinations.PrimaryDestination

@Composable
fun AccountSetupPrompt(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 50.dp, end = 20.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(0.9f)
        ) {
            Text(
                text = "Let's setup your account",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "Accounts help organize income and expenses",
                fontWeight = FontWeight(500),
                fontSize = 18.sp
            )
        }

        Button(
            onClick = {
                navController.navigate(
                    Destination.AccountSetupFormDestination.route
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                disabledBackgroundColor = Color.Gray
            ),
            shape = RoundedCornerShape(20),

            ) {
            Text(text = "Let's go")
        }
    }
}