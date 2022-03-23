package com.nubari.montra.budget.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.auth.util.Keyboard
import com.nubari.montra.auth.util.keyboardAsState
import com.nubari.montra.budget.events.BudgetFormEvent
import com.nubari.montra.budget.events.BudgetProcessEvent
import com.nubari.montra.budget.viewmodels.BudgetFormViewModel
import com.nubari.montra.data.local.models.enums.BudgetType
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.general.components.dialogs.CustomDialog
import com.nubari.montra.general.components.input.InputField
import com.nubari.montra.general.components.input.SelectInput
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun BudgetForm(
    navController: NavController,
    budgetFormViewModel: BudgetFormViewModel = hiltViewModel()
) {
    val state = budgetFormViewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val isKeyboardOpen by keyboardAsState()
    val shouldExpand = isKeyboardOpen == Keyboard.Opened
    val keyboardController = LocalSoftwareKeyboardController.current
    val categoryNames = state.categories.map { it.name }
    var showDialog by remember {
        mutableStateOf(false)
    }
    var dialogMessage by remember {
        mutableStateOf("Something went wrong, please try again later")
    }

    LaunchedEffect(Unit) {
        budgetFormViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is BudgetProcessEvent.BudgetCreationSuccess -> {
                    navController.navigate(
                        Destination.Budget.route
                    )
                }
                is BudgetProcessEvent.BudgetCreationFail -> {
                    showDialog = true
                    dialogMessage = event.message
                }
                else -> {}
            }
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    Text(
                        text = "Create a Budget",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                backgroundColor = violet100
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(violet100)
        ) {
            AnimatedVisibility(visible = !shouldExpand) {
                Box(modifier = Modifier.fillMaxHeight(.1f))
            }
            if (showDialog) {
                CustomDialog(
                    dismiss = { showDialog = false },
                    message = dialogMessage,
                    success = false
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = "How much do you want to spend?",
                        fontSize = 18.sp,
                        color = light80
                    )
                    Text(
                        text = "₦${state.limit.text}",
                        fontSize = 50.sp,
                        fontWeight = FontWeight(600),
                        color = light80
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 35.dp,
                            topEnd = 35.dp
                        )
                    )
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.7f)
                            .padding(20.dp)
                    ) {
                        InputField(
                            value = state.limit.text,
                            placeholder = "Budget Limit",
                            onFocusChange = {},
                            onValueChange = { limit ->
                                budgetFormViewModel.createEvent(
                                    BudgetFormEvent.EnteredAmount(
                                        amount = limit
                                    )
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                            hasError = !state.limit.isValid,
                            errorMessage = state.limit.errorMessage
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        AnimatedVisibility(visible = state.budgetType == BudgetType.CATEGORY) {
                            SelectInput(
                                modifier = Modifier.fillMaxWidth(),
                                value = state.category.text,
                                options = categoryNames,
                                placeHolder = "Category",
                                onSelect = { category ->
                                    budgetFormViewModel.createEvent(
                                        BudgetFormEvent.ChangedCategory(
                                            category = category
                                        )
                                    )
                                },
                                hasError = !state.category.isValid,
                                errorMessage = state.category.errorMessage
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Receive Alert",
                            color = dark75,
                            fontSize = 16.sp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Repeat an alert when" +
                                        " budget expenditure reaches a threshold",
                                color = light20,
                                fontSize = 13.sp,
                                modifier = Modifier.fillMaxWidth(.7f)
                            )
                            Switch(
                                checked = state.shouldAlert,
                                onCheckedChange = {
                                    budgetFormViewModel.createEvent(
                                        BudgetFormEvent.ToggleReminder
                                    )
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = violet100
                                ),
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        AnimatedVisibility(visible = state.shouldAlert) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(.4f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "${(state.threshold * 100).toInt()} %")
                                Slider(
                                    value = state.threshold,
                                    onValueChange = { value ->
                                        budgetFormViewModel.createEvent(
                                            BudgetFormEvent.SetThreshold(value)
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    steps = 5,
                                    valueRange = 0.2f..1f
                                )
                            }

                        }
                        Text(
                            text = "Create General Budget instead ?",
                            color = dark75,
                            fontSize = 16.sp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "General budgets cut across all your categories",
                                color = light20,
                                fontSize = 13.sp,
                                modifier = Modifier.fillMaxWidth(.7f)
                            )
                            Switch(
                                checked = state.budgetType == BudgetType.GENERAL,
                                onCheckedChange = { checked ->
                                    if (checked) {
                                        budgetFormViewModel.createEvent(
                                            BudgetFormEvent.ChangeBudgetType(
                                                BudgetType.GENERAL
                                            )
                                        )
                                    } else {
                                        budgetFormViewModel.createEvent(
                                            BudgetFormEvent.ChangeBudgetType(
                                                BudgetType.CATEGORY
                                            )
                                        )
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = violet100
                                ),
                            )
                        }
                    }
                    Button(
                        onClick = {
                            keyboardController?.hide()
                            budgetFormViewModel.createEvent(
                                BudgetFormEvent.CreateBudget(
                                    category = state.category.text,
                                    amount = state.limit.text,
                                    shouldRemind = state.shouldAlert,
                                    thresholdRaw = state.threshold,
                                    budgetType = state.budgetType
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(start = 20.dp, end = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White,
                        ),
                        shape = RoundedCornerShape(30),
                    ) {
                        Text(text = "Create")
                    }
                }
            }
        }

    }
}
