package ru.nikitasemiklit.currencyrates.dataBase

import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {


    @Query("SELECT currencyCode from ")
    fun getAvailableCurrency(): List<String>

    @Insert
    fun insert(currencyRate: SingleCurrencyRate)
}