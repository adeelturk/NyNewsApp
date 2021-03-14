package com.data.newsapp.business.news.usecases

import com.base.common.base.BaseUseCase
import com.data.dtos.news.NewsView
import com.data.newsapp.repositories.news.NewsRepository
import kotlinx.coroutines.CoroutineScope


class GetMostViewedNews(private val newsRepository: NewsRepository) :
    BaseUseCase<List<NewsView>, GetMostViewedNews.Params>() {
    override suspend fun run(param: Params) = newsRepository.news(param.sections, param.period)
    data class Params(val sections: String, val period: Int)
}