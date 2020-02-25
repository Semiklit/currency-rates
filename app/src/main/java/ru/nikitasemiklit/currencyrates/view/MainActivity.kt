package ru.nikitasemiklit.currencyrates.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import ru.nikitasemiklit.currencyrates.databinding.ActivityMainBinding
import ru.nikitasemiklit.currencyrates.model.RatesViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);
        binding.spBaseCurrency.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)

        binding.spTargetCurrency.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)

        val viewModel: MainActivityModelInterface =
            ViewModelProvider(this).get(RatesViewModel::class.java)
        binding.etUserInput.addTextChangedListener { text ->
            viewModel.userInputValue = text.toString()
        }
        viewModel.baseCurrencies.observe(this) { value ->
            val adapter = binding.spBaseCurrency.adapter as ArrayAdapter<String>
            adapter.clear()
            adapter.addAll(value)
        }
        viewModel.targetCurrencies.observe(this) { value ->
            val adapter = binding.spTargetCurrency.adapter as ArrayAdapter<String>
            adapter.clear()
            adapter.addAll(value)
        }
        viewModel.targetValue.observe(this) { value ->
            binding.tvConvertResult.text = value
        }

        binding.spBaseCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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

        binding.spTargetCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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
