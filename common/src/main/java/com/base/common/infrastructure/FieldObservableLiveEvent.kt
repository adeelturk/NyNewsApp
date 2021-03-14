package com.base.common.infrastructure

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.lifecycle.MutableLiveData


class FieldObservableLiveEvent<T : BaseObservable> : MutableLiveData<T>()
{

    override fun setValue(value: T?) {
        super.setValue(value)
        //listen to property changes
        value!!.addOnPropertyChangedCallback(callback)
    }

    var callback: OnPropertyChangedCallback = object : OnPropertyChangedCallback() {
        override fun onPropertyChanged(
            sender: Observable?,
            propertyId: Int
        ) { //Trigger LiveData observer on change of any property in object
            value = value
        }
    }

}