package ru.nikitasemiklit.currencyrates

import android.os.Bundle
import android.view.View
import android.widget.*
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
        targetSpinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)

        val viewModel = ViewModelProvider(this).get(RatesViewModel::class.java)
        userInputET.addTextChangedListener { text ->
            viewModel.userInputValue = text.toString()
        }
        viewModel.baseCurrencies.observe(this) { value ->
            val adapter = baseSpinner.adapter as ArrayAdapter<String>
            adapter.clear()
            adapter.addAll(value)
        }
        viewModel.targetCurrencies.observe(this) { value ->
            val adapter = targetSpinner.adapter as ArrayAdapter<String>
            adapter.clear()
            adapter.addAll(value)
        }
        viewModel.targetValue.observe(this) { value ->
            resultTV.text = value
        }

        baseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.baseCurrency = parent?.getItemAtPosition(position) as String
            }
        }

        targetSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.targetCurrency = parent?.getItemAtPosition(position) as String
            }
        }
    }
}
