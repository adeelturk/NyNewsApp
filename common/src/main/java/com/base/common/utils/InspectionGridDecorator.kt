package com.base.common.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class InspectionGridDecorator :RecyclerView.ItemDecoration()  {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val itemPosition:Int = parent.getChildAdapterPosition(view)
        outRect.apply {

            left=10
            right=10
            bottom=20
            top=20

        }
    }

}