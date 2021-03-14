package com.newsapp.newyorktimes

import android.app.Application
import com.data.newsapp.business.useCasesDependencies
import com.data.newsapp.repositories.repoDependencies
import com.newsapp.network.networkModule
import com.turk.newsapp.newsDependencies
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class NewsApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@NewsApplication)
            modules (
                listOf(
                    networkModule,
                    repoDependencies,
                    newsDependencies,
                    useCasesDependencies

                )
            )
        }
    }
}