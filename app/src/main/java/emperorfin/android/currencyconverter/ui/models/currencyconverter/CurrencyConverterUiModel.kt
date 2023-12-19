package emperorfin.android.currencyconverter.ui.models.currencyconverter

import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.CurrencyConverterUiModelParams


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 08th December, 2023.
 */


data class CurrencyConverterUiModel(
    override val currencySymbolBase: String,
    override val currencySymbolOther: String,
    override val rate: Double,
    override val id: String = "$currencySymbolBase-$currencySymbolOther",
    override val currencySymbolOtherFlag: String?,
) : CurrencyConverterUiModelParams {

    companion object {

        fun newInstance(
            currencySymbolBase: String,
            currencySymbolOther: String,
            rate: Double,
            id: String = "$currencySymbolBase-$currencySymbolOther",
            currencySymbolOtherFlag: String?
        ): CurrencyConverterUiModel {
            return CurrencyConverterUiModel(
                currencySymbolBase = currencySymbolBase,
                currencySymbolOther = currencySymbolOther,
                rate = rate,
                id = id,
                currencySymbolOtherFlag = currencySymbolOtherFlag
            )
        }

    }

}
