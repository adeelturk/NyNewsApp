package com.data.newsapp.repositories.news

import com.newsapp.network.requestTransformBlocking


class NewsRepository(private val newsWebService: NewsWebService):NewsDataSource {


    override fun news(sections:String,period:Int)=newsWebService.news(sections, period).requestTransformBlocking {  it.results.map { it.toView() }}

   // override fun news(sections: String, period: Int): Either<ErrorEntity, List<NewsView>> = newsWebService.news(sections,period).requestBlocking()
}