package com.fun88.testapplication

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.loadImage(context: Context, url: String) {
    Glide
        .with(context)
        .load(url)
        .centerCrop()
        .into(this);
}
