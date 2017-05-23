package com.risenb.honourfamily.utils.imageloader;

import android.widget.ImageView;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/05/23
 *     desc   : 图片加载接口
 *     version: 1.0
 * </pre>
 */
public interface ImageLoader {

    void loadImage(ImageView imageView, String imageUrl);

    void loadImage(ImageView imageView, String imageUrl, ImageLoaderOptions options);

}
