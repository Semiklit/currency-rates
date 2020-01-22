package ru.nikitasemiklit.currencyrates.net

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.nikitasemiklit.currencyrates.model.CurrencyRatesResponse

interface ExchangeRatesApi {

    @GET("/latest")
    fun getRatesForBaseCurrency(@Query("base") baseCurrency: String): Call<CurrencyRatesResponse>
}