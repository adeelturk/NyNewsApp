package com.base.common.typeAliases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.base.common.R
import com.base.common.infrastructure.SingleLiveEvent


typealias MutableLiveDataArrayListString = MutableLiveData<ArrayList<String>>

typealias MutableListLiveDataString = MutableLiveData<List<String>>

typealias MutableListLiveDataInt = MutableLiveData<List<Int>>

typealias MutableLiveDataString = MutableLiveData<String>

typealias MutableListLiveDataCustom<T> = MutableLiveData<List<T>>

typealias MutableLiveDataCustom<T> = MutableLiveData<T>

typealias LiveDataString = LiveData<String>

typealias LiveDataCustom<T> = LiveData<T>

typealias LiveDataListCustom<T> = LiveData<List<T>>

typealias LiveDataListInt = LiveData<List<Int>>

typealias  SingleLiveEventListString = SingleLiveEvent<String>

typealias SingleLiveEventCustomList<T> = SingleLiveEvent<List<T>>

typealias StringResources = R.string




