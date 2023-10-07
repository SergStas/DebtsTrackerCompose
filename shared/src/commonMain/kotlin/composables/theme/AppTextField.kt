package composables.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
    ),
) = TextField(
    value = value,
    onValueChange = { onValueChange(it) },
    textStyle = AppTheme.fonts().h2,
    modifier = modifier,
    singleLine = true,
    shape = RoundedCornerShape(100),
    keyboardOptions = keyboardOptions,
    visualTransformation = visualTransformation,
    colors = TextFieldDefaults.textFieldColors(
        textColor = AppTheme.colors().onBackground,
        disabledTextColor = Color.Transparent,
        backgroundColor = AppTheme.colors().background,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
)