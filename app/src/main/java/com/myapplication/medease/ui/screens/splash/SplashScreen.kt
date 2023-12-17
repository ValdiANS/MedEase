package com.myapplication.medease.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapplication.medease.R
import com.myapplication.medease.ui.theme.ColorNeutral
import com.myapplication.medease.ui.theme.MedEaseTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(ColorNeutral)
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_image),
            contentDescription = "splash screen",
            modifier = Modifier.size(300.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.logo_transparent),
            contentDescription = "splash screen",
            modifier = Modifier.size(80.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.splash_image),
            contentDescription = "splash screen",
            modifier = Modifier
                .size(300.dp)
                .rotate(180f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPrev() {
    MedEaseTheme {
        SplashScreen(
        )
    }
}