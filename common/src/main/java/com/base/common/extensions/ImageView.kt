package com.base.common.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.base.common.R
import com.bumptech.glide.Glide


//endregion
@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    Glide.with(context.applicationContext).load(url).placeholder(R.drawable.ic_gallery).into(this)
}

