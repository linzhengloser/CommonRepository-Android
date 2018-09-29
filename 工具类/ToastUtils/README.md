# 简介

对 Toast 的显示进行封装，需要注意的是，此封装在做国际化适配的时候，需要重写 Application 的 attachBaseContent() 方法才会有效，并且还需要重启 APP。

默认情况下 Toast 是不能再子线程中调用的，具体原因参考 Toast 源码中对 Looper 的判断，所以在这里在 ToastUtils 中我们初始化了一个 MainLooper 的 Handler，这样就可以在子线程中使用了。

有点需要注意的，在 ToastUtils 中的 LibraryApplication.getInstance() 需要替换成可用的 Application 的对象。

# 使用方法

``` kotlin

ToastUtils.showToast("Toast Messge")
ToastUtils.showToast(R.string.hint)

```
