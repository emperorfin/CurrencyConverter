package emperorfin.android.currencyconverter.data.datasources.local.frameworks.room

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.dao.CurrencyRateDao
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity.Companion.COLUMN_INFO_CURRENCY_SYMBOL_BASE
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity.Companion.COLUMN_INFO_CURRENCY_SYMBOL_OTHER
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity.Companion.COLUMN_INFO_ID
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity.Companion.COLUMN_INFO_RATE
import emperorfin.android.currencyconverter.data.datasources.local.frameworks.room.entities.currencyconverter.CurrencyConverterEntity.Companion.TABLE_NAME
import emperorfin.android.currencyconverter.data.utils.CurrencyConverterSampleDataGeneratorUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 07th December, 2023.
 */


@Database(entities = [CurrencyConverterEntity::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract val mCurrencyRateDao: CurrencyRateDao

    companion object {

        private const val DATABASE_NAME = "database_app"

        private var isDatabaseAlreadyPopulated: Boolean = false

        private val coroutineScope = CoroutineScope(Dispatchers.IO)

        private val TAG: String = AppRoomDatabase::class.java.simpleName

        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase{

            synchronized(this){
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppRoomDatabase::class.java,
                        DATABASE_NAME
                    )
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)

                                coroutineScope.launch {
                                    // This is now commented out since real data is being cached
                                    // to the database.
//                                    populateInitialCurrencyRatesSampleDataUsingSqliteDatabaseWithCoroutineThread(db, CurrencyConverterSampleDataGeneratorUtil.getCurrencyConverterEntityList())
                                }
                            }

                            override fun onOpen(db: SupportSQLiteDatabase) {
                                super.onOpen(db)
                            }

                            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                                super.onDestructiveMigration(db)
                            }
                        })
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }

        }

        private suspend fun populateInitialCurrencyRatesSampleDataUsingSqliteDatabaseWithCoroutineThread(db: SupportSQLiteDatabase, currencyRates: List<CurrencyConverterEntity>) {
            // Unused at the moment.
            if (isDatabaseAlreadyPopulated)
                return

            db.beginTransaction()

            try {
                val initialCurrencyRateValues = ContentValues()
                for (currencyRate in currencyRates){
                    initialCurrencyRateValues.put(COLUMN_INFO_ID, currencyRate.id)
                    initialCurrencyRateValues.put(COLUMN_INFO_CURRENCY_SYMBOL_BASE, currencyRate.currencySymbolBase)
//                    initialCurrencyRateValues.put(COLUMN_INFO_CURRENCY_SYMBOL_OTHER, "${currencyRate.currencySymbolOther} (room sample data)")
                    initialCurrencyRateValues.put(COLUMN_INFO_CURRENCY_SYMBOL_OTHER, "${currencyRate.currencySymbolOther}")
                    initialCurrencyRateValues.put(COLUMN_INFO_RATE, currencyRate.rate)

                    db.insert(TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE, initialCurrencyRateValues)
                }

                db.setTransactionSuccessful()
            }finally {
                db.endTransaction()
            }
        }

    }
}