package emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter

import androidx.room.ColumnInfo
import androidx.room.Entity
import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.CurrencyConverterEntityParams


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 07th December, 2023.
 */


@Entity(
    tableName = CurrencyConverterEntity.TABLE_NAME,
    primaryKeys = [CurrencyConverterEntity.COLUMN_INFO_ID]
)
data class CurrencyConverterEntity(
    @ColumnInfo(name = COLUMN_INFO_CURRENCY_SYMBOL_BASE)
    override val currencySymbolBase: String,
    @ColumnInfo(name = COLUMN_INFO_CURRENCY_SYMBOL_OTHER)
    override val currencySymbolOther: String,
    @ColumnInfo(name = COLUMN_INFO_RATE)
    override val rate: Double,
    @ColumnInfo(name = COLUMN_INFO_ID)
    override val id: String = "$currencySymbolBase-$currencySymbolOther",
) : CurrencyConverterEntityParams {

    companion object {

        const val TABLE_NAME = "table_currency_rates"

        /**
         * This is a concatenation of the values of [COLUMN_INFO_CURRENCY_SYMBOL_BASE]
         * and [COLUMN_INFO_CURRENCY_SYMBOL_OTHER] in this order.
         */
        const val COLUMN_INFO_ID = "id"
        const val COLUMN_INFO_CURRENCY_SYMBOL_BASE = "currency_symbol_base"
        const val COLUMN_INFO_CURRENCY_SYMBOL_OTHER = "currency_symbol_other"
        const val COLUMN_INFO_RATE = "rate"

        fun newInstance(
            currencySymbolBase: String,
            currencySymbolOther: String,
            rate: Double,
            id: String = "$currencySymbolBase-$currencySymbolOther",
        ): CurrencyConverterEntity {
            return CurrencyConverterEntity(
                currencySymbolBase = currencySymbolBase,
                currencySymbolOther = currencySymbolOther,
                rate = rate,
                id = id
            )
        }
    }
}
