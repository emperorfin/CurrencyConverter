package emperorfin.android.currencyconverter.ui.screens.currencyconversion.uicomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import emperorfin.android.currencyconverter.ui.theme.CurrencyConverterTheme

/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 21st November, 2023.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateTextField(
//    trailingText: String,
    modifier: Modifier = Modifier,
//    readOnly: Boolean,
    value: String,
//    enabled: Boolean,
    onBaseAmountChanged: (newBaseAmount: String) -> Unit
) {
    //rememberable field value even when orientation and configuration changes
    var fieldValue by rememberSaveable { mutableStateOf(value) }

    //input text field
    TextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.length <= 3) onBaseAmountChanged(newValue)
        },
//        modifier = modifier.fillMaxWidth(),
        modifier = modifier.wrapContentWidth(),
        singleLine = true,
//        trailingIcon = {
//            Text(
//                text = trailingText,
//                color = MaterialTheme.colorScheme.secondary,
//                fontWeight = FontWeight.Bold
//            )
//        },
        shape = RoundedCornerShape(6.dp),
        colors = TextFieldDefaults.textFieldColors(
//            textColor = Color.DarkGray,
//            backgroundColor = MaterialTheme.colorScheme.secondary,
            disabledTextColor = Color.DarkGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onSecondary
        ),
        maxLines = 3,
        //keyboard should only show numbers
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Preview
@Composable
private fun RateTextFieldPreview() {
    CurrencyConverterTheme {
        Surface {
            RateTextField(
                value = "1234",
                onBaseAmountChanged = {}
            )
        }
    }
}