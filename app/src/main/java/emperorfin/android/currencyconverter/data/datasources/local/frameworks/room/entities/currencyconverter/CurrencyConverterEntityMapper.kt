package emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter

import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModel


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 11th December, 2023.
 */


class CurrencyConverterEntityMapper {

    fun transform(currencyConverterModel: CurrencyConverterModel): CurrencyConverterEntity {

        val id: String = currencyConverterModel.id
        val currencySymbolBase: String = currencyConverterModel.currencySymbolBase
        val currencySymbolOther: String = currencyConverterModel.currencySymbolOther
        val rate: Double = currencyConverterModel.rate

        return CurrencyConverterEntity(
            currencySymbolBase = currencySymbolBase,
            currencySymbolOther = currencySymbolOther,
            rate = rate,
            id = id
        )
    }

}