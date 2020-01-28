package ru.nikitasemiklit.currencyrates.net

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi {

    @GET("/latest")
    fun getRatesForBaseCurrency(@Query("base") baseCurrency: String): Single<CurrencyRatesResponse>
}