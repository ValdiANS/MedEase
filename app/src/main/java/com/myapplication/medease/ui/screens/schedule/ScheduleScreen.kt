package com.myapplication.medease.ui.screens.schedule

import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapplication.medease.R
import com.myapplication.medease.ViewModelFactory
import com.myapplication.medease.data.local.entity.ScheduleEntity
import com.myapplication.medease.data.local.entity.ScheduleWithTime
import com.myapplication.medease.ui.common.UiState
import com.myapplication.medease.ui.components.ScheduleItem
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily
import com.myapplication.medease.utils.getCurrentDateAndTime

@Composable
fun ScheduleScreen(
    onNavigateToAddSchedule: () -> Unit,
    viewModel: ScheduleScreenViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(
                context,
                "Notifications permission granted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        // permission granted
    } else {
        SideEffect {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    viewModel.listScheduleState.collectAsState().value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                ScheduleContent(
                    scheduleList = emptyList(),
                    onDeleteSchedule = {
                        viewModel.deleteSchedule(context, it)
                    },
                    onAddClick = onNavigateToAddSchedule,
                    isLoading = true,
                )

                viewModel.getAllSchedule()
            }

            is UiState.Success -> {
                ScheduleContent(
                    scheduleList = uiState.data,
                    onDeleteSchedule = {
                        viewModel.deleteSchedule(context, it)
                    },
                    onAddClick = onNavigateToAddSchedule,
                    isLoading = false,
                )
            }

            is UiState.Error -> {

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduleContent(
    scheduleList: List<ScheduleWithTime>,
    onDeleteSchedule: (schedule: ScheduleEntity) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.tertiary,
                onClick = onAddClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(
                        bottomStart = 48.dp,
                        bottomEnd = 48.dp,
                    ),
                    colors = CardDefaults.cardColors(
                        MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(10f)
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
            }

            if (isLoading) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            if (!isLoading && scheduleList.isEmpty()) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "There is no schedule...")
                    }
                }
            }

            items(scheduleList, key = { it.schedule.scheduleId }) { item: ScheduleWithTime ->
                ScheduleItem(
                    title = item.schedule.medicineName,
                    dose = "${item.schedule.dose} Pills ${item.schedule.frequency} / day",
                    hourList = item.times.map {
                        it.time
                    },
                    onSwipe = {
                        onDeleteSchedule(item.schedule)
                    },
                    modifier = Modifier
                        .animateItemPlacement(tween(durationMillis = 250))
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleScreenPreview() {
    MedEaseTheme {
        ScheduleScreen(
            onNavigateToAddSchedule = {}
        )
    }
}