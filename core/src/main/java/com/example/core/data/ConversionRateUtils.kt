package com.example.core.data

fun convertToUSD(price: Float): Float {
    val eurToUsdRate = 1.18 // approximately
    return (price * eurToUsdRate).toFloat()
}