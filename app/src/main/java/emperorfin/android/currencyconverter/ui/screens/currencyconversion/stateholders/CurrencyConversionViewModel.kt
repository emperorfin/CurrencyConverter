package emperorfin.android.currencyconverter.ui.screens.currencyconversion.stateholders

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.AppRoomDatabase
import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModelMapper
import emperorfin.android.currencyconverter.ui.models.CurrencyRate
import emperorfin.android.currencyconverter.ui.models.currencyconverter.CurrencyConverterUiModel
import emperorfin.android.currencyconverter.ui.models.currencyconverter.CurrencyConverterUiModelMapper
import emperorfin.android.currencyconverter.ui.utils.Async
import emperorfin.android.currencyconverter.ui.utils.CurrencyConverterSampleDataGeneratorUtil
import emperorfin.android.currencyconverter.ui.utils.Helpers
import emperorfin.android.currencyconverter.ui.utils.WhileUiSubscribed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 27th November, 2023.
 */


class CurrencyConversionViewModel(application: Application) : AndroidViewModel(application) {

    private companion object {

        const val DELAY_TIME_MILLIS_3000: Long = 3000

    }

    private val applicationContext = getApplication<Application>()

    private val currencyRatesInMemory: Map<String, Number> = mapOf(
        "AED" to 3.6721,
        "AFN" to 69.845466,
        "ALL" to 92.941497,
        "AMD" to 402.788325,
        "ANG" to 1.802745,
        "AOA" to 831.5,
        "ARS" to 361.018315,
        "AUD" to 1.512608,
        "AWG" to 1.8,
        "AZN" to 1.7,
        "BAM" to 1.795292,
        "BBD" to 2,
        "BDT" to 110.280394,
        "BGN" to 1.79726,
        "BHD" to 0.376833,
        "BIF" to 2845.22284,
        "BMD" to 1,
        "BND" to 1.337427,
        "BOB" to 6.912093,
        "BRL" to 4.934,
        "BSD" to 1,
        "BTC" to 0.000026049722,
        "BTN" to 83.300372,
        "BWP" to 13.535829,
        "BYN" to 3.295652,
        "BZD" to 2.01633,
        "CAD" to 1.352816,
        "CDF" to 2723.319593,
        "CHF" to 0.87643,
        "CLF" to 0.03141,
        "CLP" to 866.71,
        "CNH" to 7.152011,
        "CNY" to 7.093,
        "COP" to 4011.89,
        "CRC" to 529.349574,
        "CUC" to 1,
        "CUP" to 25.75,
        "CVE" to 101.09283,
        "CZK" to 22.3916,
        "DJF" to 178.099048,
        "DKK" to 6.859152,
        "DOP" to 56.964521,
        "DZD" to 134.741,
        "EGP" to 30.9037,
        "ERN" to 15,
        "ETB" to 56.201471,
        "EUR" to 0.92014,
        "FJD" to 2.23075,
        "FKP" to 0.79177,
        "GBP" to 0.79177,
        "GEL" to 2.705,
        "GGP" to 0.79177,
        "GHS" to 12.014008,
        "GIP" to 0.79177,
        "GMD" to 67.3,
        "GNF" to 8596.364175,
        "GTQ" to 7.835146,
        "GYD" to 209.279426,
        "HKD" to 7.81335,
        "HNL" to 24.684117,
        "HRK" to 6.932363,
        "HTG" to 132.393383,
        "HUF" to 348.992,
        "IDR" to 15496.312085,
        "ILS" to 3.732344,
        "IMP" to 0.79177,
        "INR" to 83.332049,
        "IQD" to 1310.439597,
        "IRR" to 42250,
        "ISK" to 139.06,
        "JEP" to 0.79177,
        "JMD" to 154.591753,
        "JOD" to 0.7094,
        "JPY" to 148.18825,
        "KES" to 152.8,
        "KGS" to 89.1855,
        "KHR" to 4115.837775,
        "KMF" to 448.624972,
        "KPW" to 900,
        "KRW" to 1306.413695,
        "KWD" to 0.308848,
        "KYD" to 0.833574,
        "KZT" to 459.865718,
        "LAK" to 20712.202204,
        "LBP" to 15035.139273,
        "LKR" to 328.371859,
        "LRD" to 188.149999,
        "LSL" to 18.671752,
        "LYD" to 4.800786,
        "MAD" to 10.07192,
        "MDL" to 17.755727,
        "MGA" to 4546.548899,
        "MKD" to 56.562983,
        "MMK" to 2100.684864,
        "MNT" to 3450,
        "MOP" to 8.050725,
        "MRU" to 39.65,
        "MUR" to 44.195745,
        "MVR" to 15.36,
        "MWK" to 1683.859494,
        "MXN" to 17.302208,
        "MYR" to 4.6735,
        "MZN" to 63.87499,
        "NAD" to 18.83,
        "NGN" to 789.8,
        "NIO" to 36.607136,
        "NOK" to 10.766163,
        "NPR" to 133.280377,
        "NZD" to 1.623532,
        "OMR" to 0.384989,
        "PAB" to 1,
        "PEN" to 3.742261,
        "PGK" to 3.729558,
        "PHP" to 55.390001,
        "PKR" to 281.721938,
        "PLN" to 3.995829,
        "PYG" to 7442.199998,
        "QAR" to 3.64667,
        "RON" to 4.5746,
        "RSD" to 107.627562,
        "RUB" to 90.355011,
        "RWF" to 1243.338712,
        "SAR" to 3.751322,
        "SBD" to 8.482374,
        "SCR" to 13.132739,
        "SDG" to 601,
        "SEK" to 10.467205,
        "SGD" to 1.33784,
        "SHP" to 0.79177,
        "SLL" to 20969.5,
        "SOS" to 571.693227,
        "SRD" to 37.8155,
        "SSP" to 130.26,
        "STD" to 22281.8,
        "STN" to 22.69,
        "SVC" to 8.752484,
        "SYP" to 2512.53,
        "SZL" to 18.665234,
        "THB" to 35.116167,
        "TJS" to 10.937996,
        "TMT" to 3.51,
        "TND" to 3.1095,
        "TOP" to 2.358128,
        "TRY" to 28.912201,
        "TTD" to 6.786336,
        "TWD" to 31.446813,
        "TZS" to 2515,
        "UAH" to 36.479179,
        "UGX" to 3803.935274,
        "USD" to 1,
        "UYU" to 39.113095,
        "UZS" to 12303.978346,
        "VES" to 35.449555,
        "VND" to 24298.387385,
        "VUV" to 118.722,
        "WST" to 2.8,
        "XAF" to 603.572587,
        "XAG" to 0.03964007,
        "XAU" to 0.00049068,
        "XCD" to 2.70255,
        "XDR" to 0.751603,
        "XOF" to 603.572587,
        "XPD" to 0.00099184,
        "XPF" to 109.801966,
        "XPT" to 0.00107779,
        "YER" to 250.29996,
        "ZAR" to 18.701183,
        "ZMW" to 23.75814,
        "ZWL" to 322
    )

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _currencyRatesWithFlags: MutableStateFlow<List<CurrencyRate>> = MutableStateFlow(emptyList())
    private val currencyRatesWithFlags: MutableStateFlow<List<CurrencyRate>> = _currencyRatesWithFlags
    private val _currencyRates: MutableStateFlow<Map<String, Number>?> = MutableStateFlow(emptyMap())
    private val currencyRates: MutableStateFlow<Map<String, Number>?> = _currencyRates

