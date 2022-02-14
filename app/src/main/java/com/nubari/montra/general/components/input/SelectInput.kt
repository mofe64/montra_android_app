package com.nubari.montra.general.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.nubari.montra.R
import com.nubari.montra.transaction.events.TransactionFormEvent
import com.nubari.montra.ui.theme.grayBorderColor
import com.nubari.montra.ui.theme.red100
import com.nubari.montra.ui.theme.violet100
import com.nubari.montra.ui.theme.yellow100

@ExperimentalMaterialApi
@Composable
fun SelectInput(
    modifier: Modifier = Modifier,
    value: String = "",
    placeHolder: String = "",
    options: List<String>,
    onSelect: (String) -> Unit,
    textColor: Color = Color.Black
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier
    ) {

        OutlinedTextField(
            readOnly = true,
            value = value,
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorLeadingIconColor = Color.Red,
                textColor = textColor,
                focusedBorderColor = violet100,
                unfocusedBorderColor = grayBorderColor,
            ),
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeHolder,
                    style = TextStyle(
                        color = textColor
                    )
                )
            },
            shape = RoundedCornerShape(20)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        onSelect(selectionOption)
                        expanded = false
                    },
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}