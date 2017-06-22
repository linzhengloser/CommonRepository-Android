# 使用方法
``` java
CheckClickListener checkNetworkClickListener = new CheckClickListener(this);
view.setOnClickListener(checkNetworkclickListener);

@Override
public void onAllChekSuccess(View view) {
    //只有当所有检查条件都满足，才会触发该方法
}

```

# 扩展方法
``` java

public class CustomCheck implements Check{

    @Override
    public boolean isChechSuccess(View view) {
        return true or false;
    }

    @Override
    public void checkFailure(View view) {
       //检查条件不满足的情况下给出提示
    }

}

```