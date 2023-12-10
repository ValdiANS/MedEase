package com.myapplication.medease.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
    onLogout: () -> Unit
) {
    ProfileContent(
        username = "bedul",
        email = "bedul@gmail.com",
        password = "password",
        onEdit = {},
        onValueChange = {},
        onLogout = onLogout
    )
}

@Composable
fun ProfileContent(
    username: String,
    email: String,
    password: String,
    modifier: Modifier = Modifier,
    onEdit: () -> Unit,
    onValueChange: () -> Unit,
    onLogout: () -> Unit
) {
    Column {
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
            onEdit = onEdit,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp)
        )
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

@Composable
fun FormProfile(
    username: String,
    email: String,
    password: String,
    modifier: Modifier = Modifier,
    onValueChange: () -> Unit,
    onEdit: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = username,
            onValueChange = { onValueChange() },
            enabled = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "username")},
            trailingIcon = { IconButton(onClick = { onEdit() }) {
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
            modifier = Modifier
                .fillMaxWidth()
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
        onEdit = {},
        onValueChange = {},
    )
}