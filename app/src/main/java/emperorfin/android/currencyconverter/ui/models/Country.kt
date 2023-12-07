package emperorfin.android.currencyconverter.ui.models


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 24th November, 2023.
 */


data class Country (
    val isoAlpha3: String,
    val currency: Currency,
    val flag: String
)