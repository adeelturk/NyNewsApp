package com.base.common.customView.pager;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/**
 *
 * @author Adeel Ur Rehman Turk
 *
 * */

/**
 * This class is a Custom ViewPager , You can enable and disable the swipe using boolean flag [enabled]
 */

public class ControlledSwipeViewPager extends ViewPager {

    private boolean enabled=false;


    public ControlledSwipeViewPager(Context context) {
        super(context);
    }

    public ControlledSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }
    /**
     * use this method to enable or disable viewpager swipe at runtime
     *  @param enabled Booelan Flag.
     *
     */
    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}