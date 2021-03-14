package com.turk.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.base.common.base.BaseViewModel
import com.data.dtos.news.NewsView
import com.data.newsapp.business.news.usecases.GetMostViewedNews

class NewsViewModel(  val getMostViewedNews: GetMostViewedNews) :
    BaseViewModel() {

    private val _mostViewedNews = MutableLiveData<List<NewsView>>()
     var selectedNews: NewsView?=null

    val mostViewedNews: LiveData<List<NewsView>>
        get() = _mostViewedNews

    fun fetchMostViewedNews(params: GetMostViewedNews.Params) {


        getMostViewedNews(viewModelScope,params){
            it.either(::handleFailure){
                _mostViewedNews.value = it
            }


        }
    }

    override fun onCleared() {
        super.onCleared()
    }


}