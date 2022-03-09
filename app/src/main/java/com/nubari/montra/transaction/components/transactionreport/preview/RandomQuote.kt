package com.nubari.montra.transaction.components.transactionreport.preview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.ui.theme.violet100

@Composable
fun RandomQuote(
    navController: NavController? = null,
    quote: String,
    author: String
) {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Text(
            text = "\"$quote\"",
            fontSize = 30.sp,
            fontWeight = FontWeight(700),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = " - $author",
            fontSize = 24.sp,
            fontWeight = FontWeight(600),
            color = Color.White
        )
        Spacer(modifier = Modifier.fillMaxHeight(.8f))
        Button(
            onClick = {
                navController?.navigate(Destination.TransactionReport.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = violet100,
            ),
            shape = RoundedCornerShape(20),

            ) {
            Text(
                text = "See full details",
                fontWeight = FontWeight(600),
                fontSize = 18.sp
            )
        }
    }
}