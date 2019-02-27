package com.raka.revolutdemo.presentation.utils

import com.raka.revolutdemo.R

fun currencyImageMapper(currency: String): Int {
    return when(currency) {
        "AUD" -> R.drawable.ic_aud
        "BGN" -> R.drawable.ic_bgn
        "BRL" -> R.drawable.ic_brl
        "CAD" -> R.drawable.ic_cad
        "CHF" -> R.drawable.ic_chf
        "CNY" -> R.drawable.ic_cny
        "CZK" -> R.drawable.ic_czk
        "DKK" -> R.drawable.ic_dkk
        "EUR" -> R.drawable.ic_eur
        "GBP" -> R.drawable.ic_gbp
        "HKD" -> R.drawable.ic_hkd
        "HRK" -> R.drawable.ic_hrk
        "HUF" -> R.drawable.ic_huf
        "IDR" -> R.drawable.ic_idr
        "ILS" -> R.drawable.ic_ils
        "INR" -> R.drawable.ic_inr
        "ISK" -> R.drawable.ic_isk
        "JPY" -> R.drawable.ic_jpy
        "KRW" -> R.drawable.ic_krw
        "MXN" -> R.drawable.ic_mxn
        "MYR" -> R.drawable.ic_myr
        "NOK" -> R.drawable.ic_nok
        "NZD" -> R.drawable.ic_nzd
        "PHP" -> R.drawable.ic_php
        "PLN" -> R.drawable.ic_pln
        "RON" -> R.drawable.ic_ron
        "RUB" -> R.drawable.ic_rub
        "SEK" -> R.drawable.ic_sek
        "SGD" -> R.drawable.ic_sgd
        "THB" -> R.drawable.ic_thb
        "TRY" -> R.drawable.ic_try
        "USD" -> R.drawable.ic_usd
        "ZAR" -> R.drawable.ic_zar
        else -> android.R.drawable.stat_notify_error
    }
}

fun currencyNameMapper(currency: String): Int {
    return when(currency) {
        "AUD" -> R.string.aud_string
        "BGN" -> R.string.bgn_string
        "BRL" -> R.string.brl_string
        "CAD" -> R.string.cad_string
        "CHF" -> R.string.chf_string
        "CNY" -> R.string.cny_string
        "CZK" -> R.string.czk_string
        "DKK" -> R.string.dkk_string
        "EUR" -> R.string.eur_string
        "GBP" -> R.string.gbp_string
        "HKD" -> R.string.hkd_string
        "HRK" -> R.string.hrk_string
        "HUF" -> R.string.huf_string
        "IDR" -> R.string.idr_string
        "ILS" -> R.string.ils_string
        "INR" -> R.string.inr_string
        "ISK" -> R.string.isk_string
        "JPY" -> R.string.jpy_string
        "KRW" -> R.string.krw_string
        "MXN" -> R.string.mxn_string
        "MYR" -> R.string.myr_string
        "NOK" -> R.string.nok_string
        "NZD" -> R.string.nzd_string
        "PHP" -> R.string.php_string
        "PLN" -> R.string.pln_string
        "RON" -> R.string.ron_string
        "RUB" -> R.string.rub_string
        "SEK" -> R.string.sek_string
        "SGD" -> R.string.sgd_string
        "THB" -> R.string.thb_string
        "TRY" -> R.string.try_string
        "USD" -> R.string.usd_string
        "ZAR" -> R.string.zar_string
        else -> R.string.unknown_string
    }
}