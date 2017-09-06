package com.lz.rxjava2demo.message;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lz.rxjava2demo.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/08/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseMessageItemViewBinder<VH extends BaseMessageItemViewBinder.ContentViewHolder> extends ItemViewBinder<Message, BaseMessageItemViewBinder.MessageViewHolder> {


    @NonNull
    @Override
    protected MessageViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.base_item_message, parent, false);
        ContentViewHolder contentViewHolder = onCreateContentViewHolder(inflater, parent);
        return new MessageViewHolder(root, contentViewHolder);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, @NonNull Message item) {

        onBindContentViewHolder((VH) holder.contentViewHolder, item);

        holder.fl_start_container.removeAllViews();
        holder.fl_end_container.removeAllViews();
        if (item.getMessageDirect() == Message.Direct.SEND) {
            holder.ll_start_container.setVisibility(View.GONE);
            holder.ll_end_container.setVisibility(View.VISIBLE);
            holder.fl_end_container.addView(holder.contentViewHolder.itemView);
        } else {
            holder.ll_start_container.setVisibility(View.VISIBLE);
            holder.ll_end_container.setVisibility(View.GONE);
            holder.fl_start_container.addView(holder.contentViewHolder.itemView);
        }
    }

    protected abstract void onBindContentViewHolder(VH holder, Message item);

    protected abstract ContentViewHolder onCreateContentViewHolder(LayoutInflater inflater, ViewGroup parent);

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_user_head;

        LinearLayout ll_end_container;

        LinearLayout ll_start_container;

        FrameLayout fl_start_container;

        FrameLayout fl_end_container;

        ContentViewHolder contentViewHolder;

        public MessageViewHolder(View itemView, ContentViewHolder contentViewHolder) {
            super(itemView);
            ll_end_container = itemView.findViewById(R.id.ll_end_container);
            ll_start_container = itemView.findViewById(R.id.ll_start_container);
            fl_end_container = itemView.findViewById(R.id.fl_end_container);
            fl_start_container = itemView.findViewById(R.id.fl_start_container);
            iv_user_head = itemView.findViewById(R.id.iv_user_head);
            this.contentViewHolder = contentViewHolder;
        }

    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        public ContentViewHolder(View itemView) {
            super(itemView);
        }
    }

}
