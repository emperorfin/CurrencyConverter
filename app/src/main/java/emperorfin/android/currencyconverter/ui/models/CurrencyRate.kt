package emperorfin.android.currencyconverter.ui.models


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 24th November, 2023.
 */


data class CurrencyRate(
    val currencyCode: String,
    val currencyFlag: String,
    val rate: String,
)
