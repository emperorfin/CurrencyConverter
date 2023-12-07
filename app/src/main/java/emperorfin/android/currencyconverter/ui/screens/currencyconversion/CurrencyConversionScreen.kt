package emperorfin.android.currencyconverter.ui.screens.currencyconversion

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.ui.models.CurrencyRate
import emperorfin.android.currencyconverter.ui.screens.currencyconversion.stateholders.CurrencyConversionViewModel
import emperorfin.android.currencyconverter.ui.screens.currencyconversion.stateholders.CurrencyConversionViewModelFactory
import emperorfin.android.currencyconverter.ui.screens.currencyconversion.uicomponents.AppName
import emperorfin.android.currencyconverter.ui.screens.currencyconversion.uicomponents.CurrencyPicker
import emperorfin.android.currencyconverter.ui.screens.currencyconversion.uicomponents.RateTextField
import emperorfin.android.currencyconverter.ui.theme.CurrencyConverterTheme
import emperorfin.android.currencyconverter.ui.utils.CurrencyConversionTopAppBar
import emperorfin.android.currencyconverter.ui.utils.Helpers
import emperorfin.android.currencyconverter.ui.utils.LoadingContent


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
        context.applicationContext as Application
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
                    viewModel.convert(
                        assets = context.assets,
                        baseAmount = baseAmountRefresh,
                        baseCurrencySymbol = baseCurrencySymbolRefresh
                    )
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
            noCurrenciesLabel = R.string.app_name,
            noCurrenciesIconRes = R.drawable.icon_refresh_3104,
            onRefresh = {
                viewModel.convert(
                    assets = context.assets,
                    baseAmount = baseAmountRefresh,
                    baseCurrencySymbol = baseCurrencySymbolRefresh
                )
            }, // viewModel::refresh,
            mapOfCurrencySymbolsToFlag = uiState.mapOfCurrencySymbolsToFlag,//mapOfCurrencySymbolsToFlag,
            modifier = Modifier.padding(paddingValues),
            onConvert = { baseAmount, baseCurrencySymbol ->

                baseAmountRefresh = baseAmount
                baseCurrencySymbolRefresh = baseCurrencySymbol

                viewModel.convert(
                    assets = context.assets,
                    baseAmount = baseAmount,
                    baseCurrencySymbol = baseCurrencySymbol
                )

            }
        )

    }
}

