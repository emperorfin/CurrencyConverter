package emperorfin.android.currencyconverter.ui.models


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 24th November, 2023.
 */


data class RatesResult(
    val success: Boolean? = false,
    val timeSeries: Boolean? = false,
    val startDate: String? = null,
    val endDate: String? = null,
    val base: String? = null,
    val rates: Map<String, Map<String, Double>>? = null,
    val error: Error? = null
)
