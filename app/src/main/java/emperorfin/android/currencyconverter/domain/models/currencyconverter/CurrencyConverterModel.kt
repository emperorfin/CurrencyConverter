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
) : CurrencyConverterModelParams
