package com.risenb.honourfamily.utils.imageloader;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;


/**
 * 作者:linzheng 日期:2016/11/17 功能:对通用逻辑封装
 */

public abstract class ImageLoaderLogic implements ImageLoader {

    /**
     * 默认Options
     */
    public static ImageLoader.ImageLoaderOptions defaultOptions;

    static {
        defaultOptions = new ImageLoaderOptions();
//        defaultOptions.loadingResId = R.drawable.loadingimage;
//        defaultOptions.loadErrorResId = R.drawable.notimage;
        defaultOptions.isFitXY = true;
    }

    @Override
    public void loadImage(ImageView imageView, Uri imageUri) {
        loadImage(imageView, imageUri, null);
    }

    @Override
    public void loadImage(ImageView imageView, Uri imageUri, ImageLoaderOptions options) {
        options = options == null ? defaultOptions : options;

        if(options.isFitXY){
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        commonLoadImage(imageView, imageUri, options);
    }

    @Override
    public void loadImage( ImageView imageView, String imageUrl) {
        loadImage(imageView, imageUrl, null);
    }

    @Override
    public void loadImage(ImageView imageView, String imageUrl, ImageLoaderOptions options) {
        options = options == null ? defaultOptions : options;
        Log.i("ImageLoader","ImageLoaderUtils loade image url = " + imageUrl);
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(options.loadErrorResId);
            return;
        }

        if(options.isFitXY){
            if(!(imageView.getScaleType() == ImageView.ScaleType.FIT_XY))
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        commonLoadImage( imageView, getImageUrl(imageUrl), options);
    }

    @Override
    public void loadImage2SimpleTarget(ImageView imageView, String imageUrl, ImageLoaderOptions options) {

        options = options == null ? defaultOptions : options;

        //检测图片URL
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(options.loadErrorResId);
            return;
        }

        imageUrl = getImageUrl(imageUrl);

        commonLoadImage2SimpleTarget(imageView,imageUrl,options);

    }

    @Override
    public void loadImage2SimpleTarget(ImageView imageView, String imageUrl) {
        loadImage2SimpleTarget(imageView,imageUrl,null);
    }



    //每个不用框架加载图片的逻辑
    abstract void commonLoadImage(ImageView imageView, String imageUrl, ImageLoaderOptions options);

    //每个不用框架加载图片的逻辑
    abstract void commonLoadImage(ImageView imageView, Uri imageUri, ImageLoaderOptions options);

    //使用SimpleTarget加载图片
    abstract void commonLoadImage2SimpleTarget(ImageView imageView,String imageUrl,ImageLoaderOptions options);

    /**
     * 对图片路径做拼接操作
     */
    public String getImageUrl(String imageUrl) {
        if (imageUrl.startsWith("http"))
            return imageUrl;
        else
//            return .getString(R.string.service_host_address_image).concat(imageUrl);
        return "";
    }

}
