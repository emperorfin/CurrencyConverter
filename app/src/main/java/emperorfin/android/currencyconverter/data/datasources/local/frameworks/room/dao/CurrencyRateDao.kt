package emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity.Companion.COLUMN_INFO_CURRENCY_SYMBOL_BASE
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity.Companion.TABLE_NAME
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity.Companion.COLUMN_INFO_ID


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 07th December, 2023.
 */


@Dao
interface CurrencyRateDao {

    @Query("SELECT COUNT(*) FROM $TABLE_NAME")
    suspend fun countCurrencyRates(): Int

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_INFO_CURRENCY_SYMBOL_BASE = :currencySymbolBase ORDER BY $COLUMN_INFO_ID ASC")
    suspend fun getCurrencyRates(currencySymbolBase: String): List<CurrencyConverterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyRates(currencyRates: List<CurrencyConverterEntity>): List<Long>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteCurrencyRates(): Int

}