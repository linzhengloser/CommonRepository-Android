# 简介

在 Android 中实现多语言的切换需要在 Activity 和 Application 中重写 attachBaseContent() 方法具体如下：

``` kotlin

//Application 中
class MyApplication : Application() {

    override fun attachBaseContext(newBase: Context) {
        val locale = Locale.getDefault()
        val context = ContextWrapperUtils.wrap(newBase, locale)
        super.attachBaseContext(context)
    }

}

//Activity 中
class MainActivity : AppCompatActivity(){

    override fun attachBaseContext(newBase: Context) {
        val locale = Locale.getDefault()
        val context = ContextWrapperUtils.wrap(newBase, locale)
        super.attachBaseContext(context)
    }

}

```

如上代码，可以将里面的 Local 替换成自己的逻辑，在 ContextWrapperUtils 这个类中，使用了 Context 提供的 createConfigurationContext() 方法，此方法会修改 ContextImpl 对象的 mOuterContext 字段，会导致一些奇怪的问题，所以在 ContextWrapperUtils 的 wrap() 方法中使用反射将 newBase 中的 mOuterContext 设置给使用 Context.createConfigurationContext() 方法创建的 Context 对象。具体参考 ContextWrapperUtils 的 wrap() 方法。

因为 ContextImpl 这个类是 hide 的，故必须要使用反射才能设置。

**可能还有更好的方法。**

## 相关链接
[https://blog.csdn.net/Luoshengyang/article/details/8201936](https://blog.csdn.net/Luoshengyang/article/details/8201936)