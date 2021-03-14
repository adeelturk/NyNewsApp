package com.data.dtos.network

/**
 * Created by 999160 on 30/11/2017.
 */
object RequestHeaders {
    val headers: HashMap<String, String> = HashMap()

    fun setLanguage(language: String?) {
        if (language == null)
            headers.remove("Accept-Language")
        else
            headers["Accept-Language"] = language
    }

    init {
        headers["Content-Type"] = "application/json"
    }
}