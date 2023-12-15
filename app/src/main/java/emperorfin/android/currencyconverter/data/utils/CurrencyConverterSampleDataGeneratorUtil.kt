package emperorfin.android.currencyconverter.data.utils

import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_BASE_USD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AED
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AFN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AFN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AFN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_ALL
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_ALL
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_ALL
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AMD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AMD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AMD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_ANG
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_ANG
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_ANG
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AOA
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_6
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_6
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_ARS
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AOA
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AOA
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AUD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AUD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AUD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AWG
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AWG
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AWG
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_AZN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_AZN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_AZN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BAM
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BAM
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BAM
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BBD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BBD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BBD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BDT
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BDT
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BDT
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BGN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BGN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BGN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BHD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BHD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BHD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BIF
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BIF
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BIF
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BMD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BMD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BMD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BND
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BND
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BND
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BOB
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BOB
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BOB
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_USD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_USD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_USD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BRL
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BRL
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BRL
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BSD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BSD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BSD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BTC
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BTC
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BTC
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BTN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BTN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BTN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BWP
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BWP
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BWP
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BYN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BYN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BYN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_BZD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.CURRENCY_SYMBOL_OTHER_NGN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_BZD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.ID_NGN
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_BZD
import emperorfin.android.currencyconverter.data.constants.CurrencyRatesSampleDataConstants.RATE_NGN
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 07th December, 2023.
 */


object CurrencyConverterSampleDataGeneratorUtil {

    fun getCurrencyConverterEntityList(): ArrayList<CurrencyConverterEntity>  {

        val currencyRates: ArrayList<CurrencyConverterEntity> = ArrayList()

        var currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_AED,
            rate = RATE_AED,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_AED,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_AFN,
            rate = RATE_AFN,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_AFN,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_ALL,
            rate = RATE_ALL,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_ALL,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_AMD,
            rate = RATE_AMD,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_AMD,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_ANG,
            rate = RATE_ANG,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_ANG,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_6,
            rate = RATE_6,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_AOA,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_AOA,
            rate = RATE_AOA,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_ARS,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_AUD,
            rate = RATE_AUD,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_AUD,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_AWG,
            rate = RATE_AWG,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_AWG,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_AZN,
            rate = RATE_AZN,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_AZN,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BAM,
            rate = RATE_BAM,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BAM,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BBD,
            rate = RATE_BBD,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BBD,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BDT,
            rate = RATE_BDT,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BDT,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BGN,
            rate = RATE_BGN,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BGN,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BHD,
            rate = RATE_BHD,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BHD,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BIF,
            rate = RATE_BIF,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BIF,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BMD,
            rate = RATE_BMD,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BMD,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BND,
            rate = RATE_BND,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BND,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BOB,
            rate = RATE_BOB,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BOB,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_USD,
            rate = RATE_USD,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_USD,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BRL,
            rate = RATE_BRL,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BRL,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BSD,
            rate = RATE_BSD,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BSD,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BTC,
            rate = RATE_BTC,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BTC,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BTN,
            rate = RATE_BTN,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BTN,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BWP,
            rate = RATE_BWP,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BWP,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BYN,
            rate = RATE_BYN,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BYN,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_NGN,
            rate = RATE_NGN,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_NGN,
        )

        currencyRates.add(currencyRate)

        currencyRate = CurrencyConverterEntity.newInstance(
            id = ID_BZD,
            rate = RATE_BZD,
            currencySymbolBase = CURRENCY_SYMBOL_BASE_USD,
            currencySymbolOther = CURRENCY_SYMBOL_OTHER_BZD,
        )

        currencyRates.add(currencyRate)

        return currencyRates
    }

}