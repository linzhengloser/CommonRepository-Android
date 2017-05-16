package com.risenb.honourfamily.utils.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


/**
 * 实现GLide
 */
public class ImageLoaderGlide extends ImageLoaderLogic {

    @Override
    void commonLoadImage(ImageView imageView, String imageUrl, ImageLoaderOptions options) {
        buildDrawableRequestBuilder(imageView.getContext(),null,imageUrl,options).into(imageView);

//        DrawableRequestBuilder<String> drawableRequestBuilder = Glide.with(imageView.getContext())
//                .load(imageUrl)
//                .placeholder(options.loadingResId)
//                .error(options.loadErrorResId);
//
//        drawableRequestBuilder.into(imageView);
    }

    @Override
    void commonLoadImage(ImageView imageView, Uri imageUri, ImageLoaderOptions options) {

        buildDrawableRequestBuilder(imageView.getContext(),imageUri,null,options).into(imageView);

//        Glide.with(imageView.getContext())
//                .load(imageUri)
//                .placeholder(options.loadingResId)
//                .error(options.loadErrorResId)
//                .into(imageView);
    }

    @Override
    void commonLoadImage2SimpleTarget(final ImageView imageView, String imageUrl, ImageLoaderOptions options) {
        buildDrawableRequestBuilder(imageView.getContext(),null,imageUrl,options).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                imageView.setImageDrawable(resource);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                imageView.setImageDrawable(errorDrawable);
            }
        });


//        Glide.with(imageView.getContext())
//                .load(imageUrl)
//                .placeholder(options.loadingResId)
//                .error(options.loadErrorResId)
//                .into(new SimpleTarget<GlideDrawable>() {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                        imageView.setImageDrawable(resource);
//                    }
//
//                    @Override
//                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                        imageView.setImageDrawable(errorDrawable);
//                    }
//                });
    }

    private DrawableRequestBuilder buildDrawableRequestBuilder(Context context, Uri imageUri, String imageUrl, ImageLoaderOptions options) {

        DrawableTypeRequest drawableTypeRequest;

        if (imageUri != null) {
            drawableTypeRequest = Glide.with(context).load(imageUri);
        } else {
            drawableTypeRequest = Glide.with(context).load(imageUrl);
        }

        DrawableRequestBuilder drawableRequestBuilder = drawableTypeRequest.placeholder(options.loadingResId).error(options.loadErrorResId);

        if (options.width != 0 && options.height != 0) {
            drawableRequestBuilder.override(options.width,options.height);
        }

        if(options.bitmapTransformation!=null){
            drawableRequestBuilder.transform(options.bitmapTransformation);
        }

        return drawableTypeRequest;

    }


}
