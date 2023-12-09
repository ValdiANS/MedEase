package com.myapplication.medease.ui.screens.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.myapplication.medease.R
import com.myapplication.medease.data.local.preference.UserPreferences
import com.myapplication.medease.data.local.preference.dataStore
import com.myapplication.medease.data.repository.AuthenticationRepository
import com.myapplication.medease.ui.components.CustomButton
import com.myapplication.medease.ui.components.CustomOutlinedTextField
import com.myapplication.medease.ui.components.CustomOutlinedTextFieldButton
import com.myapplication.medease.ui.components.ErrorCard
import com.myapplication.medease.ui.theme.ColorNeutral
import com.myapplication.medease.ui.theme.ColorTertiary
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily
import com.myapplication.medease.utils.convertMillisToString
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterForm(
    viewModel: RegisterFormViewModel,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val fullNameValue by viewModel.fullNameValue
    val isFullNameError by viewModel.isFullNameError

    val usernameValue by viewModel.usernameValue
    val isUsernameError by viewModel.isUsernameError

    val emailValue by viewModel.emailValue
    val isEmailError by viewModel.isEmailError

    val passwordValue by viewModel.passwordValue
    val isPasswordError by viewModel.isPasswordError
    val isPasswordVisible by viewModel.isPasswordVisible

    val birthdateValue by viewModel.birthdateValue
    val isBirthdateError by viewModel.isBirthdateError
    val isOpenDatePickerDialog by viewModel.isOpenDatePickerDialog

    val isSubmitting by viewModel.isSubmitting
    val isFormValid by viewModel.isFormValid
    val submitErrorMsg by viewModel.submitErrorMsg
    val isSubmitEnabled by viewModel.submitEnabled

    // Birthdate Date Picker
    val datePickerState = rememberDatePickerState()
    val datePickerConfirmEnabled = remember {
        derivedStateOf {
            val isSelectedDateNotNull = datePickerState.selectedDateMillis != null

            if (isSelectedDateNotNull) {
                viewModel.onBirthdateChangeHandler(
                    convertMillisToString(datePickerState.selectedDateMillis!!)
                )
            }

            isSelectedDateNotNull
        }
    }

    if (isOpenDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = viewModel::dismissDatePickerDialog,
            confirmButton = {
                TextButton(
                    onClick = viewModel::dismissDatePickerDialog,
                    enabled = datePickerConfirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = viewModel::dismissDatePickerDialog
                ) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = Color.White,
                    selectedDayContentColor = Color.Black,
                    selectedDayContainerColor = ColorTertiary
                )
            )
        }
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
                value = fullNameValue,
                onValueChange = viewModel::onFullNameChangeHandler,
                labelText = stringResource(R.string.full_name_label),
                placeholderText = stringResource(R.string.full_name_placeholder),
                isError = isFullNameError,
                leadingIconDrawableId = R.drawable.ic_person
            )

            CustomOutlinedTextField(
                value = usernameValue,
                onValueChange = viewModel::onUsernameChangeHandler,
                labelText = stringResource(R.string.username_label),
                placeholderText = stringResource(R.string.username_placeholder),
                isError = isUsernameError,
                leadingIconDrawableId = R.drawable.ic_person
            )

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
                placeholderText = stringResource(R.string.password_register_placeholder),
                isError = isPasswordError,
                keyboardType = KeyboardType.Password,
                leadingIconDrawableId = R.drawable.ic_lock,
                trailingIconDrawableId = if (isPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility,
                trailingIconOnClick = viewModel::onPasswordTrailingIconClickHandler,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            )

            CustomOutlinedTextFieldButton(
                text = birthdateValue,
                labelText = stringResource(R.string.birthdate_label),
                placeholderText = stringResource(R.string.birthdate_placeholder),
                onClick = viewModel::openDatePickerDialog,
                leadingIconDrawableId = R.drawable.ic_date_range,
            )

            if (!isFormValid) {
                ErrorCard(
                    text = submitErrorMsg,
                    leadingIconDrawableId = R.drawable.ic_warning
                )
            }

            CustomButton(
                text = stringResource(R.string.sign_up),
                onClick = viewModel::onSubmitHandler,
                isLoading = isSubmitting,
                enabled = isSubmitEnabled
            )
        }

        RegisterFooter(
            onSignInClick = onSignInClick
        )
    }
}

@Composable
fun RegisterFooter(
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val signInTag = stringResource(R.string.sign_in)

    val annotatedString = buildAnnotatedString {
        append("Have an account? ")

        pushStringAnnotation(
            tag = signInTag,
            annotation = stringResource(R.string.sign_in)
        )
        withStyle(
            style = SpanStyle(
                color = Color.White,
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
            fontSize = 14.sp,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Normal,
            color = ColorNeutral,
            textAlign = TextAlign.Center,
        ),
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = signInTag,
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                onSignInClick()
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}


@Preview(
    showBackground = true, backgroundColor = 0xFF4F56A9
)
@Composable
fun RegisterFormPreview() {
    MedEaseTheme {
        RegisterForm(
            viewModel = RegisterFormViewModel(AuthenticationRepository(UserPreferences.getInstance(
                LocalContext.current.dataStore))),
            onSignInClick = {}
        )
    }
}