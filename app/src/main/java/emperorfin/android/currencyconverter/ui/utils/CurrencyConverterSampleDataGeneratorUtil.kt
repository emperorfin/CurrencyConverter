package emperorfin.android.currencyconverter.ui.utils

import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity
import emperorfin.android.currencyconverter.data.utils.CurrencyConverterSampleDataGeneratorUtil as CurrencyConverterSampleDataGeneratorUtil_FromDataLayer
import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModelMapper
import emperorfin.android.currencyconverter.ui.models.currencyconverter.CurrencyConverterUiModel
import emperorfin.android.currencyconverter.ui.models.currencyconverter.CurrencyConverterUiModelMapper


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 11th December, 2023.
 */


object CurrencyConverterSampleDataGeneratorUtil {

    fun getTransformedCurrencyConverterEntityList(): ArrayList<CurrencyConverterUiModel> {
        val currencyConverterModelMapper = CurrencyConverterModelMapper()
        val currencyConverterUiModelMapper = CurrencyConverterUiModelMapper()

        val currencyRates: List<CurrencyConverterEntity> =
            CurrencyConverterSampleDataGeneratorUtil_FromDataLayer.getCurrencyConverterEntityList()

        return currencyRates.map {
            currencyConverterModelMapper.transform(it)
        }.map {
            currencyConverterUiModelMapper.transform(it)
        } as ArrayList<CurrencyConverterUiModel>
    }

}