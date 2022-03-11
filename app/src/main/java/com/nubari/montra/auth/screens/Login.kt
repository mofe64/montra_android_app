package com.nubari.montra.auth.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nubari.montra.general.components.input.InputField
import com.nubari.montra.general.components.input.PasswordField
import com.nubari.montra.auth.events.AuthProcessEvent
import com.nubari.montra.auth.events.AuthUIEvent
import com.nubari.montra.auth.events.LoginEvent
import com.nubari.montra.auth.viewmodels.AuthViewModel
import com.nubari.montra.auth.viewmodels.LoginViewModel
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.navigation.destinations.Destination
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun Login(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    authViewModel: AuthViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val formState = loginViewModel.state.value
    val isLoading = authViewModel.state.value.isProcessing
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        authViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AuthProcessEvent.SuccessfulLogin -> {}
                is AuthProcessEvent.FailedLogin -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.errorMessage,
                            actionLabel = "Error",
                        )
                    }
                }
                else -> {}
            }
        }
    }
    com.google.accompanist.insets.ui.Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    Text(
                        text = "Login",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                backIconColor = Color.Black
            )
        },
        )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = 50.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            InputField(
                value = formState.email.text,
                placeholder = "Email",
                onFocusChange = {
                    loginViewModel.createEvent(
                        LoginEvent.FocusChange("email")
                    )
                },
                onValueChange = {
                    loginViewModel.createEvent(
                        LoginEvent.EnteredEmail(it)
                    )
                },
                hasError = !formState.email.isValid,
                errorMessage = formState.email.errorMessage,
                singleLine = true,
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth(),
                imeAction = ImeAction.Next,
                textColor = Color.Black
            )
            Spacer(modifier = Modifier.height(20.dp))
            PasswordField(
                value = formState.password.text,
                placeholder = "Password",
                onFocusChange = {
                    loginViewModel.createEvent(
                        LoginEvent.FocusChange("password")
                    )
                },
                hasError = !formState.password.isValid,
                onValueChange = {
                    loginViewModel.createEvent(
                        LoginEvent.EnteredPassword(it)
                    )
                },
                errorMessage = formState.password.errorMessage,
                imeAction = ImeAction.Done,
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    keyboardController?.hide()
                    val email = formState.email.text
                    val password = formState.email.text
                    authViewModel.createEvent(
                        AuthUIEvent.Login(email = email, password = password)
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
                    Text(text = "Login")
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "Forgot Password?",
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Don't have an account yet? ")
                TextButton(onClick = {
                    navController.navigate(Destination.SignupDestination.route)
                }) {
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colors.primary,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }

    }
}