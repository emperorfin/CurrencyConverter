package emperorfin.android.currencyconverter.ui.screens.currencyconversion.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import emperorfin.android.currencyconverter.ui.theme.CurrencyConverterTheme


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 08th December, 2023.
 */


@Composable
fun LoadingIndicator(
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Preview
@Composable
private fun LoadingIndicatorPreview() {
    CurrencyConverterTheme {
        Surface {
            LoadingIndicator(
                Modifier
            )
        }
    }
}