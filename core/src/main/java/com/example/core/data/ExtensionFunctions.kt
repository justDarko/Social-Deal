package com.example.core.data

fun Float.toStringWithTwoDecimals(): String {
    return String.format("%.2f", this)
}