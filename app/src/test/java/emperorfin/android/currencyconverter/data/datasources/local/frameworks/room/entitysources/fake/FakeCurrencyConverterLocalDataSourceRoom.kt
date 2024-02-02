package emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entitysources.fake

import emperorfin.android.currencyconverter.data.constants.StringConstants
import emperorfin.android.currencyconverter.data.constants.StringConstants.ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED
import emperorfin.android.currencyconverter.data.constants.StringConstants.ERROR_MESSAGE_NOT_YET_IMPLEMENTED
import emperorfin.android.currencyconverter.domain.datalayer.datasources.AFakeCurrencyConverterDataSource
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.CurrencyRateLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.CurrencyRateListNotAvailableLocalError
import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModel
import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.Params
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData
import emperorfin.android.currencyconverter.ui.screens.events.inputs.currencyconverter.CurrencyConverterParams
import emperorfin.android.currencyconverter.ui.screens.events.inputs.currencyconverter.None


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 22nd January, 2024.
 */


internal data class FakeCurrencyConverterLocalDataSourceRoom(
//    private val isCountAllCurrencyRatesIllegalArgumentException: Boolean = false,
//    private val isCountAllCurrencyRatesNotImplementedError: Boolean = false,
    private val isCountAllCurrencyRatesException: Boolean = false,
    private val countAllCurrencyRatesResultData: ResultData.Success<Int> = ResultData.Success(NUM_OF_CURRENCY_RATES_0),
//    private val isCountCurrencyRatesIllegalArgumentException: Boolean = false,
//    private val isCountCurrencyRatesNotImplementedError: Boolean = false,
    private val isCountCurrencyRatesException: Boolean = false,
    private val countCurrencyRatesResultData: ResultData.Success<Int> = ResultData.Success(NUM_OF_CURRENCY_RATES_0),
    private val isGetCurrencyRatesException: Boolean = false,
    private val getCurrencyRatesResultData: ResultData.Success<List<CurrencyConverterModel>> = ResultData.Success(CURRENCY_RATES_MODEL),
    private val isSaveCurrencyRatesError: Boolean = false,
    private val saveCurrencyRatesResultData: ResultData<List<Long>> = ResultData.Success(listOf(1L, 2L)),
    private val isDeleteCurrencyRatesException: Boolean = false,
    private val isDeleteCurrencyRatesErrorDuringRatesCount: Boolean = false,
    private val isDeleteCurrencyRatesErrorWhileDeletingAllRates: Boolean = false,
    private val deleteCurrencyRatesResultData: ResultData.Success<Int> = ResultData.Success(NUM_OF_CURRENCY_RATES_DELETED_1),
) : AFakeCurrencyConverterDataSource(
//    isCountAllCurrencyRatesIllegalArgumentException = isCountAllCurrencyRatesIllegalArgumentException,
//    isCountAllCurrencyRatesNotImplementedError = isCountAllCurrencyRatesNotImplementedError,
    isCountAllCurrencyRatesException = isCountAllCurrencyRatesException,
    countAllCurrencyRatesResultData = countAllCurrencyRatesResultData,
//    isCountCurrencyRatesIllegalArgumentException = isCountCurrencyRatesIllegalArgumentException,
//    isCountCurrencyRatesNotImplementedError = isCountCurrencyRatesNotImplementedError,
    isCountCurrencyRatesException = isCountCurrencyRatesException,
    countCurrencyRatesResultData = countCurrencyRatesResultData,
    isGetCurrencyRatesException = isGetCurrencyRatesException,
    getCurrencyRatesResultData = getCurrencyRatesResultData,
    isSaveCurrencyRatesError = isSaveCurrencyRatesError,
    saveCurrencyRatesResultData = saveCurrencyRatesResultData,
    isDeleteCurrencyRatesException = isDeleteCurrencyRatesException,
    isDeleteCurrencyRatesErrorDuringRatesCount = isDeleteCurrencyRatesErrorDuringRatesCount,
    isDeleteCurrencyRatesErrorWhileDeletingAllRates = isDeleteCurrencyRatesErrorWhileDeletingAllRates,
    deleteCurrencyRatesResultData = deleteCurrencyRatesResultData,
) {

    private companion object {

        const val NUM_OF_CURRENCY_RATES_0: Int = 0
        const val NUM_OF_CURRENCY_RATES_DELETED_1: Int = 1

        val CURRENCY_RATES_MODEL: List<CurrencyConverterModel> = buildModelCurrencyRates()

    }

    override suspend fun countAllCurrencyRates(params: Params): ResultData<Int> =
        super.countAllCurrencyRates(params = params)

    override suspend fun countCurrencyRates(params: Params): ResultData<Int> =
        super.countCurrencyRates(params = params)

    override suspend fun getCurrencyRates(params: Params): ResultData<List<CurrencyConverterModel>> {
        when(params){
            is None -> {
                throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
            }
            is CurrencyConverterParams -> {

                if (isGetCurrencyRatesException)
                    return ResultData.Error(failure = CurrencyRateLocalError())

                if (getCurrencyRatesResultData.data.isEmpty())
                    return ResultData.Error(failure = CurrencyRateListNotAvailableLocalError())

                return getCurrencyRatesResultData
            }

            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }
    }

    override suspend fun saveCurrencyRates(currencyRatesModel: List<CurrencyConverterModel>): ResultData<List<Long>> =
        super.saveCurrencyRates(currencyRatesModel = currencyRatesModel)

    override suspend fun deleteCurrencyRates(params: Params): ResultData<Int> =
        super.deleteCurrencyRates(params = params)

}
