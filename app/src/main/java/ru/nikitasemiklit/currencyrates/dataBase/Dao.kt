package ru.nikitasemiklit.currencyrates.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface Dao {
    @Query("SELECT currencyCode FROM singleCurrencyRate")
    fun getAvailableCurrency(): Single<List<String>>

    @Query("SELECT currencyRate FROM singleCurrencyRate WHERE currencyCode = :currencyCode")
    fun getRateForCurrency(currencyCode: String): Single<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyRates: List<SingleCurrencyRate>)
}