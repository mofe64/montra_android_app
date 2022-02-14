package com.nubari.montra.general.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nubari.montra.R
import java.util.*

@Composable
fun MonthDropDown(
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val calender = Calendar.getInstance(Locale.getDefault())
    var monthIndex by remember {
        mutableStateOf(calender.get(Calendar.MONTH))

    }
    val months = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )
    Box(
        modifier = modifier
            .background(Color.Transparent)
            .wrapContentSize(Alignment.Center),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = { expanded = true }) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_down_2),
                    contentDescription = "Month dropdown",
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = months[monthIndex],
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500)
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(
                    Color.Transparent
                )
        ) {
            months.forEachIndexed { index, month ->
                DropdownMenuItem(onClick = {
                    monthIndex = index
                    expanded = false
                }) {
                    Text(
                        text = month,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500)
                    )
                }
            }
        }
    }
}