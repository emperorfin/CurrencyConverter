package emperorfin.android.currencyconverter.domain.datalayer.datasources

import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_BASE_USD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AFN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AFN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AFN
import emperorfin.android.currencyconverter.data.constants.StringConstants.ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED
import emperorfin.android.currencyconverter.data.constants.StringConstants.ERROR_MESSAGE_NOT_YET_IMPLEMENTED
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.DeleteCurrencyRateLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.InsertCurrencyRateLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.NonExistentCurrencyRateDataLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.CurrencyRateLocalError
import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModel
import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.Params
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.succeeded
import emperorfin.android.currencyconverter.ui.screens.events.inputs.currencyconverter.CurrencyConverterParams
import emperorfin.android.currencyconverter.ui.screens.events.inputs.currencyconverter.None
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entitysources.fake.FakeCurrencyConverterLocalDataSourceRoom
import emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.modelsources.fake.FakeCurrencyConverterRemoteDataSourceRetrofit


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Wednesday 24th January, 2024.
 */


internal abstract class AFakeCurrencyConverterDataSource(
//    private val isCountAllCurrencyRatesIllegalArgumentException: Boolean,
//    private val isCountAllCurrencyRatesNotImplementedError: Boolean,
    private val isCountAllCurrencyRatesException: Boolean,
    private val countAllCurrencyRatesResultData: ResultData.Success<Int>,
//    private val isCountCurrencyRatesIllegalArgumentException: Boolean,
//    private val isCountCurrencyRatesNotImplementedError: Boolean,
    private val isCountCurrencyRatesException: Boolean,
    private val countCurrencyRatesResultData: ResultData.Success<Int>,
    private val isGetCurrencyRatesException: Boolean,
    private val getCurrencyRatesResultData: ResultData.Success<List<CurrencyConverterModel>>,
    private val isSaveCurrencyRatesError: Boolean,
    private val saveCurrencyRatesResultData: ResultData<List<Long>>,
    private val isDeleteCurrencyRatesException: Boolean,
    private val isDeleteCurrencyRatesErrorDuringRatesCount: Boolean,
    private val isDeleteCurrencyRatesErrorWhileDeletingAllRates: Boolean,
    private val deleteCurrencyRatesResultData: ResultData.Success<Int>,
) : CurrencyConverterDataSource {

    companion object {

        private const val NUM_OF_CURRENCY_RATES_0: Int = 0

        fun buildModelCurrencyRates(): List<CurrencyConverterModel> {

            val currencyRateModel1 = CurrencyConverterModel.newInstance(
                id = ID_AED,
                rate = RATE_AED,
                currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
                currencySymbolOther = CURRENCY_SYMBOL_OTHER_AED,
            )

            val currencyRateModel2 = CurrencyConverterModel.newInstance(
                id = ID_AFN,
                rate = RATE_AFN,
                currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
                currencySymbolOther = CURRENCY_SYMBOL_OTHER_AFN,
            )

            return listOf(currencyRateModel1, currencyRateModel2)
        }

    }

    /**
     * TODO: Move this implementation to [FakeCurrencyConverterLocalDataSourceRoom.countAllCurrencyRates]
     *  when [FakeCurrencyConverterRemoteDataSourceRetrofit.countAllCurrencyRates] is implemented and
     *  make this function abstract.
     */
    override suspend fun countAllCurrencyRates(params: Params): ResultData<Int> {

//        if (isCountAllCurrencyRatesIllegalArgumentException)
//            throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
//
//        if (isCountAllCurrencyRatesNotImplementedError)
//            throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
//
//        if (isCountAllCurrencyRatesException)
//            return ResultData.Error(failure = CurrencyRateLocalError())
//
//        if (countAllCurrencyRatesResultData is ResultData.Success && countAllCurrencyRatesResultData.data == NUM_OF_CURRENCY_RATES_0)
//            return ResultData.Error(failure = NonExistentCurrencyRateDataLocalError())
//
//        if (countAllCurrencyRatesResultData is ResultData.Success && countAllCurrencyRatesResultData.data < NUM_OF_CURRENCY_RATES_0)
//            return ResultData.Error(failure = CurrencyRateLocalError())
//
//        return countAllCurrencyRatesResultData

        when(params){
            is None -> {

                if (isCountAllCurrencyRatesException)
                    return ResultData.Error(failure = CurrencyRateLocalError())

                if (countAllCurrencyRatesResultData.data == NUM_OF_CURRENCY_RATES_0)
                    return ResultData.Error(failure = NonExistentCurrencyRateDataLocalError())

                if (countAllCurrencyRatesResultData.data < NUM_OF_CURRENCY_RATES_0)
                    return ResultData.Error(failure = CurrencyRateLocalError())

                return countAllCurrencyRatesResultData

            }
            is CurrencyConverterParams -> {
                throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
            }

            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }
    }

    /**
     * TODO: Move this implementation to [FakeCurrencyConverterLocalDataSourceRoom.countCurrencyRates]
     *  when [FakeCurrencyConverterRemoteDataSourceRetrofit.countCurrencyRates] is implemented and
     *  make this function abstract.
     */
    override suspend fun countCurrencyRates(params: Params): ResultData<Int> {

//        if (isCountCurrencyRatesIllegalArgumentException)
//            throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
//
//        if (isCountCurrencyRatesNotImplementedError)
//            throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
//
//        if (isCountCurrencyRatesException)
//            return ResultData.Error(failure = CurrencyRateLocalError())
//
//        if (countCurrencyRatesResultData is ResultData.Success && countCurrencyRatesResultData.data == NUM_OF_CURRENCY_RATES_0)
//            return ResultData.Error(failure = NonExistentCurrencyRateDataLocalError())
//
//        if (countCurrencyRatesResultData is ResultData.Success && countCurrencyRatesResultData.data < NUM_OF_CURRENCY_RATES_0)
//            return ResultData.Error(failure = CurrencyRateLocalError())
//
//        return countCurrencyRatesResultData

        when(params){
            is None -> {
                throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
            }
            is CurrencyConverterParams -> {

                if (isCountCurrencyRatesException)
                    return ResultData.Error(failure = CurrencyRateLocalError())

                if (countCurrencyRatesResultData.data == NUM_OF_CURRENCY_RATES_0)
                    return ResultData.Error(failure = NonExistentCurrencyRateDataLocalError())

                if (countCurrencyRatesResultData.data < NUM_OF_CURRENCY_RATES_0)
                    return ResultData.Error(failure = CurrencyRateLocalError())

                return countCurrencyRatesResultData
            }

            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }
    }

    abstract override suspend fun getCurrencyRates(params: Params): ResultData<List<CurrencyConverterModel>>

    /**
     * TODO: Move this implementation to [FakeCurrencyConverterLocalDataSourceRoom.saveCurrencyRates]
     *  when [FakeCurrencyConverterRemoteDataSourceRetrofit.saveCurrencyRates] is implemented and
     *  make this function abstract.
     */
    override suspend fun saveCurrencyRates(currencyRatesModel: List<CurrencyConverterModel>): ResultData<List<Long>> {

        if (currencyRatesModel.isEmpty())
            return ResultData.Error(
                failure = InsertCurrencyRateLocalError(message = R.string.error_cant_save_empty_currency_rate_list)
            )

        if (isSaveCurrencyRatesError)
            return ResultData.Error(
                failure = InsertCurrencyRateLocalError(message = R.string.error_all_currency_rates_not_saved)
            )

        if (saveCurrencyRatesResultData.succeeded && (saveCurrencyRatesResultData as ResultData.Success).data.isEmpty())
            throw IllegalArgumentException(
                "The property saveCurrencyRatesResultData must be of type ResultData.Success<List<Long>> " +
                        "where saveCurrencyRatesResultData.data must not be empty"
            )

        return saveCurrencyRatesResultData
    }

    /**
     * TODO: Move this implementation to [FakeCurrencyConverterLocalDataSourceRoom.deleteCurrencyRates]
     *  when [FakeCurrencyConverterRemoteDataSourceRetrofit.deleteCurrencyRates] is implemented and
     *  make this function abstract.
     */
    override suspend fun deleteCurrencyRates(params: Params): ResultData<Int> {

        when(params){
            is None -> {
                throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
            }
            is CurrencyConverterParams -> {

                if (isDeleteCurrencyRatesException)
                    return ResultData.Error(failure = DeleteCurrencyRateLocalError())

                if (isDeleteCurrencyRatesErrorDuringRatesCount)
                    return ResultData.Error(failure = DeleteCurrencyRateLocalError())

                if (isDeleteCurrencyRatesErrorWhileDeletingAllRates)
                    return ResultData.Error(failure = DeleteCurrencyRateLocalError(R.string.error_deleting_currency_rates))

                if (deleteCurrencyRatesResultData.data < 1)
                    throw IllegalArgumentException("deleteCurrencyRatesResultData.data must be greater than 0.")

                return deleteCurrencyRatesResultData
            }

            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }

    }

}