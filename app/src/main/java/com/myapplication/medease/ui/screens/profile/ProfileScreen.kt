package com.myapplication.medease.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapplication.medease.R
import com.myapplication.medease.ViewModelFactory
import com.myapplication.medease.ui.components.CustomButton
import com.myapplication.medease.ui.components.TopBanner
import com.myapplication.medease.ui.theme.ColorPrimary
import com.myapplication.medease.ui.theme.MedEaseTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val username by viewModel.username
    val email by viewModel.email
    val onEdit by viewModel.onEdit
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    ProfileContent(
        username = username,
        email = email,
        password = "password",
        focusRequester = focusRequester,
        onValueChange = viewModel::onUsernameChanged,
        onLogout = onLogout,
        onEdit = onEdit,
        onSaveChange = {
            viewModel.saveChanged()
            Toast.makeText(context, "changes saved" ,Toast.LENGTH_SHORT).show()
        }
    )
}

@Composable
fun ProfileContent(
    username: String,
    email: String,
    password: String,
    onEdit: Boolean,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onLogout: () -> Unit,
    onSaveChange: () -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        TopBanner {
            Text(
                text = "My Profile",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(
                    top = 64.dp,
                    start = 56.dp,
                    bottom = 32.dp
                )
            )
        }
        FormProfile(
            username = username,
            email = email,
            password = password,
            onValueChange = onValueChange,
            focusRequester = focusRequester,
            onSaveChange = onSaveChange,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp)
        )
        if (onEdit) {
            CustomButton(
                text = stringResource(R.string.save_changes),
                onClick = onSaveChange,
                containerColor = ColorPrimary,
                contentColor = Color.White,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 32.dp
                    )
            )
        } else {
            CustomButton(
                text = stringResource(R.string.logout),
                onClick = onLogout,
                containerColor = ColorPrimary,
                contentColor = Color.White,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 32.dp
                    )
            )
        }
    }
}

@Composable
fun FormProfile(
    username: String,
    email: String,
    password: String,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onSaveChange: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = TextFieldValue(username, selection = TextRange(username.length)),
            onValueChange = { onValueChange(it.text) },
            enabled = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "username")},
            trailingIcon = { IconButton(onClick = {
                focusRequester.requestFocus()
                keyboardController?.show()
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onSaveChange()
                keyboardController?.hide()
            }),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .padding(horizontal = 32.dp)
        )
        TextField(
            value = email,
            onValueChange = {},
            readOnly = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription =  "Email")},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
        TextField(
            value = password,
            onValueChange = {},
            readOnly = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Password")},
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePrev() {
    MedEaseTheme {
        ProfileScreen(
            onLogout = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FormPrev() {
    FormProfile(
        username = "bedul",
        email = "bedul@gmail.com",
        password = "password",
        onValueChange = {},
        focusRequester = FocusRequester(),
        onSaveChange = {}
    )
}