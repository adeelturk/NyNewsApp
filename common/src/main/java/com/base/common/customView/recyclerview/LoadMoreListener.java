package com.base.common.customView.recyclerview;

/**
 * Created by Turk
 * On 6/14/2018.
 */

/**
 * Use LoadMoreListener class to listen to get callback when list is scrolled to last time and its good to fetch new
 * page data
 */
public interface LoadMoreListener {

    void loadMore();
}
