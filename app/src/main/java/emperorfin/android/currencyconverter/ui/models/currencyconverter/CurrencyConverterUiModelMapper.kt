package emperorfin.android.currencyconverter.ui.models.currencyconverter

import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModel


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 11th December, 2023.
 */


class CurrencyConverterUiModelMapper {

    fun transform(currencyConverterModel: CurrencyConverterModel): CurrencyConverterUiModel {

        val id: String = currencyConverterModel.id
        val currencySymbolBase: String = currencyConverterModel.currencySymbolBase
        val currencySymbolOther: String = currencyConverterModel.currencySymbolOther
        val rate: Double = currencyConverterModel.rate

        return CurrencyConverterUiModel(
            currencySymbolBase = currencySymbolBase,
            currencySymbolOther = currencySymbolOther,
            rate = rate,
            id = id
        )
    }

}