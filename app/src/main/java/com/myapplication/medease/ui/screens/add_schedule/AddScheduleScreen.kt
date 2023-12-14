package com.myapplication.medease.ui.screens.add_schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.medease.R
import com.myapplication.medease.ui.components.CustomOutlinedTextField
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily
import com.myapplication.medease.utils.getCurrentDateAndTime

@Composable
fun AddScheduleScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            shape = RoundedCornerShape(
                bottomStart = 48.dp,
                bottomEnd = 48.dp,
            ),
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_date_range),
                        contentDescription = null
                    )

                    Text(
                        text = getCurrentDateAndTime(),
                        fontSize = 12.sp,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

                Text(
                    text = stringResource(R.string.schedule_screen_title),
                    fontSize = 20.sp,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 34.dp)
                )
            }
        }

        Column(
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 16.dp)
        ) {
            CustomOutlinedTextField(
                value = "",
                onValueChange = { },
                labelText = stringResource(R.string.medicine_name_label),
                placeholderText = stringResource(R.string.medicine_name_placeholder),
                borderColor = Color.Black,
                trailingIconDrawableId = R.drawable.ic_edit
            )

            Spacer(Modifier.size(16.dp))

            Text(
                text = stringResource(R.string.set_time_title),
                fontSize = 14.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
            )

            Spacer(Modifier.size(16.dp))

            TextField(
                value = "",
                onValueChange = { },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddScheduleScreenPreview() {
    MedEaseTheme {
        AddScheduleScreen()
    }
}