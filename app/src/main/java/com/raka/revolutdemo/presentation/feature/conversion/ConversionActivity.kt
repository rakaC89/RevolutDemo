package com.raka.revolutdemo.presentation.feature.conversion

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.raka.revolutdemo.App
import com.raka.revolutdemo.common.di.ConversionModule
import com.raka.revolutdemo.common.di.DaggerConversionComponent
import com.raka.revolutdemo.domain.model.Rate
import kotlinx.android.synthetic.main.activity_conversion.*
import javax.inject.Inject


class ConversionActivity : AppCompatActivity(), ConversionPresenter.View {

    @Inject
    lateinit var presenter: ConversionPresenter
    private lateinit var conversionAdapter: ConversionAdapter
    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.raka.revolutdemo.R.layout.activity_conversion)
        initDagger()

        currencyRecyclerView.layoutManager = LinearLayoutManager(this)
        currencyRecyclerView.itemAnimator?.apply {
            moveDuration = 500
        }
        val clickListener = { newBase: String, position: Int -> onCurrencyItemInteraction(newBase, position) }
        val touchListener = { newBase: String, position: Int -> onCurrencyItemInteraction(newBase, position); true }
        conversionAdapter = ConversionAdapter(clickListener, touchListener)
        currencyRecyclerView.adapter = conversionAdapter

        presenter.onViewAttached(this)
    }

    private fun onCurrencyItemInteraction(newBase: String, position: Int) {
        if (!loading) {
            conversionAdapter.moveItem(position)
            currencyRecyclerView.scrollToPosition(0)
            presenter.getConversionRate(newBase)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDetached()
    }

    private fun initDagger() {
        DaggerConversionComponent.builder()
            .appComponent((application as App).getAppComponent())
            .conversionModule(ConversionModule(this))
            .build().inject(this)
    }

    override fun showLoading() {
        loading = true
        loadingView.visibility = VISIBLE
    }

    override fun hideLoading() {
        loading = false
        loadingView.visibility = INVISIBLE
    }

    override fun showErrorView(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(com.raka.revolutdemo.R.string.retry)) { presenter.retry() }
            .show()
    }

    override fun updateRates(rates: List<Rate>) {
        conversionAdapter.updateRates(rates)
    }
}
