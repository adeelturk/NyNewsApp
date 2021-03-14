package com.base.common.error

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Error(@SerializedName("code")val code:Int,
                 @SerializedName("message")val message:String,
                 @SerializedName("details")val details:List<String>)
//                 @SerializedName("details")val details:String)