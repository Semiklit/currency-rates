package ru.nikitasemiklit.currencyrates.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import ru.nikitasemiklit.currencyrates.dataBase.RatesDataBase

class RatesViewModel(app: Application) : AndroidViewModel(app) {
    val dataBase = Room.databaseBuilder(
        app.applicationContext,
        RatesDataBase::class.java,
        "ratesDataBase"
    ).build

    lateinit var baseCurrency: String

    lateinit var targetCurrency: String

    override fun onCleared() {
        super.onCleared()
    }
}