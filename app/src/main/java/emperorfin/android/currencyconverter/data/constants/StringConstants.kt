package emperorfin.android.currencyconverter.data.constants


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 08th January, 2024.
 */


object StringConstants {

    const val ERROR_MESSAGE_NOT_YET_IMPLEMENTED: String = "Not yet implemented."
    const val ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED: String = "The argument passed is inappropriate."
    const val ERROR_MESSAGE_ALL_CURRENCY_RATES_NOT_SAVED: String = "All the currency rate were not saved."
    const val ERROR_MESSAGE_SHOULD_NOT_BE_IMPLEMENTED: String =
        "This exception is intentional as the function from which it's being thrown should not be " +
            "implemented in this remote DAO."
    const val ERROR_MESSAGE_ON_CONVERT_RATES_PARAM_CANT_BE_NULL_WHEN_WOULD_RECONVERT_RATES_PARAM_IS_TRUE: String =
        "When the parameter wouldReconvertRates is true, the parameter onConvertRates " +
                "must not be null."

}