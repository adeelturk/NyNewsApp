package com.base.common.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalItemDecorator :RecyclerView.ItemDecoration()  {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val itemPosition:Int = parent.getChildAdapterPosition(view)
        outRect.apply {
            left = 10
            if(itemPosition==0) {
                left = 20
            }
            right=10
            bottom=10
            top=10

        }
    }

}