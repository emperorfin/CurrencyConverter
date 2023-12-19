package emperorfin.android.currencyconverter.domain.models.currencyconverter

import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.CurrencyConverterModelParams


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 08th December, 2023.
 */


data class CurrencyConverterModel(
    override val currencySymbolBase: String,
    override val currencySymbolOther: String,
    override val rate: Double,
    override val id: String = "$currencySymbolBase-$currencySymbolOther",
) : CurrencyConverterModelParams {

    companion object {

        fun newInstance(
            currencySymbolBase: String,
            currencySymbolOther: String,
            rate: Double,
            id: String = "$currencySymbolBase-$currencySymbolOther",
        ): CurrencyConverterModel {
            return CurrencyConverterModel(
                currencySymbolBase = currencySymbolBase,
                currencySymbolOther = currencySymbolOther,
                rate = rate,
                id = id
            )
        }

    }

}
