package com.nubari.montra.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nubari.montra.R
import com.nubari.montra.ui.theme.*

@Composable
fun TransactionTile(
    modifier: Modifier = Modifier,
    colorPair: Pair<Color, Color>,
    icon: Int,
    name: String = "some name"
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(20))
            .background(
                light80
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center

    ) {
        Row() {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .background(color = colorPair.first)
                    .padding(10.dp)

            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "icon",
                    tint = colorPair.second
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Some description",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = light20
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "-123",
                    color = red100,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "10:00 AM",
                    fontWeight = FontWeight(500),
                    fontSize = 13.sp,
                    color = light20
                )
            }
        }
    }
}