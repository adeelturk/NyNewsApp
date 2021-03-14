package com.turk.newsapp.ui

import com.base.common.base.BaseFragment
import com.base.common.base.BaseViewModel
import com.base.common.extensions.sharedGraphViewModel
import com.turk.newsapp.news.R
import com.turk.newsapp.news.databinding.FragmentNewsDetailBinding
import com.turk.newsapp.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NewsDetailFragment : BaseFragment() {

    //region Base Overrides
    private val newsViewModel : NewsViewModel by sharedViewModel()

    override fun layoutResource(): Int = R.layout.fragment_news_detail

    override fun getFragment(): BaseFragment = this

    override fun getViewModel(): BaseViewModel =newsViewModel

    override fun initialise() {

        (binding as FragmentNewsDetailBinding).newsDetail=newsViewModel.selectedNews
    }


    //endregion
}

