package com.raka.revolutdemo.presentation.feature.conversion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.raka.revolutdemo.R.layout
import com.raka.revolutdemo.common.extension.onChange
import com.raka.revolutdemo.domain.model.Rate
import com.raka.revolutdemo.presentation.utils.currencyImageMapper
import com.raka.revolutdemo.presentation.utils.currencyNameMapper
import kotlinx.android.synthetic.main.item_recycler_currency.view.*
import java.math.BigDecimal
import java.math.RoundingMode


class ConversionAdapter(private val clickListener: (String, Int) -> Unit,
                        private val touchListener: (String, Int) -> Boolean) :
    RecyclerView.Adapter<ConversionAdapter.ViewHolder>() {

    companion object {
        var conversionRateList: List<Rate> = emptyList()
        var baseAmount: BigDecimal = BigDecimal(100)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layout.item_recycler_currency, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = conversionRateList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(conversionRateList[holder.adapterPosition], holder.adapterPosition, clickListener, touchListener)

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(rate: Rate, position: Int, clickListener: (String, Int) -> Unit,
                 touchListener: (String, Int) -> Boolean) = with(itemView) {
            itemView.currencyTextView.text = rate.currency
            itemView.currencyNameTextView.text = itemView.context.getString(currencyNameMapper(rate.currency))
            Glide.with(context)
                .load(currencyImageMapper(rate.currency))
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(itemView.currencyImageView)
            if (position == 0) {
                itemView.valueEditText.onChange { baseAmount = if (it.isEmpty()) BigDecimal(-1) else it.toBigDecimal() }
            } else {
                itemView.valueEditText.setOnTouchListener { _, _ -> touchListener(rate.currency, position) }
                itemView.valueEditText.setText(
                    if (baseAmount == BigDecimal(-1))
                        ""
                    else
                        (rate.rate * baseAmount).setScale(2, RoundingMode.UP).toPlainString()
                )
            }
            setOnClickListener { clickListener(rate.currency, position) }
        }
    }

    fun updateRates(rates: List<Rate>) {
        conversionRateList = rates
        notifyDataSetChanged()
    }

    fun moveItem(oldPosition: Int) {
        notifyItemMoved(oldPosition, 0)
    }
}
