package emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.modelsources

import android.content.Context
import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.models.currencyconverter.CurrencyConverterDataTransferObject
import emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.webservices.openexchangerates.endpoints.api.latest.ResponseWrapper
import emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.webservices.openexchangerates.service.OpenExchangeRatesService
import emperorfin.android.currencyconverter.domain.datalayer.dao.CurrencyRatesDao
import emperorfin.android.currencyconverter.domain.datalayer.datasources.CurrencyConverterDataSource
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure
import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModel
import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModelMapper
import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.Params
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData.Success
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData.Error
import emperorfin.android.currencyconverter.ui.screens.events.inputs.currencyconverter.CurrencyConverterParams
import emperorfin.android.currencyconverter.ui.screens.events.inputs.currencyconverter.None
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 18th December, 2023.
 */


class CurrencyConverterRemoteDataSourceRetrofit internal constructor(
    private val context: Context,
    private val currencyRatesDao: CurrencyRatesDao = OpenExchangeRatesService.INSTANCE,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val currencyConverterModelMapper: CurrencyConverterModelMapper = CurrencyConverterModelMapper()
) : CurrencyConverterDataSource {
    override suspend fun countAllCurrencyRates(params: Params): ResultData<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun countCurrencyRates(params: Params): ResultData<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrencyRates(
        params: Params
    ): ResultData<List<CurrencyConverterModel>> = withContext(ioDispatcher) {
        when(params){
            is None -> {
                throw IllegalArgumentException(
                    context.getString(R.string.error_inappropriate_argument_passed)
                )
            }
            is CurrencyConverterParams -> {

                return@withContext try {

//                    val response = OpenExchangeRatesService
//                        .INSTANCE
//                        .getCurrencyRates(
//                            currencySymbolBase = params.currencySymbolBase!!,
//                            arg1 = OpenExchangeRatesService.APP_ID
//                        )
                    val response = currencyRatesDao.getCurrencyRates(
                            currencySymbolBase = params.currencySymbolBase!!,
                            appId = OpenExchangeRatesService.APP_ID
                    ) as Response<ResponseWrapper>

                    withContext(Dispatchers.Main){
                        if (response.isSuccessful){

                            response.body()?.let {

                                val currencyRatesModel: List<CurrencyConverterModel> =
                                    buildProductOverviewModelList(base = it.base, openExchangesRates = it.rates)

                                // try block doesn't seem to return without return@withContext
                                return@withContext Success(currencyRatesModel)
                            }
                        }

//                        println("HTTP status code: ${response.code()}")

                        return@withContext Error(failure = CurrencyConverterFailure.GetCurrencyRateRemoteError())
                    }

                } catch (e: Exception){
                    return@withContext Error(failure = CurrencyConverterFailure.CurrencyRateRemoteError(cause = e))
                }
            }
            else -> throw NotImplementedError(context.getString(R.string.error_not_yet_implemented))
        }
    }

    override suspend fun saveCurrencyRates(currencyRatesModel: List<CurrencyConverterModel>): ResultData<List<Long>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCurrencyRates(params: Params): ResultData<Int> {
        TODO("Not yet implemented")
    }

    private fun buildProductOverviewModelList(
        base: String, openExchangesRates: Map<String, Number>
    ): List<CurrencyConverterModel> {
        val currencyRatesDto = mutableListOf<CurrencyConverterDataTransferObject>()

        openExchangesRates.forEach {

            val currencySymbolOther: String = it.key
            val rate: Double = it.value.toDouble()

            val currencyRate = CurrencyConverterDataTransferObject.newInstance(
                currencySymbolBase = base,
                currencySymbolOther = currencySymbolOther,
                rate = rate

            )

            currencyRatesDto.add(currencyRate)
        }

        return currencyRatesDto.map {
            currencyConverterModelMapper.transform(it)
        }
    }
}