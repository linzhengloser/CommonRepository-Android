package com.lz.rxjava2demo.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lz.rxjava2demo.R;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/08/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ImageMessageItemViewBinder extends BaseMessageItemViewBinder<ImageMessageItemViewBinder.ImageMessageViewHolder> {

    @Override
    protected void onBindContentViewHolder(ImageMessageViewHolder holder, Message item) {

    }

    @Override
    protected ContentViewHolder onCreateContentViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ImageMessageViewHolder(inflater.inflate(R.layout.item_image_message, parent, false));
    }

    public static class ImageMessageViewHolder extends BaseMessageItemViewBinder.ContentViewHolder {

        public ImageMessageViewHolder(View itemView) {
            super(itemView);
        }

    }

}
