package com.nubari.montra.general.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.nubari.montra.R
import com.nubari.montra.ui.theme.green100
import com.nubari.montra.ui.theme.red100

@Composable
fun CustomDialog(
    dismiss: () -> Unit,
    message: String,
    success: Boolean = true
) {
    Dialog(onDismissRequest = {
        dismiss()
    }) {

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10))
                .background(color = Color.White)
                .fillMaxWidth()
                .fillMaxHeight(.5f)
                .padding(top = 10.dp, bottom = 10.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(56.dp)
                        .height(56.dp)
                        .background(
                            color = if (success) {
                                green100
                            } else {
                                red100
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (success) {
                                R.drawable.ic_check
                            } else {
                                R.drawable.ic_error
                            }
                        ),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = message,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}