package ru.nikitasemiklit.currencyrates

import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nikitasemiklit.currencyrates.model.CurrencyRatesResponse
import ru.nikitasemiklit.currencyrates.model.RatesViewModel
import ru.nikitasemiklit.currencyrates.net.Client

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userInputET = findViewById<EditText>(R.id.et_user_input)
        val resultTV = findViewById<TextView>(R.id.tv_convert_result)
        val baseSpinner = findViewById<Spinner>(R.id.sp_base_currency)
        val targetSpinner = findViewById<Spinner>(R.id.sp_target_currency)
//        val viewModel = ViewModelProvider(this).get(RatesViewModel::class.java)
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(RatesViewModel::class.java)
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
