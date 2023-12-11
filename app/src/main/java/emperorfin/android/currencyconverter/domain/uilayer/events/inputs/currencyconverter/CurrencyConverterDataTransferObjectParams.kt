package emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 08th December, 2023.
 */


interface CurrencyConverterDataTransferObjectParams : Params {
    val id: String?
    val currencySymbolBase: String?
    val currencySymbolOther: String?
    val rate: Double?
}