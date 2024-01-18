package emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity.Companion.COLUMN_INFO_CURRENCY_SYMBOL_BASE
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity.Companion.TABLE_NAME
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity.Companion.COLUMN_INFO_ID
import emperorfin.android.currencyconverter.domain.datalayer.dao.CurrencyRatesDao


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 07th December, 2023.
 */


@Dao
interface CurrencyRateDao : CurrencyRatesDao {

    @Query("SELECT COUNT(*) FROM $TABLE_NAME")
    override suspend fun countAllCurrencyRates(): Int

    @Query("SELECT COUNT(*) FROM $TABLE_NAME WHERE $COLUMN_INFO_CURRENCY_SYMBOL_BASE = :currencySymbolBase")
    override suspend fun countCurrencyRates(currencySymbolBase: String): Int

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_INFO_CURRENCY_SYMBOL_BASE = :currencySymbolBase ORDER BY $COLUMN_INFO_ID ASC")
    override suspend fun getCurrencyRates(currencySymbolBase: String): List<CurrencyConverterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertCurrencyRates(currencyRates: List<CurrencyConverterEntity>): List<Long>

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_INFO_CURRENCY_SYMBOL_BASE = :currencySymbolBase")
    override suspend fun deleteCurrencyRates(currencySymbolBase: String): Int

    /**
     * This should not be implemented in this local DAO.
     * The remote DAO should be the one to implement this function.
     *
     * And not implementing this function would make build to fail which is related to how Room works.
     */
    override suspend fun getCurrencyRates(
        currencySymbolBase: String, appId: String,
    ): Any {
        throw IllegalStateException(
            "This exception is intentional as the function from which it's being thrown should not be " +
                    "implemented in this local DAO."
        )
    }

}