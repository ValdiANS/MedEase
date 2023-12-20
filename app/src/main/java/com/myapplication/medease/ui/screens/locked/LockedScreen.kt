package com.myapplication.medease.ui.screens.locked

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.myapplication.medease.R
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily

@Composable
fun LockedScreen(
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Button(
                onClick = onSignIn,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = stringResource(R.string.sign_in),
                    fontSize = 18.sp,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun LockedScreenPreview() {
    MedEaseTheme {
        LockedScreen(
            onSignIn = {}
        )
    }
}