package emperorfin.android.currencyconverter.data.repositories.test

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
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entitysources.fake.FakeCurrencyConverterLocalDataSourceRoom
import emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.modelsources.fake.FakeCurrencyConverterRemoteDataSourceRetrofit
import emperorfin.android.currencyconverter.data.repositories.CurrencyConverterRepository
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.CurrencyRateLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.NonExistentCurrencyRateDataLocalError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.GetCurrencyRateRepositoryError
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
 * @Date: Wednesday 24th January, 2024.
 */


@OptIn(ExperimentalCoroutinesApi::class)
internal class CurrencyConverterRepositoryTest {

    private companion object {

        const val NUM_OF_CURRENCY_RATES_MINUS_1: Int = -1
        const val NUM_OF_CURRENCY_RATES_0: Int = 0
        const val NUM_OF_CURRENCY_RATES_150: Int = 150

        const val IS_FORCE_UPDATE_FALSE: Boolean = false
        const val IS_FORCE_UPDATE_TRUE: Boolean = true
        const val IS_COUNT_REMOTELY_FALSE: Boolean = false
        const val IS_COUNT_REMOTELY_TRUE: Boolean = true
        const val IS_GET_CURRENCY_RATES_RESPONSE_UNSUCCESSFUL_TRUE: Boolean = true
        const val IS_SAVE_REMOTELY_FALSE: Boolean = false
        const val IS_SAVE_REMOTELY_TRUE: Boolean = true
        const val IS_DELETE_REMOTELY_FALSE: Boolean = false
        const val IS_DELETE_REMOTELY_TRUE: Boolean = true
        const val IS_EXCEPTION_TRUE: Boolean = true
        const val IS_SAVE_CURRENCY_RATES_ERROR_TRUE: Boolean = true
        const val IS_DELETE_CURRENCY_RATES_ERROR_WHILE_DELETING_ALL_RATE_TRUE: Boolean = true
        const val IS_DELETE_CURRENCY_RATES_ERROR_DURING_RATES_COUNT_TRUE: Boolean = true

        const val ERROR_MESSAGE_TODO_NOT_YET_IMPLEMENTED: String =
            "An operation is not implemented: Not yet implemented"

        val PARAMS_NONE: None = None()
        val PARAMS_CURRENCY_CONVERTER: CurrencyConverterParams = CurrencyConverterParams(currencySymbolBase = "USD")
        val PARAMS_BAD: BadParams = BadParams()

    }

    private lateinit var currencyConverterLocalDataSourceRoom: FakeCurrencyConverterLocalDataSourceRoom
    private lateinit var currencyConverterRemoteDataSourceRetrofit: FakeCurrencyConverterRemoteDataSourceRetrofit

    // Class under test
    private lateinit var currencyConverterRepository: CurrencyConverterRepository

    @JvmField
    @Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun createRepository() {

        currencyConverterLocalDataSourceRoom = FakeCurrencyConverterLocalDataSourceRoom()
        currencyConverterRemoteDataSourceRetrofit = FakeCurrencyConverterRemoteDataSourceRetrofit()

        currencyConverterRepository = CurrencyConverterRepository(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom,
            currencyConverterRemoteDataSource = currencyConverterRemoteDataSourceRetrofit,
            ioDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun countAllCurrencyRates_LocalDataSourceCurrencyRatesMoreThanZero() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            countAllCurrencyRatesResultData = ResultData.Success(NUM_OF_CURRENCY_RATES_150)
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val noOfCurrencyRatesExpected: Int = NUM_OF_CURRENCY_RATES_150

        val params = PARAMS_NONE

        val numOfAllCurrencyRatesResultData: ResultData.Success<Int> = currencyConverterRepository
            .countAllCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_FALSE) as ResultData.Success

        assertThat(numOfAllCurrencyRatesResultData.data, IsEqual(noOfCurrencyRatesExpected))
    }

    @Test
    fun countAllCurrencyRates_LocalDataSourceNonExistentCurrencyRateDataError() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            countAllCurrencyRatesResultData = ResultData.Success(NUM_OF_CURRENCY_RATES_0)
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val params = PARAMS_NONE

