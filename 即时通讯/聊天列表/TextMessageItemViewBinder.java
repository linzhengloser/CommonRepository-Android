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
public class TextMessageItemViewBinder extends BaseMessageItemViewBinder<TextMessageItemViewBinder.TextMessageViewHolder> {


    @Override
    protected void onBindContentViewHolder(TextMessageViewHolder holder, Message item) {

    }

    @Override
    protected ContentViewHolder onCreateContentViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new TextMessageViewHolder(inflater.inflate(R.layout.item_text_message,parent,false));
    }

    public static class TextMessageViewHolder extends BaseMessageItemViewBinder.ContentViewHolder {

        public TextMessageViewHolder(View itemView) {
            super(itemView);
        }
    }


}
