package ru.nikitasemiklit.currencyrates.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SingleCurrencyRate(
    @PrimaryKey val id: Long,
    val currencyCode: String,
    val currencyRate: String
)