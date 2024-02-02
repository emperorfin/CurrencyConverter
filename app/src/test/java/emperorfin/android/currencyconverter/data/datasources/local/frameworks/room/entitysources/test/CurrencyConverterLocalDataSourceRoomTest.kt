package emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entitysources.test

import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_BASE_USD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AFN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_ALL
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AFN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_ALL
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AFN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_ALL
import emperorfin.android.currencyconverter.data.constants.StringConstants.ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED
import emperorfin.android.currencyconverter.data.constants.StringConstants.ERROR_MESSAGE_NOT_YET_IMPLEMENTED
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.dao.FakeCurrencyRatesDao
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.dao.FakeCurrencyRatesDao.Companion.NUM_OF_CURRENCY_RATES_150
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.dao.FakeCurrencyRatesDao.Companion.TABLE_ROW_IDS_TWO
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entitysources.CurrencyConverterLocalDataSourceRoom
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.DeleteCurrencyRateLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.InsertCurrencyRateLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.CurrencyRateListNotAvailableLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.CurrencyRateLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.NonExistentCurrencyRateDataLocalError
import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModel
import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.Params
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData
import emperorfin.android.currencyconverter.ui.screens.events.inputs.currencyconverter.CurrencyConverterParams
import emperorfin.android.currencyconverter.ui.screens.events.inputs.currencyconverter.None
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 05th January, 2024.
 */


@OptIn(ExperimentalCoroutinesApi::class)
internal class CurrencyConverterLocalDataSourceRoomTest {

    private companion object {

        const val NUM_OF_CURRENCY_RATES_MINUS_1: Int = -1
        const val NUM_OF_CURRENCY_RATES_0: Int = 0
        const val NUM_OF_CURRENCY_RATES_DELETED_1: Int = 1

        const val IS_EXCEPTION_TRUE: Boolean = true
        const val IS_CURRENCY_RATES_LIST_EMPTY_TRUE: Boolean = true

        val PARAMS_NONE: None = None()
        val PARAMS_CURRENCY_CONVERTER: CurrencyConverterParams = CurrencyConverterParams(currencySymbolBase = "USD")
        val PARAMS_BAD: BadParams = BadParams()

    }

    private lateinit var currencyRatesDao: FakeCurrencyRatesDao

    // Class under test
    private lateinit var currencyConverterLocalDataSourceRoom: CurrencyConverterLocalDataSourceRoom

    @JvmField
    @Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun createLocalDataSource() {

        currencyRatesDao = FakeCurrencyRatesDao()

        currencyConverterLocalDataSourceRoom = CurrencyConverterLocalDataSourceRoom(
            currencyRateDao = currencyRatesDao,
            ioDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun countAllCurrencyRates_CurrencyRatesMoreThanZero() = runBlockingTest {
        val noOfCurrencyRatesExpected: Int = NUM_OF_CURRENCY_RATES_150

        val params = PARAMS_NONE

        val numOfAllCurrencyRatesResultData: ResultData.Success<Int> = currencyConverterLocalDataSourceRoom
            .countAllCurrencyRates(params = params) as ResultData.Success

        assertThat(numOfAllCurrencyRatesResultData.data, IsEqual(noOfCurrencyRatesExpected))
    }

    @Test
    fun countAllCurrencyRates_NonExistentCurrencyRateDataError() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(noOfCurrencyRates = NUM_OF_CURRENCY_RATES_0)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

        val params = PARAMS_NONE

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .countAllCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(NonExistentCurrencyRateDataLocalError::class.java))
    }

    @Test
    fun countAllCurrencyRates_GeneralError() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(noOfCurrencyRates = NUM_OF_CURRENCY_RATES_MINUS_1)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

