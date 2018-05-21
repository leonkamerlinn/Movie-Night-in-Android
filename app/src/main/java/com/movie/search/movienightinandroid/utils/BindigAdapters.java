package com.movie.search.movienightinandroid.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.movie.search.movienightinandroid.layout.TagLayout;

public class BindigAdapters {
    @BindingAdapter("imageLoad")
    public static void setBufferType(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    @BindingAdapter("showView")
    public static void showView(View view, boolean show) {
        if (show) {
            if (view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        } else {
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            }
        }
    }

    @BindingAdapter("hideView")
    public static void hideView(View view, boolean hide) {
        showView(view, !hide);
    }

    @BindingAdapter("setGenres")
    public static void setGenres(TagLayout tagLayout, String[] genres) {
        tagLayout.setCheckedGenres(genres);
    }

    @BindingAdapter("setAdapter")
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("setLayoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

}
