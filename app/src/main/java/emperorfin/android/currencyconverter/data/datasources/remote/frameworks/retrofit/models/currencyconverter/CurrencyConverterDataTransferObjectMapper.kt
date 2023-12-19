package emperorfin.android.currencyconverter.data.datasources.remote.frameworks.retrofit.models.currencyconverter

import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModel


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 11th December, 2023.
 */


class CurrencyConverterDataTransferObjectMapper {

    fun transform(currencyConverterModel: CurrencyConverterModel): CurrencyConverterDataTransferObject {

        val currencySymbolBase: String = currencyConverterModel.currencySymbolBase
        val currencySymbolOther: String = currencyConverterModel.currencySymbolOther
        val rate: Double = currencyConverterModel.rate

        return CurrencyConverterDataTransferObject.newInstance(
            currencySymbolBase = currencySymbolBase,
            currencySymbolOther = currencySymbolOther,
            rate = rate
        )
    }

}