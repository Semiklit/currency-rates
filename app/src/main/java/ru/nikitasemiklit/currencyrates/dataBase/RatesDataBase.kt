package ru.nikitasemiklit.currencyrates.dataBase

import androidx.room.Database

@Database(entities = arrayOf(SingleCurrencyRate::class), version = 1)
abstract class RatesDataBase {
    abstract fun currencyRatesDao(): Dao
}