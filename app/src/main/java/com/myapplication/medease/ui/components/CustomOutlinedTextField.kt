package com.myapplication.medease.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.medease.R
import com.myapplication.medease.ui.theme.ColorError
import com.myapplication.medease.ui.theme.ColorError40
import com.myapplication.medease.ui.theme.ColorPrimary
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String = "",
    placeholderText: String = "",
    isError: Boolean = false,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    @DrawableRes leadingIconDrawableId: Int? = null,
    @DrawableRes trailingIconDrawableId: Int? = null,
    trailingIconOnClick: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val placeholder: @Composable (() -> Unit)? = {
        Text(
            text = placeholderText,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = montserratFamily
        )
    }

    val leadingIcon: @Composable (() -> Unit)? = if (leadingIconDrawableId != null) {
        {
            Icon(
                painter = painterResource(leadingIconDrawableId),
                contentDescription = null,
            )
        }
    } else {
        null
    }

    val trailingIcon: @Composable (() -> Unit)? = if (trailingIconDrawableId != null) {
        {
            IconButton(
                onClick = trailingIconOnClick
            ) {
                Icon(
                    painter = painterResource(trailingIconDrawableId),
                    contentDescription = null,
                )
            }
        }
    } else {
        null
    }

    val colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.White,
        unfocusedBorderColor = Color.White,
        errorBorderColor = ColorError,

        focusedLabelColor = Color.White,
        unfocusedLabelColor = Color.White,
        errorLabelColor = ColorError40,

        focusedLeadingIconColor = Color.White,
        unfocusedLeadingIconColor = Color.White,
        errorLeadingIconColor = ColorError40,

        focusedTrailingIconColor = Color.White,
        unfocusedTrailingIconColor = Color.White,
        errorTrailingIconColor = ColorError40,

        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        errorTextColor = ColorError40,

        errorPlaceholderColor = ColorError,

        cursorColor = Color.White,
        errorCursorColor = ColorError,

        )

    val labelTextColor = when {
        isError -> ColorError40
        else -> Color.White
    }

    /*
    * LAYOUT
    * */

    Column {
        Text(
            text = labelText,
            color = labelTextColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = montserratFamily,
        )

        Spacer(Modifier.size(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            shape = RoundedCornerShape(20.dp),
            colors = colors,
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            maxLines = maxLines,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.SemiBold,
                color = if (isError) ColorError40 else Color.White,
            )
        )
    }
}

@Composable
fun CustomOutlinedTextFieldButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    labelText: String = "",
    placeholderText: String = "",
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    @DrawableRes leadingIconDrawableId: Int? = null,
    @DrawableRes trailingIconDrawableId: Int? = null,
) {
    Column {
        Text(
            text = labelText,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = montserratFamily,
        )

        Spacer(Modifier.size(8.dp))

        OutlinedButton(
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = modifier
                .fillMaxWidth()
                .heightIn(
                    min = OutlinedTextFieldDefaults.MinHeight,
                )
                .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(20.dp))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = OutlinedTextFieldDefaults.MinHeight,
                    )
            ) {
                if (leadingIconDrawableId != null) {
                    Box(
                        modifier = Modifier
                            .defaultMinSize(48.dp, 48.dp)
                            .padding(top = 2.dp, bottom = 2.dp, end = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(leadingIconDrawableId),
                            contentDescription = null
                        )
                    }
                }

                Text(
                    text = text.ifEmpty { placeholderText },
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = if (text.isNotEmpty()) FontWeight.SemiBold else FontWeight.Normal,
                    fontFamily = montserratFamily,
                    textAlign = textAlign,
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis,
                    modifier = when {
                        leadingIconDrawableId != null && trailingIconDrawableId != null ->
                            Modifier
                                .padding(start = 2.dp, top = 14.dp, bottom = 14.dp, end = 2.dp)
                                .weight(1f)

                        leadingIconDrawableId != null ->
                            Modifier
                                .padding(start = 2.dp, top = 14.dp, bottom = 14.dp, end = 16.dp)
                                .weight(1f)

                        trailingIconDrawableId != null ->
                            Modifier
                                .padding(start = 16.dp, top = 14.dp, bottom = 14.dp, end = 2.dp)
                                .weight(1f)


                        else ->
                            Modifier
                                .padding(16.dp)
                                .weight(1f)
                    }
                )

                if (trailingIconDrawableId != null) {
                    Box(
                        modifier = Modifier
                            .defaultMinSize(48.dp, 48.dp)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(trailingIconDrawableId),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true, backgroundColor = 0xFF4F56A9
)
@Composable
fun CustomOutlinedTextFieldPreview() {
    MedEaseTheme {
        Column {
            CustomOutlinedTextField(
                value = "",
                onValueChange = {},
                labelText = "Outlined Text Field",
                placeholderText = "This is a placeholder"
            )

            Spacer(Modifier.size(16.dp))

            CustomOutlinedTextField(
                value = "Test Value",
                onValueChange = {},
                labelText = "Outlined Text Field",
                placeholderText = "This is a placeholder"
            )

            Spacer(Modifier.size(16.dp))

            CustomOutlinedTextField(
                value = "Test Value",
                onValueChange = {},
                labelText = "Outlined Text Field",
                placeholderText = "This is a placeholder",
                isError = true,
                leadingIconDrawableId = R.drawable.ic_mail
            )

            Spacer(Modifier.size(16.dp))

            CustomOutlinedTextField(
                value = "Test Value",
                onValueChange = {},
                labelText = "Outlined Text Field",
                placeholderText = "This is a placeholder",
                leadingIconDrawableId = R.drawable.ic_mail,
                trailingIconDrawableId = R.drawable.ic_visibility
            )

            Spacer(Modifier.size(16.dp))

            CustomOutlinedTextFieldButton(
                text = "Test Value",
                labelText = "Outlined Button Text Field",
                placeholderText = "Placeholder",
                onClick = {},
            )

            Spacer(Modifier.size(16.dp))

            CustomOutlinedTextFieldButton(
                text = "",
                labelText = "Outlined Button Text Field",
                placeholderText = "Placeholder",
                onClick = {},
            )

            Spacer(Modifier.size(16.dp))

            CustomOutlinedTextFieldButton(
                text = "Test Value",
                labelText = "Outlined Button Text Field",
                placeholderText = "Placeholder",
                onClick = {},
                leadingIconDrawableId = R.drawable.ic_date_range,
            )

            Spacer(Modifier.size(16.dp))

            CustomOutlinedTextFieldButton(
                text = "Test Value",
                labelText = "Outlined Button Text Field",
                placeholderText = "Placeholder",
                onClick = {},
                leadingIconDrawableId = R.drawable.ic_mail,
                trailingIconDrawableId = R.drawable.ic_visibility
            )
        }
    }
}