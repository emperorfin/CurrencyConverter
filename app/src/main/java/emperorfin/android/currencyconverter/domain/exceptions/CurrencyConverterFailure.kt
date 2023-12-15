package emperorfin.android.currencyconverter.domain.exceptions

import androidx.annotation.StringRes
import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.domain.exceptions.Failure.FeatureFailure


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 12th December, 2023.
 */


sealed class CurrencyConverterFailure(
    @StringRes open val message: Int,
    open val cause: Throwable?
) : FeatureFailure() {

    class CurrencyRateListNotAvailableMemoryError(
        @StringRes override val message: Int = R.string.error_memory_currency_rate_list_not_available,
        override val cause: Throwable? = null
    ) : CurrencyConverterFailure(message = message, cause = cause)

    class CurrencyRateListNotAvailableLocalError(
        @StringRes override val message: Int = R.string.error_local_currency_rate_list_not_available,
        override val cause: Throwable? = null
    ) : CurrencyConverterFailure(message = message, cause = cause)

    class CurrencyRateListNotAvailableRemoteError(
        @StringRes override val message: Int = R.string.error_remote_currency_rate_list_not_available,
        override val cause: Throwable? = null
    ) : CurrencyConverterFailure(message = message, cause = cause)

    class CurrencyRateMemoryError(
        @StringRes override val message: Int = R.string.error_memory_currency_rate,
        override val cause: Throwable? = null
    ) : CurrencyConverterFailure(message = message, cause = cause)

    class CurrencyRateLocalError(
        @StringRes override val message: Int = R.string.error_local_currency_rate,
        override val cause: Throwable? = null
    ) : CurrencyConverterFailure(message = message, cause = cause)

    class CurrencyRateRemoteError(
        @StringRes override val message: Int = R.string.error_remote_currency_rate,
        override val cause: Throwable? = null
    ) : CurrencyConverterFailure(message = message, cause = cause)

    class GetCurrencyRateMemoryError(
        @StringRes override val message: Int = R.string.error_memory_get_currency_rate,
        override val cause: Throwable? = null
    ) : CurrencyConverterFailure(message = message, cause = cause)

    class GetCurrencyRateLocalError(
        @StringRes override val message: Int = R.string.error_local_get_currency_rate,
        override val cause: Throwable? = null
    ) : CurrencyConverterFailure(message = message, cause = cause)

    class GetCurrencyRateRemoteError(
        @StringRes override val message: Int = R.string.error_remote_get_currency_rate,
        override val cause: Throwable? = null
    ) : CurrencyConverterFailure(message = message, cause = cause)

    class InsertCurrencyRateMemoryError(
        @StringRes override val message: Int = R.string.error_memory_insert_currency_rate,
        override val cause: Throwable? = null
    ) : CurrencyConverterFailure(message = message, cause = cause)

    class InsertCurrencyRateLocalrror(
        @StringRes override val message: Int = R.string.error_local_insert_currency_rate,
        override val cause: Throwable? = null
    ) : CurrencyConverterFailure(message = message, cause = cause)

    class InsertCurrencyRateRemoteError(
        @StringRes val message: Int = R.string.error_remote_insert_currency_rate,
        val cause: Throwable? = null
    ) : FeatureFailure()

    class UpdateCurrencyRateMemoryError(
        @StringRes val message: Int = R.string.error_memory_update_currency_rate,
        val cause: Throwable? = null
    ) : FeatureFailure()

    class UpdateCurrencyRateLocalError(
        @StringRes val message: Int = R.string.error_local_update_currency_rate,
        val cause: Throwable? = null
    ) : FeatureFailure()

    class UpdateCurrencyRateRemoteError(
        @StringRes val message: Int = R.string.error_remote_update_currency_rate,
        val cause: Throwable? = null
    ) : FeatureFailure()

    class DeleteCurrencyRateMemoryError(
        @StringRes val message: Int = R.string.error_memory_delete_currency_rate,
        val cause: Throwable? = null
    ) : FeatureFailure()

    class DeleteCurrencyRateLocalError(
        @StringRes val message: Int = R.string.error_local_delete_currency_rate,
        val cause: Throwable? = null
    ) : FeatureFailure()

    class DeleteCurrencyRateRemoteError(
        @StringRes val message: Int = R.string.error_remote_delete_currency_rate,
        val cause: Throwable? = null
    ) : FeatureFailure()

    class NonExistentCurrencyRateDataMemoryError(
        @StringRes val message: Int = R.string.error_memory_non_existent_currency_rate_data,
        val cause: Throwable? = null
    ) : FeatureFailure()

    class NonExistentCurrencyRateDataLocalError(
        @StringRes val message: Int = R.string.error_local_non_existent_currency_rate_data,
        val cause: Throwable? = null
    ) : FeatureFailure()

    class NonExistentCurrencyRateDataRemoteError(
        @StringRes val message: Int = R.string.error_remote_non_existent_currency_rate_data,
        val cause: Throwable? = null
    ) : FeatureFailure()

    // For Repositories
    class GetCurrencyRateRepositoryError(
        @StringRes val message: Int = R.string.error_repository_get_currency_rate,
        val cause: Throwable? = null
    ) : FeatureFailure()
}
