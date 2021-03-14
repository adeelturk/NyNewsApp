package com.newsapp.network

import com.data.dtos.network.RequestHeaders
import java.io.IOException
import okhttp3.*
import okhttp3.Headers.Companion.toHeaders


class RequestInterceptor(private val requestHeaders: RequestHeaders) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val builder: Request.Builder = original
                .newBuilder()
                .headers(requestHeaders.headers.toHeaders())
                .method(original.method, original.body)
        val newRequest: Request = builder.build()
        return chain.proceed(newRequest)
    }
}