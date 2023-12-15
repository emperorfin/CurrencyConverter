package emperorfin.android.currencyconverter.ui.screens.currencyconversion.stateholders

import emperorfin.android.currencyconverter.ui.models.currencyconverter.CurrencyConverterUiModel


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 28th November, 2023.
 */


data class CurrencyConversionUiState(
    val items: List<CurrencyConverterUiModel> = emptyList(),
    val mapOfCurrencySymbolsToFlag: Map<String, String?> = emptyMap(),
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val initRates: Boolean = false
)
