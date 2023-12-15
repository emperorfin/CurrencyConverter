package emperorfin.android.currencyconverter.ui.models.currencyconverter

import android.content.Context
import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModel
import emperorfin.android.currencyconverter.ui.utils.Helpers


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 11th December, 2023.
 */


class CurrencyConverterUiModelMapper(
    private val context: Context
) {

    fun transform(currencyConverterModel: CurrencyConverterModel): CurrencyConverterUiModel {

        val id: String = currencyConverterModel.id
        val currencySymbolBase: String = currencyConverterModel.currencySymbolBase
        val currencySymbolOther: String = currencyConverterModel.currencySymbolOther
        val rate: Double = currencyConverterModel.rate

        val mapOfCurrencySymbolsToFlag = Helpers.loadMapOfCurrencySymbolToFlag(context.assets)

        val currencySymbolOtherFlag: String? = mapOfCurrencySymbolsToFlag[currencySymbolOther]

        return CurrencyConverterUiModel(
            currencySymbolBase = currencySymbolBase,
            currencySymbolOther = currencySymbolOther,
            rate = rate,
            id = id,
            currencySymbolOtherFlag = currencySymbolOtherFlag
        )
    }

}