@Composable
private fun Content(
    context: Context,
    loading: Boolean,
    currencyRates: List<CurrencyRate>,
    @StringRes noCurrenciesLabel: Int,
    @DrawableRes noCurrenciesIconRes: Int,
    onRefresh: () -> Unit,
    mapOfCurrencySymbolsToFlag: Map<String, String>,
    modifier: Modifier = Modifier,
    onConvert: (Int, String) -> Unit
) {

    var baseCurrencySymbol by rememberSaveable { mutableStateOf("USD") }

    var baseAmount by rememberSaveable { mutableStateOf("1")}

    Column(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.horizontal_margin),
                vertical = dimensionResource(id = R.dimen.horizontal_margin)
            )
    ) {
//        AppName(modifier)
        AppName(Modifier.padding(24.dp, 0.dp))

//        Spacer(modifier = Modifier.height(40.dp))
        Spacer(modifier = Modifier.height(8.dp))

        //..

        LoadingContent(
            loading = loading,
            empty = currencyRates.isEmpty() && !loading,
            emptyContent = { EmptyContent(noCurrenciesLabel, noCurrenciesIconRes, modifier, onRefresh) },
            onRefresh = onRefresh
        ) {

            Column {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {

                    CurrencyPicker(
                        modifier,
                        defaultSymbol = "USD",
                        mapOfCurrencySymbolsToFlag = mapOfCurrencySymbolsToFlag,
                        onSymbolSelected = { newText -> baseCurrencySymbol = newText }
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    RateTextField(
                        modifier,
                        value = baseAmount,
                        onBaseAmountChanged = { newBaseAmount -> baseAmount = newBaseAmount }
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    elevation = null,
                    onClick = {

                        if(baseAmount.isNotEmpty()) {
                            // remv uncomment
//                        mainViewModel.convert(
//                            baseCurrencySymbol,
//                            targetCurrencySymbol,
//                            baseAmount.toDouble()
//                        )
//
//                        Toast.makeText(context, "Calculating..", Toast.LENGTH_SHORT).show()
//
//                        //if conversion gives an error, toast the error message
//                        if(conversionRate.error != null) {
//                            Toast.makeText(
//                                context,
//                                "${conversionRate.error?.info}",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }

                        if (!mapOfCurrencySymbolsToFlag.containsKey(baseCurrencySymbol)) {
                            Toast.makeText(context, "Please select a valid currency symbol.", Toast.LENGTH_SHORT).show()

                            return@Button
                        }

                            onConvert(baseAmount.toInt(), baseCurrencySymbol)

                        } else {
                            Toast.makeText(context, "Enter an amount", Toast.LENGTH_SHORT).show()
                        }

                    },
                    modifier = Modifier
                        .height(57.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.inversePrimary,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    //convert text
                    Text(
                        text = stringResource(R.string.convert_text),
                        fontWeight = FontWeight.Bold, fontSize = 16.sp
                    )
                }

//        Spacer(modifier = Modifier.height(16.dp))
//                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = modifier
                        .fillMaxSize()
//                    .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin))
                ) {
//            Text(
//                text = stringResource(currentFilteringLabel),
//                modifier = Modifier.padding(
//                    horizontal = dimensionResource(id = R.dimen.list_item_padding),
//                    vertical = dimensionResource(id = R.dimen.vertical_margin)
//                ),
//                style = MaterialTheme.typography.h6
//            )
                    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 328.dp),
                        contentPadding = PaddingValues(
                            start = 12.dp,
//                        top = 16.dp,
                            end = 12.dp,
                            bottom = 16.dp
                        ),
                        content = {
                            items(items = currencyRates) { currencyRate ->
                                CurrencyItem(
                                    modifier = Modifier,
                                    currencyRate = currencyRate
                                )
                            }
                        })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyItem(
    modifier: Modifier,
    currencyRate: CurrencyRate
) {

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Card(
//                            backgroundColor = Color.Red,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
//                            elevation = 8.dp,
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            val base64String = currencyRate.currencyFlag
            if (base64String != null) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    bitmap = Helpers.getFlagImageBitMap(base64String),
                    contentDescription = "Flag",
                    tint = Color.Unspecified
                )
            }

            Text(
                text = "${currencyRate.currencyCode}: ${currencyRate.rate}",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun EmptyContent(
    @StringRes noCurrenciesLabel: Int,
    @DrawableRes noCurrenciesIconRes: Int,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = noCurrenciesIconRes),
            contentDescription = stringResource(R.string.no_currencies_image_content_description),
            modifier = Modifier
                .size(46.dp)
                .clickable { onRefresh() }
        )
        Text(stringResource(id = noCurrenciesLabel))
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

@Preview
@Composable
private fun ContentPreview() {
    CurrencyConverterTheme {
        Surface {

            val flagAfn = "iVBORw0KGgoAAAANSUhEUgAAAB4AAAAUCAIAAAAVyRqTAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo3REQwQzQwNjE3NTMxMUUyODY3Q0FBOTFCQzlGNjlDRiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo3REQwQzQwNzE3NTMxMUUyODY3Q0FBOTFCQzlGNjlDRiI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjdERDBDNDA0MTc1MzExRTI4NjdDQUE5MUJDOUY2OUNGIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjdERDBDNDA1MTc1MzExRTI4NjdDQUE5MUJDOUY2OUNGIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+duaIkQAAAalJREFUeNpiZMAN2BkYVjMwyDIwfMCQ4mNgeM3K4DEFj24GJgaagaFpNAuR6v4yMPwDO4SRaOcQZfQfBgZOCQkOSclfb978/fbt59u3/6li9G+guTIy/EZGTOycbDJyrIIC3+7e/XX0CMgXZIc1I9hcPnstsbzA94ePfH/x+N//n683b+ILNBGw0v3zlwKjIYErGub94dhpJi5u3Q1rtFcuZeYTerNtj3CwJxMjBQECdBaXoRybhCzLFy6eQJuHPW1/P30W9Db9fuMlh6QSt706A8NNMl0NNJpNVIrHyIyDR/DV4UOMivxf3tx/vnM7p4gor5kFh7gs+QEC9NG38ze+3jz7m+Mrv6i2oKGtTGo6v7jOr3/vP1878fXcNfIDBCj39fWHdyt2K/Q3vpy05FFWCwMTE6+NrnhR3PvyCZ9vP6Mo8bEyMLxZsOkTD4tkaga/vvXvt285rdSeT5n1dckaFkrS9X+w9F+Gf8+nrPl9+Q2LkMD/f//+rd/4es8ePoZ/LKyUZZn/YIczMzC8PHgALsgGFvxPeW78D8477BiCw7RQpaHRAAEGAEWfj+c8VB1nAAAAAElFTkSuQmCC"
            val flagAll = "iVBORw0KGgoAAAANSUhEUgAAAB4AAAAUCAIAAAAVyRqTAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDoyRDFBRDI0NTE3NkYxMUUyODY3Q0FBOTFCQzlGNjlDRiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDoyRDFBRDI0NjE3NkYxMUUyODY3Q0FBOTFCQzlGNjlDRiI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjdERDBDNDA4MTc1MzExRTI4NjdDQUE5MUJDOUY2OUNGIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjJEMUFEMjQ0MTc2RjExRTI4NjdDQUE5MUJDOUY2OUNGIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+GPq/agAAAiRJREFUeNrEVb9rFEEUfm9m9nb3bhNz50UMClopRAsFrUURW1tBrSzsLPwfbPwDbGz8F8QiIkLAKiCkUIKiGBEFwXAhd7fZH7Mz83zZtbC4TdyF4LDF8N7ON9/73jczuN4/A4czBBzaqIUmAA+Q0wjQRzkUCsv4USEHKKs4/0DtWOdAgxLxrUk+mqyHIkLx2eg1k1gA3kwDtYFeFOqVnj5NRwXQip7eGG9+svlPV1wff3mejwuiZ9n2i3zCRWANAta1kaFX9OS1jkdkHdGyCt6blMmel8E3p1OgY6iueL2b/pEtZ5qx5kRCLIhMyK4WMQFt2HzdpEzypZ5OnOVUSoT1gqi6BOvA7ZoDUan5JB3BXxPeOALBahigxloLQO4SFy5hBjMOpuA0zc4ebL4OYExuZl0dxNiRh63MZ4jYXjzJiG77/cuqW8UvqvBO0Ge+jjsplKHmgrCIIeICyke9pXPKZ+kvqPCS1+X6T4vO42iJN/YB22jNIo6cYWN9dfqdya560TxKruKaF32w2abVW2VWtNCa6fRQnpTeD1vcD4anZOdNEa8VCZN9EA6/2+KE9Ob3dUit+XbJHRfqXjBgTZjYhk3nUDAQN/CsDJbDYIfcbvlhU+hqQUpuSo6tcstfYMp8q9z1+7+cyfZMuUe4zZGp/GfLxRm4bbIPu4scYbIJOO6EO+hSVf9y8zLQmGxUKrNDRu7HtSH0n+NHrpr8/1fmtwADAEjB+xzEjgF0AAAAAElFTkSuQmCC"
            val flagAmd = "iVBORw0KGgoAAAANSUhEUgAAAB4AAAAUCAYAAACaq43EAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDpDMzA3QTc5RTE3NzAxMUUyODY3Q0FBOTFCQzlGNjlDRiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDpDMzA3QTc5RjE3NzAxMUUyODY3Q0FBOTFCQzlGNjlDRiI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOkMzMDdBNzlDMTc3MDExRTI4NjdDQUE5MUJDOUY2OUNGIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOkMzMDdBNzlEMTc3MDExRTI4NjdDQUE5MUJDOUY2OUNGIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+WDHK5QAAAF9JREFUeNpi/M/A8IlhAAALEPMOhMVMDAMERi2mX+LawmD8ZyAsZmRg2P9vgLLTO8YBsvjvCEtcA2U30GKx/wOSqpcm2/weEIv/b2b4PzBB/Wy0rB7++Zjh80BYDBBgAAfmD594ReJrAAAAAElFTkSuQmCC"
            val flagAng = "iVBORw0KGgoAAAANSUhEUgAAAB4AAAAUCAIAAAAVyRqTAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAxJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6Q0ZDQjI4NzIxNzgzMTFFMkE3MTQ5QzRBQkZDRDc3NjYiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6Q0ZDQjI4NzExNzgzMTFFMkE3MTQ5QzRBQkZDRDc3NjYiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiBNYWNpbnRvc2giPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0iQkE2REE0NTQ3MzVGMzJGMUI0MjQ3MkQxOUFBQTIyNDciIHN0UmVmOmRvY3VtZW50SUQ9IkJBNkRBNDU0NzM1RjMyRjFCNDI0NzJEMTlBQUEyMjQ3Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+ZDn+FAAAAexJREFUeNrkVM9P1EAY/aad7UK72RZkNSgmROFiOHEyZoUQIgkJRw/Gm4khMfHf8KxnxRMQTsqJAyEEDv6InkiAhUAWVrbID3cL2y67225nPmd7U9L2ZGL061ya7703r+/rDEFEiCxndcUcGVOuXAVKW++cez/M7tk54/GjaKIEf6z+VWkinl8LW4vEEmksItWj9YKflG1GBBglCj5A8loiXnpxaSu8i1SmF2vfTuQ7tKmf2S5y7OhUuXSSWS5pZIdxH5CEfy48i9y7KXwDyQBWHj4fNvS2qReLQNKAFkA1yJNEBGKEWRbMtKE4VQ/9BoDf+G5VLIFngC5Au6aptRpHZGEDkwGGgp1/Wy1pVU3OTI8XCo5ZPBbed7b2c+tFgDRA/fatztdvRj9/PD6vXAT+LisQIX0/JCjCGG6sl/O7tuuKyQnjqpgfgC1seq68mSvl9yqMkbBMhHQ2dIjIT0/LrusamY7BwZvFogPgZYf6GCbKJevo6IyxmKyt2H9f7e+dmBz49GFB4B88yb57v3d4kA9a9agDMfX2S0Rblmgtt119+SqRUj1seaSSz85t7emkeu8u580oSxhb+1+Ft3pKwes63tAbeltNsFbnY3nxp9EpVE2gSrILWABWuAdm92HD+F9vvr9R+qcAAwDK6wIXIkUhaQAAAABJRU5ErkJggg=="
            val flagEur = "/9j/4AAQSkZJRgABAQEBLAEsAAD/4QBIRXhpZgAASUkqAAgAAAACAA4BAgATAAAAJgAAAJiCAgAHAAAAOQAAAAAAAABFdXJvcGVhbiBVbmlvbiBGbGFnY255dGh6bP/hBURodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+Cjx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iPgoJPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4KCQk8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczpwaG90b3Nob3A9Imh0dHA6Ly9ucy5hZG9iZS5jb20vcGhvdG9zaG9wLzEuMC8iIHhtbG5zOklwdGM0eG1wQ29yZT0iaHR0cDovL2lwdGMub3JnL3N0ZC9JcHRjNHhtcENvcmUvMS4wL3htbG5zLyIgICB4bWxuczpHZXR0eUltYWdlc0dJRlQ9Imh0dHA6Ly94bXAuZ2V0dHlpbWFnZXMuY29tL2dpZnQvMS4wLyIgeG1sbnM6ZGM9Imh0dHA6Ly9wdXJsLm9yZy9kYy9lbGVtZW50cy8xLjEvIiB4bWxuczpwbHVzPSJodHRwOi8vbnMudXNlcGx1cy5vcmcvbGRmL3htcC8xLjAvIiAgeG1sbnM6aXB0Y0V4dD0iaHR0cDovL2lwdGMub3JnL3N0ZC9JcHRjNHhtcEV4dC8yMDA4LTAyLTI5LyIgeG1sbnM6eG1wUmlnaHRzPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvcmlnaHRzLyIgZGM6UmlnaHRzPSJjbnl0aHpsIiBwaG90b3Nob3A6Q3JlZGl0PSJHZXR0eSBJbWFnZXMvaVN0b2NrcGhvdG8iIEdldHR5SW1hZ2VzR0lGVDpBc3NldElEPSI1MzAyMzQ0OTkiIHhtcFJpZ2h0czpXZWJTdGF0ZW1lbnQ9Imh0dHBzOi8vd3d3LmlzdG9ja3Bob3RvLmNvbS9sZWdhbC9saWNlbnNlLWFncmVlbWVudD91dG1fbWVkaXVtPW9yZ2FuaWMm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"

            Content(
                context = ComponentActivity(),
                loading = false,
                currencyRates = listOf(
                    CurrencyRate(
                        currencyCode = "AFN",
                        currencyFlag = flagAfn,
                        rate = "73.6"
                    ),
                    CurrencyRate(
                        currencyCode = "ALL",
                        currencyFlag = flagAll,
                        rate = "95.8"
                    ),
                    CurrencyRate(
                        currencyCode = "AMD",
                        currencyFlag = flagAmd,
                        rate = "401.7"
                    ),
                    CurrencyRate(
                        currencyCode = "ANG",
                        currencyFlag = flagAng,
                        rate = "1.8"
                    ),
                    CurrencyRate(
                        currencyCode = "EUR",
                        currencyFlag = flagEur,
                        rate = "1.8"
                    )
                ),
                noCurrenciesLabel = R.string.no_currencies,
                noCurrenciesIconRes = R.drawable.logo_no_fill,
                mapOfCurrencySymbolsToFlag = mutableMapOf("USD" to "iVBORw0KGgoAAAANSUhEUgAAAB4AAAAUCAYAAACaq43EAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDpERTc5MkI3RjE3OEExMUUyQTcxNDlDNEFCRkNENzc2NiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDpERTc5MkI4MDE3OEExMUUyQTcxNDlDNEFCRkNENzc2NiI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOkEyMTE0RjIyMTc4QTExRTJBNzE0OUM0QUJGQ0Q3NzY2IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOkRFNzkyQjdFMTc4QTExRTJBNzE0OUM0QUJGQ0Q3NzY2Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+60cYSwAAAyhJREFUeNrElN1PU3cYxz/tOQUBD/aNymtbUAq2IOiUWXmZA40Iy2BzcW53y7JlyZLtZuE/8NaY7Gbe7WJbdJnTDOdQCbLKrERUotgCSodQ7AsFpK28yKT7rfsL2gv7JCcn+eV3zpPv5/l+H9X2xp65SqtJGfr1Fg3vNPD02SIhfwRniwP3pdvsOVxPaCHGs7+DOA/VJs8crXXEs3P48OfTfMIcU+SRaqlMzm8SNut2VuefIxvyydZIxFbWyX35iviLNZRiPZJaxdLyCkoiQUyc6cwFTPvC9FRkcbJMy7JaTrmxHIuvxaZm5xW7+Jl3NkKRaRt5OVlMjvuoqa9gwr9AgS4PvTYP78hjdtVVEAw9J+Kdxv7Td+hL8tGTeslGg8Jeexk3/riLs62O+cU441NBDjbZGbg+SlNbPYvRF9zzzHCoycFA/yhvCtRqnZbr5a1YEjGm5S2po1ZXfRHVaCTlWLODq24v1eWFGPVbuXH5Dh3vORm88xhziR5zoZ5rl9y0dx/ggS/EzGSQs5Ua3s39h7CUlbri0mKdUGzmijBXqzBXYH4Z931fsmlf7zBvd+wjIigMDI/TcbyRvt+GOSgUZ62uU3S2h8IdRgrTQK1S2T6PyhpZ+aB9LxcF2hpbCUUF27hy4S+Of/wWfUMeykuNVIin9/xNuj9qYWR8juknIc5szNC1voA/DdSypayAhlor57/vp/NEC7OBRfpveek+0cwvP/7JsfedhEWcLg8+pOtkMxfOuTjc5WSrSc+S6ymSQYtGyk5dsVT9/4zbhZmu3Z5IztggXOwSZjvSuZ+hUR9mEan/KAz+PkJb5z7GngSYdXu46T9Ho3EL6ZSKnZ9Fax0W5aFrDNuB6mROA6El7BYTnns+bPt3srK2gV+QcIjIPRLzrxL3ZkLLfB0c40udRCAd1EfFNioxaSG+Sl2NmchSnCKjwh6HBWlzk/rd1uTyMOTn8MbuctRiieyqLKbKbqXs4gSvQmFephOnRCIRFW+F11yyp/3TtD/eSKjYTM4rjcZh110yUZlDPfnVqcwovkppRhRnDrX/2x+UjKDuJXcuE4r/FWAAjBMttNdoYOEAAAAASUVORK5CYII="),
                onRefresh = { },
                onConvert = { _, _ -> }
            )
        }
    }
}

@Preview
@Composable
private fun ContentEmptyPreview() {
    CurrencyConverterTheme {
        Surface {
            Content(
                context = ComponentActivity(),
                loading = false,
                currencyRates = emptyList(),
                noCurrenciesLabel = R.string.no_currencies,
                noCurrenciesIconRes = R.drawable.logo_no_fill,
                onRefresh = { },
                mapOfCurrencySymbolsToFlag = mutableMapOf("USD" to "iVBORw0KGgoAAAANSUhEUgAAAB4AAAAUCAYAAACaq43EAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDpERTc5MkI3RjE3OEExMUUyQTcxNDlDNEFCRkNENzc2NiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDpERTc5MkI4MDE3OEExMUUyQTcxNDlDNEFCRkNENzc2NiI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOkEyMTE0RjIyMTc4QTExRTJBNzE0OUM0QUJGQ0Q3NzY2IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOkRFNzkyQjdFMTc4QTExRTJBNzE0OUM0QUJGQ0Q3NzY2Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+60cYSwAAAyhJREFUeNrElN1PU3cYxz/tOQUBD/aNymtbUAq2IOiUWXmZA40Iy2BzcW53y7JlyZLtZuE/8NaY7Gbe7WJbdJnTDOdQCbLKrERUotgCSodQ7AsFpK28yKT7rfsL2gv7JCcn+eV3zpPv5/l+H9X2xp65SqtJGfr1Fg3vNPD02SIhfwRniwP3pdvsOVxPaCHGs7+DOA/VJs8crXXEs3P48OfTfMIcU+SRaqlMzm8SNut2VuefIxvyydZIxFbWyX35iviLNZRiPZJaxdLyCkoiQUyc6cwFTPvC9FRkcbJMy7JaTrmxHIuvxaZm5xW7+Jl3NkKRaRt5OVlMjvuoqa9gwr9AgS4PvTYP78hjdtVVEAw9J+Kdxv7Td+hL8tGTeslGg8Jeexk3/riLs62O+cU441NBDjbZGbg+SlNbPYvRF9zzzHCoycFA/yhvCtRqnZbr5a1YEjGm5S2po1ZXfRHVaCTlWLODq24v1eWFGPVbuXH5Dh3vORm88xhziR5zoZ5rl9y0dx/ggS/EzGSQs5Ua3s39h7CUlbri0mKdUGzmijBXqzBXYH4Z931fsmlf7zBvd+wjIigMDI/TcbyRvt+GOSgUZ62uU3S2h8IdRgrTQK1S2T6PyhpZ+aB9LxcF2hpbCUUF27hy4S+Of/wWfUMeykuNVIin9/xNuj9qYWR8juknIc5szNC1voA/DdSypayAhlor57/vp/NEC7OBRfpveek+0cwvP/7JsfedhEWcLg8+pOtkMxfOuTjc5WSrSc+S6ymSQYtGyk5dsVT9/4zbhZmu3Z5IztggXOwSZjvSuZ+hUR9mEan/KAz+PkJb5z7GngSYdXu46T9Ho3EL6ZSKnZ9Fax0W5aFrDNuB6mROA6El7BYTnns+bPt3srK2gV+QcIjIPRLzrxL3ZkLLfB0c40udRCAd1EfFNioxaSG+Sl2NmchSnCKjwh6HBWlzk/rd1uTyMOTn8MbuctRiieyqLKbKbqXs4gSvQmFephOnRCIRFW+F11yyp/3TtD/eSKjYTM4rjcZh110yUZlDPfnVqcwovkppRhRnDrX/2x+UjKDuJXcuE4r/FWAAjBMttNdoYOEAAAAASUVORK5CYII="),
                onConvert = { _, _ ->}
            )
        }
    }
}

@Preview
@Composable
private fun EmptyContentPreview() {
    CurrencyConverterTheme {
        Surface {
            EmptyContent(
                noCurrenciesLabel = R.string.no_currencies,
                noCurrenciesIconRes = R.drawable.logo_no_fill,
                onRefresh = {}
            )
        }
    }
}