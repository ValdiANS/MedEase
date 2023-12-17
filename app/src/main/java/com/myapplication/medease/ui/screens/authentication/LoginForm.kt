package com.myapplication.medease.ui.screens.authentication

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.medease.MainActivity
import com.myapplication.medease.R
import com.myapplication.medease.data.local.preference.UserPreferences
import com.myapplication.medease.data.local.preference.dataStore
import com.myapplication.medease.data.remote.retrofit.ApiConfig
import com.myapplication.medease.data.repository.AuthenticationRepository
import com.myapplication.medease.ui.components.CustomButton
import com.myapplication.medease.ui.components.CustomOutlinedTextField
import com.myapplication.medease.ui.components.ErrorCard
import com.myapplication.medease.ui.theme.ColorNeutral
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily

@Composable
fun LoginForm(
    viewModel: LoginFormViewModel,
    onSignUpClick: () -> Unit,
    onSignInAsGuestClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val emailValue by viewModel.emailValue
    val passwordValue by viewModel.passwordValue
    val isEmailError by viewModel.isEmailError
    val isPasswordError by viewModel.isPasswordError
    val isPasswordVisible by viewModel.isPasswordVisible
    val isSubmitting by viewModel.isSubmitting
    val isFormValid by viewModel.isFormValid
    val submitErrorMsg by viewModel.submitErrorMsg
    val isSubmitEnabled by viewModel.submitEnabled

    val submitHandler = {
        viewModel.onSubmitHandler(
            onSuccessSignIn = {
                val mainActivityIntent = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(mainActivityIntent)
            }
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = modifier
        ) {
            CustomOutlinedTextField(
                value = emailValue,
                onValueChange = viewModel::onEmailChangeHandler,
                labelText = stringResource(R.string.email_address_label),
                placeholderText = stringResource(R.string.email_address_placeholder),
                isError = isEmailError,
                keyboardType = KeyboardType.Email,
                leadingIconDrawableId = R.drawable.ic_mail
            )

            CustomOutlinedTextField(
                value = passwordValue,
                onValueChange = viewModel::onPasswordChangeHandler,
                labelText = stringResource(R.string.password_label),
                placeholderText = stringResource(R.string.password_placeholder),
                isError = isPasswordError,
                keyboardType = KeyboardType.Password,
                leadingIconDrawableId = R.drawable.ic_lock,
                trailingIconDrawableId = if (isPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility,
                trailingIconOnClick = viewModel::onPasswordTrailingIconClickHandler,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            )

            if (!isFormValid) {
                ErrorCard(
                    text = submitErrorMsg,
                    leadingIconDrawableId = R.drawable.ic_warning
                )
            }

            CustomButton(
                text = stringResource(R.string.sign_in),
                onClick = submitHandler,
                isLoading = isSubmitting,
                enabled = isSubmitEnabled
            )
        }

        LoginFooter(
            onSignUpClick = onSignUpClick,
            onSignInAsGuestClick = onSignInAsGuestClick
        )
    }
}

@Composable
fun LoginFooter(
    onSignUpClick: () -> Unit,
    onSignInAsGuestClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val signUpTag = stringResource(R.string.sign_up)
    val guestTag = stringResource(R.string.guest)

    val annotatedString = buildAnnotatedString {
        append("Don’t have an account?\n")

        pushStringAnnotation(
            tag = signUpTag,
            annotation = stringResource(R.string.sign_up)
        )
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(R.string.sign_up))
        }
        pop()

        append(" or Sign in as ")

        pushStringAnnotation(
            tag = guestTag,
            annotation = stringResource(R.string.guest)
        )
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(R.string.guest))
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Normal,
            color = ColorNeutral,
            textAlign = TextAlign.Center,
        ),
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = signUpTag,
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                onSignUpClick()
            }

            annotatedString.getStringAnnotations(
                tag = guestTag,
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                onSignInAsGuestClick()
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(
    showBackground = true, backgroundColor = 0xFF4F56A9
)
@Composable
fun LoginFormPreview() {
    MedEaseTheme {
        LoginForm(
            viewModel = LoginFormViewModel(
                AuthenticationRepository(
                    UserPreferences.getInstance(
                        LocalContext.current.dataStore
                    ), ApiConfig.getApiService()
                )
            ),
            onSignUpClick = {},
            onSignInAsGuestClick = {},
        )
    }
}