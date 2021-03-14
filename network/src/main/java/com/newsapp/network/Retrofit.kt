package com.newsapp.network

import com.base.common.error.ErrorEntity
import com.base.common.functional.Either
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call


/**
 * Takes in a transform lambda to return a modified version of the responsex
 */

var generalErrorImplementation = GeneralErrorImplementation()

@Suppress("unused")
fun <T, R> Call<T>.requestTransformBlocking(transform: (T) -> R): Either<ErrorEntity, R> {
    return try {
        val response = execute()
        when (response.isSuccessful) {
            true -> Either.Right(transform(response.body()!!))
            false -> Either.Left(generalErrorImplementation.getHttpErrors(response))
        }
    } catch (exception: Throwable) {
        Either.Left(generalErrorImplementation.getError(exception))

    }
}

@Suppress("unused")
fun <T> Call<T>.requestBlocking(default: T): Either<ErrorEntity, T> {
    return try {
        val response = execute()
        when (response.isSuccessful) {
            true -> Either.Right((response.body() ?: default))
            false -> Either.Left(generalErrorImplementation.getHttpErrors(response))
        }
    } catch (exception: Throwable) {

        Either.Left(generalErrorImplementation.getError(exception))

    }
}

fun <T> Call<T>.requestBlocking(): Either<ErrorEntity, T> {
    return try {
        val response = execute()
        when (response.isSuccessful) {
            true -> Either.Right((response.body()!!))
            false -> Either.Left(generalErrorImplementation.getHttpErrors(response))
        }
    } catch (exception: Throwable) {
        Either.Left(generalErrorImplementation.getError(exception))
    }
}


inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object : TypeToken<T>() {}.type)








