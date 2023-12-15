package emperorfin.android.currencyconverter.ui.screens.events.inputs.currencyconverter

import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.CurrencyConverterModelParams


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 12th December, 2023.
 */


data class CurrencyConverterParams(
    override val id: String?,
    override val currencySymbolBase: String?,
    override val currencySymbolOther: String?,
    override val rate: Double?
) : CurrencyConverterModelParams