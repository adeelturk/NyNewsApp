package com.turk.newsapp.ui

import com.data.dtos.news.PictureView

object PictureUtil {
    fun findLargestImage(pictureViews: List<PictureView>): PictureView {
        return pictureViews.maxByOrNull { it.height + it.width } ?:PictureView.empty()
    }
}