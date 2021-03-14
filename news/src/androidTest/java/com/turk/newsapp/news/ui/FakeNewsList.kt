    package com.turk.newsapp.news.ui

import android.os.Bundle
import com.turk.common.newsapp.base.BaseFragment
import com.turk.common.newsapp.base.GeneralAdapter
import com.turk.common.newsapp.extensions.configureVerticalList
import com.turk.dtos.news.NewsView
import com.turk.newsapp.news.BR
import com.turk.newsapp.news.R
import com.turk.newsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news_list.*

    class FakeNewsList : BaseFragment() {
    override fun layoutResourceId() = R.layout.fragment_news_list

    private lateinit var adapter: GeneralAdapter<NewsView>
    private val newsViewModel: NewsViewModel by sharedViewModel(from = {
        findNavController().getViewModelStoreOwner(R.id.newsNavigation)
    })
    override fun ignite(savedInstanceState: Bundle?) {
        adapter = GeneralAdapter(BR.news,R.layout.news_item, NewsView.DIFF_CALLBACK)
        adapter.clickListener={ news,view->
            findNavController().navigate(NewsListFragmentDirections.toDetail(news))
        }
    }
    fun populateData(news:List<NewsView>)
    {
        newsRv.configureVerticalList(adapter)
        adapter.submitList(news)
    }
}

