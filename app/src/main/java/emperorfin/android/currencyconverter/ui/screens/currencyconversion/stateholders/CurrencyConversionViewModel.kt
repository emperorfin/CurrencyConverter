package emperorfin.android.currencyconverter.ui.screens.currencyconversion.stateholders

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.AppRoomDatabase
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entitysources.CurrencyConverterLocalDataSourceRoom
import emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.modelsources.CurrencyConverterRemoteDataSourceRetrofit
import emperorfin.android.currencyconverter.data.repositories.CurrencyConverterRepository
import emperorfin.android.currencyconverter.domain.datalayer.repositories.ICurrencyConverterRepository
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure
import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModel
import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModelMapper
import emperorfin.android.currencyconverter.ui.models.currencyconverter.CurrencyConverterUiModel
import emperorfin.android.currencyconverter.ui.models.currencyconverter.CurrencyConverterUiModelMapper
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.succeeded
import emperorfin.android.currencyconverter.ui.screens.events.inputs.currencyconverter.CurrencyConverterParams
import emperorfin.android.currencyconverter.ui.utils.CurrencyConverterSampleDataGeneratorUtil
import emperorfin.android.currencyconverter.ui.utils.Helpers
import emperorfin.android.currencyconverter.ui.utils.InternetConnectivityUtil.hasInternetConnection
import emperorfin.android.currencyconverter.ui.utils.WhileUiSubscribed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.math.roundToInt
import kotlin.properties.Delegates


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 27th November, 2023.
 */