        val params = PARAMS_NONE

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .countAllCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyRateLocalError::class.java))
    }

    @Test
    fun countAllCurrencyRates_ExceptionThrown() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(isCountException = IS_EXCEPTION_TRUE)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

        val params = PARAMS_NONE

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .countAllCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyRateLocalError::class.java))
    }

    // Docs: https://stackoverflow.com/a/49817981
    @Test
    fun countAllCurrencyRates_IllegalArgumentExceptionThrown() = runBlockingTest {

        val params = CurrencyConverterParams()

        expectedException.expect(IllegalArgumentException::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED))

        currencyConverterLocalDataSourceRoom.countAllCurrencyRates(params = params)
    }

    @Test
    fun countAllCurrencyRates_NotImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_NOT_YET_IMPLEMENTED))

        currencyConverterLocalDataSourceRoom.countAllCurrencyRates(params = params)
    }

    @Test
    fun countCurrencyRates_CurrencyRatesMoreThanZero() = runBlockingTest {
        val noOfCurrencyRatesExpected: Int = NUM_OF_CURRENCY_RATES_150

        val params = PARAMS_CURRENCY_CONVERTER

        val numOfCurrencyRatesResultData: ResultData.Success<Int> = currencyConverterLocalDataSourceRoom
            .countCurrencyRates(params = params) as ResultData.Success

        assertThat(numOfCurrencyRatesResultData.data, IsEqual(noOfCurrencyRatesExpected))
    }

    @Test
    fun countCurrencyRates_NonExistentCurrencyRateDataError() = runBlockingTest {
        currencyRatesDao = currencyRatesDao.copy(noOfCurrencyRates = NUM_OF_CURRENCY_RATES_0)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .countCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(NonExistentCurrencyRateDataLocalError::class.java))
    }

    @Test
    fun countCurrencyRates_GeneralError() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(noOfCurrencyRates = NUM_OF_CURRENCY_RATES_MINUS_1)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .countCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyRateLocalError::class.java))
    }

    @Test
    fun countCurrencyRates_ExceptionThrown() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(isCountException = IS_EXCEPTION_TRUE)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .countCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyRateLocalError::class.java))
    }

    @Test
    fun countCurrencyRates_ExceptionThrownWhenBaseCurrencySymbolParamsIsNull() = runBlockingTest {

        val params = CurrencyConverterParams() // When currencySymbolBase is null.

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .countCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyRateLocalError::class.java))
    }

    @Test
    fun countCurrencyRates_IllegalArgumentExceptionThrown() = runBlockingTest {

        val params = PARAMS_NONE

        expectedException.expect(IllegalArgumentException::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED))

        currencyConverterLocalDataSourceRoom.countCurrencyRates(params = params)
    }

    @Test
    fun countCurrencyRates_NotImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_NOT_YET_IMPLEMENTED))

        currencyConverterLocalDataSourceRoom.countCurrencyRates(params = params)
    }

    @Test
    fun getCurrencyRates_CurrencyRatesListNotEmpty() = runBlockingTest {

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

        val currencyRatesModel = listOf(currencyRateModel1, currencyRateModel2)

        val params = PARAMS_CURRENCY_CONVERTER

        val currencyRatesModelResultData: ResultData.Success<List<CurrencyConverterModel>> = currencyConverterLocalDataSourceRoom
            .getCurrencyRates(params = params) as ResultData.Success

        assertThat(currencyRatesModelResultData.data, IsEqual(currencyRatesModel))
    }

    @Test
    fun getCurrencyRates_CurrencyRateListNotAvailableLocalError() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(isEmptyList = IS_CURRENCY_RATES_LIST_EMPTY_TRUE)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .getCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyRateListNotAvailableLocalError::class.java))
    }

    @Test
    fun getCurrencyRates_ExceptionThrown() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(isException = IS_EXCEPTION_TRUE)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .getCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyRateLocalError::class.java))
    }

    @Test
    fun getCurrencyRates_IllegalArgumentExceptionThrown() = runBlockingTest {

        val params = PARAMS_NONE

        expectedException.expect(IllegalArgumentException::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED))

        currencyConverterLocalDataSourceRoom.getCurrencyRates(params = params)
    }

    @Test
    fun getCurrencyRates_NotImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_NOT_YET_IMPLEMENTED))

        currencyConverterLocalDataSourceRoom.getCurrencyRates(params = params)
    }

    @Test
    fun saveCurrencyRates_CurrencyRatesListNotEmpty() = runBlockingTest {
        val tableRowIdsExpected: List<Long> = TABLE_ROW_IDS_TWO

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

        val currencyRatesModel: List<CurrencyConverterModel> = listOf(currencyRateModel1, currencyRateModel2)

        // TODO: Rename currencyRatesModelResultData to tableRowIdsResultData
        val currencyRatesModelResultData: ResultData.Success<List<Long>> = currencyConverterLocalDataSourceRoom
            .saveCurrencyRates(currencyRatesModel = currencyRatesModel) as ResultData.Success

        assertThat(currencyRatesModelResultData.data, IsEqual(tableRowIdsExpected))
    }

    @Test
    fun saveCurrencyRates_CurrencyRatesListIsEmpty() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(isEmptyList = IS_CURRENCY_RATES_LIST_EMPTY_TRUE)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

        val errorMessageExpected: Int = R.string.error_cant_save_empty_currency_rate_list

        val currencyRatesModel = emptyList<CurrencyConverterModel>()

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .saveCurrencyRates(currencyRatesModel = currencyRatesModel) as ResultData.Error

        val errorMessageActual: Int = (errorResultData.failure as InsertCurrencyRateLocalError).message

        assertThat(errorMessageActual, IsEqual(errorMessageExpected))
        assertThat(errorResultData.failure, IsInstanceOf(InsertCurrencyRateLocalError::class.java))
    }

    @Test
    fun saveCurrencyRates_AllCurrencyRatesNotSavedError() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(tableRowIds = TABLE_ROW_IDS_TWO)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

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

        val currencyRateModel3 = CurrencyConverterModel.newInstance(
            id = ID_ALL,
            rate = RATE_ALL,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_ALL,
        )

        val currencyRatesModel: List<CurrencyConverterModel> =
            listOf(currencyRateModel1, currencyRateModel2, currencyRateModel3)

        val errorMessageExpected: Int = R.string.error_all_currency_rates_not_saved

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .saveCurrencyRates(currencyRatesModel = currencyRatesModel) as ResultData.Error

        val errorMessageActual: Int = (errorResultData.failure as InsertCurrencyRateLocalError).message

        assertThat(errorMessageActual, IsEqual(errorMessageExpected))
        assertThat(errorResultData.failure, IsInstanceOf(InsertCurrencyRateLocalError::class.java))
    }

    @Test
    fun deleteCurrencyRates_CurrencyRatesDeletedSuccessfully() = runBlockingTest {
        val numOfCurrencyRatesDeletedExpected: Int = NUM_OF_CURRENCY_RATES_150

        val params = PARAMS_CURRENCY_CONVERTER

        val numOfCurrencyRatesDeletedResultData: ResultData.Success<Int> = currencyConverterLocalDataSourceRoom
            .deleteCurrencyRates(params = params) as ResultData.Success

        assertThat(numOfCurrencyRatesDeletedResultData.data, IsEqual(numOfCurrencyRatesDeletedExpected))
    }

    @Test
    fun deleteCurrencyRates_ExceptionThrownWhileCountingCurrencyRates() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(isCountException = IS_EXCEPTION_TRUE)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .deleteCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(DeleteCurrencyRateLocalError::class.java))
    }

    @Test
    fun deleteCurrencyRates_ErrorDeletingCurrencyRates() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(noOfCurrencyRatesDeleted = NUM_OF_CURRENCY_RATES_DELETED_1)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorMessageExpected: Int = R.string.error_deleting_currency_rates

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .deleteCurrencyRates(params = params) as ResultData.Error

        val errorMessageActual: Int = (errorResultData.failure as DeleteCurrencyRateLocalError).message

        assertThat(errorMessageActual, IsEqual(errorMessageExpected))
        assertThat(errorResultData.failure, IsInstanceOf(DeleteCurrencyRateLocalError::class.java))
    }

    @Test
    fun deleteCurrencyRates_ExceptionThrown() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(isException = IS_EXCEPTION_TRUE)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            currencyRateDao = currencyRatesDao
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterLocalDataSourceRoom
            .deleteCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(DeleteCurrencyRateLocalError::class.java))
    }

    @Test
    fun deleteCurrencyRates_IllegalArgumentExceptionThrown() = runBlockingTest {

        val params = PARAMS_NONE

        expectedException.expect(IllegalArgumentException::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED))

        currencyConverterLocalDataSourceRoom.deleteCurrencyRates(params = params)
    }

    @Test
    fun deleteCurrencyRates_NotImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_NOT_YET_IMPLEMENTED))

        currencyConverterLocalDataSourceRoom.deleteCurrencyRates(params = params)
    }

}

private class BadParams : Params