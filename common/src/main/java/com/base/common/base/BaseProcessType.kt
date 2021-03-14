package com.base.common.base

import androidx.lifecycle.MutableLiveData

open class BaseProcessType() : MutableLiveData<String>() {

    constructor( type:String): this(){

        value=type
    }

    companion object BaseProcessType{

        val DEFAULT=
            BaseProcessType("DEFAULT")

        val MOVE_TO_LOGIN=
            BaseProcessType("moveToLogin")

    }


}