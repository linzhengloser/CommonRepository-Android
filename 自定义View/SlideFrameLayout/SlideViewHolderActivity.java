package com.lz.eventbus3demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lz.eventbus3demo.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

public class SlideViewHolderActivity extends AppCompatActivity {

    RecyclerView rv_list;

    SingleAndMultiSelectedAdapter mAdapter;

    List<MessageBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_view_holder);

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MessageRecyclerViewAdapter(rv_list);
        rv_list.setAdapter(mAdapter);

        mDatas = new ArrayList<>();
        MessageBean messageBean;
        for (int i = 0; i < 100; i++) {
            messageBean = new MessageBean();
            messageBean.setMessageTitle("消息标题"+i);
            messageBean.setMessageContent("消息内容"+i);
            mDatas.add(messageBean);
        }

        mAdapter.setData(mDatas);

    }

    public void editmode(View view) {
        mAdapter.toggleEditMode();
    }
}