class CurrencyConversionViewModel(
    application: Application,
    private val currencyConverterRepository: ICurrencyConverterRepository
) : AndroidViewModel(application) {

    companion object {

        private const val DELAY_TIME_MILLIS_3000: Long = 3000

        private const val CURRENCY_SYMBOL_USD: String = "USD"

        fun getCurrencyConverterRepository(application: Application): CurrencyConverterRepository {
            val currencyRateDao = AppRoomDatabase.getInstance(application).mCurrencyRateDao

            val currencyRatesLocalDataSource =
                CurrencyConverterLocalDataSourceRoom(context = application, currencyRateDao = currencyRateDao)

            val currencyRatesRemoteDataSource =
                CurrencyConverterRemoteDataSourceRetrofit(context = application)

            return CurrencyConverterRepository(
                currencyConverterLocalDataSource = currencyRatesLocalDataSource,
                currencyConverterRemoteDataSource = currencyRatesRemoteDataSource
            )
        }

    }

    private val coroutineDispatcherDefault = Dispatchers.Default
    private val coroutineDispatcherIo = Dispatchers.IO

    private val applicationContext = getApplication<Application>()

    private val currencyConverterModelMapper = CurrencyConverterModelMapper()
    private val currencyConverterUiModelMapper = CurrencyConverterUiModelMapper(context = applicationContext)

    private var initCurrencyRates = true

    private var currencyRatesWithFlagsDefault = listOf<CurrencyConverterUiModel>()

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

    private val _currencyRatesWithFlags: MutableStateFlow<ResultData<List<CurrencyConverterUiModel>>> =  MutableStateFlow(ResultData.Loading)
    private val currencyRatesWithFlags: StateFlow<ResultData<List<CurrencyConverterUiModel>>> = _currencyRatesWithFlags

    private val _messageSnackBar: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val messageSnackBar: StateFlow<Int?> = _messageSnackBar

    init {

        // Option 1
//        simulateLoadingCurrencyRatesErrorOnAppStartup()
        // Option 2
//        simulateNoCurrencyRatesOnAppStartup()
        // Option 3
        initCurrencyRates(context = applicationContext)

    }

    fun convert(isFreeOpenExchangeRatesAccount: Boolean = true, baseAmount: Int, baseCurrencySymbol: String) {

        val currencyRatesWithFlagsResultData: ResultData<List<CurrencyConverterUiModel>> = _currencyRatesWithFlags.value

        if (currencyRatesWithFlagsResultData is ResultData.Error) {
            _currencyRatesWithFlags.value = currencyRatesWithFlagsResultData

            return
        } /*else if (currencyRatesWithFlagsResultData is ResultData.Loading) {
            return
        }*/

//        val currencyRatesWithFlags: List<CurrencyConverterUiModel> = (currencyRatesWithFlagsResultData as ResultData.Success).data
        val currencyRatesWithFlags: List<CurrencyConverterUiModel> = currencyRatesWithFlagsDefault

        if (currencyRatesWithFlags.isEmpty()) {
            _currencyRatesWithFlags.value = ResultData.Error(
                failure = CurrencyConverterFailure.CurrencyRateMemoryError(message = R.string.error_unexpected_during_conversion)
            )

            return
        }

        _currencyRatesWithFlags.value = ResultData.Loading

        val currencyRatesWithFlagsNew = mutableListOf<CurrencyConverterUiModel>()

        println("convert() 1")

        currencyRatesWithFlags.forEach {

            val currencyRateWithFlag: CurrencyConverterUiModel = currencyRatesWithFlags.single { currencyRateWithFlag ->
                currencyRateWithFlag.currencySymbolOther == baseCurrencySymbol
            }

            val baseCurrencySymbolValue: Double = currencyRateWithFlag.rate

            val currencyRateValue: Double = it.rate

            if (baseCurrencySymbol == CURRENCY_SYMBOL_USD || !isFreeOpenExchangeRatesAccount) {
                val newRate = currencyRateValue * baseAmount
                val rateToThreeDecimalPlace = roundToThreeDecimalPlaces(newRate)

                val currencyRateWithFlagNew = CurrencyConverterUiModel.newInstance(
                    currencySymbolBase = baseCurrencySymbol,
                    currencySymbolOther = it.currencySymbolOther,
                    rate = rateToThreeDecimalPlace,
                    currencySymbolOtherFlag = it.currencySymbolOtherFlag
                )

                currencyRatesWithFlagsNew.add(currencyRateWithFlagNew)
            } else {

                val newRate = (currencyRateValue / baseCurrencySymbolValue) * baseAmount
                val rateToThreeDecimalPlace = roundToThreeDecimalPlaces(newRate)

                val currencyRateWithFlagNew = CurrencyConverterUiModel.newInstance(
                    currencySymbolBase = baseCurrencySymbol,
                    currencySymbolOther = it.currencySymbolOther,
                    rate = rateToThreeDecimalPlace,
                    currencySymbolOtherFlag = it.currencySymbolOtherFlag
                )

                currencyRatesWithFlagsNew.add(currencyRateWithFlagNew)
            }
        }

        initCurrencyRates = false

        println("convert() 2")

        _currencyRatesWithFlags.value = ResultData.Success(data = currencyRatesWithFlagsNew)

        println("convert() 3")

    }

    fun initCurrencyRates(
        context: Context = applicationContext,
        params: CurrencyConverterParams = CurrencyConverterParams(currencySymbolBase = CURRENCY_SYMBOL_USD),
        isRefresh: Boolean = false,
        wouldReconvertRates: Boolean = false,
        onConvertRates: (() -> Unit)? = null
    ) {

        // Option 1
//        getInMemoryCurrencyRates(context = context, isRefresh = isRefresh)
        // Option 2
//        generateCurrencyRatesSampleData(isRefresh = isRefresh)
        // Option 3
//        getDatabaseCurrencyRatesSampleDataWithoutLocalDataSource(isRefresh = isRefresh)
        // Option 4
//        getDatabaseCurrencyRatesSampleDataViaLocalDataSource(params = params, isRefresh = isRefresh)
        // Option 5
//        getCurrencyRatesRealDataViaRemoteDataSource(params = params, isRefresh = isRefresh)
        // Option 6
//        getDatabaseCurrencyRatesSampleDataViaRepository(params = params, isRefresh = isRefresh)
        // Option 7
        loadCurrencyRates(
            params = params,
            isRefresh = isRefresh,
            wouldReconvertRates = wouldReconvertRates,
            onConvertRates = onConvertRates
        )

    }

    // Running the content of this function inside of a viewModelScope.launch makes
    // the contents to execute in sequence i.e. initCurrencyRates() must return before
    // convert() function gets invoked as suspend functions are synchronous ( https://stackoverflow.com/a/69406442 ).
    // Not running the block of this function inside of viewModelScope.launch might cause convert()
    // to run without initCurrencyRates() returning which would cause an exception.
    fun refreshCurrencyRates(
        context: Context = applicationContext,
        isFreeOpenExchangeRatesAccount: Boolean = true,
        baseAmount: Int,
        baseCurrencySymbol: String
    ) /*= viewModelScope.launch(context = coroutineDispatcherDefault)*/ {
        if (isFreeOpenExchangeRatesAccount && baseCurrencySymbol != CURRENCY_SYMBOL_USD) return

        val params = CurrencyConverterParams(currencySymbolBase = baseCurrencySymbol)

        println("refreshCurrencyRates() 1")

        initCurrencyRates(
            context = context,
            params = params,
            isRefresh = true,
            wouldReconvertRates = true,
            onConvertRates = {
                convert(baseAmount = baseAmount, baseCurrencySymbol = baseCurrencySymbol)
            }
        )

//        convert(baseAmount = baseAmount, baseCurrencySymbol = baseCurrencySymbol)

    }

    val uiState: StateFlow<CurrencyConversionUiState> = combine(
        _isLoading, _errorMessage, currencyRatesWithFlags, messageSnackBar
    ) { isLoading, errorMessage, currencyRatesWithFlags, messageSnackBar ->
        when (currencyRatesWithFlags) {
            ResultData.Loading -> {
                CurrencyConversionUiState(isLoading = true)
            }
            is ResultData.Error -> {
                CurrencyConversionUiState(
                    errorMessage = (currencyRatesWithFlags.failure as CurrencyConverterFailure).message,
                    messageSnackBar = messageSnackBar,
                    initRates = initCurrencyRates
                )
            }
            is ResultData.Success<List<CurrencyConverterUiModel>> -> {

                val currencyRatesWithFlagsOld: List<CurrencyConverterUiModel> = currencyRatesWithFlags.data
                val currencyRatesWithFlagsNew: MutableList<CurrencyConverterUiModel> = mutableListOf()

                val mapOfCurrencySymbolsToFlag = mutableMapOf<String, String?>()

                currencyRatesWithFlagsOld.forEach {

                    if (it.currencySymbolBase != it.currencySymbolOther) {
                        currencyRatesWithFlagsNew.add(it)
                    }

                    mapOfCurrencySymbolsToFlag[it.currencySymbolOther] = it.currencySymbolOtherFlag
                }

                val currencyRatesWithFlagsSorted = currencyRatesWithFlagsNew.sortedBy {
                    it.currencySymbolOther
                }

                val mapOfCurrencySymbolsToFlagSorted = mapOfCurrencySymbolsToFlag.toSortedMap()

                CurrencyConversionUiState(
                    items = currencyRatesWithFlagsSorted,
                    mapOfCurrencySymbolsToFlag = mapOfCurrencySymbolsToFlagSorted,
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    messageSnackBar = messageSnackBar
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

    fun snackbarMessageShown() {
        _messageSnackBar.value = null
    }

    private fun simulateLoadingCurrencyRatesErrorOnAppStartup() = viewModelScope.launch(context = coroutineDispatcherDefault) {
        _currencyRatesWithFlags.value = ResultData.Loading

        delay(DELAY_TIME_MILLIS_3000)

        _currencyRatesWithFlags.value = ResultData.Error(
            failure = CurrencyConverterFailure.CurrencyRateLocalError()
        )
    }

    private fun simulateNoCurrencyRatesOnAppStartup() = viewModelScope.launch(context = coroutineDispatcherDefault) {
        _currencyRatesWithFlags.value = ResultData.Loading

        delay(DELAY_TIME_MILLIS_3000)

        _currencyRatesWithFlags.value = ResultData.Error(
            failure = CurrencyConverterFailure.CurrencyRateListNotAvailableLocalError()
        )
    }

    private fun getInMemoryCurrencyRates(
        context: Context,
        wouldReconvertRates: Boolean,
        onConvertRates: (() -> Unit)? = null
    ) = viewModelScope.launch(context = coroutineDispatcherDefault) {
        _currencyRatesWithFlags.value = ResultData.Loading

        val currencyRates = mutableListOf<CurrencyConverterUiModel>()

        currencyRatesInMemory.forEach {

            val mapOfCurrencySymbolsToFlag = Helpers.loadMapOfCurrencySymbolToFlag(context.assets)

            val currencySymbolOther: String = it.key
            val currencySymbolOtherFlag: String? = mapOfCurrencySymbolsToFlag[currencySymbolOther]

            val currencyRate = CurrencyConverterUiModel.newInstance(
                currencySymbolBase = CURRENCY_SYMBOL_USD,
                currencySymbolOther = currencySymbolOther,
                rate = it.value.toDouble(),
                currencySymbolOtherFlag = currencySymbolOtherFlag

            )

            currencyRates.add(currencyRate)
        }

        initCurrencyRates = false

        currencyRatesWithFlagsDefault = currencyRates

//        _currencyRatesWithFlags.value = ResultData.Success(data = currencyRates)
        if (!wouldReconvertRates) {
            _currencyRatesWithFlags.value = ResultData.Success(data = currencyRates)
        } else {

            if (onConvertRates == null) {
                throw IllegalArgumentException(
                    "When the parameter wouldReconvertRates is true, the parameter onConvertRates " +
                            "must not be null."
                )
            }

            onConvertRates.invoke()
        }

    }

    private fun generateCurrencyRatesSampleData(
        wouldReconvertRates: Boolean,
        onConvertRates: (() -> Unit)? = null
    ) = viewModelScope.launch(context = coroutineDispatcherDefault) {

        _currencyRatesWithFlags.value = ResultData.Loading

        delay(DELAY_TIME_MILLIS_3000)

        val currencyConverterList: List<CurrencyConverterUiModel> =
            CurrencyConverterSampleDataGeneratorUtil
                .getTransformedCurrencyConverterEntityList(context = applicationContext)

        initCurrencyRates = false

        currencyRatesWithFlagsDefault = currencyConverterList

//        _currencyRatesWithFlags.value = ResultData.Success(data = currencyConverterList)
        if (!wouldReconvertRates) {
            _currencyRatesWithFlags.value = ResultData.Success(data = currencyConverterList)
        } else {

            if (onConvertRates == null) {
                throw IllegalArgumentException(
                    "When the parameter wouldReconvertRates is true, the parameter onConvertRates " +
                            "must not be null."
                )
            }

            onConvertRates.invoke()
        }

    }

    private fun getDatabaseCurrencyRatesSampleDataWithoutLocalDataSource(
        wouldReconvertRates: Boolean,
        onConvertRates: (() -> Unit)? = null
    ) = viewModelScope.launch(context = coroutineDispatcherIo) {

        _currencyRatesWithFlags.value = ResultData.Loading

        val currencyRatesEntity = AppRoomDatabase
            .getInstance(applicationContext)
            .mCurrencyRateDao
            .getCurrencyRates(currencySymbolBase = CURRENCY_SYMBOL_USD)

        val currencyConverterModelMapper = currencyConverterModelMapper
        val currencyConverterUiModelMapper = currencyConverterUiModelMapper

        val currencyRatesUiModel: List<CurrencyConverterUiModel> = currencyRatesEntity.map {
            currencyConverterModelMapper.transform(it)
        }.map {
            currencyConverterUiModelMapper.transform(it)
        }

        initCurrencyRates = false

        currencyRatesWithFlagsDefault = currencyRatesUiModel

//        _currencyRatesWithFlags.value = ResultData.Success(data = currencyRatesUiModel)
        if (!wouldReconvertRates) {
            _currencyRatesWithFlags.value = ResultData.Success(data = currencyRatesUiModel)
        } else {

            if (onConvertRates == null) {
                throw IllegalArgumentException(
                    "When the parameter wouldReconvertRates is true, the parameter onConvertRates " +
                            "must not be null."
                )
            }

            onConvertRates.invoke()
        }

    }

    private fun getDatabaseCurrencyRatesSampleDataViaLocalDataSource(
        params: CurrencyConverterParams,
        wouldReconvertRates: Boolean,
        onConvertRates: (() -> Unit)? = null
    ) = viewModelScope.launch(context = coroutineDispatcherIo) {

        println("getDatabaseCurrencyRatesSampleDataViaLocalDataSource")

        _currencyRatesWithFlags.value = ResultData.Loading

        val localDataSourceRoom = CurrencyConverterLocalDataSourceRoom(
            applicationContext,
            AppRoomDatabase.getInstance(applicationContext).mCurrencyRateDao
        )

        val currencyRatesResultData: ResultData<List<CurrencyConverterModel>> = localDataSourceRoom.getCurrencyRates(params)

        if (currencyRatesResultData.succeeded) {
            val currencyRatesEntity = (currencyRatesResultData as ResultData.Success).data

            val currencyConverterModelMapper = currencyConverterModelMapper
            val currencyConverterUiModelMapper = currencyConverterUiModelMapper

            val currencyRatesUiModel: List<CurrencyConverterUiModel> = currencyRatesEntity.map {
                currencyConverterModelMapper.transform(it)
            }.map {
                currencyConverterUiModelMapper.transform(it)
            }

            initCurrencyRates = false

            currencyRatesWithFlagsDefault = currencyRatesUiModel

//        _currencyRatesWithFlags.value = ResultData.Success(data = currencyRatesUiModel)
            if (!wouldReconvertRates) {
                _currencyRatesWithFlags.value = ResultData.Success(data = currencyRatesUiModel)
            } else {

                if (onConvertRates == null) {
                    throw IllegalArgumentException(
                        "When the parameter wouldReconvertRates is true, the parameter onConvertRates " +
                                "must not be null."
                    )
                }

                onConvertRates.invoke()
            }
        } else {
            val error: ResultData.Error = (currencyRatesResultData as ResultData.Error)
            _currencyRatesWithFlags.value = error
        }

    }

    private fun getCurrencyRatesRealDataViaRemoteDataSource(
        params: CurrencyConverterParams,
        wouldReconvertRates: Boolean,
        onConvertRates: (() -> Unit)? = null
    ) = viewModelScope.launch(context = coroutineDispatcherIo) {

        _currencyRatesWithFlags.value = ResultData.Loading

        val remoteDataSourceRetrofit = CurrencyConverterRemoteDataSourceRetrofit(
            context = applicationContext
        )

        val currencyRatesResultData: ResultData<List<CurrencyConverterModel>> = remoteDataSourceRetrofit.getCurrencyRates(params)

        if (currencyRatesResultData.succeeded) {
            val currencyRatesEntity = (currencyRatesResultData as ResultData.Success).data

            val currencyConverterModelMapper = currencyConverterModelMapper
            val currencyConverterUiModelMapper = currencyConverterUiModelMapper

            val currencyRatesUiModel: List<CurrencyConverterUiModel> = currencyRatesEntity.map {
                currencyConverterModelMapper.transform(it)
            }.map {
                currencyConverterUiModelMapper.transform(it)
            }

            initCurrencyRates = false

            currencyRatesWithFlagsDefault = currencyRatesUiModel

//        _currencyRatesWithFlags.value = ResultData.Success(data = currencyRatesUiModel)
            if (!wouldReconvertRates) {
                _currencyRatesWithFlags.value = ResultData.Success(data = currencyRatesUiModel)
            } else {

                if (onConvertRates == null) {
                    throw IllegalArgumentException(
                        "When the parameter wouldReconvertRates is true, the parameter onConvertRates " +
                                "must not be null."
                    )
                }

                onConvertRates.invoke()
            }
        } else {
            val error: ResultData.Error = (currencyRatesResultData as ResultData.Error)
            _currencyRatesWithFlags.value = error
        }

    }

    private fun getDatabaseCurrencyRatesSampleDataViaRepository(
        params: CurrencyConverterParams,
        isRefresh: Boolean,
        wouldReconvertRates: Boolean,
        onConvertRates: (() -> Unit)? = null
    ) = viewModelScope.launch(context = coroutineDispatcherIo) {

        println("getDatabaseCurrencyRatesSampleDataViaRepository")

        _currencyRatesWithFlags.value = ResultData.Loading

        val currencyRatesResultData: ResultData<List<CurrencyConverterModel>> =
            currencyConverterRepository.getCurrencyRates(params = params, forceUpdate = isRefresh)

        println("getDatabaseCurrencyRatesSampleDataViaRepository -> currencyRatesResultData: $currencyRatesResultData")

        if (currencyRatesResultData.succeeded) {
            val currencyRatesEntity = (currencyRatesResultData as ResultData.Success).data

            val currencyConverterModelMapper = currencyConverterModelMapper
            val currencyConverterUiModelMapper = currencyConverterUiModelMapper

            val currencyRatesUiModel: List<CurrencyConverterUiModel> = currencyRatesEntity.map {
                currencyConverterModelMapper.transform(it)
            }.map {
                currencyConverterUiModelMapper.transform(it)
            }

            initCurrencyRates = false

            currencyRatesWithFlagsDefault = currencyRatesUiModel

//        _currencyRatesWithFlags.value = ResultData.Success(data = currencyRatesUiModel)
            if (!wouldReconvertRates) {
                _currencyRatesWithFlags.value = ResultData.Success(data = currencyRatesUiModel)
            } else {

                if (onConvertRates == null) {
                    throw IllegalArgumentException(
                        "When the parameter wouldReconvertRates is true, the parameter onConvertRates " +
                                "must not be null."
                    )
                }

                onConvertRates.invoke()
            }
        } else {
            val error: ResultData.Error = (currencyRatesResultData as ResultData.Error)
            _currencyRatesWithFlags.value = error
        }

    }

    private fun loadCurrencyRates(
        params: CurrencyConverterParams, isRefresh: Boolean, wouldReconvertRates: Boolean, onConvertRates: (() -> Unit)? = null
    ){
        viewModelScope.launch/*(context = coroutineDispatcherIo)*/ {

            var currencyRatesCount by Delegates.notNull<Int>()

            val currencyRatesCountDataResultEvent = currencyConverterRepository.countCurrencyRates(params = params)

            currencyRatesCount = if (currencyRatesCountDataResultEvent.succeeded)
                (currencyRatesCountDataResultEvent as ResultData.Success).data
            else
                -1

            println("currencyRatesCount: $currencyRatesCount")

            if (currencyRatesCount > 0 || isRefresh){
                println("loadCurrencyRates() 1")

                if (hasInternetConnection(applicationContext)){
                    println("loadCurrencyRates() 2")

                    getCurrencyRatesViaRepository(
                        params = params,
                        isRefresh = true,
                        wouldReconvertRates = wouldReconvertRates,
                        onConvertRates = onConvertRates
                    )
                } else {
                    println("loadCurrencyRates() 3")

                    withContext(Dispatchers.Main){
                        Toast.makeText(
                            applicationContext,
                            R.string.message_no_internet_loading_cached_currency_rates,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    getCurrencyRatesViaRepository(
                        params = params,
                        isRefresh = false,
                        wouldReconvertRates = wouldReconvertRates,
                        onConvertRates = onConvertRates
                    )
                }
            } else {
                println("loadCurrencyRates() 4")

                if (hasInternetConnection(applicationContext)){
                    getCurrencyRatesViaRepository(
                        params = params,
                        isRefresh = true,
                        wouldReconvertRates = wouldReconvertRates,
                        onConvertRates = onConvertRates
                    )
                } else {
                    println("loadCurrencyRates() 5")

                    _messageSnackBar.value = R.string.message_no_internet_connectivity

                    _currencyRatesWithFlags.value = ResultData.Error(
                        failure = CurrencyConverterFailure.CurrencyRateRemoteError(
                            message = R.string.message_no_internet_connectivity
                        )
                    )
                }
            }

        }
    }

    private fun getCurrencyRatesViaRepository(
        params: CurrencyConverterParams,
        isRefresh: Boolean,
        wouldReconvertRates: Boolean,
        onConvertRates: (() -> Unit)? = null
    ){
        getDatabaseCurrencyRatesSampleDataViaRepository(
            params = params,
            isRefresh = isRefresh,
            wouldReconvertRates = wouldReconvertRates,
            onConvertRates = onConvertRates
        )
    }

    private fun getSavedCurrencyRates(
        params: CurrencyConverterParams,
        wouldReconvertRates: Boolean,
        onConvertRates: (() -> Unit)? = null
    ){
        getDatabaseCurrencyRatesSampleDataViaLocalDataSource(
            params = params,
            wouldReconvertRates = wouldReconvertRates,
            onConvertRates = onConvertRates
        )
    }

}