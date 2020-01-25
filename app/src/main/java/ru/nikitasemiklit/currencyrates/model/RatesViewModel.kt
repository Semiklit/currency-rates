package ru.nikitasemiklit.currencyrates.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nikitasemiklit.currencyrates.App
import ru.nikitasemiklit.currencyrates.dataBase.RatesDataBase
import ru.nikitasemiklit.currencyrates.dataBase.SingleCurrencyRate
import ru.nikitasemiklit.currencyrates.net.Client

class RatesViewModel : ViewModel() {
    private val dataBase: RatesDataBase = Room.databaseBuilder(
        App.instance,
        RatesDataBase::class.java,
        "ratesDataBase"
    ).build()
    private val currenciesLoaderDisposable: Disposable;

    val baseCurrencies = MutableLiveData<List<String>>()
    val targetCurrencies = MutableLiveData<List<String>>()
    val result = MutableLiveData<String>()

    init {
        currenciesLoaderDisposable =
            Client.getRatesForCurrency().observeOn(Schedulers.io()).subscribe(
                { value ->
                    dataBase.currencyRatesDao()
                    dataBase.currencyRatesDao().insert(value.rates.map { entry ->
                        SingleCurrencyRate(entry.key, entry.value)
                    }.plus(SingleCurrencyRate(value.baseCurrency, "1")))
                    update()
                },
                { error -> error.printStackTrace() }
            )
    }

    lateinit var baseCurrency: String
    lateinit var targetCurrency: String

    fun onUserInput(text: String) {

    }

    fun update() {
        val d = dataBase.currencyRatesDao().getAvailableCurrency()
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ value ->
                baseCurrencies.value = value
            },
                { error -> error.printStackTrace() })
    }

    override fun onCleared() {
        currenciesLoaderDisposable.dispose()
        super.onCleared()
    }
}