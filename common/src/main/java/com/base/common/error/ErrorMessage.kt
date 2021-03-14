package com.base.common.error

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
class ErrorMessage(@SerializedName("success") val isSuccess:Boolean,
                   @SerializedName("error") val errors: Error
):Serializable




/*
{"result":null,"targetUrl":null,"success":false,"error":{"code":0,"message":"Login failed!","details":"Invalid user name or password","validationErrors":null},"unAuthorizedRequest":false,"__abp":true}*/
