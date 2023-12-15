package emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entitysources

import android.content.Context
import androidx.annotation.StringRes
import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.dao.CurrencyRateDao
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntityMapper
import emperorfin.android.currencyconverter.domain.datalayer.datasources.CurrencyConverterDataSource
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


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 11th December, 2023.
 */


class CurrencyConverterLocalDataSourceRoom internal constructor(
    private val context: Context,
    private val currencyRateDao: CurrencyRateDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val currencyConverterEntityMapper: CurrencyConverterEntityMapper = CurrencyConverterEntityMapper(),
    private val currencyConverterModelMapper: CurrencyConverterModelMapper = CurrencyConverterModelMapper()
) : CurrencyConverterDataSource {

    private companion object {

        @StringRes val ERROR_MESSAGE_CURRENCY_RATES_UNAVAILABLE: Int =
            R.string.error_currency_rates_unavailable

        @StringRes val ERROR_MESSAGE_CANT_LOAD_DB_CURRENCY_RATES: Int =
            R.string.error_loading_db_currency_rates
    }

    override suspend fun countAllCurrencyRates(): ResultData<Int> = withContext(ioDispatcher) {
        TODO("Not yet implemented")
//        val numOfAllCurrencyRates = currencyRateDao.countAllCurrencyRates()
//
//        if (numOfAllCurrencyRates > 0) {
//            return@withContext Success(data = numOfAllCurrencyRates)
//        } else if (numOfAllCurrencyRates == 0) {
//            return@withContext Error(errorMessage = ERROR_MESSAGE_CURRENCY_RATES_UNAVAILABLE)
//        }
//
//        return@withContext Error(errorMessage = ERROR_MESSAGE_CANT_LOAD_DB_CURRENCY_RATES)
    }

    override suspend fun getCurrencyRates(params: Params): ResultData<List<CurrencyConverterModel>> = withContext(ioDispatcher) {
        TODO("Not yet implemented")
//        when(params){
//            is None -> {
//                throw IllegalArgumentException(
//                    context.getString(R.string.error_inappropriate_argument_passed)
//                )
//            }
//            is CurrencyConverterParams -> {
//                return@withContext try {
//                    val currencyRatesEntity: List<CurrencyConverterEntity> =
//                        currencyRateDao.getCurrencyRates(params.currencySymbolBase!!)
//
//                    if (currencyRatesEntity == null)
//                        Error(errorMessage = ERROR_MESSAGE_CANT_LOAD_DB_CURRENCY_RATES)
//                    else if (currencyRatesEntity.isEmpty())
//                        Error(errorMessage = ERROR_MESSAGE_CURRENCY_RATES_UNAVAILABLE)
//
//                    val currencyRatesModel = currencyRatesEntity.map {
//                        currencyConverterModelMapper.transform(it)
//                    }
//
//                    Success(currencyRatesModel)
//
//                } catch (e: Exception){
//                    Error(LocalProductOverviewError(cause = e))
//                }
//            }
//            else -> throw NotImplementedError(context.getString(R.string.error_not_yet_implemented))
//        }
    }

    override suspend fun saveCurrencyRates(currencyRates: List<CurrencyConverterModel>): ResultData<List<Long>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCurrencyRates(params: Params): ResultData<Int> {
        TODO("Not yet implemented")
    }
}