    init {
        // Option 1 (error)
//        simulateLoadingCurrencyRatesErrorOnAppStartup()
        // Option 2 (no data)
//        simulateNoCurrencyRatesOnAppStartup()
        // Option 3 (sample data)
        getInMemoryCurrencyRates()
        // Option 4 (incomplete sample data)
//        generateCurrencyRatesSampleData()
        // Option 5 (db sample data)
//        getDatabaseCurrencyRatesSampleDataWithoutLocalDataSource()

    }

    fun convert(assets: AssetManager = applicationContext.assets, baseAmount: Int, baseCurrencySymbol: String) {

        val mapOfCurrencySymbolsToFlag = Helpers.loadMapOfCurrencySymbolToFlag(assets)

        val currencies: Map<String, Number>? = currencyRates.value

        if (currencies?.isEmpty() == true) {
            _errorMessage.value = null

            return
        } else if (currencies == null) {
            _errorMessage.value = R.string.error_loading_currency_rates

            return
        }

        val currencyRates = mutableListOf<CurrencyRate>()

        currencies.forEach {

            val baseCurrencySymbolValue: Double = currencies[baseCurrencySymbol]?.toDouble()!!

            //if (baseCurrencySymbol != it.key) {
                if (baseCurrencySymbol == "USD") {
                    if(mapOfCurrencySymbolsToFlag.containsKey(it.key)) {

                        val currencyFlag = mapOfCurrencySymbolsToFlag[it.key]

                        val rateValue = it.value

                        var rateString = ""

                        if (rateValue is Double) {
                            val newRate = rateValue * baseAmount
                            val rateToOneDecimalPlace = roundToThreeDecimalPlaces(newRate)

                            rateString = rateToOneDecimalPlace.toString()
                        } else if (rateValue is Float) {
                            val newRate = rateValue * baseAmount
                            val rateToOneDecimalPlace = roundToThreeDecimalPlaces(newRate as Double)

                            rateString = rateToOneDecimalPlace.toString()
                        } else if (rateValue is Int) {
                            val newRate = rateValue * baseAmount
                            rateString = newRate.toString()
                        } else if (rateValue is Long) {
                            val newRate = rateValue * baseAmount
                            rateString = newRate.toString()
                        }

                        val currencySymbolNew: String = if (baseCurrencySymbol == it.key) "_${it.key}" else it.key

                        val currencyRate = CurrencyRate(
                            currencyCode = currencySymbolNew,
                            currencyFlag = currencyFlag!!,
                            rate = rateString
                        )

                        currencyRates.add(currencyRate)
                    }
                } else {

                    if(mapOfCurrencySymbolsToFlag.containsKey(it.key)) {

                        val currencyFlag = mapOfCurrencySymbolsToFlag[it.key]

                        val rateValue = it.value

                        var rateString = ""

                        if (rateValue is Double) {
                            val newRate = (rateValue / baseCurrencySymbolValue) * baseAmount
                            val rateToOneDecimalPlace = roundToThreeDecimalPlaces(newRate)

                            rateString = rateToOneDecimalPlace.toString()
                        } else if (rateValue is Float) {
                            val newRate = (rateValue / baseCurrencySymbolValue) * baseAmount
                            val rateToOneDecimalPlace = roundToThreeDecimalPlaces(newRate as Double)

                            rateString = rateToOneDecimalPlace.toString()
                        } else if (rateValue is Int) {
                            val newRate = (rateValue / baseCurrencySymbolValue) * baseAmount
                            rateString = newRate.toString()
                        } else if (rateValue is Long) {
                            val newRate = (rateValue / baseCurrencySymbolValue) * baseAmount
                            rateString = newRate.toString()
                        }

                        val currencySymbolNew: String = if (baseCurrencySymbol == it.key) "_${it.key}" else it.key

                        val currencyRate = CurrencyRate(
                            currencyCode = currencySymbolNew,
                            currencyFlag = currencyFlag!!,
                            rate = rateString
                        )

                        currencyRates.add(currencyRate)
                    }
                }
            //}
        }

        _currencyRatesWithFlags.value = currencyRates
    }

