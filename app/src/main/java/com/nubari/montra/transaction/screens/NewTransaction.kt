package com.nubari.montra.transaction.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.R
import com.nubari.montra.general.components.input.InputField
import com.nubari.montra.auth.util.Keyboard
import com.nubari.montra.auth.util.keyboardAsState
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.general.components.input.SelectInput
import com.nubari.montra.transaction.events.TransactionFormEvent
import com.nubari.montra.transaction.viewmodels.NewTransactionViewModel
import com.nubari.montra.ui.theme.*

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
                        .fillMaxSize(.2f)
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Text(
                    text = "How much?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight(600),
                    color = light80
                )
                Text(
                    text = "₦0",
                    fontSize = 50.sp,
                    fontWeight = FontWeight(600),
                    color = light80
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
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
                        options = formState.categories,
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
                }
            }
        }
    }
}