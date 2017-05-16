package com.risenb.honourfamily.utils.imageloader;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;

/**
 * Created by admin on 2016/3/28.
 */
public interface ImageLoader {

    /**
     * 加载网络图片
     * @param imageView 要显示图片的ImageView
     * @param imageUrl 图片的URL
     * @param options 加载图片的相关参数
     */
    void loadImage(ImageView imageView, String imageUrl, ImageLoaderOptions options);


    /**
     * 使用默认方式加载网络图片
     * @param imageView
     * @param imageUrl
     */
    void loadImage( ImageView imageView, String imageUrl);

    /**
     * 通过Uri加载图片
     * @param imageView
     * @param imageUri
     * @param options
     */
    void loadImage( ImageView imageView, Uri imageUri, ImageLoaderOptions options);

    /**
     * 使用默认的方式加载Uri图片
     * @param imageView
     * @param imageUri
     */
    void loadImage(ImageView imageView, Uri imageUri);

    void loadImage2SimpleTarget(ImageView imageView,String imageUrl, ImageLoaderOptions options);

    void loadImage2SimpleTarget(ImageView imageView,String imageUrl);

    class ImageLoaderOptions {
        /**
         * 图片加载中要显示的图片
         */
        public int loadingResId;

        /**
         * 图片加载出错要显示的图片
         */
        public int loadErrorResId;

        /**
         * 显示缩略图的比例
         */
        public int loadThumbnail;

        /**
         * Glide中的 Transformation
         */
        public Transformation bitmapTransformation;

        /**
         * 是否需要设置ImageView 为 fitXY
         */
        public boolean isFitXY;

        /**
         * 设置图片显示宽度 width 和 height 需要同时设置 否者无效
         */
        public int width;

        /**
         * 设置图片显示的高度
         */
        public int height;

    }

}
