package com.base.common.extensions

import android.view.MotionEvent
import android.widget.EditText

fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
    this.setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is EditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}

fun EditText.checkAndPopulateTextViewData(value:Int){

    if(value>=0){

        this.setText(value.toString())
    }else{
        this.setText( String.empty())
    }



}

fun EditText.checkAndPopulateTextViewData(value:Double){

    if(value>=0){

        this.setText( value.toString())
    }else{
        this.setText(String.empty())
    }



}