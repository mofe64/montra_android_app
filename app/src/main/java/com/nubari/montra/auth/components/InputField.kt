package com.nubari.montra.auth.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    hasError: Boolean = false,
    errorMessage: String = "",
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    onValueChange: (String) -> Unit,
    icon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val touched = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = value,
        textStyle = textStyle,
        singleLine = singleLine,
        onValueChange = {
            touched.value = true
            onValueChange(it)
        },
        modifier = modifier.onFocusChanged {
            if (touched.value) onFocusChange(it);
        },
        isError = hasError,
        placeholder = {
            Text(
                text = placeholder, style = TextStyle(
                    textAlign = TextAlign.Center
                )
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            errorBorderColor = Color.Red,
            errorLabelColor = Color.Red,
            errorLeadingIconColor = Color.Red
        ),
        leadingIcon = icon,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        shape = RoundedCornerShape(20)
    )
    if (hasError) {
        Text(
            text = errorMessage,
            color = Color.Red,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}