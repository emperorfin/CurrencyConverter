package emperorfin.android.currencyconverter.domain.exceptions


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 12th December, 2023.
 */


/**
 * Base class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnectionError : Failure()
    object ServerError : Failure()

    /**
     * Extend this class for feature specific failures.
     */
    abstract class FeatureFailure : Failure()
}

