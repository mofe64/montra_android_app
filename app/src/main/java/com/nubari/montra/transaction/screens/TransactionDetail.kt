package com.nubari.montra.transaction.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.R
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.general.components.dialogs.CustomDialog
import com.nubari.montra.transaction.components.transactiondetail.ConfirmationModal
import com.nubari.montra.transaction.events.TransactionDetailEvent
import com.nubari.montra.transaction.events.TransactionProcessEvent
import com.nubari.montra.transaction.viewmodels.TransactionDetailViewModel
import com.nubari.montra.ui.theme.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

@ExperimentalMaterialApi
@Composable
fun TransactionDetail(
    navController: NavController,
    transactionDetailViewModel: TransactionDetailViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val state = transactionDetailViewModel.state.value
    val isExpense = state.transaction?.type == TransactionType.EXPENSE
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember {
        mutableStateOf(false)
    }

    val dismissModal = fun() {
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }
    }
    val deleteTx = fun() {
        state.transaction?.let {
            transactionDetailViewModel.createEvent(
                TransactionDetailEvent.DeleteTransaction(tx = it)
            )
        }
    }


    LaunchedEffect(Unit) {
        transactionDetailViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is TransactionProcessEvent.TransactionDeleteSuccess -> {
                    showDialog = true
                }
                else -> {}
            }
        }
    }
    val scrim by animateColorAsState(
        targetValue =
        if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
            light20
        } else {
            Color.White
        },
        animationSpec = tween(500)
    )
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                dismissModal()
            })
        },
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    Text(
                        text = "Transaction Details",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                backgroundColor = if (isExpense) {
                    red100
                } else {
                    green100
                },
                backIconColor = Color.White
            ) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = "Delete Transaction",
                        tint = Color.White
                    )
                }
            }
        }
    ) { scaffoldPaddingValues ->
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                ConfirmationModal(
                    dismiss = dismissModal,
                    action = deleteTx,
                    title = "Delete this transaction ?",
                    subtitle = "Are you sure you want to delete this transaction ?"
                )
            },
            sheetPeekHeight = 0.dp,
            sheetBackgroundColor = Color.White,
            sheetElevation = 20.dp,
            sheetShape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(scaffoldPaddingValues)
                    .fillMaxSize()
                    .background(color = scrim),
            ) {
                if (showDialog) {
                    CustomDialog(
                        dismiss = {
                            showDialog = false
                            //TODO update this to use navigate.up
                            /**
                             * This is a work around to ensure that we force
                             * the previous screen to reload its data in the
                             * view model
                             * This work around was neccessary for the home screen
                             * since it's list of tx does not use a flow and as such
                             * is not updated in real time
                             * This will force the view model to be re instantiated
                             * when the home screen composable is re rendered
                             * TODO replace the home screen tx list with a flow
                             * */
                            val previousRoute =
                                navController.previousBackStackEntry?.destination?.route
                            previousRoute?.let {
                                navController.navigate(it)
                            }
                        },
                        message = "Transaction deleted successfully"
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.2f)
                        .background(
                            color = if (isExpense) {
                                red100
                            } else {
                                green100
                            },
                        ),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val amount = state.transaction?.amount?.toPlainString() ?: "--"
                        Text(
                            text = "₦$amount",
                            fontSize = 48.sp,
                            fontWeight = FontWeight(700),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        val dateFormat = DateFormat.getDateTimeInstance()
                        // Todo set time zone
                        val date = state.transaction?.date ?: Date()
                        val dateString = dateFormat.format(date)
                        Text(
                            text = dateString,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = scrim),
                        shape = RoundedCornerShape(20),
                        elevation = 10.dp,
                        backgroundColor = scrim
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Type",
                                    color = light20,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = state.transaction?.type.toString(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = dark75
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Category",
                                    color = light20,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = state.transaction?.categoryName ?: "--",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = dark75
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.7f)
                    ) {
                        Text(
                            text = "Description",
                            fontSize = 16.sp,
                            color = light20
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = state.transaction?.description ?: "--",
                            fontSize = 20.sp,
                            color = Color.Black,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Recurring Transaction ?",
                            fontSize = 16.sp,
                            color = light20
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = state.transaction?.isRecurring.toString(),
                            fontSize = 20.sp,
                            color = Color.Black,
                        )
                        state.transaction?.attachmentRemote?.let {
                            //TODO handle image attachment display
                            if (it.isNotEmpty()) {
                                Text(
                                    text = "Attachment",
                                    fontSize = 16.sp,
                                    color = light20
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "No Attachment")
                            }
                        }
                    }
                    Button(
                        onClick = {
                            showDialog = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White,
                            disabledBackgroundColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(20),

                        ) {
                        Text(text = "Edit")
                    }
                }
            }
        }
    }
}