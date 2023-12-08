package com.myapplication.medease.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.medease.R
import com.myapplication.medease.ui.theme.ColorError
import com.myapplication.medease.ui.theme.ColorError40
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily

@Composable
fun ErrorCard(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = ColorError,
    containerColor: Color = ColorError40,
    @DrawableRes leadingIconDrawableId: Int? = null,
) {
    OutlinedCard(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
        border = BorderStroke(width = 1.dp, color = textColor),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            if (leadingIconDrawableId != null) {
                Icon(
                    painter = painterResource(leadingIconDrawableId),
                    contentDescription = null,
                    tint = textColor
                )
            }
            
            Spacer(Modifier.size(4.dp))

            Text(
                text = text,
                fontSize = 14.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                color = textColor,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorCardPreview() {
    MedEaseTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ErrorCard(
                text = "Invalid Password"
            )

            ErrorCard(
                text = "Invalid Password",
                leadingIconDrawableId = R.drawable.ic_warning
            )
        }
    }
}