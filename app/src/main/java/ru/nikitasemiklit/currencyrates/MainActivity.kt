package ru.nikitasemiklit.currencyrates

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import ru.nikitasemiklit.currencyrates.model.RatesViewModel

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userInputET = findViewById<EditText>(R.id.et_user_input)
        val resultTV = findViewById<TextView>(R.id.tv_convert_result)
        val baseSpinner = findViewById<Spinner>(R.id.sp_base_currency)
        baseSpinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)
        val targetSpinner = findViewById<Spinner>(R.id.sp_target_currency)

        val viewModel = ViewModelProvider(this).get(RatesViewModel::class.java)
        userInputET.addTextChangedListener { text ->
            viewModel.onUserInput(text.toString())
        }
        viewModel.baseCurrencies.observe(this) { value ->
            val adapter = baseSpinner.adapter as ArrayAdapter<String>
            adapter.clear()
            adapter.addAll(value)
        }
    }
}
