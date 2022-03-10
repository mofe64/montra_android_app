package com.nubari.montra.transaction.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.R
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.transaction.components.newtransaction.AttachmentBottomModalSheet
import com.nubari.montra.transaction.viewmodels.TransactionDetailViewModel
import com.nubari.montra.ui.theme.green100
import com.nubari.montra.ui.theme.light80
import com.nubari.montra.ui.theme.red100

@ExperimentalMaterialApi
@Composable
fun TransactionDetail(
    navController: NavController,
    transactionDetailViewModel: TransactionDetailViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val state = transactionDetailViewModel.state.value
    val isExpense = state.transaction?.type == TransactionType.EXPENSE
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    Text(
                        text = "Transaction Details",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                backgroundColor = if (isExpense) {
                    red100
                } else {
                    green100
                },
                backIconColor = Color.White
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = "Delete Transaction"
                    )
                }
            }
        }
    ) { scaffoldPaddingValues ->
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {},
            sheetPeekHeight = 0.dp,
            sheetBackgroundColor = light80,
            sheetElevation = 20.dp,
            sheetShape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(scaffoldPaddingValues)
                    .fillMaxSize(),
            ) {
                Text(text = "detail")
            }
        }

    }

}