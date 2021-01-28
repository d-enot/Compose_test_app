package com.sklymenko.composetestapp.ui.views

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.vectorResource
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sklymenko.composetestapp.R
import com.sklymenko.composetestapp.api.Api
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun NetworkImage(url: String?, modifier: Modifier) {
    if (url == null) {
        Image(
            imageVector = vectorResource(id = R.drawable.baseline_cancel_presentation_24),
            modifier = modifier
        )
        return
    }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    Glide.with(AmbientContext.current)
        .asBitmap()
        .load(Api.IMG_URL + url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

    if (bitmap != null) {
        Image(bitmap = bitmap!!.asImageBitmap(), modifier = modifier)
    }
}