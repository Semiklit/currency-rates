package ru.nikitasemiklit.currencyrates.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface Dao {
    @Query("SELECT currencyCode FROM singleCurrencyRate")
    fun getAvailableCurrency(): Flowable<List<String>>

    @Query("SELECT currencyRate FROM singleCurrencyRate WHERE currencyCode = :currencyCode")
    fun getRateForCurrency(currencyCode: String): Flowable<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyRates: List<SingleCurrencyRate>)
}