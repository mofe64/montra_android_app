package com.nubari.montra.profile.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.skydoves.landscapist.glide.GlideImage
import com.nubari.montra.R
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.transaction.components.transactiondetail.ConfirmationModal
import com.nubari.montra.ui.theme.*
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun Profile(
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    val dismissModal = fun() {
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            })
        },

        ) { scaffoldPaddingValues ->
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                ConfirmationModal(
                    dismiss = dismissModal,
                    action = { /*TODO*/ },
                    title = "Log out",
                    subtitle = "Are you sure you want to logout ?",
                    raiseActionButtons = true
                )
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(scaffoldPaddingValues)
                    .fillMaxSize()
                    .background(light)
                    .padding(top = 60.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colors.primary
                        ),
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        GlideImage(
                            imageModel = R.drawable.avatar_male,
                            contentScale = ContentScale.Crop,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .clip(CircleShape)
                                .height(70.dp)
                                .width(70.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier
                            .weight(2f)
                    ) {
                        Text(
                            text = "Name",
                            color = light20,
                            fontSize = 15.sp,
                        )

                        Text(
                            text = "Gojo Satoru",
                            color = dark75,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = "Edit Profile"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .clip(
                            RoundedCornerShape(20.dp)
                        )
                        .fillMaxWidth()
                        .fillMaxHeight(.6f)
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clickable {
                                    navController.navigate(
                                        route = Destination.UserAccounts.route
                                    )
                                },
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .background(violet40)
                                        .padding(10.dp)

                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.wallet_3),
                                        contentDescription = "Account icon",
                                        tint = violet100,
                                        modifier = Modifier.defaultMinSize(
                                            minHeight = 30.dp,
                                            minWidth = 30.dp
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.width(15.dp))
                                Text(text = "Account", fontSize = 18.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clickable {
                                    navController.navigate(
                                        route = Destination.AllSettings.route
                                    )
                                },
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .background(violet40)
                                        .padding(10.dp)

                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.settings),
                                        contentDescription = "Settings icon",
                                        tint = violet100,
                                        modifier = Modifier.defaultMinSize(
                                            minHeight = 30.dp,
                                            minWidth = 30.dp
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.width(15.dp))
                                Text(text = "Settings", fontSize = 18.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clickable {
                                    navController.navigate(
                                        route = Destination.ExportDataForm.route
                                    )
                                },
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .background(violet40)
                                        .padding(10.dp)

                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.upload),
                                        contentDescription = "Export data icon",
                                        tint = violet100,
                                        modifier = Modifier.defaultMinSize(
                                            minHeight = 30.dp,
                                            minWidth = 30.dp
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.width(15.dp))
                                Text(text = "Export Data", fontSize = 18.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                            bottomSheetScaffoldState.bottomSheetState.expand()
                                        } else {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
                                    }
                                },
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .background(red20)
                                        .padding(10.dp)

                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.logout),
                                        contentDescription = "Logout icon",
                                        tint = red100,
                                        modifier = Modifier.defaultMinSize(
                                            minHeight = 30.dp,
                                            minWidth = 30.dp
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.width(15.dp))
                                Text(text = "Logout", fontSize = 18.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}