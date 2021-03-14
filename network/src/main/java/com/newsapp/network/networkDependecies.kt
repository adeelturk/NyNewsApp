package com.newsapp.network

import com.data.dtos.network.RequestHeaders
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule= module{
    single {OkHttpClient.Builder()}
    single { RequestInterceptor(RequestHeaders) }


    single<Retrofit> {



        val  logger=  HttpLoggingInterceptor().apply {

            level= if(BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE

        }


        val retrofitBuilder= Retrofit.Builder()
        retrofitBuilder.apply {

            baseUrl(BuildConfig.API_ENDPOINT)
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        }.also {
            val okHttpClientBuilder =get<OkHttpClient.Builder>()

            //inserting Request Interceptor
            okHttpClientBuilder.addInterceptor(get<RequestInterceptor>())
            okHttpClientBuilder.addInterceptor(logger)
            it.client(okHttpClientBuilder.build())

            okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
            okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS).build()
        }
        retrofitBuilder.build()

    }


}