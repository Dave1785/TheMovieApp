package com.funapps.themovie.extensions

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun ImageView.load(imageUrl: String, @DrawableRes placeholder: Int, @DrawableRes error: Int) {
    var requestOptions =  RequestOptions().transforms( CenterCrop(),  RoundedCorners(24))
    Glide.with(this.context)
        .load(imageUrl)
        .apply(requestOptions)
        .placeholder(placeholder)
        .error(error)
        .into(this)
}

fun ImageView.loadUri(uri: Uri) {
    Glide.with(this)
        .load(uri)
        .into(this)
}