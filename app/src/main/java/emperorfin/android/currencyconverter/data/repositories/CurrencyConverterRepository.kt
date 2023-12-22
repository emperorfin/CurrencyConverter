package emperorfin.android.currencyconverter.data.repositories

import emperorfin.android.currencyconverter.R
import emperorfin.android.currencyconverter.domain.datalayer.datasources.CurrencyConverterDataSource
import emperorfin.android.currencyconverter.domain.datalayer.repositories.ICurrencyConverterRepository
import emperorfin.android.currencyconverter.domain.exceptions.CurrencyConverterFailure
import emperorfin.android.currencyconverter.domain.models.currencyconverter.CurrencyConverterModel
import emperorfin.android.currencyconverter.domain.uilayer.events.inputs.currencyconverter.Params
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData.Error
import emperorfin.android.currencyconverter.domain.uilayer.events.outputs.ResultData.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 19th December, 2023.
 */


class CurrencyConverterRepository(
    private val currencyConverterLocalDataSource: CurrencyConverterDataSource,
    private val currencyConverterRemoteDataSource: CurrencyConverterDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ICurrencyConverterRepository {

    private var cachedCurrencyRates: ConcurrentMap<String, List<CurrencyConverterModel>>? = null

    override suspend fun countAllCurrencyRates(
        params: Params,
        countRemotely: Boolean
    ): ResultData<Int> = withContext(ioDispatcher) {
        if (countRemotely) {
            currencyConverterRemoteDataSource.countAllCurrencyRates(params = params)
        } else {
            currencyConverterLocalDataSource.countAllCurrencyRates(params = params)
        }
    }

    override suspend fun countCurrencyRates(
        params: Params,
        countRemotely: Boolean
    ): ResultData<Int> = withContext(ioDispatcher) {
        if (countRemotely) {
            currencyConverterRemoteDataSource.countCurrencyRates(params = params)
        } else {
            currencyConverterLocalDataSource.countCurrencyRates(params = params)
        }
    }

    override suspend fun getCurrencyRates(
        params: Params,
        forceUpdate: Boolean
    ): ResultData<List<CurrencyConverterModel>> = withContext(ioDispatcher) {
        // Respond immediately with cache if available and not dirty
        if (!forceUpdate) {
            cachedCurrencyRates?.let {
//                return@withContext Success(cachedCurrencyRates.values.sortedBy { it.currencySymbolOther })
                val cachedCurrencyRates: MutableCollection<List<CurrencyConverterModel>> = it.values

                if (cachedCurrencyRates.isNotEmpty()) {
                    return@withContext Success(cachedCurrencyRates.first())
                }
            }
        }

        val newCurrencyRates: ResultData<List<CurrencyConverterModel>> =
            fetchCurrencyRatesFromRemoteOrLocal(params = params, forceUpdate = forceUpdate)

        // Refresh the cache with the new currencyRates
        (newCurrencyRates as? Success)?.let { refreshCache(it.data) }

        cachedCurrencyRates?.values?.let {
//            return@withContext Success(currencyRates.sortedBy { it.currencySymbolOther })
            val currencyRates: MutableCollection<List<CurrencyConverterModel>> = it

            if (currencyRates.isNotEmpty()) {
                return@withContext Success(currencyRates.first())
            }
        }

        (newCurrencyRates as? Success)?.let {
//            if (it.data.isEmpty()) {
//                return@withContext Success(it.data)
//            }
            return@withContext it
        }

        return@withContext newCurrencyRates as Error
    }

    override suspend fun saveCurrencyRates(
        currencyRatesModel: List<CurrencyConverterModel>,
        saveRemotely: Boolean?
    ): ResultData<List<Long>> = withContext(ioDispatcher) {

        saveRemotely?.let {saveRemotely ->
            if (saveRemotely) {
                return@withContext currencyConverterRemoteDataSource.saveCurrencyRates(currencyRatesModel = currencyRatesModel)
            } else {
                return@withContext currencyConverterLocalDataSource.saveCurrencyRates(currencyRatesModel = currencyRatesModel)
            }
        }

        val savedCurrencyRatesResultDataRemote: ResultData<List<Long>> =
            currencyConverterRemoteDataSource.saveCurrencyRates(currencyRatesModel = currencyRatesModel)

        if (savedCurrencyRatesResultDataRemote is Error) {
            return@withContext savedCurrencyRatesResultDataRemote
        }

        val savedCurrencyRatesResultDataLocal: ResultData<List<Long>> =
            currencyConverterLocalDataSource.saveCurrencyRates(currencyRatesModel = currencyRatesModel)

        if (savedCurrencyRatesResultDataLocal is Error) {
            return@withContext savedCurrencyRatesResultDataLocal
        }

        if (savedCurrencyRatesResultDataRemote is Success && savedCurrencyRatesResultDataLocal is Success) {
            return@withContext Success(savedCurrencyRatesResultDataRemote.data)
        }

        return@withContext Error(
            failure = CurrencyConverterFailure.InsertCurrencyRateRepositoryError()
        )

    }

    override suspend fun deleteCurrencyRates(
        params: Params,
        deleteRemotely: Boolean?
    ): ResultData<Int> = withContext(ioDispatcher) {

        deleteRemotely?.let { deleteRemotely ->
            if (deleteRemotely) {
                return@withContext currencyConverterRemoteDataSource.deleteCurrencyRates(params = params)
            } else {
                return@withContext currencyConverterLocalDataSource.deleteCurrencyRates(params = params)
            }
        }

        val deletedCurrencyRatesResultDataRemote: ResultData<Int> =
            currencyConverterRemoteDataSource.deleteCurrencyRates(params = params)

        if (deletedCurrencyRatesResultDataRemote is Error) {
            return@withContext deletedCurrencyRatesResultDataRemote
        }

        val deletedCurrencyRatesResultDataLocal: ResultData<Int> =
            currencyConverterLocalDataSource.deleteCurrencyRates(params = params)

        if (deletedCurrencyRatesResultDataLocal is Error) {
            return@withContext deletedCurrencyRatesResultDataLocal
        }

        if (deletedCurrencyRatesResultDataRemote is Success && deletedCurrencyRatesResultDataLocal is Success) {
            return@withContext Success(deletedCurrencyRatesResultDataRemote.data)
        }

        return@withContext Error(
            failure = CurrencyConverterFailure.DeleteCurrencyRateRepositoryError()
        )
    }

    private suspend fun fetchCurrencyRatesFromRemoteOrLocal(
        params: Params, forceUpdate: Boolean
    ): ResultData<List<CurrencyConverterModel>> {
        // Remote first
        if (forceUpdate) {
            when (val currencyRatesRemote = currencyConverterRemoteDataSource.getCurrencyRates(params = params)) {
//             is Error -> return remoteCurrencyRates // Timber.w("Remote data source fetch failed")
                is Success -> {
                    refreshLocalDataSource(params = params, currencyRates = currencyRatesRemote.data)

                    return currencyRatesRemote
                }
//             else -> throw IllegalStateException()
                else -> {}
            }
        }

        // Don't read from local if it's forced
        if (forceUpdate) {
            return Error(
                CurrencyConverterFailure.GetCurrencyRateRemoteError(
                    message = R.string.error_cant_force_refresh_currency_rates_remote_data_source_unavailable
                )
            )
        }

        // Local if remote fails
        val currencyRatesLocal = currencyConverterLocalDataSource.getCurrencyRates(params = params)

        if (currencyRatesLocal is Success) return currencyRatesLocal

//        return Error((currencyRatesLocal as Error).failure)
        return Error(
            CurrencyConverterFailure.GetCurrencyRateRepositoryError(
                R.string.error_fetching_from_remote_and_local
            )
        )
    }

    private fun refreshCache(currencyRates: List<CurrencyConverterModel>) {
        cachedCurrencyRates?.clear()

//        currencyRates.sortedBy { it.currencySymbolOther }.forEach {
//            cacheAndPerform(it) {}
//        }
        currencyRates.sortedBy { it.currencySymbolOther }.apply {
            cacheAndPerform(currencyRates = this) {}
        }
    }

    private suspend fun refreshLocalDataSource(params: Params, currencyRates: List<CurrencyConverterModel>) {
        currencyConverterLocalDataSource.deleteCurrencyRates(params = params)

        currencyConverterLocalDataSource.saveCurrencyRates(currencyRatesModel = currencyRates)
    }

    private fun cacheCurrencyRates(currencyRates: List<CurrencyConverterModel>): List<CurrencyConverterModel> {

        val cachedCurrencyRatesNew = mutableListOf<CurrencyConverterModel>()

        currencyRates.forEach {
            val currencyRate = CurrencyConverterModel.newInstance(
                currencySymbolBase = it.currencySymbolBase,
                currencySymbolOther = it.currencySymbolOther,
                rate = it.rate,
                id = it.id,
            )

            cachedCurrencyRatesNew.add(currencyRate)
        }
        // Create if it doesn't exist.
        if (cachedCurrencyRates == null) {
            cachedCurrencyRates = ConcurrentHashMap()
        }
        cachedCurrencyRates?.put(cachedCurrencyRatesNew.first().currencySymbolBase, cachedCurrencyRatesNew)

        return cachedCurrencyRatesNew
    }

    private inline fun cacheAndPerform(currencyRates: List<CurrencyConverterModel>, perform: (List<CurrencyConverterModel>) -> Unit) {
        val cachedCurrencyRates = cacheCurrencyRates(currencyRates = currencyRates)
        perform(cachedCurrencyRates)
    }
}