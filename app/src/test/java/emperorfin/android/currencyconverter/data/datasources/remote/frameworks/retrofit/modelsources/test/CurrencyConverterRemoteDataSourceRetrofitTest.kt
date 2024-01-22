package emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.modelsources.test

import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_BASE_USD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AFN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AFN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AFN
import emperorfin.android.currencyconverter.data.constants.StringConstants.ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED
import emperorfin.android.currencyconverter.data.constants.StringConstants.ERROR_MESSAGE_NOT_YET_IMPLEMENTED
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.dao.FakeCurrencyRatesDao
import emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.modelsources.CurrencyConverterRemoteDataSourceRetrofit
import emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.webservices.openexchangerates.service.fake.FakeOpenExchangeRatesService
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.GetCurrencyRateRemoteError
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure.CurrencyRateRemoteError
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
 * @Date: Friday 19th January, 2024.
 */


@OptIn(ExperimentalCoroutinesApi::class)
internal class CurrencyConverterRemoteDataSourceRetrofitTest {

    private companion object {

        const val ERROR_MESSAGE_TODO_NOT_YET_IMPLEMENTED: String =
            "An operation is not implemented: Not yet implemented"

        const val IS_EXCEPTION_TRUE: Boolean = true
        const val IS_GET_CURRENCY_RATES_FAILED_TRUE: Boolean = true

        val PARAMS_NONE: None = None()
        val PARAMS_CURRENCY_CONVERTER: CurrencyConverterParams = CurrencyConverterParams(currencySymbolBase = "USD")
        val PARAMS_BAD: BadParams = BadParams()

    }

    private lateinit var currencyRatesDao: FakeOpenExchangeRatesService

    // Class under test
    private lateinit var currencyConverterRemoteDataSourceRetrofit: CurrencyConverterRemoteDataSourceRetrofit

    @JvmField
    @Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun createLocalDataSource() {

        currencyRatesDao = FakeOpenExchangeRatesService()

        currencyConverterRemoteDataSourceRetrofit = CurrencyConverterRemoteDataSourceRetrofit(
            currencyRatesDao = currencyRatesDao,
            ioDispatcher = Dispatchers.Unconfined,
            mainDispatcher = Dispatchers.Unconfined,
        )
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

        val currencyRatesModelResultData: ResultData.Success<List<CurrencyConverterModel>> =
            currencyConverterRemoteDataSourceRetrofit.getCurrencyRates(params = params) as ResultData.Success

        assertThat(currencyRatesModelResultData.data, IsEqual(currencyRatesModel))
    }

    @Test
    fun getCurrencyRates_GetCurrencyRatesRemoteError() = runBlockingTest {

        currencyRatesDao = currencyRatesDao
            .copy(isGetRemoteCurrencyRatesFailed = IS_GET_CURRENCY_RATES_FAILED_TRUE)

        currencyConverterRemoteDataSourceRetrofit = currencyConverterRemoteDataSourceRetrofit
            .copy(currencyRatesDao = currencyRatesDao)

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error =
            currencyConverterRemoteDataSourceRetrofit.getCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(GetCurrencyRateRemoteError::class.java))
    }

    @Test
    fun getCurrencyRates_ExceptionThrown() = runBlockingTest {

        currencyRatesDao = currencyRatesDao.copy(isException = IS_EXCEPTION_TRUE)

        currencyConverterRemoteDataSourceRetrofit = currencyConverterRemoteDataSourceRetrofit
            .copy(currencyRatesDao = currencyRatesDao)

        val params = PARAMS_CURRENCY_CONVERTER

        val errorResultData: ResultData.Error =
            currencyConverterRemoteDataSourceRetrofit.getCurrencyRates(params = params) as ResultData.Error

        assertThat(errorResultData.failure, IsInstanceOf(CurrencyRateRemoteError::class.java))
    }

    @Test
    fun getCurrencyRates_IllegalArgumentExceptionThrown() = runBlockingTest {

        val params = PARAMS_NONE

        expectedException.expect(IllegalArgumentException::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED))

        currencyConverterRemoteDataSourceRetrofit.getCurrencyRates(params = params)
    }

    @Test
    fun getCurrencyRates_NotImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_NOT_YET_IMPLEMENTED))

        currencyConverterRemoteDataSourceRetrofit.getCurrencyRates(params = params)
    }

    @Test
    fun countAllCurrencyRates_NotYetImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_TODO_NOT_YET_IMPLEMENTED))

        currencyConverterRemoteDataSourceRetrofit.countAllCurrencyRates(params = params)
    }

    @Test
    fun countCurrencyRates_NotYetImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_TODO_NOT_YET_IMPLEMENTED))

        currencyConverterRemoteDataSourceRetrofit.countCurrencyRates(params = params)
    }

    @Test
    fun saveCurrencyRates_NotYetImplementedErrorThrown() = runBlockingTest {

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_TODO_NOT_YET_IMPLEMENTED))

        currencyConverterRemoteDataSourceRetrofit.saveCurrencyRates(currencyRatesModel = emptyList())
    }

    @Test
    fun deleteCurrencyRates_NotYetImplementedErrorThrown() = runBlockingTest {

        val params = PARAMS_BAD

        expectedException.expect(NotImplementedError::class.java)
        expectedException.expectMessage(equalTo(ERROR_MESSAGE_TODO_NOT_YET_IMPLEMENTED))

        currencyConverterRemoteDataSourceRetrofit.deleteCurrencyRates(params = params)
    }

}

private class BadParams : Params