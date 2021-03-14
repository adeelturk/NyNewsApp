package com.data.newsapp.business

import com.data.newsapp.business.news.usecases.GetMostViewedNews
import org.koin.dsl.module

val useCasesDependencies= module {
    //waiter use cases
    single { GetMostViewedNews(get()) }
//    single { GetMostViewedNews2(get()) }

}