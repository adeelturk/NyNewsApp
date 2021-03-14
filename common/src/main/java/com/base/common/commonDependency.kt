package com.base.common

import android.content.Context
import com.base.common.customView.dialog.MyProgressDialog
import com.base.common.sharedPrefs.SharedPrefsUtil
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

var accReconModule= module{

    single { SharedPrefsUtil(androidApplication().getSharedPreferences("mySharedD_"+androidApplication().packageName,Context.MODE_PRIVATE)) }
    single { MyProgressDialog() }
//    single { CoroutineScope(Dispatchers.IO + Job()) }
     //https://github.com/mcastillof/FakeTraveler/blob/master/app/src/main/java/cl/coders/faketraveler/MainActivity.java#L94
}