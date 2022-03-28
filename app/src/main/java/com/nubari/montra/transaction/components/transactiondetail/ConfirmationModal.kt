package com.nubari.montra.transaction.components.transactiondetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nubari.montra.ui.theme.*

@Composable
fun ConfirmationModal(
    dismiss: () -> Unit,
    action: () -> Unit,
    title: String,
    subtitle: String,
    raiseActionButtons: Boolean = false
) {
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStartPercent = 10, topEndPercent = 10))
            .fillMaxHeight(.4f)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(
                        fraction = if (raiseActionButtons) {
                            .4f
                        } else {
                            .5f
                        }
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = subtitle,
                    color = light20,
                    fontSize = 16.sp
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        dismiss()
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = violet20,
                        contentColor = violet100,
                    ),
                    shape = RoundedCornerShape(20),
                ) {
                    Text(text = "No")


                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {
                        action()
                        dismiss()
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = violet100,
                        contentColor = Color.White,
                    ),
                    shape = RoundedCornerShape(20),
                ) {
                    Text(text = "Yes")


                }
            }
        }
    }
}