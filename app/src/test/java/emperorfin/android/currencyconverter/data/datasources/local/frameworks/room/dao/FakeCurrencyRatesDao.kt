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
import okhttp3.ResponseBody
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
    private val isEmptyList: Boolean = false,
    private val isGetRemoteCurrencyRatesFailed: Boolean = false
) : CurrencyRatesDao {

    companion object {

        const val NUM_OF_CURRENCY_RATES_150: Int = 150

        private val CURRENCY_RATES_MAP: Map<String, Double> = mapOf(
            "AED" to 3.6721,
            "AFN" to 69.845466
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

        fun getFailedRemoteCurrencyRates(): Response<ResponseWrapper> {
            val responseBody: ResponseBody = ResponseBody.create(null, "Error encountered.")

            val response: Response<ResponseWrapper> = Response.error(404, responseBody)

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

        if (isGetRemoteCurrencyRatesFailed) return getFailedRemoteCurrencyRates()

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