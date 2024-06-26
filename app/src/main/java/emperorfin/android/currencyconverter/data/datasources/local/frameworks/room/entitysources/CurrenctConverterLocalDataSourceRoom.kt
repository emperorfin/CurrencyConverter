package emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entitysources

import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.data.constants.StringConstants.ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED
import emperorfin.android.currencyconverter.data.constants.StringConstants.ERROR_MESSAGE_NOT_YET_IMPLEMENTED
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntityMapper
import emperorfin.android.currencyconverter.domain.datalayer.dao.CurrencyRatesDao
import emperorfin.android.currencyconverter.domain.datalayer.datasources.CurrencyConverterDataSource
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.CurrencyRateLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.NonExistentCurrencyRateDataLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.CurrencyRateListNotAvailableLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.InsertCurrencyRateLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.DeleteCurrencyRateLocalError
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


data class CurrencyConverterLocalDataSourceRoom internal constructor(
    private val currencyRateDao: CurrencyRatesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val currencyConverterEntityMapper: CurrencyConverterEntityMapper = CurrencyConverterEntityMapper(),
    private val currencyConverterModelMapper: CurrencyConverterModelMapper = CurrencyConverterModelMapper()
) : CurrencyConverterDataSource {

    private companion object {
        const val NUM_OF_CURRENCY_RATES_0: Int = 0
    }

    override suspend fun countAllCurrencyRates(params: Params): ResultData<Int> = withContext(ioDispatcher) {
        when(params){
            is None -> {
                return@withContext try {

                    val numOfAllCurrencyRates: Int = currencyRateDao.countAllCurrencyRates()

                    if (numOfAllCurrencyRates > NUM_OF_CURRENCY_RATES_0) {
                        return@withContext Success(data = numOfAllCurrencyRates)
                    } else if (numOfAllCurrencyRates == NUM_OF_CURRENCY_RATES_0) {
                        return@withContext Error(failure = NonExistentCurrencyRateDataLocalError())
                    }

                    return@withContext Error(failure = CurrencyRateLocalError())

                } catch (e: Exception){
                    return@withContext Error(failure = CurrencyRateLocalError(cause = e))
                }
            }
            is CurrencyConverterParams -> {
                throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
            }
            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }


    }

    override suspend fun countCurrencyRates(params: Params): ResultData<Int> = withContext(ioDispatcher) {
        when(params){
            is None -> {
                throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
            }
            is CurrencyConverterParams -> {
                return@withContext try {

                    val numOfCurrencyRates: Int = currencyRateDao.countCurrencyRates(params.currencySymbolBase!!)

                    if (numOfCurrencyRates > NUM_OF_CURRENCY_RATES_0) {
                        return@withContext Success(data = numOfCurrencyRates)
                    } else if (numOfCurrencyRates == NUM_OF_CURRENCY_RATES_0) {
                        return@withContext Error(failure = NonExistentCurrencyRateDataLocalError())
                    }

                    return@withContext Error(failure = CurrencyRateLocalError())

                } catch (e: Exception){
                    return@withContext Error(failure = CurrencyRateLocalError(cause = e))
                }
            }
            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }
    }

    override suspend fun getCurrencyRates(
        params: Params
    ): ResultData<List<CurrencyConverterModel>> = withContext(ioDispatcher) {

        when(params){
            is None -> {
                throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
            }
            is CurrencyConverterParams -> {
                return@withContext try {
                    val currencyRatesEntity: List<CurrencyConverterEntity> =
                        currencyRateDao.getCurrencyRates(params.currencySymbolBase!!)

                    if (currencyRatesEntity == null) // Deliberate check but shouldn't do this
                        return@withContext Error(failure = CurrencyRateLocalError())
                    else if (currencyRatesEntity.isEmpty())
                        return@withContext Error(failure = CurrencyRateListNotAvailableLocalError())

                    val currencyRatesModel = currencyRatesEntity.map {
                        currencyConverterModelMapper.transform(it)
                    }

                    return@withContext Success(currencyRatesModel)

                } catch (e: Exception){
                    return@withContext Error(failure = CurrencyRateLocalError(cause = e))
                }
            }
            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }
    }

    override suspend fun saveCurrencyRates(
        currencyRatesModel: List<CurrencyConverterModel>
    ): ResultData<List<Long>> = withContext(ioDispatcher){

        if (currencyRatesModel.isEmpty())
            return@withContext Error(
                failure = InsertCurrencyRateLocalError(message = R.string.error_cant_save_empty_currency_rate_list)
            )

        val currencyRatesEntity = currencyRatesModel.map {
            currencyConverterEntityMapper.transform(it)
        }

        // TODO: Consider putting this in a try/catch block and then write test cases for it.
        val tableRowIds: List<Long> = currencyRateDao.insertCurrencyRates(currencyRatesEntity)

        if (tableRowIds.size != currencyRatesEntity.size)
            return@withContext Error(
                InsertCurrencyRateLocalError(message = R.string.error_all_currency_rates_not_saved)
            )

        return@withContext Success(tableRowIds)
    }

    override suspend fun deleteCurrencyRates(params: Params): ResultData<Int> = withContext(ioDispatcher) {
        when(params){
            is None -> {
                throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
            }
            is CurrencyConverterParams -> {
                return@withContext try {

                    val numOfCurrencyRatesResultData: ResultData<Int> = countCurrencyRates(params)

                    val numOfCurrencyRates: Int = if(numOfCurrencyRatesResultData is Error &&
                        numOfCurrencyRatesResultData.failure is CurrencyRateLocalError){
                        return@withContext Error(failure = DeleteCurrencyRateLocalError())
                    } else if(numOfCurrencyRatesResultData is Error &&
                        numOfCurrencyRatesResultData.failure is NonExistentCurrencyRateDataLocalError) {
                        NUM_OF_CURRENCY_RATES_0
                    } else {
                        (numOfCurrencyRatesResultData as Success).data
                    }

                    // TODO: Uncomment the following and be sure to add test case.
//                    if (numOfCurrencyRates == NUM_OF_CURRENCY_RATES_0) {
//                        return@withContext Error(failure = DeleteCurrencyRateLocalError(R.string.no_currency_rates_to_delete))
//                    }

                    val numOfCurrencyRatesDeleted: Int = currencyRateDao.deleteCurrencyRates(params.currencySymbolBase!!)

                    if (numOfCurrencyRatesDeleted > NUM_OF_CURRENCY_RATES_0 && numOfCurrencyRatesDeleted != numOfCurrencyRates) {
                        return@withContext Error(failure = DeleteCurrencyRateLocalError(R.string.error_deleting_currency_rates))
                    } /*else {
                        return@withContext Error(failure = DeleteCurrencyRateLocalError())
                    }*/

                    return@withContext Success(numOfCurrencyRatesDeleted)

                } catch (e: Exception){
                    return@withContext Error(failure = DeleteCurrencyRateLocalError(cause = e))
                }
            }
            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }
    }
}