package com.data.newsapp.repositories.news

import com.data.dtos.serverObjects.NewsHolder
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsWebService {
    @GET("mostpopular/v2/mostviewed/{sections}/{period}.json?api-key=S4jwUM4ieSHAdemoo0MJ3YzYURhK70vF")
    fun news(
        @Path("sections") sections: String,
        @Path("period") period: Int
    ): Call<NewsHolder>
}