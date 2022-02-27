package com.nubari.montra.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nubari.montra.R
import com.nubari.montra.ui.theme.violet100
import com.nubari.montra.ui.theme.violet40

@Composable
fun AttachmentBottomModalSheet() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .height(100.dp)
                    .weight(1f)
                    .background(violet40),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = "Camera",
                            tint = violet100
                        )
                        Text(
                            text = "Camera",
                            color = violet100
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .height(100.dp)
                    .weight(1f)
                    .background(violet40),
                contentAlignment = Alignment.Center

            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.gallery),
                            contentDescription = "Gallery",
                            tint = violet100
                        )
                        Text(
                            text = "Gallery",
                            color = violet100
                        )
                    }

                }
            }

        }
    }
}