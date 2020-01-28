package ru.nikitasemiklit.currencyrates.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SingleCurrencyRate(
    @PrimaryKey val currencyCode: String,
    @ColumnInfo val currencyRate: String
)