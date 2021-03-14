package com.turk.newsapp

import com.turk.newsapp.viewmodel.NewsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsDependencies = module {
    viewModel {
        NewsViewModel(
            get()
        )
    }
}