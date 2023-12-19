package emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.webservices.openexchangerates.service

import emperorfin.android.currencyconverter.BuildConfig
import emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.webservices.openexchangerates.endpoints.api.latest.ResponseWrapper
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 18th December, 2023.
 */


interface OpenExchangeRatesService {

    companion object {
        private const val APP_ID: String = BuildConfig.API_KEY

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.OPEN_EXCHANGE_RATES_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        val INSTANCE: OpenExchangeRatesService by lazy { retrofit.create(OpenExchangeRatesService::class.java) }
    }

    @GET("latest.json")
    suspend fun getCurrencyRates(
        @Query("app_id") appId: String = APP_ID,
        @Query("base") base: String
    ): Response<ResponseWrapper>

}