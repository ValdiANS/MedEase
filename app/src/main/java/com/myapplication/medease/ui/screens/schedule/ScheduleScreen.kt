package com.myapplication.medease.ui.screens.schedule

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.myapplication.medease.R
import com.myapplication.medease.ui.theme.ColorError
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily

@Composable
fun ScheduleScreen() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
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
                            text = "Sat, 18 Nov 2023",
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

        items(10) {
            ScheduleItem(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleItem(
    modifier: Modifier = Modifier,
) {
    val dismissState = rememberDismissState()

    if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
        Log.d("test", "dismissed")
    }

    SwipeToDismissBox(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        backgroundContent = {
            Box(modifier = modifier.fillMaxSize()) {

                if (dismissState.targetValue == DismissValue.Default || dismissState.targetValue == DismissValue.DismissedToStart) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f / 1f, true)
                            .align(Alignment.CenterEnd)
                            .background(
                                color = ColorError,
                                shape = RoundedCornerShape(size = 20.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
            modifier = modifier
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Itraconazole",
                        fontSize = 20.sp,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                    )

                    Text(
                        text = "1 Pills 3x / daye",
                        fontSize = 10.sp,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
//                                .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "07:00",
                        fontSize = 11.sp,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 15.sp,
                        color = Color.White
                    )

                    Text(
                        text = "12:00",
                        fontSize = 11.sp,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 15.sp,
                        color = Color.White
                    )

                    Text(
                        text = "20:00",
                        fontSize = 11.sp,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 15.sp,
                        color = Color.White
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleScreenPreview() {
    MedEaseTheme {
        ScheduleScreen()
    }
}