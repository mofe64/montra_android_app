package com.nubari.montra.auth.screens

import android.util.Log
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nubari.montra.auth.components.InputField
import com.nubari.montra.auth.components.PasswordField
import com.nubari.montra.auth.events.AuthProcessEvent
import com.nubari.montra.auth.events.AuthUIEvent
import com.nubari.montra.auth.events.RegistrationEvent
import com.nubari.montra.auth.viewmodels.AuthViewModel
import com.nubari.montra.auth.viewmodels.RegisterViewModel
import com.nubari.montra.general.components.MainAppBar
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.ui.theme.violet100
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun SignUp(
    navController: NavController,
    registerViewModel: RegisterViewModel = viewModel(),
    authViewModel: AuthViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val formState = registerViewModel.state.value
    val isLoading = authViewModel.state.value.isProcessing
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        authViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AuthProcessEvent.SuccessfulRegistration -> {
                    navController.navigate(Destination.VerificationDestination.route)
                }
                is AuthProcessEvent.FailedRegistration -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.errorMessage,
                            actionLabel = "Error",
                        )
                    }
                }
                else -> {

                }
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
                        text = "Sign Up",
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
                .padding(top = 70.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxSize()
        ) {
            InputField(
                value = formState.name.text,
                placeholder = "Name",
                onFocusChange = {
                    registerViewModel.createEvent(
                        RegistrationEvent.FocusChange("name")
                    )
                },
                onValueChange = {
                    registerViewModel.createEvent(
                        RegistrationEvent.EnteredName(it)
                    )
                },
                hasError = !formState.name.isValid,
                errorMessage = formState.name.errorMessage,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            )
            Spacer(modifier = Modifier.height(20.dp))
            InputField(
                value = formState.email.text,
                placeholder = "Email",
                onFocusChange = {
                    registerViewModel.createEvent(
                        RegistrationEvent.FocusChange("email")
                    )
                },
                onValueChange = {
                    registerViewModel.createEvent(
                        RegistrationEvent.EnteredEmail(it)
                    )
                },
                hasError = !formState.email.isValid,
                errorMessage = formState.email.errorMessage,
                singleLine = true,
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth(),
                imeAction = ImeAction.Next
            )
            Spacer(modifier = Modifier.height(20.dp))
            PasswordField(
                value = formState.password.text,
                placeholder = "Password",
                onFocusChange = {
                    registerViewModel.createEvent(
                        RegistrationEvent.FocusChange("password")
                    )
                },
                hasError = !formState.password.isValid,
                onValueChange = {
                    registerViewModel.createEvent(
                        RegistrationEvent.EnteredPassword(it)
                    )
                },
                errorMessage = formState.password.errorMessage,
                imeAction = ImeAction.Done
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = formState.agreedToTerms,
                    onCheckedChange = {
                        registerViewModel.createEvent(
                            RegistrationEvent.ToggleTermsCheckBox
                        )
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colors.primary,
                        uncheckedColor = MaterialTheme.colors.primary
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = buildAnnotatedString {
                        append("By signing up, you agree to the ")
                        withStyle(style = SpanStyle(color = violet100)) {
                            append("Terms of Service and Privacy Policy")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    keyboardController?.hide()
                    val name = formState.name.text
                    val email = formState.email.text
                    val password = formState.email.text
                    Log.i("xyz", "on click")
                    authViewModel.createEvent(
                        AuthUIEvent.Register(name, email, password)
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
                    Text(text = "Sign Up")
                }

            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Already have an account? ")
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Login",
                        color = MaterialTheme.colors.primary,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun PreviewSignUp() {
    SignUp(navController = rememberNavController(), authViewModel = viewModel())
}


