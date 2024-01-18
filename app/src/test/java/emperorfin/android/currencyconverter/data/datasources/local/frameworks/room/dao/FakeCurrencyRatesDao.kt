package emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.dao

import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_BASE_USD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AFN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AFN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AFN
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.utils.CurrencyConverterEntitySampleDataGeneratorUtil
import emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.webservices.openexchangerates.endpoints.api.latest.ResponseWrapper
import emperorfin.android.currencyconverter.domain.datalayer.dao.CurrencyRatesDao
import retrofit2.Response


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 05th January, 2024.
 */


data class FakeCurrencyRatesDao(
    private val noOfCurrencyRates: Int = NUM_OF_CURRENCY_RATES_150,
    private val noOfCurrencyRatesDeleted: Int = NUM_OF_CURRENCY_RATES_150,
    private val tableRowIds: List<Long> = TABLE_ROW_IDS_TWO,
    private val currencyRatesEntity: List<CurrencyConverterEntity> = CURRENCY_RATES_ENTITY,
    private val isException: Boolean = false,
    private val isCountException: Boolean = false,
    private val isEmptyList: Boolean = false
) : CurrencyRatesDao {

    companion object {

        const val NUM_OF_CURRENCY_RATES_150: Int = 150

        private val CURRENCY_RATES_MAP: Map<String, Double> = mapOf(
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
            "BBD" to 2.0,
            "BDT" to 110.280394,
            "BGN" to 1.79726,
            "BHD" to 0.376833,
            "BIF" to 2845.22284,
            "BMD" to 1.0,
            "BND" to 1.337427,
            "BOB" to 6.912093,
            "BRL" to 4.934,
            "BSD" to 1.0,
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
            "CUC" to 1.0,
            "CUP" to 25.75,
            "CVE" to 101.09283,
            "CZK" to 22.3916,
            "DJF" to 178.099048,
            "DKK" to 6.859152,
            "DOP" to 56.964521,
            "DZD" to 134.741,
            "EGP" to 30.9037,
            "ERN" to 15.0,
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
            "IRR" to 42250.0,
            "ISK" to 139.06,
            "JEP" to 0.79177,
            "JMD" to 154.591753,
            "JOD" to 0.7094,
            "JPY" to 148.18825,
            "KES" to 152.8,
            "KGS" to 89.1855,
            "KHR" to 4115.837775,
            "KMF" to 448.624972,
            "KPW" to 900.0,
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
            "MNT" to 3450.0,
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
            "PAB" to 1.0,
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
            "SDG" to 601.0,
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
            "TZS" to 2515.0,
            "UAH" to 36.479179,
            "UGX" to 3803.935274,
            "USD" to 1.0,
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
            "ZWL" to 322.0
        )

        val TABLE_ROW_IDS_TWO: List<Long> = listOf(1L, 2L)

        val CURRENCY_RATES_ENTITY: List<CurrencyConverterEntity> =
            CurrencyConverterEntitySampleDataGeneratorUtil.getCurrencyConverterEntityList()

        fun getSuccessfulRemoteCurrencyRates(): Response<ResponseWrapper> {
            val responseWrapper = ResponseWrapper(
                disclaimer = "Usage subject to terms: https://openexchangerates.org/terms",
                license = "https://openexchangerates.org/license",
                timestamp = 1701774000,
                base = "USD",
                rates = CURRENCY_RATES_MAP
            )

            val response: Response<ResponseWrapper> = Response.success(responseWrapper)

            return response
        }
    }

    override suspend fun countAllCurrencyRates(): Int {

        println("noOfCurrencyRates: $noOfCurrencyRates")

        if (isCountException) throw Exception()

        return noOfCurrencyRates
    }

    override suspend fun countCurrencyRates(currencySymbolBase: String): Int  {

        println("noOfCurrencyRates: $noOfCurrencyRates")

        if (isCountException) throw Exception()

        return noOfCurrencyRates
    }

    override suspend fun getCurrencyRates(currencySymbolBase: String): List<CurrencyConverterEntity> {

        if (isException) throw Exception()

        if (isEmptyList) return emptyList()

        val currencyRate1 = CurrencyConverterEntity.newInstance(
            id = ID_AED,
            rate = RATE_AED,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_AED,
        )

        val currencyRate2 = CurrencyConverterEntity.newInstance(
            id = ID_AFN,
            rate = RATE_AFN,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_AFN,
        )

        return listOf(currencyRate1, currencyRate2)
    }

    override suspend fun getCurrencyRates(currencySymbolBase: String, appId: String): Any {

        if (isException) throw Exception()

        return getSuccessfulRemoteCurrencyRates()
    }

    override suspend fun insertCurrencyRates(currencyRates: List<CurrencyConverterEntity>): List<Long> {
        return tableRowIds
    }

    override suspend fun deleteCurrencyRates(currencySymbolBase: String): Int  {

        if (isException) throw Exception()

        return noOfCurrencyRatesDeleted
    }
}