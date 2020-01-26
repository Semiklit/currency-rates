package ru.nikitasemiklit.currencyrates.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import ru.nikitasemiklit.currencyrates.App
import ru.nikitasemiklit.currencyrates.dataBase.RatesDataBase
import ru.nikitasemiklit.currencyrates.dataBase.SingleCurrencyRate
import ru.nikitasemiklit.currencyrates.net.Client
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class RatesViewModel : ViewModel() {
    private val dataBase: RatesDataBase = Room.databaseBuilder(
        App.instance,
        RatesDataBase::class.java,
        "ratesDataBase"
    ).build()
    private val subscriptions = ArrayList<Disposable>()

    val baseCurrencies = MutableLiveData<List<String>>()
    val targetCurrencies = MutableLiveData<List<String>>()
    val targetValue = MutableLiveData<String>()

    init {
        subscriptions.add(Client.getRatesForCurrency().observeOn(Schedulers.io()).subscribe(
            { value ->
                dataBase.currencyRatesDao()
                dataBase.currencyRatesDao().insert(value.rates.map { entry ->
                    SingleCurrencyRate(entry.key, entry.value)
                }.plus(SingleCurrencyRate(value.baseCurrency, "1")))
                update()
            },
            { error -> error.printStackTrace() }
        )
        )
    }

    var baseCurrency: String = "EUR"
        set(value) {
            field = value
            count()
        }
    var targetCurrency: String = "EUR"
        set(value) {
            field = value
            count()
        }
    var userInputValue: String = "0"
        set(value) {
            field = value
            count()
        }

    private fun count() {
        if (userInputValue.isEmpty()) {
            targetValue.value = userInputValue
        } else {
            subscriptions.add(
                Single.zip(
                    dataBase.currencyRatesDao().getRateForCurrency(baseCurrency),
                    dataBase.currencyRatesDao().getRateForCurrency(targetCurrency),
                    BiFunction<String, String, String> { t1, t2 ->
                        val valueToConvert = BigDecimal(userInputValue)
                        val baseRate = BigDecimal(t1)
                        val targetRate = BigDecimal(t2)
                        val scale = Currency.getInstance(targetCurrency).defaultFractionDigits
                        valueToConvert.divide(baseRate, scale, BigDecimal.ROUND_HALF_UP)
                            .multiply(targetRate).toString()
                    }
                )
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                        targetValue.value = result
                    }, { error ->
                        error.printStackTrace()
                    })
            )
        }
    }

    private fun update() {
        subscriptions.add(
            dataBase.currencyRatesDao().getAvailableCurrency()
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ value ->
                    baseCurrencies.value = value
                    targetCurrencies.value = value
                },
                    { error -> error.printStackTrace() })
        )
    }

    override fun onCleared() {
        subscriptions.map { disposable -> if (!disposable.isDisposed) disposable.dispose() }
        super.onCleared()
    }
}