package emperorfin.android.currencyconverter.domain.uilayer.events.outputs

import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData.Success
import emperorfin.android.currencyconverter.domain.exceptions.Failure


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 27th November, 2023.
 */


/**
 * A generic class that holds a loading signal or the result of an async operation.
 * @param R
 */
sealed class ResultData<out R>{
    /**
     * @param T
     */
    data class Success<out T>(val data: T) : ResultData<T>()
    data class Error(val failure: Failure) : ResultData<Nothing>()
    object Loading : ResultData<Nothing>()

    override fun toString(): String {
        return when (this){
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[throwable=$failure]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [ResultData] is of type [Success] & holds non-null [Success.data].
 */
val ResultData<*>.succeeded
    get() = this is Success && data != null

