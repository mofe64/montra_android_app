package com.nubari.montra.accountsetup.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nubari.montra.accountsetup.events.AccountFormEvent
import com.nubari.montra.accountsetup.viewmodels.AccountSetupViewModel
import com.nubari.montra.auth.components.InputField
import com.nubari.montra.auth.util.Keyboard
import com.nubari.montra.auth.util.keyboardAsState
import com.nubari.montra.general.components.MainAppBar
import com.nubari.montra.ui.theme.light80

@ExperimentalComposeUiApi
@Composable
fun SetupAccount(
    navController: NavController,
    viewModel: AccountSetupViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val formState = viewModel.state.value
    val keyboardController = LocalSoftwareKeyboardController.current
    val isKeyboardOpen by keyboardAsState()
    val isLoading = formState.isProcessing
    val shouldExpand = isKeyboardOpen == Keyboard.Opened
    val expanded = remember {
        mutableStateOf(shouldExpand)
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    Text(
                        text = "Add new account",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                backIconColor = Color.White,
                backgroundColor = MaterialTheme.colors.primary
            )
        },
    ) {
        Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Column(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = "Balance",
                        color = light80,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(600)
                    )
                    Text(
                        text = if (formState.initialBalance.text.isEmpty()) {
                            "₦ 0.00"
                        } else {
                            "₦ ${formState.initialBalance.text}"
                        },
                        color = Color.White,
                        fontSize = 45.sp,
                        fontWeight = FontWeight(600),
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 35.dp,
                                topEnd = 35.dp
                            )
                        )
                        .fillMaxWidth()
                        .fillMaxHeight(
                            if (shouldExpand) {
                                .9f
                            } else {
                                .5f
                            }
                        )
                        .background(Color.White)

                        .padding(
                            top = 25.dp,
                            bottom = 15.dp,
                            start = 15.dp,
                            end = 10.dp
                        )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        InputField(
                            value = formState.name.text,
                            placeholder = "Account name Ex.( My main account )",
                            onFocusChange = {
                                viewModel.createEvent(
                                    AccountFormEvent.FocusChange("name")
                                )
                            },
                            onValueChange = {
                                viewModel.createEvent(
                                    AccountFormEvent.EnteredName(it)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            textColor = Color.Black,
                            hasError = !formState.name.isValid,
                            errorMessage = formState.name.errorMessage
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        InputField(
                            value = formState.initialBalance.text,
                            placeholder = "Initial balance Ex.( 1,000,000)",
                            onFocusChange = {
                                viewModel.createEvent(
                                    AccountFormEvent.FocusChange("initialBalance")
                                )
                            },
                            onValueChange = {
                                viewModel.createEvent(
                                    AccountFormEvent.EnteredInitialBalance(it)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                            textColor = Color.Black,
                            hasError = !formState.initialBalance.isValid,
                            errorMessage = formState.initialBalance.errorMessage
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {
                                keyboardController?.hide()
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
}