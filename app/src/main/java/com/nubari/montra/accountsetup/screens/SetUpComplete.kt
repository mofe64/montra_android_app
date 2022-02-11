package com.nubari.montra.accountsetup.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nubari.montra.R
import com.nubari.montra.navigation.destinations.PrimaryDestination
import com.nubari.montra.preferences

@Composable
fun AccountSetupComplete(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.success), contentDescription = "success")
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "You're all set",
            fontSize = 24.sp,
            fontWeight = FontWeight(500)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                preferences.hasOnboarded = true
                navController.navigate(PrimaryDestination.Home.rootRoute)
            },
            modifier = Modifier
                .height(50.dp),
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

@Preview(showBackground = true)
@Composable
fun PreviewAccountSetupComplete() {
    AccountSetupComplete(
        navController = rememberNavController()
    )
}