package emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.models.currencyconverter

import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.CurrencyConverterDataTransferObjectParams


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 08th December, 2023.
 */


data class CurrencyConverterDataTransferObject(
    override val currencySymbolBase: String,
    override val currencySymbolOther: String,
    override val rate: Double
) : CurrencyConverterDataTransferObjectParams
