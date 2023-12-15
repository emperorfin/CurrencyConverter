package emperorfin.android.currencyconverter.ui.screens.currencyconversion.uicomponents

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.ui.theme.CurrencyConverterTheme


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 15th December, 2023.
 */


@Composable
fun EmptyContent(
    @StringRes noCurrenciesLabel: Int,
    @DrawableRes noCurrenciesIconRes: Int,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = noCurrenciesIconRes),
            contentDescription = stringResource(R.string.no_currencies_image_content_description),
            modifier = Modifier
                .size(46.dp)
                .clickable { onRefresh() }
        )
        Text(stringResource(id = noCurrenciesLabel))
    }
}

@Preview
@Composable
private fun EmptyContentPreview() {
    CurrencyConverterTheme {
        Surface {
            EmptyContent(
                noCurrenciesLabel = R.string.no_currencies,
                noCurrenciesIconRes = R.drawable.logo_no_fill,
                onRefresh = {}
            )
        }
    }
}