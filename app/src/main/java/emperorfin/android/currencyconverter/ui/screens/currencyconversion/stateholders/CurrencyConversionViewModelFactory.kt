package emperorfin.android.currencyconverter.ui.screens.currencyconversion.stateholders

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 05th December, 2023.
 */


class CurrencyConversionViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrencyConversionViewModel::class.java)){
            return CurrencyConversionViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}