package com.base.common.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat


fun String.serverDateFormatter(): String {

    val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return SimpleDateFormat("dd-MMM-yyyy h:mm a").format(formatter.parse(this))
}