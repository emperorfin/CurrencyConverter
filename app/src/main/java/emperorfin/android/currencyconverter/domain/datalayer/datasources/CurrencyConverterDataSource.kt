package emperorfin.android.currencyconverter.domain.datalayer.datasources

import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModel
import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.Params
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 11th December, 2023.
 */


interface CurrencyConverterDataSource {

    suspend fun countAllCurrencyRates(): ResultData<Int>

    suspend fun getCurrencyRates(params: Params): ResultData<List<CurrencyConverterModel>>

    suspend fun saveCurrencyRates(currencyRates: List<CurrencyConverterModel>): ResultData<List<Long>>

    suspend fun deleteCurrencyRates(params: Params): ResultData<Int>

}