    private val _currencyRatesWithFlagsAsync = combine(currencyRatesWithFlags) { currencyRates ->
            currencyRates
        }
            .map { Async.Success(it[0]) }
            .catch<Async<List<CurrencyRate>>> { emit(Async.Error(R.string.error_loading_currency_rates)) }

    val uiState: StateFlow<CurrencyConversionUiState> = combine(
        _isLoading, _errorMessage, _currencyRatesWithFlagsAsync
    ) { isLoading, errorMessage, currencyRatesWithFlagsAsync ->
        when (currencyRatesWithFlagsAsync) {
            Async.Loading -> {
                CurrencyConversionUiState(isLoading = true)
            }
            is Async.Error -> {
                CurrencyConversionUiState(errorMessage = currencyRatesWithFlagsAsync.errorMessage)
            }
            is Async.Success<List<CurrencyRate>> -> {

                val currencyRateItems: List<CurrencyRate> = currencyRatesWithFlagsAsync.data
                val currencyRateItemsNew: MutableList<CurrencyRate> = mutableListOf()

                val mapOfCurrencySymbolsToFlag = mutableMapOf<String, String>()

                currencyRateItems.forEach {

                    val currencyCode: String = it.currencyCode

                    val currencyCodeNew: String = if (currencyCode.contains("_")) {
                        currencyCode.substring(1)
                    } else {
                        currencyRateItemsNew.add(it)

                        currencyCode
                    }

                    mapOfCurrencySymbolsToFlag[currencyCodeNew] = it.currencyFlag
                }

                CurrencyConversionUiState(
                    items = currencyRateItemsNew,
                    mapOfCurrencySymbolsToFlag = mapOfCurrencySymbolsToFlag,
                    isLoading = isLoading,
                    errorMessage = errorMessage
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = CurrencyConversionUiState(isLoading = true)
        )

    private fun roundToThreeDecimalPlaces(rateValue: Double): Double {
        return (rateValue * 1000.0).roundToInt() / 1000.0
    }

    private fun simulateLoadingCurrencyRatesErrorOnAppStartup() = viewModelScope.launch {
        _isLoading.value = true

        delay(DELAY_TIME_MILLIS_3000)

        _currencyRates.value = null

        convert(baseAmount = 1, baseCurrencySymbol = "USD")

        _currencyRates.value = currencyRatesInMemory

        _isLoading.value = false
    }
    private fun simulateNoCurrencyRatesOnAppStartup() = viewModelScope.launch {
        _isLoading.value = true

        delay(DELAY_TIME_MILLIS_3000)

        _currencyRates.value = currencyRatesInMemory

        _isLoading.value = false
    }

    private fun getInMemoryCurrencyRates() {
        _isLoading.value = true

        _currencyRates.value = currencyRatesInMemory

        convert(baseAmount = 1, baseCurrencySymbol = "USD")

        _isLoading.value = false
    }

    private fun generateCurrencyRatesSampleData() = viewModelScope.launch {

        _isLoading.value = true

        delay(DELAY_TIME_MILLIS_3000)

        val currencyRates = mutableMapOf<String, Number>()

        val currencyConverterList: List<CurrencyConverterUiModel> =
            CurrencyConverterSampleDataGeneratorUtil.getTransformedCurrencyConverterEntityList()

        currencyConverterList.forEach {
            currencyRates[it.currencySymbolOther] = it.rate
        }

        val baseCurrencySymbolUsd: String = currencyConverterList[0].currencySymbolBase

        currencyRates[baseCurrencySymbolUsd] = currencyConverterList[0].rate

        _currencyRates.value = currencyRates
//        _currencyRates.value = emptyMap()

        convert(baseAmount = 1, baseCurrencySymbol = baseCurrencySymbolUsd)

        _isLoading.value = false
    }

    private fun getDatabaseCurrencyRatesSampleDataWithoutLocalDataSource() = viewModelScope.launch{

        _isLoading.value = true

        val entityCurrencyRates = AppRoomDatabase
            .getInstance(applicationContext)
            .mCurrencyRateDao
            .getCurrencyRates(currencySymbolBase = "USD")

        val currencyConverterModelMapper = CurrencyConverterModelMapper()
        val currencyConverterUiModelMapper = CurrencyConverterUiModelMapper()

        val currencyRates = mutableMapOf<String, Number>()

        val currencyConverterList: List<CurrencyConverterUiModel> = entityCurrencyRates.map {
            currencyConverterModelMapper.transform(it)
        }.map {
            currencyConverterUiModelMapper.transform(it)
        }

        currencyConverterList.forEach {
            currencyRates[it.currencySymbolOther] = it.rate
        }

        val baseCurrencySymbol: String = currencyConverterList[0].currencySymbolBase

        currencyRates[baseCurrencySymbol] = currencyConverterList[0].rate

        _currencyRates.value = currencyRates

        convert(baseAmount = 1, baseCurrencySymbol = baseCurrencySymbol)

        _isLoading.value = false
    }

}