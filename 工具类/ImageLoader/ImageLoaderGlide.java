package com.risenb.honourfamily.utils.imageloader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/05/23
 *     desc   : ImageLoaeer Glide 的实现
 *     version: 1.0
 * </pre>
 */
public class ImageLoaderGlide implements ImageLoader {

    ImageLoaderLogic mImageLoaderLogic;


    public ImageLoaderGlide(ImageLoaderLogic imageLoaderLogic) {
        mImageLoaderLogic = imageLoaderLogic;
    }

    public ImageLoaderGlide() {
        this(new ImageLoaderLogic());
    }

    @Override
    public void loadImage(ImageView imageView, String imageUrl) {
        loadImage(imageView,imageUrl,ImageLoaderOptions.sDefaultImageLoaderOptions);
    }

    @Override
    public void loadImage(ImageView imageView, String imageUrl, ImageLoaderOptions options) {
        if(imageView == null)return;
        DrawableRequestBuilder builder = buildDrawableRequestBuilder(imageView.getContext(), null, mImageLoaderLogic.generateImageUrl(imageView.getContext(), imageUrl, options), options);
        mImageLoaderLogic.intoImageView(imageView,builder,options);
    }

    /**
     * 构建 DrawableRequestBuilder
     * @param context 上下文对象
     * @param imageUri imageUri
     * @param imageUrl imageUrl
     * @param options options
     * @return DrawableRequestBuilder
     */
    private DrawableRequestBuilder buildDrawableRequestBuilder(Context context, Uri imageUri, String imageUrl, ImageLoaderOptions options) {

        DrawableTypeRequest drawableTypeRequest;

        if (imageUri != null) {
            drawableTypeRequest = Glide.with(context).load(imageUri);
        } else {
            drawableTypeRequest = Glide.with(context).load(imageUrl);
        }

        DrawableRequestBuilder drawableRequestBuilder = drawableTypeRequest.placeholder(options.getLoadingResId()).error(options.getLoadErrorResId());

        if (options.getWidth() != 0 && options.getHeight() != 0) {
            drawableRequestBuilder.override(options.getWidth(),options.getHeight());
        }

        if(options.getBitmapTransformation()!=null){
            drawableRequestBuilder.transform(options.getBitmapTransformation());
        }

        return drawableTypeRequest;

    }

}
