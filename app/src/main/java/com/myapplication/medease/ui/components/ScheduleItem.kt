package com.myapplication.medease.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.medease.ui.theme.ColorError
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleItem(
    title: String,
    dose: String,
    hourList: List<String>,
    onSwipe: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dismissState = rememberDismissState()

    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        onSwipe()
    }

    SwipeToDismissBox(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

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
        },
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
            modifier = Modifier
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
                        text = title,
                        fontSize = 20.sp,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 27.sp,
                        color = Color.White,
                    )

                    Text(
                        text = dose,
                        fontSize = 10.sp,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 14.sp,
                        color = Color.White,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    hourList.forEach {
                        Text(
                            text = it,
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
}

@Preview(showBackground = true)
@Composable
fun ScheduleItemPreview() {
    MedEaseTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ScheduleItem(
                title = "Itraconazole 1",
                dose = "1 Pills 3x / day",
                hourList = listOf("07:00", "12:00", "20:00"),
                onSwipe = {}
            )

            ScheduleItem(
                title = "Itraconazole 2",
                dose = "1 Pills 3x / day",
                hourList = listOf("07:00"),
                onSwipe = {}
            )

            ScheduleItem(
                title = "Itraconazole 3",
                dose = "1 Pills 3x / day",
                hourList = listOf("07:00", "12:00", "20:00", "23:00"),
                onSwipe = {}
            )
        }
    }
}