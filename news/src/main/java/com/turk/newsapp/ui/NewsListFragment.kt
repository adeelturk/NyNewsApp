package com.turk.newsapp.ui

import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import com.base.common.base.BaseFragment
import com.base.common.base.BaseViewModel
import com.base.common.base.GeneralAdapter
import com.base.common.extensions.configureVerticalList
import com.base.common.extensions.observe
import com.data.dtos.news.NewsView
import com.data.newsapp.business.news.usecases.GetMostViewedNews
import com.turk.newsapp.news.BR
import com.turk.newsapp.news.R
import com.turk.newsapp.news.databinding.FragmentNewsListBinding
import com.turk.newsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.android.synthetic.main.fragment_news_list.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NewsListFragment : BaseFragment() {
    //region Members and Props
    private val adapter = GeneralAdapter(BR.news, R.layout.news_item, NewsView.DIFF_CALLBACK)
    //endregion

    //region Injections
    //TODO("Read this sharedGraphViewModelExtension function and its purpose")
    private val newsViewModel: NewsViewModel by sharedViewModel()

    //endregion


    //endregion

    //region Base Overrides

    override fun layoutResource(): Int = R.layout.fragment_news_list
    override fun getFragment(): BaseFragment = this

    override fun getViewModel(): BaseViewModel = newsViewModel

    override fun initialise() {

        setUpCLickListener()
        newsRv.configureVerticalList(adapter)
        setScreenTitle(getString(R.string.ny_times), getString(R.string.most_popular))
        observeMostViewed()
        fetchNews()
        ( binding as FragmentNewsListBinding).swipeRefreh.setOnRefreshListener {

            fetchNews()
        }
    }
    private fun fetchNews(){
        newsViewModel.fetchMostViewedNews(GetMostViewedNews.Params("all-sections", 7))
    }
    private fun observeMostViewed() {
        observe(newsViewModel.mostViewedNews)
        {
            //only commit the largest picture from the array , so as to smoothly show the transitions between this screen and the detail
            it.forEach {
                val largestPicture = PictureUtil.findLargestImage(it.pictures)
                it.largestPicture = largestPicture
            }
            adapter.submitList(it)

            ( binding as FragmentNewsListBinding).swipeRefreh?.isRefreshing = false
        }

    }

    private fun setUpCLickListener() {

         adapter.clickListener={ news,_->
             newsViewModel.selectedNews=news
             findNavController().navigate(R.id.newsDetail)
         }
    }


//endregion
}