        val errorResultData: ResultData.Error = currencyConverterRepository
            .countAllCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_FALSE) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(NonExistentCurrencyRateDataLocalError::class.java))
    }

    @Test
    fun countAllCurrencyRates_LocalDataSourceGeneralError() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            countAllCurrencyRatesResultData = ResultData.Success(NUM_OF_CURRENCY_RATES_MINUS_1)
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val params = PARAMS_NONE

        val errorResultData: ResultData.Error = currencyConverterRepository
            .countAllCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_FALSE) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyRateLocalError::class.java))
    }

    @Test
    fun countAllCurrencyRates_LocalDataSourceExceptionThrown() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            isCountAllCurrencyRatesException = IS_EXCEPTION_TRUE
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val params = PARAMS_NONE

        val errorResultData: ResultData.Error = currencyConverterRepository
            .countAllCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_FALSE) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyRateLocalError::class.java))
    }

    @Test
    fun countAllCurrencyRates_LocalDataSourceIllegalArgumentExceptionThrown() = runBlockingTest {

//        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
//            isCountAllCurrencyRatesIllegalArgumentException = IS_EXCEPTION_TRUE
//        )
//
//        currencyConverterRepository = currencyConverterRepository.copy(
//            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
//        )

        val params = CurrencyConverterParams()

        expectedException.expect(IllegalArgumentException::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED))

        currencyConverterRepository.countAllCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_FALSE)
    }

    @Test
    fun countAllCurrencyRates_LocalDataSourceNotImplementedErrorThrown() = runBlockingTest {

//        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
//            isCountAllCurrencyRatesNotImplementedError = IS_EXCEPTION_TRUE
//        )
//
//        currencyConverterRepository = currencyConverterRepository.copy(
//            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
//        )

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_NOT_YET_IMPLEMENTED))

        currencyConverterRepository.countAllCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_FALSE)
    }

    @Test
    fun countCurrencyRates_LocalDataSourceCurrencyRatesMoreThanZero() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            countCurrencyRatesResultData = ResultData.Success(NUM_OF_CURRENCY_RATES_150)
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val noOfCurrencyRatesExpected: Int = NUM_OF_CURRENCY_RATES_150

        val params = PARAMS_CURRENCY_CONVERTER

        val numOfAllCurrencyRatesResultData: ResultData.Success<Int> = currencyConverterRepository
            .countCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_FALSE) as ResultData.Success

        assertThat(numOfAllCurrencyRatesResultData.data, IsEqual(noOfCurrencyRatesExpected))
    }

    @Test
    fun countCurrencyRates_LocalDataSourceNonExistentCurrencyRateDataError() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            countCurrencyRatesResultData = ResultData.Success(NUM_OF_CURRENCY_RATES_0)
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterRepository
            .countCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_FALSE) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(NonExistentCurrencyRateDataLocalError::class.java))
    }

    @Test
    fun countCurrencyRates_LocalDataSourceGeneralError() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            countCurrencyRatesResultData = ResultData.Success(NUM_OF_CURRENCY_RATES_MINUS_1)
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterRepository
            .countCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_FALSE) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyRateLocalError::class.java))
    }

    @Test
    fun countCurrencyRates_LocalDataSourceExceptionThrown() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            isCountCurrencyRatesException = IS_EXCEPTION_TRUE
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterRepository
            .countCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_FALSE) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyRateLocalError::class.java))
    }

    @Test
    fun countCurrencyRates_LocalDataSourceIllegalArgumentExceptionThrown() = runBlockingTest {

//        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
//            isCountCurrencyRatesIllegalArgumentException = IS_EXCEPTION_TRUE
//        )
//
//        currencyConverterRepository = currencyConverterRepository.copy(
//            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
//        )

        val params = PARAMS_NONE

        expectedException.expect(IllegalArgumentException::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED))

        currencyConverterRepository.countCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_FALSE)
    }

    @Test
    fun countCurrencyRates_LocalDataSourceNotImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_NOT_YET_IMPLEMENTED))

        currencyConverterRepository.countCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_FALSE)
    }

    @Test
    fun getCurrencyRates_LocalDataSourceCurrencyRatesListNotEmpty() = runBlockingTest {

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

        val currencyRatesModelResultData: ResultData.Success<List<CurrencyConverterModel>> = currencyConverterRepository
            .getCurrencyRates(params = params, forceUpdate = IS_FORCE_UPDATE_FALSE) as ResultData.Success

        assertThat(currencyRatesModelResultData.data, IsEqual(currencyRatesModel))
    }

    @Test
    fun getCurrencyRates_LocalDataSourceCurrencyRateListNotAvailableLocalError() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            getCurrencyRatesResultData = ResultData.Success(emptyList())
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val errorMessageExpected: Int = R.string.error_fetching_from_remote_and_local

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterRepository
            .getCurrencyRates(params = params, forceUpdate = IS_FORCE_UPDATE_FALSE) as ResultData.Error

        val errorMessageActual: Int = (errorResultData.failure as GetCurrencyRateRepositoryError).message

        assertThat(errorMessageActual, IsEqual(errorMessageExpected))
        assertThat(errorResultData.failure, IsInstanceOf(GetCurrencyRateRepositoryError::class.java))
    }

    @Test
    fun getCurrencyRates_LocalDataSourceExceptionThrown() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            isGetCurrencyRatesException = IS_EXCEPTION_TRUE
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val errorMessageExpected: Int = R.string.exception_occurred_local

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterRepository
            .getCurrencyRates(params = params, forceUpdate = IS_FORCE_UPDATE_FALSE) as ResultData.Error

        val errorMessageActual: Int = (errorResultData.failure as GetCurrencyRateRepositoryError).message

        assertThat(errorMessageActual, IsEqual(errorMessageExpected))
        assertThat(errorResultData.failure, IsInstanceOf(GetCurrencyRateRepositoryError::class.java))
    }

    @Test
    fun getCurrencyRates_LocalDataSourceIllegalArgumentExceptionThrown() = runBlockingTest {

        val params = PARAMS_NONE

        expectedException.expect(IllegalArgumentException::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED))

        currencyConverterRepository.getCurrencyRates(params = params, forceUpdate = IS_FORCE_UPDATE_FALSE)
    }

    @Test
    fun getCurrencyRates_LocalDataSourceNotImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_NOT_YET_IMPLEMENTED))

        currencyConverterRepository.getCurrencyRates(params = params, forceUpdate = IS_FORCE_UPDATE_FALSE)
    }

    @Test
    fun saveCurrencyRates_LocalDataSourceCurrencyRatesListNotEmpty() = runBlockingTest {
        val tableRowIdsExpected: List<Long> = listOf(1L, 2L)

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            saveCurrencyRatesResultData = ResultData.Success(tableRowIdsExpected)
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
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

        val currencyRatesModel: List<CurrencyConverterModel> = listOf(currencyRateModel1, currencyRateModel2)

        val tableRowIdsResultData: ResultData.Success<List<Long>> = currencyConverterRepository
            .saveCurrencyRates(currencyRatesModel = currencyRatesModel, saveRemotely = IS_SAVE_REMOTELY_FALSE) as ResultData.Success

        assertThat(tableRowIdsResultData.data, IsEqual(tableRowIdsExpected))
    }

    @Test
    fun saveCurrencyRates_LocalDataSourceCurrencyRatesListIsEmpty() = runBlockingTest {

        val errorMessageExpected: Int = R.string.error_cant_save_empty_currency_rate_list

        val currencyRatesModel = emptyList<CurrencyConverterModel>()

        val errorResultData: ResultData.Error = currencyConverterRepository
            .saveCurrencyRates(currencyRatesModel = currencyRatesModel, saveRemotely = IS_SAVE_REMOTELY_FALSE) as ResultData.Error

        val errorMessageActual: Int = (errorResultData.failure as CurrencyConverterFailure.InsertCurrencyRateLocalError).message

        assertThat(errorMessageActual, IsEqual(errorMessageExpected))
        assertThat(errorResultData.failure, IsInstanceOf(CurrencyConverterFailure.InsertCurrencyRateLocalError::class.java))
    }

    @Test
    fun saveCurrencyRates_LocalDataSourceAllCurrencyRatesNotSavedError() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            isSaveCurrencyRatesError = IS_SAVE_CURRENCY_RATES_ERROR_TRUE
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val errorMessageExpected: Int = R.string.error_all_currency_rates_not_saved

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

        val errorResultData: ResultData.Error = currencyConverterRepository
            .saveCurrencyRates(currencyRatesModel = currencyRatesModel, saveRemotely = IS_SAVE_REMOTELY_FALSE) as ResultData.Error

        val errorMessageActual: Int = (errorResultData.failure as CurrencyConverterFailure.InsertCurrencyRateLocalError).message

        assertThat(errorMessageActual, IsEqual(errorMessageExpected))
        assertThat(errorResultData.failure, IsInstanceOf(CurrencyConverterFailure.InsertCurrencyRateLocalError::class.java))
    }

    @Test
    fun deleteCurrencyRates_LocalDataSourceCurrencyRatesDeletedSuccessfully() = runBlockingTest {
        val numOfCurrencyRatesDeletedExpected: Int = NUM_OF_CURRENCY_RATES_150

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            deleteCurrencyRatesResultData = ResultData.Success(numOfCurrencyRatesDeletedExpected)
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val numOfCurrencyRatesDeletedResultData: ResultData.Success<Int> = currencyConverterRepository
            .deleteCurrencyRates(params = params, deleteRemotely = IS_DELETE_REMOTELY_FALSE) as ResultData.Success

        assertThat(numOfCurrencyRatesDeletedResultData.data, IsEqual(numOfCurrencyRatesDeletedExpected))
    }

    @Test
    fun deleteCurrencyRates_LocalDataSourceErrorDeletingCurrencyRates() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            isDeleteCurrencyRatesErrorWhileDeletingAllRates = IS_DELETE_CURRENCY_RATES_ERROR_WHILE_DELETING_ALL_RATE_TRUE
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorMessageExpected: Int = R.string.error_deleting_currency_rates

        val errorResultData: ResultData.Error = currencyConverterRepository
            .deleteCurrencyRates(params = params, deleteRemotely = IS_DELETE_REMOTELY_FALSE) as ResultData.Error

        val errorMessageActual: Int = (errorResultData.failure as CurrencyConverterFailure.DeleteCurrencyRateLocalError).message

        assertThat(errorMessageActual, IsEqual(errorMessageExpected))
        assertThat(errorResultData.failure, IsInstanceOf(CurrencyConverterFailure.DeleteCurrencyRateLocalError::class.java))
    }

    @Test
    fun deleteCurrencyRates_LocalDataSourceExceptionThrownWhileCountingCurrencyRates() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            isDeleteCurrencyRatesErrorDuringRatesCount = IS_DELETE_CURRENCY_RATES_ERROR_DURING_RATES_COUNT_TRUE
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterRepository
            .deleteCurrencyRates(params = params, deleteRemotely = IS_DELETE_REMOTELY_FALSE) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyConverterFailure.DeleteCurrencyRateLocalError::class.java))
    }

    @Test
    fun deleteCurrencyRates_LocalDataSourceExceptionThrown() = runBlockingTest {

        currencyConverterLocalDataSourceRoom = currencyConverterLocalDataSourceRoom.copy(
            isDeleteCurrencyRatesException = IS_EXCEPTION_TRUE
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterLocalDataSource = currencyConverterLocalDataSourceRoom
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterRepository
            .deleteCurrencyRates(params = params, deleteRemotely = IS_DELETE_REMOTELY_FALSE) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyConverterFailure.DeleteCurrencyRateLocalError::class.java))
    }

    @Test
    fun deleteCurrencyRates_LocalDataSourceIllegalArgumentExceptionThrown() = runBlockingTest {

        val params = PARAMS_NONE

        expectedException.expect(IllegalArgumentException::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED))

        currencyConverterRepository.deleteCurrencyRates(params = params, deleteRemotely = IS_DELETE_REMOTELY_FALSE)
    }

    @Test
    fun deleteCurrencyRates_LocalDataSourceNotImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_NOT_YET_IMPLEMENTED))

        currencyConverterRepository.deleteCurrencyRates(params = params, deleteRemotely = IS_DELETE_REMOTELY_FALSE)
    }

    @Test
    fun getCurrencyRates_RemoteDataSourceCurrencyRatesListNotEmpty() = runBlockingTest {

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

        val currencyRatesModelResultData: ResultData.Success<List<CurrencyConverterModel>> =
            currencyConverterRepository.getCurrencyRates(params = params, forceUpdate = IS_FORCE_UPDATE_TRUE) as ResultData.Success

        assertThat(currencyRatesModelResultData.data, IsEqual(currencyRatesModel))
    }

    @Test
    fun getCurrencyRates_RemoteDataSourceGetCurrencyRatesRemoteError() = runBlockingTest {

        currencyConverterRemoteDataSourceRetrofit = currencyConverterRemoteDataSourceRetrofit.copy(
            isGetCurrencyRatesResponseUnsuccessful = IS_GET_CURRENCY_RATES_RESPONSE_UNSUCCESSFUL_TRUE
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterRemoteDataSource = currencyConverterRemoteDataSourceRetrofit
        )

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterRepository
            .getCurrencyRates(params = params, forceUpdate = IS_FORCE_UPDATE_TRUE) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyConverterFailure.GetCurrencyRateRemoteError::class.java))
    }

    @Test
    fun getCurrencyRates_RemoteDataSourceExceptionThrown() = runBlockingTest {

        currencyConverterRemoteDataSourceRetrofit = currencyConverterRemoteDataSourceRetrofit.copy(
            isGetCurrencyRatesException = IS_EXCEPTION_TRUE
        )

        currencyConverterRepository = currencyConverterRepository.copy(
            currencyConverterRemoteDataSource = currencyConverterRemoteDataSourceRetrofit
        )

        val errorMessageExpected: Int = R.string.exception_occurred_remote

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error = currencyConverterRepository
            .getCurrencyRates(params = params, forceUpdate = IS_FORCE_UPDATE_TRUE) as ResultData.Error

        val errorMessageActual: Int = (errorResultData.failure as GetCurrencyRateRepositoryError).message

        assertThat(errorMessageActual, IsEqual(errorMessageExpected))
        assertThat(errorResultData.failure, IsInstanceOf(GetCurrencyRateRepositoryError::class.java))
    }

    @Test
    fun getCurrencyRates_RemoteDataSourceIllegalArgumentExceptionThrown() = runBlockingTest {

        val params = PARAMS_NONE

        expectedException.expect(IllegalArgumentException::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED))

        currencyConverterRepository.getCurrencyRates(params = params, forceUpdate = IS_FORCE_UPDATE_TRUE)
    }

    @Test
    fun getCurrencyRates_RemoteDataSourceNotImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_NOT_YET_IMPLEMENTED))

        currencyConverterRepository.getCurrencyRates(params = params, forceUpdate = IS_FORCE_UPDATE_TRUE)
    }

    @Test
    fun countAllCurrencyRates_RemoteDataSourceNotYetImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_TODO_NOT_YET_IMPLEMENTED))

        currencyConverterRepository.countAllCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_TRUE)
    }

    @Test
    fun countCurrencyRates_RemoteDataSourceNotYetImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_TODO_NOT_YET_IMPLEMENTED))

        currencyConverterRepository.countCurrencyRates(params = params, countRemotely = IS_COUNT_REMOTELY_TRUE)
    }

    @Test
    fun saveCurrencyRates_RemoteDataSourceNotYetImplementedErrorThrown() = runBlockingTest {

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_TODO_NOT_YET_IMPLEMENTED))

        currencyConverterRepository.saveCurrencyRates(currencyRatesModel = emptyList(), saveRemotely = IS_SAVE_REMOTELY_TRUE)
    }

    @Test
    fun deleteCurrencyRates_RemoteDataSourceNotYetImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_TODO_NOT_YET_IMPLEMENTED))

        currencyConverterRepository.deleteCurrencyRates(params = params, deleteRemotely = IS_DELETE_REMOTELY_TRUE)
    }

}

private class BadParams : Params