package emperorfin.android.currencyconverter.ui.screens.currencyconversion.stateholders

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import emperorfin.android.currencyconverter.domain.datalayer.repositories.ICurrencyConverterRepository


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 05th December, 2023.
 */


class CurrencyConversionViewModelFactory(private val application: Application, private val currencyConverterRepository: ICurrencyConverterRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrencyConversionViewModel::class.java)){
            return CurrencyConversionViewModel(application, currencyConverterRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}