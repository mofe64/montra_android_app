package com.nubari.montra.transaction.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.R
import com.nubari.montra.auth.events.AuthUIEvent
import com.nubari.montra.general.components.input.InputField
import com.nubari.montra.auth.util.Keyboard
import com.nubari.montra.auth.util.keyboardAsState
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.general.components.input.SelectInput
import com.nubari.montra.navigation.destinations.PrimaryDestination
import com.nubari.montra.preferences
import com.nubari.montra.transaction.events.TransactionFormEvent
import com.nubari.montra.transaction.events.TransactionProcessEvent
import com.nubari.montra.transaction.viewmodels.NewTransactionViewModel
import com.nubari.montra.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun NewTransaction(
    navController: NavController,
    isExpense: Boolean = true,
    newTransactionViewModel: NewTransactionViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val isKeyboardOpen by keyboardAsState()
    val shouldExpand = isKeyboardOpen == Keyboard.Opened
    val formState = newTransactionViewModel.state.value
    val keyboardController = LocalSoftwareKeyboardController.current
    val isLoading: Boolean = formState.isProcessing
    val categoryOptions = formState.categories?.map { category ->
        category.second
    } ?: emptyList()

    LaunchedEffect(Unit) {
        newTransactionViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is TransactionProcessEvent.TransactionCreationSuccess -> {
                    Log.i("account", "tx created success")
                    navController.navigate(
                        PrimaryDestination.Home.startRoute
                    )
                }
                is TransactionProcessEvent.TransactionCreationFail -> {}
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
                        text = if (isExpense) {
                            "Expense"
                        } else {
                            "Income"
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                backgroundColor = if (isExpense) {
                    red100
                } else {
                    green100
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(
                    if (isExpense) {
                        red100
                    } else {
                        green100
                    }
                ),
        ) {
            AnimatedVisibility(visible = !shouldExpand) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.2f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                    ) {
                        Text(
                            text = "How much?",
                            fontSize = 18.sp,
                            fontWeight = FontWeight(600),
                            color = light80
                        )
                        Text(
                            text = if (formState.amount.text.isEmpty()) {
                                "₦0"
                            } else {
                                "₦${formState.amount.text}"
                            },
                            fontSize = 50.sp,
                            fontWeight = FontWeight(600),
                            color = light80
                        )
                    }
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
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(20.dp)
                ) {
                    SelectInput(
                        modifier = Modifier.fillMaxWidth(),
                        placeHolder = "Category",
                        value = formState.category.text,
                        options = categoryOptions,
                        onSelect = { category ->
                            newTransactionViewModel.createEvent(
                                TransactionFormEvent.ChangedCategory(
                                    category = category
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    InputField(
                        value = formState.description.text,
                        placeholder = "Description",
                        onFocusChange = {},
                        onValueChange = { value ->
                            newTransactionViewModel.createEvent(
                                TransactionFormEvent.EnteredDescription(
                                    value = value
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    InputField(
                        value = formState.amount.text,
                        placeholder = "Amount",
                        onFocusChange = {
                            newTransactionViewModel.createEvent(
                                TransactionFormEvent.FocusChange(
                                    focusFieldName = "amount"
                                )
                            )
                        },
                        onValueChange = { amount ->
                            newTransactionViewModel.createEvent(
                                TransactionFormEvent.EnteredAmount(
                                    amount = amount
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextButton(
                        onClick = {
                            keyboardController?.hide()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = light20,
                        ),
                        shape = RoundedCornerShape(20),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_attach_file),
                            contentDescription = "Add Attachment",
                            tint = light20
                        )
                        Text(
                            text = "Add Attachment",
                            fontSize = 16.sp,
                            color = light20
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Repeat",
                                color = dark25,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Repeat Transaction",
                                color = light20,
                                fontSize = 13.sp
                            )
                        }
                        Switch(
                            checked = formState.isRecurring,
                            onCheckedChange = {
                                newTransactionViewModel.createEvent(
                                    TransactionFormEvent.ToggledRepeatTransaction
                                )
                            }
                        )
                    }
                    AnimatedVisibility(visible = !shouldExpand) {
                        Box(modifier = Modifier.height(30.dp))
                    }
                    Button(
                        onClick = {
                            keyboardController?.hide()
                            newTransactionViewModel.createEvent(
                                TransactionFormEvent.CreateTransaction(
                                    categoryName = formState.category.text,
                                    description = "",
                                    amount = formState.amount.text,
                                    type = if (isExpense) {
                                        TransactionType.EXPENSE
                                    } else {
                                        TransactionType.INCOME
                                    }
                                )
                            )
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
                        enabled = formState.formValid

                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(text = "Create")
                        }
                    }
                }
            }
        }
    }
}