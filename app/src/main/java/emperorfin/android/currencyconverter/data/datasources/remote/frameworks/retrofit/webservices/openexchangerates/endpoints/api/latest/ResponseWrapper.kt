package emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.webservices.openexchangerates.endpoints.api.latest


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 18th December, 2023.
 */


data class ResponseWrapper(
    val disclaimer: String,
    val license: String,
    val timestamp: Long,
    val base: String,
//    val rates: Map<String, Number>,
    val rates: Map<String, Double>,
)
