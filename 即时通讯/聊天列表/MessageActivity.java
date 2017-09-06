package com.lz.rxjava2demo.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lz.rxjava2demo.R;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/08/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MessageActivity extends AppCompatActivity {

    MultiTypeAdapter mAdapter;

    Items mItems;

    RecyclerView rv_message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        rv_message = (RecyclerView) findViewById(R.id.rv_message);

        rv_message.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MultiTypeAdapter();
        mItems = new Items();

        mAdapter.register(Message.class)
                .to(new TextMessageItemViewBinder(), new ImageMessageItemViewBinder())
                .withClassLinker(message -> {
                    if (message.getMessageType() == Message.Type.TXT)
                        return TextMessageItemViewBinder.class;
                    else if (message.getMessageType() == Message.Type.IMAGE) {
                        return ImageMessageItemViewBinder.class;
                    }
                    return null;
                });

        mItems.add(new Message("", Message.Type.TXT, Message.Direct.SEND));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.RECEIVE));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.RECEIVE));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.SEND));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.SEND));
        mItems.add(new Message("", Message.Type.IMAGE, Message.Direct.RECEIVE));
        mItems.add(new Message("", Message.Type.IMAGE, Message.Direct.RECEIVE));
        mItems.add(new Message("", Message.Type.IMAGE, Message.Direct.SEND));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.SEND));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.RECEIVE));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.RECEIVE));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.SEND));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.SEND));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.RECEIVE));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.RECEIVE));
        mItems.add(new Message("", Message.Type.IMAGE, Message.Direct.SEND));
        mItems.add(new Message("", Message.Type.IMAGE, Message.Direct.SEND));
        mItems.add(new Message("", Message.Type.IMAGE, Message.Direct.RECEIVE));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.RECEIVE));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.SEND));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.SEND));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.RECEIVE));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.RECEIVE));
        mItems.add(new Message("", Message.Type.TXT, Message.Direct.SEND));

        mAdapter.setItems(mItems);
        rv_message.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }
}
