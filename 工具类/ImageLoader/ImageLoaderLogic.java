package com.risenb.honourfamily.utils.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.risenb.honourfamily.R;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/05/23
 *     desc   : 图片加载的逻辑封装
 *     version: 1.0
 * </pre>
 */
public class ImageLoaderLogic {

    /**
     * 根据Options 里面的参数 将图片加载到不同的地方
     * @param imageView
     * @param drawableRequestBuilder
     * @param imageLoaderOptions
     */
    public void intoImageView(final ImageView imageView, DrawableRequestBuilder drawableRequestBuilder, final ImageLoaderOptions imageLoaderOptions){
        if(imageLoaderOptions.isLoad2CircleImageView()){
            drawableRequestBuilder.into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    imageView.setImageDrawable(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    imageView.setImageDrawable(errorDrawable);
                }

                @Override
                public void onLoadStarted(Drawable placeholder) {
                    imageView.setImageDrawable(placeholder);
                }
            });
        }else if(imageLoaderOptions.getSimpleTarget()!=null){
            drawableRequestBuilder.into(imageLoaderOptions.getSimpleTarget());
        }
        else{
            drawableRequestBuilder.into(imageView);
        }
    }


    /**
     * 生成 ImageUrl
     * @param context
     * @param imageUrl
     * @param options
     * @return
     */
    public String generateImageUrl(Context context , String imageUrl, ImageLoaderOptions options){
        if(options.isNetworkUrl()){
            return generateNetworkUrl(context,imageUrl);
        }else{
            return imageUrl;
        }
    }

    /**
     * 生成网络 Url
     * @param context
     * @param url
     * @return
     */
    private String generateNetworkUrl(Context context, String url) {
        //处理绝对路径和相对路径
        if(!url.startsWith("http")){
            return context.getResources().getString(R.string.service_host_address_photo).concat(url);
        }else{
            return url;
        }
    }
}
