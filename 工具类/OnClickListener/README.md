# 使用方法
``` java
CheckNetworkClickListener checkNetworkClickListener = new CheckNetworkClickListener(this);
view.setOnClickListener(checkNetworkclickListener);

@Override
public void networkAvailable(View view) {
    //有网才会触发
}

```