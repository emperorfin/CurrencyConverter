package emperorfin.android.currencyconverter.ui.screens.currencyconversion.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.ui.theme.CurrencyConverterTheme

/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 21st November, 2023.
 */


@Composable
fun AppName(modifier: Modifier) {
    Spacer(modifier = modifier.height(20.dp))
    Column {
        Text(
            text = stringResource(R.string.currency_text),
//            color = MaterialTheme.colorScheme.onPrimary,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            modifier = modifier.fillMaxWidth()
        )
        Row(modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.converter_text),
//                color = MaterialTheme.colorScheme.onPrimary,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
            Text(
                text = stringResource(R.string.dot_text),
//                color = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
            )
        }
    }
}

@Preview
@Composable
private fun AppNamePreview() {
    CurrencyConverterTheme {
        Surface {
            AppName(
                Modifier.padding(24.dp, 0.dp)
            )
        }
    }
}