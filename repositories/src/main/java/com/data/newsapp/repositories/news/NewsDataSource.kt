package com.data.newsapp.repositories.news

import com.base.common.error.ErrorEntity
import com.base.common.functional.Either
import com.data.dtos.news.NewsView

interface NewsDataSource {
    fun news(sections:String,period:Int) : Either<ErrorEntity, List<NewsView>>
}