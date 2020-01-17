package com.example.advancedandroid.other.extension

fun<T> HashMap<T,String>.getValue(key: T): String {
    return get(key) ?: return ""
}