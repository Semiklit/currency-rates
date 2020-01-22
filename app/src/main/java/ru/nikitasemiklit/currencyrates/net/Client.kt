package ru.nikitasemiklit.currencyrates.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {
    val client: ExchangeRatesApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.exchangeratesapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        client = retrofit.create(ExchangeRatesApi::class.java)
    }

    fun getRatesForCurrency(baseCurrency: String) = client.getRatesForBaseCurrency(baseCurrency)
}