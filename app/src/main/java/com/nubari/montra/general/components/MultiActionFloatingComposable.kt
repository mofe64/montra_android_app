package com.nubari.montra.general.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nubari.montra.R
import com.nubari.montra.ui.theme.blue100
import com.nubari.montra.ui.theme.green100
import com.nubari.montra.ui.theme.red100

enum class MultiFabState {
    COLLAPSED, EXPANDED
}


@Composable
fun MultiActionFAB(
    modifier: Modifier = Modifier
) {
    val fabState = rememberSaveable {
        mutableStateOf(MultiFabState.COLLAPSED)
    }

    val rotation by animateFloatAsState(
        if (fabState.value == MultiFabState.EXPANDED) {
            45f
        } else {
            0f
        }
    )
    Column(
        modifier = modifier
            .wrapContentSize()
            .background(Color.Transparent)
            .offset(
                y = if (fabState.value == MultiFabState.EXPANDED) {
                    (-55).dp
                } else {
                    0.dp
                }
            ),
        verticalArrangement = Arrangement.Center
    ) {
        if (fabState.value == MultiFabState.EXPANDED) {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center
            ) {
                FloatingActionButton(
                    onClick = {},
                    backgroundColor = blue100,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.transaction_fab_icon),
                        contentDescription = "Transaction",
                        tint = Color.White
                    )
                }
                Row {
                    FloatingActionButton(
                        onClick = {},
                        backgroundColor = green100,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.income),
                            contentDescription = "Income",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(65.dp))
                    FloatingActionButton(
                        onClick = {},
                        backgroundColor = red100
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.expense),
                            contentDescription = "Expenses",
                            tint = Color.White
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                if (fabState.value == MultiFabState.EXPANDED) {
                    fabState.value = MultiFabState.COLLAPSED
                } else {
                    fabState.value = MultiFabState.EXPANDED
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "FAB",
                modifier = Modifier.rotate(rotation)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewMultiActionFAB() {
    MultiActionFAB()
}