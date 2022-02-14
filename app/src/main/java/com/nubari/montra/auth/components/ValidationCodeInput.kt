 package com.nubari.montra.auth.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.nubari.montra.ui.theme.violet100

@ExperimentalComposeUiApi
@Composable
fun ValidationCodeTextField(
    modifier: Modifier,
    codeLength: Int = 6,
    whenFull: (code: String) -> Unit
) {
    val enteredNumbers = remember {
        mutableStateListOf(
            *((0 until codeLength).map { "" }.toTypedArray())
        )
    }
    val focusRequesters: List<FocusRequester> = remember {
        (0 until codeLength).map { FocusRequester() }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Row(modifier = modifier) {
        (0 until codeLength).forEach { index ->
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .size(50.dp, 80.dp)
                    .onKeyEvent { event ->
                        val cellValue = enteredNumbers[index]
                        if (event.type == KeyEventType.KeyUp) {
                            if (event.key == Key.Backspace && cellValue == "") {
                                // we move back in this instance
                                // we request focus for previous input
                                if (index != 0) {
                                    focusRequesters
                                        .getOrNull(index - 1)
                                        ?.requestFocus()
                                    enteredNumbers[index - 1] = ""
                                }
                            } else if (cellValue != "") {
                                // in this scenario we want to move forward
                                // we request focus for next input
                                focusRequesters
                                    .getOrNull(index + 1)
                                    ?.requestFocus()
                            }
                        }
                        false
                    }
                    .padding(vertical = 2.dp)
                    .focusOrder(focusRequesters[index])
                    .focusRequester(focusRequesters[index]),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedIndicatorColor = violet100,
                    cursorColor = Color.Gray,
                    textColor = violet100
                ),
//                textStyle = smsCodeEnterStyle,
                singleLine = true,
                value = enteredNumbers[index],
                onValueChange = { value: String ->
                    if (value.isDigitsOnly()) {
                        if (value.length > 1) {
                            enteredNumbers[index] = value.last().toString()
                            return@TextField
                        }
                        if (focusRequesters[index].freeFocus()) {
                            enteredNumbers[index] = value
                            if (enteredNumbers[index].isBlank() && index > 0 && index < codeLength) {
                                focusRequesters[index - 1].requestFocus()
                            } else if (index < codeLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            } else if (enteredNumbers.size == codeLength) {
                                whenFull(enteredNumbers.joinToString(separator = ""))
                            }
                        }
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (index < codeLength - 1) {
                        ImeAction.Next
                    } else {
                        ImeAction.Done
                    }
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (enteredNumbers[index] != "") {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }

                    }
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}