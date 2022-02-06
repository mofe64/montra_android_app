package com.nubari.montra.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nubari.montra.R

@Composable
fun PasswordField(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    hasError: Boolean = false,
    errorMessage: String = "",
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    nextFocusDirection: FocusDirection = FocusDirection.Down
) {
    val touched = remember {
        mutableStateOf(false)
    }
    val passwordVisible = remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = value,
        onValueChange = {
            touched.value = true
            onValueChange(it)
        },
        placeholder = {
            Text(
                text = placeholder, style = TextStyle(
                    textAlign = TextAlign.Center
                )
            )
        },
        singleLine = singleLine,
        isError = hasError,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                if (touched.value) onFocusChange(it)
            },
        visualTransformation = if (passwordVisible.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisible.value = !passwordVisible.value
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_password_reveal),
                    contentDescription = "Toggle Password Visibility"
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        shape = RoundedCornerShape(20),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(nextFocusDirection)
            },
            onDone = {
                focusManager.clearFocus()
            }
        )
    )
    if (hasError) {
        Text(
            text = errorMessage,
            color = Color.Red,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}