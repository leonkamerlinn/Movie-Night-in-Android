package com.example.leon.movienightinandroid.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BindigAdapters {
    @BindingAdapter("imageLoad")
    public static void setBufferType(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
