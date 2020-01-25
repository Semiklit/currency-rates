package ru.nikitasemiklit.currencyrates.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SingleCurrencyRate::class), version = 1)
abstract class RatesDataBase : RoomDatabase() {
    abstract fun currencyRatesDao(): Dao
}