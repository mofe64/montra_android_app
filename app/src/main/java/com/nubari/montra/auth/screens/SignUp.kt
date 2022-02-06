package com.nubari.montra.auth.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nubari.montra.auth.components.InputField
import com.nubari.montra.auth.components.PasswordField
import com.nubari.montra.auth.events.RegistrationEvent
import com.nubari.montra.auth.viewmodels.RegisterViewModel
import com.nubari.montra.general.components.MainAppBar

@Composable
fun SignUp(
    navController: NavController,
    registerViewModel: RegisterViewModel = viewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val formState = registerViewModel.state.value
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
                modifier = Modifier.fillMaxWidth()
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
                modifier = Modifier.fillMaxWidth()
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
                errorMessage = formState.password.errorMessage
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUp() {
    SignUp(navController = rememberNavController())
}


