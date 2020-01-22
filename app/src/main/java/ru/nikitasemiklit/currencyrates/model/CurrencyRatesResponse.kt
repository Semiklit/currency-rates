package ru.nikitasemiklit.currencyrates.model

import com.google.gson.annotations.SerializedName

data class CurrencyRatesResponse(
    @SerializedName("base") val baseCurrency: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: HashMap<String, String>
)