package emperorfin.android.currencyconverter.domain.datalayer.dao

import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity
import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.Params


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 05th January, 2024.
 */


interface CurrencyRatesDao  {

    suspend fun countAllCurrencyRates(): Int

    suspend fun countCurrencyRates(currencySymbolBase: String): Int

//    suspend fun getCurrencyRates(currencySymbolBase: String, arg1: String? = null): Any
    suspend fun getCurrencyRates(currencySymbolBase: String): List<CurrencyConverterEntity>
    suspend fun getCurrencyRates(currencySymbolBase: String, appId: String): Any

    /**
     * This should not be implemented in the remote DAO. Throw an exception instead.
     *
     * The local DAO should be the one to implement this function.
     *
     * This should have been currencyRates: List<[Params]> instead of
     * currencyRates: List<CurrencyConverterEntity> but Kotlin doesn't support parameter overrides.
     * So if the remote DAO requires currency rate insertions, just create a function overload with
     * the necessary parameter list and have the remote DAO override and implement the function.
     */
    suspend fun insertCurrencyRates(currencyRates: List<CurrencyConverterEntity>): List<Long>

    suspend fun deleteCurrencyRates(currencySymbolBase: String): Int

}