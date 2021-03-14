package com.base.common.customView.recyclerview;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WrapContentGridLayoutManager extends GridLayoutManager {
    //... constructor

    public WrapContentGridLayoutManager(Context context, int spanCount){
        super(context,spanCount);
    }


    public WrapContentGridLayoutManager(Context context, int spanCount,
                                        @RecyclerView.Orientation int orientation, boolean reverseLayout) {
        super(context,spanCount ,orientation, reverseLayout);
    }



    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("TAG", "boom in RecyclerView");
        }
    }


    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}