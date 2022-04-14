package com.nubari.montra.budget.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.nubari.montra.budget.events.BudgetDetailEvent
import com.nubari.montra.budget.events.BudgetProcessEvent
import com.nubari.montra.budget.viewmodels.BudgetDetailViewModel
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.general.components.dialogs.CustomDialog
import com.nubari.montra.general.util.Util
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.transaction.components.transactiondetail.ConfirmationModal
import com.nubari.montra.ui.theme.green100
import com.nubari.montra.ui.theme.light20
import com.nubari.montra.ui.theme.light60
import com.nubari.montra.ui.theme.red100
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

@ExperimentalMaterialApi
@Composable
fun BudgetDetail(
    navController: NavController,
    budgetDetailViewModel: BudgetDetailViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val state = budgetDetailViewModel.state.value
    val coroutineScope = rememberCoroutineScope()
    val dismissModal = fun() {
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    val deleteBudget = fun() {
        state.budget?.let {
            budgetDetailViewModel.createEvent(
                BudgetDetailEvent.DeleteBudget(bd = it)
            )
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
    val animationStart = remember {
        Animatable(0f)
    }

    var animationEnd = 0f
    val hasExceeded = state.budget?.exceeded ?: false

    state.budget?.let {
        val budgetSpendPercentage: Float = if (it.exceeded) {
            100f
        } else {
            it.spend
                .divide(it.limit, MathContext(3, RoundingMode.HALF_UP))
                .multiply(BigDecimal.valueOf(100L))
                .toFloat()
        }
        animationEnd = budgetSpendPercentage / 100f
    }


    LaunchedEffect(animationEnd) {
        animationStart.animateTo(
            animationEnd,
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
            )
        )
    }

    LaunchedEffect(Unit) {
        budgetDetailViewModel.eventFLow.collectLatest { event ->
            when (event) {
                is BudgetProcessEvent.BudgetDeleteSuccess -> {
                    showDialog = true
                }
                else -> {}
            }
        }
    }

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
                        text = "Budget Detail",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                },
                backgroundColor = scrim,
                backIconColor = Color.Black
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
                        contentDescription = "Delete Budget",
                        tint = Color.Black
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
                    action = deleteBudget,
                    title = "Remove this budget ?",
                    subtitle = "Are you sure you want to remove this budget ?"
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
            if (showDialog) {
                CustomDialog(
                    dismiss = {
                        showDialog = false
                        navController.navigate(
                            route = Destination.Budget.route
                        )
                    },
                    message = "Budget removed successfully"
                )
            }
            Column(
                modifier = Modifier
                    .padding(scaffoldPaddingValues)
                    .fillMaxSize()
                    .background(color = scrim)
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.8f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.5f)

                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val budgetCategory = state.budget?.categoryName ?: "General"
                            Box {
                                Icon(
                                    painter = painterResource(
                                        id = Util.iconMap[budgetCategory]
                                            ?: R.drawable.rocket_launch
                                    ),
                                    contentDescription = budgetCategory
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = budgetCategory,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val remainder = state.budget?.let {
                            it.limit.minus(it.spend)
                        } ?: BigDecimal.ZERO
                        Text(
                            text = "Remaining",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "₦${remainder.toPlainString()}",
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 50.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp)
                    ) {

                        LinearProgressIndicator(
                            progress = animationStart.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                                .height(10.dp)
                                .clip(RoundedCornerShape(40)),
                            backgroundColor = light60,
                            color = if (hasExceeded) {
                                red100
                            } else {
                                green100
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    if (hasExceeded) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth(.7f)
                                .height(50.dp),
                            color = red100,
                            shape = RoundedCornerShape(40)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_warning),
                                    contentDescription = "Warning icon",
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "You've exceeded the limit",
                                    fontSize = 17.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White,
                    ),
                    shape = RoundedCornerShape(20),

                    ) {
                    Text(text = "Edit")
                }
            }
        }

    }
}