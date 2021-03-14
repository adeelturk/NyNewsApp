package com.base.common.customView;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.base.common.R;

import java.io.File;

public class ImageSourceBindAdapter {
    @BindingAdapter({"link"})
    public static void setRequestAttachmentImage(ImageView imageView, String url) {
            Glide.with(imageView.getContext()).load(url)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_photo))
                    .into(imageView);
    }

    @BindingAdapter({"attachmentLink"})
    public static void setAttachmentImageLink(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .apply(new RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_photo))
                .into(imageView);
    }


    @BindingAdapter({"filePath"})
    public static void setAttachmentImage(ImageView imageView, File file) {

        Uri imageUri = Uri.fromFile(file);
        Glide.with(imageView.getContext()).load(imageUri)
                .apply(new RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_photo))
                .into(imageView);
    }

    @BindingAdapter({"filePath"})
    public static void setAttachmentImage(ImageView imageView, String filePath) {
        File file=new File(filePath);
        Uri imageUri = Uri.fromFile(file);
        Glide.with(imageView.getContext()).load(imageUri)
                .apply(new RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_photo))
                .into(imageView);
    }



    public int getImage(String imageName, Context context) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }

    @BindingAdapter({"drawable"})
    public static void setImageViewDrawable(ImageView imageView, int drawable) {
        imageView.setBackgroundResource(drawable);
    }

    @BindingAdapter({"drawable"})
    public static void setImageDrawable(ImageView imageView, int drawable) {
        Glide.with(imageView.getContext()).load("")
                .apply(new RequestOptions().placeholder(drawable).error(drawable))
                .into(imageView);
    }
}
