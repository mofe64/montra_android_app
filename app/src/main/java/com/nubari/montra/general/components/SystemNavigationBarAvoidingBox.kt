package com.nubari.montra.general.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import com.google.accompanist.insets.LocalWindowInsets

@Composable
fun SystemNavigationBarAvoidingBox(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    content: @Composable () -> Unit
) {
    val insets = LocalWindowInsets.current
    val systemNavBarBottom = with(LocalDensity.current) {
        insets.navigationBars.bottom.toDp()
    }
    Box(
        modifier = modifier
            .padding(
                bottom = systemNavBarBottom
            )
            .background(backgroundColor)
    ) {
        content()
    }
}