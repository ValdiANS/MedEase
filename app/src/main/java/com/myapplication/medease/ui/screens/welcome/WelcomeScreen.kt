package com.myapplication.medease.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.medease.R
import com.myapplication.medease.ui.theme.ColorNeutral
import com.myapplication.medease.ui.theme.ColorPrimary
import com.myapplication.medease.ui.theme.ColorPrimary40
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onSignIn: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(ColorNeutral)
            .padding(
                top = 80.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome_image),
            contentDescription = "logo",
            modifier = Modifier.size(60.dp)
        )
        Text(
            text = "Welcome to the MedEase",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = montserratFamily
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        Text(
            text = "Your intelligent AI healthcare solutions for give information of medicine",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.ExtraLight,
                color = ColorPrimary40,
                fontFamily = montserratFamily
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Image(
            painter = painterResource(id = R.drawable.welcome_image),
            contentDescription = "logo",
            modifier = Modifier
                .padding(vertical = 48.dp)
                .size(300.dp)
        )
        WelcomeFooter {
            onSignIn()
        }
    }
}

@Composable
fun WelcomeFooter(
    modifier: Modifier = Modifier,
    onSignIn: () -> Unit
) {
    val signInTag = stringResource(id = R.string.sign_in)
    val annotatedString = buildAnnotatedString {
        append("Already have an account? ")

        pushStringAnnotation(
            tag = signInTag,
            annotation = stringResource(R.string.sign_in)
        )
        withStyle(
            style = SpanStyle(
                color = ColorPrimary,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(R.string.sign_in))
        }
        pop()
    }
    ClickableText(
        text = annotatedString,
        style = TextStyle(
            fontSize = 12.sp,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Light,
            color = ColorPrimary40,
            textAlign = TextAlign.Center,
        ),
        onClick = { onSignIn() },
        modifier = Modifier.fillMaxWidth()
    )}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    MedEaseTheme {
        WelcomeScreen {

        }
    }
}