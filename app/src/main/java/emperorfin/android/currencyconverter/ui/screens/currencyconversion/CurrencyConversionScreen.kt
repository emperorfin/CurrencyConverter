package emperorfin.android.currencyconverter.ui.screens.currencyconversion

import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.ui.screens.currencyconversion.stateholders.CurrencyConversionViewModel
import emperorfin.android.currencyconverter.ui.screens.currencyconversion.stateholders.CurrencyConversionViewModelFactory
import emperorfin.android.currencyconverter.ui.screens.currencyconversion.uicomponents.Content
import emperorfin.android.currencyconverter.ui.theme.CurrencyConverterTheme
import emperorfin.android.currencyconverter.ui.utils.CurrencyConversionTopAppBar


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 16th November, 2023.
 */
 

@Composable
fun CurrencyConversionScreen(
    context: Context,
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
    mapOfCurrencySymbolsToFlag: MutableMap<String, String>,
    viewModel: CurrencyConversionViewModel = viewModel(factory = CurrencyConversionViewModelFactory(
        application = context.applicationContext as Application,
        currencyConverterRepository = CurrencyConversionViewModel.getCurrencyConverterRepository(context.applicationContext as Application)
    )),
) {

    val uiState by viewModel.uiState.collectAsState()

    var baseCurrencySymbolRefresh by rememberSaveable { mutableStateOf("USD") }

    var baseAmountRefresh by rememberSaveable { mutableStateOf(1)}

    Scaffold(
        topBar = {
            CurrencyConversionTopAppBar(
                openDrawer = openDrawer,
                onRefresh = {

                    if (uiState.errorMessage != null && uiState.initRates) {
                        viewModel.initCurrencyRates()
                    } else {
                        viewModel.refreshCurrencyRates(
                            context = context,
                            baseAmount = baseAmountRefresh,
                            baseCurrencySymbol = baseCurrencySymbolRefresh
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->

//        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//        val uiState by viewModel.uiState.collectAsState()

        Content(
            context = context,
            loading = uiState.isLoading,
            currencyRates = uiState.items,
//            noCurrenciesLabel = R.string.app_name,
            noCurrenciesLabel = uiState.errorMessage ?: R.string.no_currencies,
            noCurrenciesIconRes = R.drawable.icon_refresh_3104,
            onRefresh = {

                if (uiState.errorMessage != null && uiState.initRates) {
                    viewModel.initCurrencyRates()
                } else {
                    viewModel.refreshCurrencyRates(
                        context = context,
                        baseAmount = baseAmountRefresh,
                        baseCurrencySymbol = baseCurrencySymbolRefresh
                    )
                }

            },
            mapOfCurrencySymbolsToFlag = uiState.mapOfCurrencySymbolsToFlag,//mapOfCurrencySymbolsToFlag,
            modifier = Modifier.padding(paddingValues),
            onConvert = { baseAmount, baseCurrencySymbol ->

                baseAmountRefresh = baseAmount
                baseCurrencySymbolRefresh = baseCurrencySymbol

                viewModel.convert(
                    baseAmount = baseAmount,
                    baseCurrencySymbol = baseCurrencySymbol
                )

            }
        )

    }
}

@Preview
@Composable
private fun CurrencyConversionScreenPreview() {
    CurrencyConverterTheme {
        Surface {

            CurrencyConversionScreen(
                context = ComponentActivity(),
                openDrawer = {},
                mapOfCurrencySymbolsToFlag = mutableMapOf("USD" to "iVBORw0KGgoAAAANSUhEUgAAAB4AAAAUCAYAAACaq43EAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDpERTc5MkI3RjE3OEExMUUyQTcxNDlDNEFCRkNENzc2NiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDpERTc5MkI4MDE3OEExMUUyQTcxNDlDNEFCRkNENzc2NiI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOkEyMTE0RjIyMTc4QTExRTJBNzE0OUM0QUJGQ0Q3NzY2IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOkRFNzkyQjdFMTc4QTExRTJBNzE0OUM0QUJGQ0Q3NzY2Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+60cYSwAAAyhJREFUeNrElN1PU3cYxz/tOQUBD/aNymtbUAq2IOiUWXmZA40Iy2BzcW53y7JlyZLtZuE/8NaY7Gbe7WJbdJnTDOdQCbLKrERUotgCSodQ7AsFpK28yKT7rfsL2gv7JCcn+eV3zpPv5/l+H9X2xp65SqtJGfr1Fg3vNPD02SIhfwRniwP3pdvsOVxPaCHGs7+DOA/VJs8crXXEs3P48OfTfMIcU+SRaqlMzm8SNut2VuefIxvyydZIxFbWyX35iviLNZRiPZJaxdLyCkoiQUyc6cwFTPvC9FRkcbJMy7JaTrmxHIuvxaZm5xW7+Jl3NkKRaRt5OVlMjvuoqa9gwr9AgS4PvTYP78hjdtVVEAw9J+Kdxv7Td+hL8tGTeslGg8Jeexk3/riLs62O+cU441NBDjbZGbg+SlNbPYvRF9zzzHCoycFA/yhvCtRqnZbr5a1YEjGm5S2po1ZXfRHVaCTlWLODq24v1eWFGPVbuXH5Dh3vORm88xhziR5zoZ5rl9y0dx/ggS/EzGSQs5Ua3s39h7CUlbri0mKdUGzmijBXqzBXYH4Z931fsmlf7zBvd+wjIigMDI/TcbyRvt+GOSgUZ62uU3S2h8IdRgrTQK1S2T6PyhpZ+aB9LxcF2hpbCUUF27hy4S+Of/wWfUMeykuNVIin9/xNuj9qYWR8juknIc5szNC1voA/DdSypayAhlor57/vp/NEC7OBRfpveek+0cwvP/7JsfedhEWcLg8+pOtkMxfOuTjc5WSrSc+S6ymSQYtGyk5dsVT9/4zbhZmu3Z5IztggXOwSZjvSuZ+hUR9mEan/KAz+PkJb5z7GngSYdXu46T9Ho3EL6ZSKnZ9Fax0W5aFrDNuB6mROA6El7BYTnns+bPt3srK2gV+QcIjIPRLzrxL3ZkLLfB0c40udRCAd1EfFNioxaSG+Sl2NmchSnCKjwh6HBWlzk/rd1uTyMOTn8MbuctRiieyqLKbKbqXs4gSvQmFephOnRCIRFW+F11yyp/3TtD/eSKjYTM4rjcZh110yUZlDPfnVqcwovkppRhRnDrX/2x+UjKDuJXcuE4r/FWAAjBMttNdoYOEAAAAASUVORK5CYII="),
            )
        }
    }
}
