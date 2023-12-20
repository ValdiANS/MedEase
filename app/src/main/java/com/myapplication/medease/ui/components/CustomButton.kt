package com.myapplication.medease.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.medease.R
import com.myapplication.medease.ui.theme.ColorPrimary
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 18.sp,
    textAlign: TextAlign = TextAlign.Center,
    shape: Shape = RoundedCornerShape(20.dp),
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
    containerColor: Color = Color.White,
    contentColor: Color = ColorPrimary,
    @DrawableRes leadingIconDrawableId: Int? = null,
    @DrawableRes trailingIconDrawableId: Int? = null,
    isLoading: Boolean = false,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = shape,
        contentPadding = contentPadding,
        enabled = enabled,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (leadingIconDrawableId != null) {
                    Icon(
                        painter = painterResource(leadingIconDrawableId),
                        contentDescription = null
                    )
                }

                Text(
                    text = text,
                    fontSize = fontSize,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = textAlign,
                    modifier = Modifier.weight(1f)
                )

                if (trailingIconDrawableId != null) {
                    Icon(
                        painter = painterResource(trailingIconDrawableId),
                        contentDescription = null
                    )
                }
            }

            if (isLoading) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            color = containerColor,
                            shape = shape
                        )
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    CircularProgressIndicator(
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CustomButtonPreview() {
    MedEaseTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomButton(
                text = stringResource(R.string.sign_in),
                onClick = {},
                isLoading = false
            )

            CustomButton(
                text = stringResource(R.string.sign_in),
                onClick = {},
                isLoading = true
            )
        }
    }
}