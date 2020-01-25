package ru.nikitasemiklit.currencyrates.net

import com.google.gson.annotations.SerializedName

data class CurrencyRatesResponse(
    @SerializedName("base") val baseCurrency: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: HashMap<String, String>
)