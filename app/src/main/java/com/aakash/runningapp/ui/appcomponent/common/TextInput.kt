package com.aakash.runningapp.ui.appcomponent.common

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.aakash.runningapp.ui.theme.LocalDimens
import com.aakash.runningapp.ui.theme.error
import com.aakash.runningapp.ui.theme.inputBackground
import com.aakash.runningapp.ui.theme.onPrimary

@Composable
fun NameInput(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector? = null,
    currentValue: String? = null,
    visualTransformation: VisualTransformation? = null,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    isError: Boolean = false,
    errorText: String? = null,
    placeholderText: String? = null,
    isDateField: Boolean? = false,
    onValueChange: (String) -> Unit,
    onDateClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (isDateField == true && onDateClick != null) {
                    Modifier.clickable {
                        Log.d("DatePicker", "DatePicker clicked")
                        onDateClick()
                    }
                } else {
                    Modifier
                }
            )
    ) {

        TextField(
            value = currentValue ?: "",
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester ?: remember { FocusRequester() }),
            leadingIcon = { icon?.let { Icon(imageVector = it, contentDescription = label) } },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = inputBackground,
                focusedContainerColor = inputBackground,
                cursorColor = onPrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                if (isDateField == true) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calendar",
                        tint = onPrimary
                    )
                }
            },
            readOnly = isDateField == true,
            enabled = isDateField != true,
            shape = RoundedCornerShape(LocalDimens.current.paddingRegular),
            singleLine = true,
            placeholder = { placeholderText?.let { Text(text = it) } },
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation ?: VisualTransformation.None,
            isError = isError,
            supportingText = { errorText?.let { Text(text = it, color = error) } },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )
    }

}