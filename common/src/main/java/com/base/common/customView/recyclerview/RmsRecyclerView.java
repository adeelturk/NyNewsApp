package com.base.common.customView.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 * @author Adeel Ur Rehman Turk
 *
 * */

/**
 * A customised recyclerview made for list data having pagination
 * set [loadMoreListener] LoadMoreListener object to listen to end of scroll soc that you can fetch next page data
 *
 */

public class RmsRecyclerView extends RecyclerView {

    private LoadMoreListener loadMoreListener;

    private boolean loadingWithLoadMore = false;
    private boolean loadingPullRefresh = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    public RmsRecyclerView(Context context) {
        super(context);
    }

    public RmsRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RmsRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public LoadMoreListener getLoadMoreListener() {
        return loadMoreListener;
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
        init();
    }

    public void resetLoading() {

        loadingWithLoadMore = false;
        loadingPullRefresh=false;
    }

    public boolean isLoadingPullRefresh() {
        return loadingPullRefresh;
    }

    public void setLoadingPullRefresh(boolean loadingPullRefresh) {
        this.loadingPullRefresh = loadingPullRefresh;
        loadingWithLoadMore = !loadingPullRefresh;
    }

    public boolean isLoadingWithLoadMore() {
        return loadingWithLoadMore;
    }

    public void setLoadingWithLoadMore(boolean loadingWithLoadMore) {
        this.loadingWithLoadMore = loadingWithLoadMore;
        this.loadingPullRefresh = !loadingWithLoadMore;

    }

    public void blockLoadMore(){

        loadingWithLoadMore=true;
    }

    public void init() {


        if (loadMoreListener != null) {


            this.addOnScrollListener(new OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0) //check for scroll down
                    {
                        visibleItemCount = getLayoutManager().getChildCount();
                        totalItemCount = getLayoutManager().getItemCount();
                        pastVisiblesItems = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();

                        if (!loadingWithLoadMore&&!loadingPullRefresh) {
                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                loadingWithLoadMore = true;

                                loadMoreListener.loadMore();
                                //Do pagination.. i.e. fetch new data
                            }
                        }
                    }
                }
            });


        }

    }


}
