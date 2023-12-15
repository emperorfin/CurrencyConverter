package emperorfin.android.currencyconverter.ui.screens.currencyconversion.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import emperorfin.android.currencyconverter.ui.models.currencyconverter.CurrencyConverterUiModel
import emperorfin.android.currencyconverter.ui.utils.Helpers


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 15th December, 2023.
 */


@Composable
fun CurrencyRateItem(
    modifier: Modifier,
    currencyRate: CurrencyConverterUiModel
) {

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Card(
//                            backgroundColor = Color.Red,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
//                            elevation = 8.dp,
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            val base64String = currencyRate.currencySymbolOtherFlag
            if (base64String != null) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    bitmap = Helpers.getFlagImageBitMap(base64String),
                    contentDescription = "Flag",
                    tint = Color.Unspecified
                )
            } else {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Filled.CurrencyExchange,
                    contentDescription = "Flag",
                    tint = Color.Unspecified
                )
            }

            Text(
                text = "${currencyRate.currencySymbolOther}: ${currencyRate.rate}",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}