package com.data.newsapp.repositories.news

import com.base.common.functional.Either
import com.data.dtos.serverObjects.NewsHolder
import com.data.newsapp.network.networkDependencies
import com.data.newsapp.repositories.repoDependencies
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryTest : AutoCloseKoinTest(){

    @Before
    fun before() {
        startKoin {
            modules(listOf(
                networkDependencies,
                repoDependencies
            ))
        }
        declareMock<NewsWebService>()
    }

    val newsWebService by inject<NewsWebService>()


    @Test
    fun `should return Either_Right on Success`()
    {
        val newsRepository = NewsRepository(newsWebService)
        val serviceCall: Call<NewsHolder> = mock()
        val retrofitResponse: Response<NewsHolder> = mock()
        val responseBody: NewsHolder = mock()
//
        Mockito.`when`(serviceCall.execute()).thenReturn(retrofitResponse)
        Mockito.`when`(retrofitResponse.isSuccessful).thenReturn(true)
        Mockito.`when`(retrofitResponse.body()).thenReturn(responseBody)
        Mockito.`when`(newsWebService.news(ArgumentMatchers.anyString(),ArgumentMatchers.anyInt())).thenReturn(serviceCall)

        val response = newsRepository.news("werw",123)
        Assert.assertTrue(response is Either.Right)

    }

    @Test
    fun `should return Either_Left on Failure`()
    {
        val newsRepository = NewsRepository(newsWebService)
        val serviceCall: Call<NewsHolder> = mock()
        val retrofitResponse: Response<NewsHolder> = mock()

        Mockito.`when`(serviceCall.execute()).thenReturn(retrofitResponse)
        Mockito.`when`(retrofitResponse.isSuccessful).thenReturn(false)
        Mockito.`when`(retrofitResponse.code()).thenReturn(400)
        Mockito.`when`(newsWebService.news(ArgumentMatchers.anyString(),ArgumentMatchers.anyInt())).thenReturn(serviceCall)

        val response = newsRepository.news("werw",123)
        Assert.assertTrue(response is Either.Left)

    }

}

inline fun <reified T : Any> mock() = Mockito.mock(T::class.java)