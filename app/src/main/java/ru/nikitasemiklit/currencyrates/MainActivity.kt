package ru.nikitasemiklit.currencyrates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nikitasemiklit.currencyrates.model.CurrencyRatesResponse
import ru.nikitasemiklit.currencyrates.net.Client

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val client = Client()
        client.getRatesForCurrency("USD").enqueue(object : Callback<CurrencyRatesResponse> {
            override fun onFailure(call: Call<CurrencyRatesResponse>, t: Throwable) {
                print(t.message)
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<CurrencyRatesResponse>,
                response: Response<CurrencyRatesResponse>
            ) {
                print(response.body())
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
