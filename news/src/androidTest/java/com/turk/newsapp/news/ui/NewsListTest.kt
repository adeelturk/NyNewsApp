package com.turk.newsapp.news.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.turk.newsapp.news.R
import org.junit.Assert.*
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.turk.dtos.news.NewsView
import com.turk.newsapp.newsDependencies
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.context.startKoin

@RunWith(AndroidJUnit4::class)
class NewsListTest
{
    @Before
    fun before() {
        startKoin {
            modules(listOf(
                newsDependencies
            ))
        }
    }

    @Test
    fun clickNewsListItem_shouldNavigateToDetail()
    {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setGraph(R.navigation.news_navigation)
        val newsListScenario = launchFragmentInContainer <FakeNewsList>(themeResId = R.style.Theme_NewsApp)
        newsListScenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
            it.populateData(listOf(NewsView.dummyNews()))
        }

        onView(ViewMatchers.withId(R.id.newsRv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.newsDetail)

    }
}