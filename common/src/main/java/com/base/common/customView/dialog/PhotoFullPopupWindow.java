package com.base.common.customView.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.base.common.R;
import com.base.common.utils.RMSConstants;


import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 *
 * @author Adeel Ur Rehman Turk
 *
 * This class is a utility PopupWindow to show zoomable images in full screen
 */
public class PhotoFullPopupWindow extends PopupWindow {

    private Context mContext;
    private PhotoView photoView;
    private ProgressBar loading;
    private ViewGroup parent;


    /**
     * Constructor
     *  @param ctx  Context
     *  @param v  pas View object top show the over
     *  @param url Image url
     *
     */
    @SuppressLint("InflateParams")
    public PhotoFullPopupWindow(Context ctx, View v, String  url) {
        super(((LayoutInflater) Objects.requireNonNull(ctx.getSystemService(LAYOUT_INFLATER_SERVICE))).inflate( R.layout.popup_photo_full, null), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        if (Build.VERSION.SDK_INT >= 21) {
            setElevation(5.0f);
        }
        this.mContext = ctx;
        View view1 = getContentView();
        ImageButton closeButton =  view1.findViewById(R.id.ib_close);
        setOutsideTouchable(true);

        setFocusable(true);
        closeButton.setOnClickListener(view-> dismiss());

        photoView = view1.findViewById(R.id.image);
        loading =  view1.findViewById(R.id.loading);
        photoView.setMaximumScale(6);
        parent = (ViewGroup) photoView.getParent();
        loading.setVisibility(View.VISIBLE);
            Glide.with(ctx) .asBitmap()
                    .load(url)
                   /* .error(R.drawable.adp_logo)*/
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            parent.setBackground(new BitmapDrawable(mContext.getResources(), RMSConstants.fastBlur(Bitmap.createScaledBitmap(resource, 50, 50, true))));// ));

                            photoView.setImageBitmap(resource);

                            loading.setVisibility(View.GONE);
                            return false;
                        }
                    })

                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(photoView);

            showAtLocation(v, Gravity.CENTER, 0, 0);
        }

}