package com.nubari.montra.budget.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.auth.util.Keyboard
import com.nubari.montra.auth.util.keyboardAsState
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.general.components.input.InputField
import com.nubari.montra.general.components.input.SelectInput
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.transaction.events.TransactionFormEvent
import com.nubari.montra.ui.theme.*
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun BudgetForm(
    navController: NavController,
) {
    val scaffoldState = rememberScaffoldState()
    var shouldRemind by remember {
        mutableStateOf(false)
    }
    var threshold by remember {
        mutableStateOf(0f)
    }
    val isKeyboardOpen by keyboardAsState()
    val shouldExpand = isKeyboardOpen == Keyboard.Opened
    val keyboardController = LocalSoftwareKeyboardController.current
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
                Box(modifier = Modifier.fillMaxHeight(.3f))
            }
            AnimatedVisibility(visible = shouldExpand) {
                Box(modifier = Modifier.fillMaxHeight(.1f))
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
                            text = "₦0",
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
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(20.dp)
                ) {
                    InputField(
                        value = "" ,
                        placeholder = "Amount" ,
                        onFocusChange = {},
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    val optionsArr = listOf("One", "Two", "Three", "Four")
                    SelectInput(
                        modifier = Modifier.fillMaxWidth(),
                        options = optionsArr,
                        placeHolder = "Category",
                        onSelect = {}
                    )
                    Spacer(modifier = Modifier.height(10.dp))
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
                            checked = shouldRemind,
                            onCheckedChange = {
                                shouldRemind = !shouldRemind
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = violet100
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    AnimatedVisibility(visible = shouldRemind) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.4f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val percentage = threshold * 100
                            Text(text = "${percentage.toInt()} %")
                            Slider(
                                value = threshold,
                                onValueChange = { value ->
                                    threshold = value
                                },
                                modifier = Modifier.fillMaxWidth(),
                                steps = 5
                            )
                        }

                    }
                    AnimatedVisibility(visible = !shouldRemind) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.4f)
                        )

                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
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
