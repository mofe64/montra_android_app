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

@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    updateValue: (index: Int) -> Unit,
    options: List<String>,
    startingIndex: Int
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var index by remember {
        mutableStateOf(startingIndex)

    }

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
                    text = options[index],
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
            options.forEachIndexed { optionIndex, option ->
                DropdownMenuItem(onClick = {
                    index = optionIndex
                    expanded = false
                    updateValue(optionIndex)
                }) {
                    Text(
                        text = option,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500)
                    )
                }
            }
        }
    }
}