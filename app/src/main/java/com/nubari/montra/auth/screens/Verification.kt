package com.nubari.montra.auth.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nubari.montra.auth.components.ValidationCodeTextField
import com.nubari.montra.auth.util.Keyboard
import com.nubari.montra.auth.util.keyboardAsState
import com.nubari.montra.auth.viewmodels.AuthViewModel
import com.nubari.montra.data.models.UserDetails
import com.nubari.montra.general.components.app.MainAppBar

@ExperimentalComposeUiApi
@Composable
fun Verification(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val isKeyboardOpen by keyboardAsState()
    val code = remember {
        mutableStateOf("")
    }
    val userDetails =
        authViewModel.state.value.userDetails ?: UserDetails("name", "ab@gmail.com", "123")

    com.google.accompanist.insets.ui.Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    Text(
                        text = "Verification",
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
                .padding(20.dp)
        ) {
            AnimatedVisibility(visible = isKeyboardOpen != Keyboard.Opened) {
                Spacer(modifier = Modifier.fillMaxHeight(.3f))
            }

            Text(
                text = "Enter your Verification Code",
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal
            )
            ValidationCodeTextField(
                modifier = Modifier.fillMaxWidth(),
                whenFull = { text ->
                    code.value = text
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "04:59", color = MaterialTheme.colors.primary)
            Spacer(modifier = Modifier.height(10.dp))
            val obscuredUserEmail = obscureUserEmail(userDetails.email)
            Text(text = buildAnnotatedString {
                append("We sent a verification code to your email ")
                withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                    append(
                        obscuredUserEmail
                    )
                }
                append("You can check your inbox")
            })
            Spacer(modifier = Modifier.height(5.dp))
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "I didn't receive the code? Send again",
                    color = MaterialTheme.colors.primary,
                    textDecoration = TextDecoration.Underline
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = { },
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
                Text(text = "Verify")
            }
        }

    }
}

private fun obscureUserEmail(email: String): String {
    val emailParts = email.split("@")
    val emailBeforeAt = emailParts[0]
    val unObscuredPart = emailBeforeAt.substring(0, (emailBeforeAt.length / 2))
    val amountOfCharsToObscure = emailBeforeAt.length / 2
    val builder = StringBuilder()
    builder.append(unObscuredPart)
    (0 until amountOfCharsToObscure).forEach { _ ->
        builder.append("*")
    }
    builder.append("@")
    builder.append(emailParts[1])
    return builder.toString()
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun PreviewVerification() {
    Verification(navController = rememberNavController(), authViewModel = viewModel())
}