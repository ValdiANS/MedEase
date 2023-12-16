package com.myapplication.medease.ui.screens.add_schedule

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapplication.medease.R
import com.myapplication.medease.ViewModelFactory
import com.myapplication.medease.ui.components.CustomButton
import com.myapplication.medease.ui.components.CustomOutlinedTextField
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily
import com.myapplication.medease.utils.getCurrentDateAndTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AddScheduleScreen(
    onNavigateBack: () -> Unit,
    medicineName: String = "",
    viewModel: AddScheduleScreenViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    val context = LocalContext.current

    val enteredMedicineName by viewModel.enteredMedicineName
    val enteredTime by viewModel.enteredTime
    val enteredTimeList by viewModel.enteredTimeList

    viewModel.enteredMedicineNameChangeHandler(medicineName)

    val userData = viewModel.getSession().observeAsState()

    val addScheduleClickHandler = {
        viewModel.addScheduleToDatabase(
            context,
            userData.value?.id.toString().ifEmpty { "testUserId" }
        )
        onNavigateBack()
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column {
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
                    value = enteredMedicineName,
                    onValueChange = viewModel::enteredMedicineNameChangeHandler,
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

                enteredTimeList.forEach {
                    TimeInputRow(
                        timeText = it
                    )
                }

                if (enteredTimeList.size < 4) {
                    TimeInputRow(
                        timeText = enteredTime,
                        onSelect = viewModel::setEnteredTime,
                        onAdd = viewModel::addEnteredTimeToList,
                        readOnly = false
                    )
                }
            }
        }

        CustomButton(
            text = "Add Schedule",
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            onClick = addScheduleClickHandler,
            modifier = Modifier.padding(25.dp),
            enabled = enteredMedicineName.isNotEmpty() && enteredTimeList.isNotEmpty()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInputRow(
    timeText: String,
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit = {},
    onAdd: (String) -> Unit = {},
    readOnly: Boolean = true,
) {
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(is24Hour = true)
    val formatter = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_access_time),
            contentDescription = null
        )

        OutlinedButton(
            contentPadding = PaddingValues(0.dp),
            shape = RectangleShape,
            border = BorderStroke(width = 0.dp, color = Color.Transparent),
            onClick = {
                showTimePicker = true
            },
            enabled = !readOnly,
            modifier = Modifier.width(54.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = timeText,
                    fontSize = 13.sp,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 18.sp,
                    color = Color.Black,
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.Black
                )
            }
        }

        if (!readOnly) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                onClick = {
                    if (timeText.isNotEmpty()) {
                        onAdd(timeText)
                    }
                },
                modifier = Modifier.padding(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onCancel = { showTimePicker = false },
            onConfirm = {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                cal.set(Calendar.MINUTE, timePickerState.minute)
                cal.isLenient = false

                onSelect(formatter.format(cal.time))

                showTimePicker = false
            },
        ) {
            TimePicker(
                state = timePickerState,
            )
        }

    }
}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )

                content()

                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddScheduleScreenPreview() {
    MedEaseTheme {
        AddScheduleScreen(
            onNavigateBack = {}
        )
    }
}