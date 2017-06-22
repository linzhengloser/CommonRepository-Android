package com.lz.eventbus3demo;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.lz.eventbus3demo.bean.BaseSelectBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:linzheng
 * 日期:2017/3/7
 * 功能:实现多选和单选的 RecyclerView Adapter
 */

public abstract class SingleAndMultiSelectedAdapter<T extends BaseSelectBean> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //普通模式
    private static final int MODE_NORMAL = 1001;

    //编辑模式
    private static final int MODE_EDIT = 1002;

    //单选模式
    private static final int SELECTED_MODE_SINGLE = 1003;

    //多选模式
    private static final int SELECTED_MODE_MULTI = 1004;

    //RecyclerView 对象
    private RecyclerView mRecyclerView;


    //当前模式 : 编辑模式 or 普通模式
    private int mMode = MODE_NORMAL;

    //当前的选择模式 : 单选 or 多选
    private int mSelectedMode = SELECTED_MODE_SINGLE;

    //多选模式 : 存储被选中后的数据
    private List<T> mSelectedDatas;

    //多选模式 : 被选中的Item的position
    private int mSelectedPosition;

    //
    private T mSelectedData;

    //需要执行动画的VIewHolder集合
    protected List<SlideViewHolder> mSlideViewHolderList = new ArrayList<>();

    //数据
    protected List<T> mDatas;

    public SingleAndMultiSelectedAdapter(RecyclerView recyclerView) {
        mDatas = new ArrayList<>();
        mSelectedDatas = new ArrayList<T>();
        mRecyclerView = recyclerView;
    }

    /**
     * 使用动画开启编辑模式
     */
    public void openItemAnimation() {
        mMode = MODE_EDIT;//S
        for (SlideViewHolder slideViewHolder : mSlideViewHolderList) {
            slideViewHolder.openItemAnimation();
        }
    }

    /**
     * 使用动画关闭编辑模式
     */
    public void closeItemAnimation() {
        mMode = MODE_NORMAL;
        for (SlideViewHolder slideViewHolder : mSlideViewHolderList) {
            slideViewHolder.closeItemAnimation();
        }
    }

    /**
     * 开/关编辑模式
     */
    public void toggleEditMode() {
        if (mMode == MODE_NORMAL) {
            openItemAnimation();
        } else {
            closeItemAnimation();
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SlideViewHolder slideViewHolder = (SlideViewHolder) holder;
        slideViewHolder.bind();

        slideViewHolder.getCheckBox().setChecked(mDatas.get(position).isSelected());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMode == MODE_NORMAL) return;

                //修改数据源
                mDatas.get(position).setSelected(!mDatas.get(position).isSelected());
                //修改View
                slideViewHolder.getCheckBox().setChecked(mDatas.get(position).isSelected());

                if (mSelectedMode == SELECTED_MODE_SINGLE) {
                    //点击的未选中的Item
                    if (mDatas.get(position).isSelected()) {
                        mSelectedData = mDatas.get(position);
                        //如果当前点击的时候 没有被选中的 就不用更新上一个被选中的ViewHolder
                        if (mSelectedPosition <= 0) {
                            mSelectedPosition = position;
                            return;
                        }
                        SlideViewHolder viewHolder = (SlideViewHolder) mRecyclerView.findViewHolderForAdapterPosition(mSelectedPosition);
                        if (viewHolder != null) {
                            viewHolder.getCheckBox().setChecked(false);
                        } else {
                            notifyItemChanged(mSelectedPosition);
                        }
                        mDatas.get(mSelectedPosition).setSelected(false);
                        mSelectedPosition = position;

                    } else {
                        mSelectedData = null;
                        mSelectedPosition = -1;
                    }

                } else if (mSelectedMode == SELECTED_MODE_MULTI) {
                    if (slideViewHolder.getCheckBox().isChecked()) {
                        mSelectedDatas.add(mDatas.get(position));
                    } else {
                        mSelectedDatas.remove(mDatas.get(position));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    /**
     * 设置数据
     */
    public void setData(List<T> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void addData(List<T> data) {
        this.mDatas.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 清空所有选中的Item
     */
    public void clearAllSelectedItem() {
        for (BaseSelectBean data : mDatas) {
            if (data.isSelected()) data.setSelected(false);
        }
    }


    /**
     * 此方法只在多选模式下有用
     * @return
     */
    public List<T> getSelectedDataList(){
        return mSelectedDatas;
    }

    /**
     * 此方法只在单选模式下有用
     * @return
     */
    public T getSelectedData(){
        return mSelectedData;
    }


    public class SlideViewHolder extends RecyclerView.ViewHolder {

        SlideFrameLayout mSlideRelativeLayout;

        public SlideViewHolder(View itemView) {
            super(itemView);
            mSlideRelativeLayout = (SlideFrameLayout) itemView;
        }

        public void bind() {
            switch (mMode) {
                case MODE_NORMAL:
                    mSlideRelativeLayout.close();
                    break;
                case MODE_EDIT:
                    mSlideRelativeLayout.open();
                    break;
            }
        }

        public void openItemAnimation() {
            mSlideRelativeLayout.openAnimation();
        }

        public void closeItemAnimation() {
            mSlideRelativeLayout.closeAnimation();
        }

        public CheckBox getCheckBox() {
            return mSlideRelativeLayout.getCheckBox();
        }

    }
}
