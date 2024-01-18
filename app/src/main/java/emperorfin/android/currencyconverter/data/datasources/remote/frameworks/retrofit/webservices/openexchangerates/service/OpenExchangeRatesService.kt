package emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.webservices.openexchangerates.service

import emperorfin.android.currencyconverter.BuildConfig
import emperorfin.android.currencyconverter.data.constants.StringConstants.ERROR_MESSAGE_SHOULD_NOT_BE_IMPLEMENTED
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity
import emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.webservices.openexchangerates.endpoints.api.latest.ResponseWrapper
import emperorfin.android.currencyconverter.domain.datalayer.dao.CurrencyRatesDao
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 18th December, 2023.
 */


// TODO: The word Dao should be suffixed to the name of this interface e.g. OpenExchangeRatesServiceDao
interface OpenExchangeRatesService : CurrencyRatesDao {

    companion object {
        const val APP_ID: String = BuildConfig.API_KEY

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.OPEN_EXCHANGE_RATES_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        val INSTANCE: OpenExchangeRatesService by lazy { retrofit.create(OpenExchangeRatesService::class.java) }
    }

//    @GET("latest.json")
//    suspend fun getCurrencyRates(
//        @Query("app_id") appId: String = APP_ID,
//        @Query("base") base: String
//    ): Response<ResponseWrapper>
    @GET("latest.json")
    override suspend fun getCurrencyRates(
        @Query("base") currencySymbolBase: String,
        @Query("app_id") appId: String
    ): Response<ResponseWrapper>

    /**
     * This should not be implemented in this remote DAO.
     *
     * The local DAO should be the one to implement this function.
     */
    override suspend fun getCurrencyRates(
        currencySymbolBase: String
    ): List<CurrencyConverterEntity> {
        throw IllegalStateException(ERROR_MESSAGE_SHOULD_NOT_BE_IMPLEMENTED)
    }

    /**
     * This should not be implemented in this remote DAO.
     *
     * The local DAO should be the one to implement this function.
     */
    override suspend fun insertCurrencyRates(
        currencyRates: List<CurrencyConverterEntity>
    ): List<Long> {
        throw IllegalStateException(ERROR_MESSAGE_SHOULD_NOT_BE_IMPLEMENTED)
    }

}