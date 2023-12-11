package emperorfin.android.currencyconverter.domain.models.currencyconverter

import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.CurrencyConverterDataTransferObjectParams
import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.CurrencyConverterEntityParams
import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.CurrencyConverterUiModelParams


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 11th December, 2023.
 */


class CurrencyConverterModelMapper {

    fun transform(currencyConverterDto: CurrencyConverterDataTransferObjectParams): CurrencyConverterModel {

        val id: String = currencyConverterDto.id!!
        val currencySymbolBase: String = currencyConverterDto.currencySymbolBase!!
        val currencySymbolOther: String = currencyConverterDto.currencySymbolOther!!
        val rate: Double = currencyConverterDto.rate!!

        return CurrencyConverterModel(
            currencySymbolBase = currencySymbolBase,
            currencySymbolOther = currencySymbolOther,
            rate = rate,
            id = id
        )
    }

    fun transform(currencyConverterEntity: CurrencyConverterEntityParams): CurrencyConverterModel {

        val id: String = currencyConverterEntity.id!!
        val currencySymbolBase: String = currencyConverterEntity.currencySymbolBase!!
        val currencySymbolOther: String = currencyConverterEntity.currencySymbolOther!!
        val rate: Double = currencyConverterEntity.rate!!

        return CurrencyConverterModel(
            currencySymbolBase = currencySymbolBase,
            currencySymbolOther = currencySymbolOther,
            rate = rate,
            id = id
        )
    }

    fun transform(currencyConverterUiModel: CurrencyConverterUiModelParams): CurrencyConverterModel {

        val id: String = currencyConverterUiModel.id!!
        val currencySymbolBase: String = currencyConverterUiModel.currencySymbolBase!!
        val currencySymbolOther: String = currencyConverterUiModel.currencySymbolOther!!
        val rate: Double = currencyConverterUiModel.rate!!

        return CurrencyConverterModel(
            currencySymbolBase = currencySymbolBase,
            currencySymbolOther = currencySymbolOther,
            rate = rate,
            id = id
        )
    }

}