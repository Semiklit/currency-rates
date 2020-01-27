package ru.nikitasemiklit.currencyrates.net

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    val client: ExchangeRatesApi
    private val endpoint = "https://api.exchangeratesapi.io/"

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        client = retrofit.create(ExchangeRatesApi::class.java)
    }

    fun getRatesForCurrency(baseCurrency: String) = client.getRatesForBaseCurrency(baseCurrency)
        .subscribeOn(Schedulers.io())
}