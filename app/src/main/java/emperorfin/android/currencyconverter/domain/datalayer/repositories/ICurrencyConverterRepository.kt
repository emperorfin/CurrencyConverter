package emperorfin.android.currencyconverter.domain.datalayer.repositories

import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModel
import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.Params
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 19th December, 2023.
 */


interface ICurrencyConverterRepository {

    suspend fun countAllCurrencyRates(params: Params, countRemotely: Boolean = false): ResultData<Int>

    suspend fun countCurrencyRates(params: Params, countRemotely: Boolean = false): ResultData<Int>

    suspend fun getCurrencyRates(params: Params, forceUpdate: Boolean = false): ResultData<List<CurrencyConverterModel>>

    suspend fun saveCurrencyRates(currencyRatesModel: List<CurrencyConverterModel>, saveRemotely: Boolean? = null): ResultData<List<Long>>

    suspend fun deleteCurrencyRates(params: Params, deleteRemotely: Boolean? = null): ResultData<Int>

}