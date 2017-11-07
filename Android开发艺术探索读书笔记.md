---
title: Android开发艺术探索读书笔记
date: 2017-10-31 09:29:57
tags: 读书笔记
---

# 前言
本篇博客为《Android开发艺术探索》这本书的读书笔记。方便日后复习使用。

# 第一章 Activity 的生命周期和启动模式
**打开另一个 Activity 的时候，生命周期的调用顺序。**

>假设有两个 Activity，分别叫做 FirstActivity 和 SecondActivity。现在从 FirstActivity 中通过 startActivity() 方法打开 SecondActivity，那么这个时候，FirstActivity 的 onPause() 方法会先调用，然后调用 SecondActivit 的 onCreate()，onStart()，onResume() 方法，最后调用 FirstActivity 的 onStop() 方法。

>从上面的结论中，我们可以得出，如果们需要在 Activity 中执行一些临时的回收操作，最好放在 onStop() 方法中。 

**异常情况下，Activity 生命周期的调用顺序。**

>这里的异常情况分为两种情况，第一种情况是当系统配置发生变化，比如很竖屏切换，这个时候 Activity 会被重新创建，其生命周期方法调用顺序和正常情况下的调用顺序一致，不过会在 onStop() 方法之前调用 onSaveInstanceState() 方法，和在 onStart() 之后调用 onRestoreInstanceState() 方法。

> onSaveInstanceState() 方法和 onRestoreInstanceState() 方法，一个是用来保存数据，一个是用来恢复数据，比如当在竖屏状态下，往 EditText 中的输入一段内容，这个时候切换横屏，系统默认会帮我们把数据保存，然后在恢复。

>第二种情况是当内存不足的时候，系统会通过优先级来回收内存，如果这个时候，某个 Activity 不幸被系统给回收了，这个时候 onSaveInstanceState() 和 onRestoreInstanceState() 方法也会被调用。注：onRestoreInstanceState() 是当被回收的 Activity 重新被启动的时候调用。

**如何让系统在异常情况下不重新创建 Activity**

>系统在 Manifest 中的 Activity 标签中提供了 configChanges 属性，这个属性用来指定当前 Activity 在触发哪些异常情况的时候，不重新创建 Activity。可以指定多个，用 | 隔开，当指定了 configChanges 这个属性之后，异常情况下，onSaveInstanceState() 方法和 onRestoreInstanceState() 方就不会被调用，取而代之被调用的是 onConfigurationChanged() 方法。

**Activity 的 launchMode**

>Android 系统会帮我们维护一个 Activity 栈，后进先出的栈结构，每当用户按下 Back 键的时候，系统就会把栈顶的 Activity 出栈，每当打开一个新的 Activity 的时候，就会把这个新的 Activity 入栈(默认情况下)。我们可以通过修改 Activity 的 launchMode 属性来改变这一默认行为。

>Activity 一共有 4 种 launchMode，第一种为 standard，标准模式，也是 Activity 默认的 launchMode，注意，当我们使用 startActivity 这个方法的时候，其实是将被启动的 Activity 添加到调用这个方法的 Activity 的栈中，所有当我们使用 applicationContext 对象调用 startActivity 方法必须要添加标记 FLAG_ACTIVITY_NEW_TASK，原因是因为 applicationContext 并没有自己的 Activity 栈。

>第二种为 singleTop，栈顶复用模式，假如当前 Activity 栈为 ABCD 这个 4 个 Activity，Activity D 的 launchMode 为 singleTop，这个时候调用 startActivity() 方法打开 D 这个 Activity，此时 Activity 栈还是 ABCD，但是 Activity D 的 onNewIntent() 方法会被调用。

>第三种为 singleTask，栈内复用模式，当一个 launchMode 为 singleTask 的 Activity 被启动的时候，系统会先寻找存在该 Activity 需要的 Activity 栈，如果没有，就创建一个 Activity 栈，并把这个 Activity 入栈。反之如果有该 Activity 需要的栈，会先判断该栈中是否有该 Activity，如果有就把栈中的 Activity 调到栈顶(即把栈中的 Activity 上面的所有 Activity 全部出栈)，然后调用 onNewIntent() 方法，反之如果在栈中不存在，那么就和 standard 模式一样，直接创建 Activity 并入栈。

>第四种为 singleInstance，单实例模式，如果启动的 Activity 是 singleInstance 模式，那么系统会为该 Activity 单独创建一个 Activity 栈，后面在启动该 Activity 都只会打开这个单独栈中的 Acitivty 不会在重新创建。

**Activity 的 TaskAffinity 和 allowTaskReparenting 属性**

>TaskAffinity 属性用来表示当前 Activity 所需要的 Activity 栈，默认为当前应用的包名。如果我们想让 Activity 和 当前的 Activity 不在一个 Activity 栈中，可以设置该属性。当 allowTaskReparenting 等于 true 的时候，情况比较复杂。比如现在有两个应用，分别叫做 A 和 B，A 应用启动 B 应用中名为 B1 的 Activity，而这个 B1 的 allowTaskReparenting 等于 true，这个时候当应用 B 被重新启动的时候，会直接打开 B1 这个 Activity。

>PS:这里解释的可能不是很清楚，可参考 P19 - P20。

**IntentFilter 的匹配规则**

>IntentFilter 包括 action category data，用来匹配隐式 Intent，只有当 action category data 这三个属性同时匹配上才能成稿大概该 Activity，一个 Activity 可以声明多个 IntentFilter，当一个 Activity 声明多个 IntentFilter 的时候，只要匹配其中一个 IntentFilter 就可以成功打开该 Activity。

>action 的匹配规则，一个 IntentFilter 中可以声明多个 action，只需要匹配其中的一个 action 就表示 action 匹配成功。如果 Intent 中没有 action 则直接匹配失败。action 的匹配区分大小，大小写不一致也会导致匹配失败。

>category 的匹配规则，一个 IntentFilter 中可以声明多个 category，Intent 中如果没有 category 则表示匹配成功，但如果有 category，无论有几个，必须要和 IntentFilter 中声明的所有 category 匹配才算匹配成功。

>data 的匹配规则，一个 IntentFilter 中可以什么多个 data，和 action 一样，如果 Intent 中没有 data 则匹配失败，只用匹配 IntentFilter 中声明的任何一个 data 即表示 data 匹配成功。

**data 的声明方式**

>data 的声明方式比较特殊，使用属性 mimeType 来指定 date 的媒体类型，比如设置 mimeType 为 image/* 这个时候就算不指定 uri，uri也有默认值，默认匹配 content 和 file 类型的 uri。具体声明方式参考 P30 - P33

# 第二章 IPC 机制

**IPC 简介**

>IPC 含义为进程间通信，进程间的通信的前提是要有多进程，默认 Android 只有一个进程，可以使用多进程的方式来提高 APP 所可占用的最大内存。在 Android 中，开启多进程的方式就是为 Manifest 中的 Activity 标签添加 process 属性，具体属性值的声明方式可参考 P37。

>如果一个 APP 开启了多进程，那么 Application 类的 onCreate 会被调用多次，且静态成员不在是唯一的。线程同步机制也会失效，SharedPreferences 的可靠性下降。

**Android 中序列化的方法**

>Android 中的序列化的方式有两种，第一种是来自 Java 中的，使用 Serializable 接口来时将对象序列化，具体实现方式可参考 P42 - P43。当使用 Serializable 接口的时候，需要在对象中声明 serialVersionUID 字段，该字段是用来判断类和被序列化后的对象是否匹配的，可以省略。第二种序列化方式是 Android 中提供的，使用 Parcelable 来实现，具体可参考 P45 - P46。如果需要把对象序列化存在内存中，推荐使用 Parcelable，如果是想把对象序列化并保存在磁盘上或是通过网络传输，推荐使用 Serializable。

**Binder 与 AIDL**

>Binder 是 Android 中的一个类，集成至 IBinder 接口，是 Android 中的一种 IPC 方式。而 Binder 一般是 IDE 通过 AIDL 生成出来的。当让我们自己也可以手写。关于 Binder 和 AIDL 的实现方式，具体可参考 P49 - P52。这里总结一下 Binder 几个重要的方法和作用。

>asInterface()，该方法将服务端的 Binder 对象转换成客户端所需要的 AIDL 接口类型的对象，此方法会更具调用的进程判断返回的对象，如果服务端和客户端都在同一个进程中则会直接返回服务端对象色 Stub 对象本身，否者返回 Stub.Proxy 对象。

>asBind()，该方法返回当前的 Binder 对象。

>onTransact()，该方法运行在服务端中的 Binder 线程池中，通过区分 code 参数，来判断调用哪些方法。如果该方法返回 false 的话，那么客户端的请求会失败，可以使用这个特性来显示访问服务端的进程。

>AIDL 只是一个用来告诉 IDE，让 IDE 帮我们实现 Binder 的文件，仅此而已。上面提到的客户端和服务端指定是运行在不同进程中的 Android 组件，一般情况下客户端指的是 Activity 服务端指的是 Service。关于 Binder 的工作机制，可参考 P55。

**使用 Bundle 实现 IPC**

>Android 提供的 Bundle 类本身支持在多进程中传递，缺点是支持类型有限。具体参考 P62。

**使用文件共享实现  IPC**

>文件共享其实就是制定服务端和客户端同时读或写同一个文件来来达到通信的目的，缺点是有并发的问题。具体参考 P64

**使用 Messenger 实现 IPC**

>Messenger 是Android 提供的一个类，可以实现简单的 IPC，其底层使用的是 AIDL。具体参考 P65 - P70。

**使用 AIDL 实现 IPC**

>P71 - P86 详细的介绍了怎么使用 AIDL 实现 IPC，需要注意的是客户端的 onServiceConnected 方法是运行在 UI 线程中的，我们不能在该方法里面执行耗时的操作不然会引起 ANR，另外服务端本身是运行在 Binder 线程池中的，所以不需要我们在服务端中不需要在手动 new Thread 来执行耗时操作。

>如果需要监听服务端的状态，当发生更新的时候通知客户端，这个通知的方法是由服务端调用的所以是运行在 Binder 线程池中的，所以不能直接在数据更新的回调方法中更新 UI。

>可以参考 P89 来实现对服务的重新绑定，即当服务端异常停止后，重新绑定服务。

>如果需要给服务端添加一个权限机制，即不希望所有进程都能绑定我们自己定义的服务，可以参考 P90 中的方式去实现。

**其他 IPC实现方式**

>使用 ContentProvider 可以实现 IPC，因为 ContentProvider 本身就是 Android 4 大组件之一。参考 P91 - P103。

>使用 Socket 可以实现 IPC，需要注意，如果使用 Socket 通信需要加上网络权限。参考 P104 - P110。

**Binder 连接池**

>当业务模块越来越多的时候，每个模块都对应一个单独的 Binder，一个 Binder 又对应一个 Service，这种方法扩展性太差，所以可以使用 Binder 连接池来管理所有 Binder，这样无论多少个 Binder 都只需要一个 Service 即可。参考 P113 - P120 

>具体在什么情况下该用哪种方式实现 IPC，可参考 P121 的表格。

# 第三章 View 的事件体系

**View 的位置参数**

>getLeft()，getRight()，getTop()，getBottom() 这 4 个方法可以获取 View 的位置参数，这 4 个方法获取的值都是当前 View 相对于父布局而言的。且可以通过这几个参数得出 View 的 width = getRight() - getLeft()，View 的 height = getBottom() - getTop()。

>在 Android3.0 之后，View 还提供了 getX()，getY()，getTranslationX()，getTranslationY() 这 4 个方法。且 View 的 getX() = getLeft() + getTranslationX()，View 的 getY() = getTop() + getTranslationY()。参考 P124。

**MotionEvent，TouchSlop，VelocityTracker，GestrueDetector**

>MotionEvent 表示手指接触屏幕后产生的一列事件，当手指点击屏幕松开后事件为 DOWN-> UP，注意这里手指只是点击了屏幕没有移动，如果手指按下后又移动然后在松开，事件则为 DOWN -> MOVE -> UP。

>TouchSlop 提供了一系列常量，帮助我们更好的实现滑动处理，如最小滑动距离。我们在处理滑动的时候，如果移动的值小于这个值，就视为无效的滑动。

>VelocityTracker 用于计算滑动的速度，公式为 速度 = (终点位置 - 起点位置) / 时间段。需要注意的是，如果手指是从左往右滑的，那么获取到的速度是正数，否者反之。具体参考 P126。

>GestrueDetector 用来帮助我们判断用户的点击动作，比如可以判断用户是单击还是长按。具体使用方法参考 P127 - P128.

**View 的滑动**

>Android 中要想实现 View 的滑动一共有 3 种方式，第一种是通过 View 的 scrollBy/scrollTo 方法，这两个方法本质是通过修改 View 的 mScrollX 和 mScrollY 来实现滑动，mScrollX 等于 View 的左边缘于 View 内容的左边缘的距离，mScrollY 也是一样，且像左滑动是正值，想右滑动是负值，想上滑动是正值，向下滑动是负值。注意这里的滑动并非是 View 的位置发生变化，而是 View 的内容滑动。

>Android 中的 Scroller 其实就是通过不断调用 scrollTo() 方法来实现弹性滑动，具体可参考 P128.

>第二种方式是使用 Android 中的动画实现 View 的滑动，在 Android3.0 之前只能使用 View 动画来实现滑动，这种实现有一个问题，就是无法真正改变 View 的位置。具体参考 P132。如果想真正的改变 View 的位置参数，可以使用 Android3.0 之后提供的属性动画。当然现在几乎没有 Android3.0 以下的手机，所以推荐如果想用动画实现 View 的滑动，直接使用属性动画即可。

>第三种方式是通过修改 View 的 LayoutParams 的 margin 来实现 View 的滑动，具体参考 P133。

>如果只是滑动 View 的内容推荐使用 scrollTo/scrollBy 实现，其他的都可用属性动画实现。

**View 的事件分发机制**

>View 的整个事件分发过程由 View 的三个方法来完成，分别是，dispatchTouchEvent() 方法，该方法用来分发事件，onInterceptTouchEvent() 方法，该方法用来判断当前 View 是否需要拦截事件，onTouchEvent() 方法，该方法用来处理点击事件，返回值用来表示是否消耗当前事件。具体参考 P141。

>如果一个 View 设置了 OnTouchListener，且 OnTouchListener 的 onTouch() 方法返回 true，这个时候 View 的 onTouchEvent() 方法不会被调用，反之会在调用玩 OnTouchListener 的 onTouch() 方法之后在调用 onTouchEvent() 方法。这说明了 OnTouchListener 的 onTouch() 方法的优先级高于 View 的 onTouchEvent() 方法。

>事件的传递顺序为 Window -> Activity -> ViewGroup -> View，如果 View，ViewGourp 的 onTouchEvent() 方法都返回 false，前提是事件没有被 ViewGroup 拦截，这个时候 Activity 的 onTouchEvent() 方法会被调用。

>当一个 View 一旦决定拦截，那么这一个事件序列都只能由它来处理，并且它的 onInterceptTouchEvent() 方法将不会在被调用。原因是这个 View 已经决定拦截这个事件了，那么就不用在判断当前 View 是否需要拦截事件了。

>当一个 View 不消耗 DOWN 事件，即 onTouchEvent() 方法返回 false，那么后续的事件都只会交由他的父 View 来处理，即父 View 的 onTouchEvent() 方法会被调用。反之如果这个 View 不消耗除了 DOWN 以外的事件，那么这个事件就会消失，不会调用父 View 的 onTouchEvent() 方法，并且当前 View 可以持续受到后续事件，最终这些消失的事件都会传递给 Activity 来处理。

>ViewGroup 的 onInterceptTouchEvent() 方法默认返回 false，View 没有 onInterceptTouchEvent() 方法，当事件传递给 View 的时候，View 的 onTouchEvent() 方法会被调用。View 的 onTouchEvent() 默认返回 true，除非当前 View 是不可点击的，这一点需要注意。如果我们调用 View 的 setOnClickListener() 方法，在方法内部会为我们调用 setClickable(true) 即设置当前 View 为可点击的，这也说明了如果当前 View 是不可点击的，那么它的 onTouchEvent 是返回 false 的。View 的 enable 属性不会影响 onTouchEvent() 的返回值，只要这个 View 的 clickable 和 longClickable 一个为 true，那么这个 View 的 onTouchEvent 就会返回 false。注意这了指的是 onTouchEvent() 的默认返回值。

>事件是由外而内传递的，即事件总是会先传递个 ViewGroup 然后在传递给 View，如果我们在 View 中想告诉父 View 需要这个事件，可以调用 requestDisallowInterceptTouchEvent() 方法来干预父 View 的事件分发过程，除了 DOWN 事件。

**滑动冲突的解决方案**

>滑动冲突的解决方案推荐在引发冲突最外层的 ViewGroup 的 onInterceptTouchEvent() 方法中解决冲突。具体可以参考 P157 - P159。


#第四章 View 的工作原理

**MeasureSpec**

>MeasureSpec 用来计算 View 在测量过程中，所测量出来的值和模式。具体参考 P178。对于顶级 View 来说(即 DecorView )，它的 MeasureSpec 是由自身 LayoutParams 和屏幕的宽高来决定的。

**View 的 measure**

>View 的 MeasureSpec 是由自身 LayoutParams 和 父 View 的 MeasureSpec 一起来决定的，可参考 P182 的表格。总的来说如果一个 View 明确指定了 width/height 那么它的 MeasureSpec.MODE 就一定是 EXACTLY，且它的 MeasureSpec.SIZE 一定等于声明的具体值。

>如果一个 View 的 width/height 为 match_parent，这个时候如果它的父 View 的 MeasureSpec.MODE 为 EXACTLY，这时它的 MeasureSpec.MODE 为 EXACTLY，MeasureSpec.SIZE 为父 View 剩余可用大小。反之，如果父 View 的 MeasureSpec.MODE 为 AT_MOST，这个时候它的 MeasureSpec.MODE 为 AT_MOST，MeasureSpec.SIZE 为父 View 剩余可用大小。

>如果一个 View 的 width/hegith 为 wrap_content，这个时候无论父 View 的 MeasureSpec.MODE 为 EXACTLY 还是 AT_MOST，它的 MeasureSpec.MODE 都为 AT_MOST 且 MeasureSpec.SIZE 为父 View 剩余可用大小。

>有一个地方需要注意，View 的 mesure 过程和 Activity 的生命周期不是顺序执行的，即我们无法在 Activity 的 onCreate() onStart() onResume() 方法中获取到某个 View 的 width/height。如果需要在 Activity 中获取 View 的 width/height，可以使用如下几个方法，第一种就是在 Activity 的 onWindowFocusChanged() 方法中获取，第二种是使用 View 的 post() 方法来发送一个 Runnable 对象，在 Runnable 对象的 run() 方法中我们可以获取到 View 的 width/height，第三种就是使用 ViewTreeObserver 监听 View 的 Layout，在回调中我们可以获取到 View 的 width/height，第四种方法是手动调用 View 的 measure() 方法来计算出 width/height。注意上面说的获取 width/height 指的是调用 view 的 getMeasureWidth()/getMeasureHeight() 方法，而并非是 getWidth()/getHeight() 方法。具体参考 P190 - P192。

**View 的 layout**

>在 View 的 layout 过程中，会调用 View 的 layout() 方法，并在 layout() 方法中为指定 mLeft，mRight，mTop，mBottom 的值。然后在调用 onLayout() 方法，在 onLayout() 方法中会确定子 View 的位置。注意 ViewGroup 的 onLayout() 方法是一个抽象方法，所以如果继承 ViewGroup 就必须要实现它的 onLayout() 方法。

>需要注意的是，一般情况下 getWidth() 和 getMeasureHeight() 获取的值是一样的，但是在特殊情况下是有区别的，比如重写 layout() 方法并手动修改 right 和 top 参数的值，这个时候 getWidth()/getHeight() 方法获取到的值就和 getMeasureWidth()/getMeasureHeight() 方法获取到的值不一样。

**View 的 draw**

>View 的 draw 按照如下步骤执行，绘制背景 -> 绘制自己 -> 绘制 children -> 追装饰。View 提供了 setWillNotDraw() 方法来设置一个标记位，如果这个标记位为 true 系统会对该 View 的绘制进行优化，默认 ViewGroup 启用了这个标记位了的。所以如果我们需要在自定义 ViewGroup 的 onDrow() 方法中绘制内容，就需要取消这个标记位。

**自定义 View 需要注意的地方**

>在自定义 View 的时候需要注意一些地方，比如需要指出 WRAP_CONTENT，即实现 View 的 onMeasure() 在里面处理 AT_MOST 这个 MODE，在实现 onDraw() 方法的时候，需要计算 View 的 padding 值，如果需要在 View 中使用 Handler 可以直接使用 View 的 post() 方法，如果在 View 中使用了动画，需要在 View 的 onDetachedFromWindow() 方法中取消动画防止内存泄漏。

>在书中 P202 - P209 提供了实现一个基本的自定义 View 的方式。

# 第五，六，七，章  RmoteViews，Drawable，动画

**RemoteViews**

>RemoteViews 在 Android 中主要是用来更新远程 View 的，即更新另一个进程中的 View，如 Android 中通知栏中的通知和桌面小部件。RemoteViews 只能支持部分的 View 具体参考 P320。RemoteViews 内部使用了 Binder 来实现跨进程的更新 UI，

**Drawable**

>Android 中 Drawable 既可以表示图片也可以是之定义绘制的内容，算是 Android 中对图像的一种抽象概念，具体实现有很多，如 shape 等。Drawable 是用的范围很单一，一个是做为 View 的背景，另一个是做为 ImageView 的 src。我们可以之定义自己的 Drawable，需要注意的是，在自定义 Drawable 的时候 getIntrinsicWidth() 和 getIntrinsicHeight() 这两个方法为影响 View 的 wrap_content 属性。具体参考 P264.


**动画**

> Android 中的 View 动画比较单一，只能实现平移，缩放，旋转和透明度变化这几种效果，而帧动画也属于 View 动画，只是变换形式上跟上面几种不一样。我可以通过定义 LayoutAnimation 到实现 ListView 的布局动画，具体参考 P273。而在 Android3.0 中加入了属性动画，属性动画并不是只针对 View 做动画，而是针对所有对象都可以实现动画(由一个值到另一个值的变化)，属性动画中可以自定义插值器(Interpolator)和估值器(Evaluator)。插值器是用来将时间流逝的百分百转换成动画完成的百分百，估值器是用来将动画动画百分百转换成具体的动画属性值。ObjectAnimator 如果要对某个 View 的某个属性执行动画的，那么这个 View 的这个属性必须要提供 set() 和 get() 方法，当然也有其他方法实现，具体参考 P285。


# 第八章 Window 和 WindowManager

**Window内部机制**

>Android 中 Activity，Dialog 和 Toast 的实现都离不开 Window，Window 是一个抽象类，唯一实现类是 PhoneWindow，如果需要实现一个类似悬浮窗的效果，可以使用 WindowManager 来将一个 View 添加到 View 中。具体可以参考 P295 - P296。

>Android 提供了 WindowManager 这个类来供我们访问 Window，如果我们想讲一个 View 添加到 Window 中，那么需要调用 WindowManager 的 addView() 方法，在 addView() 方法中会创建 ViewRootImpl() 对象并调用它的 setView() 方法将我们需要添加的 View 和 ViewRootImpl 对象关联起来。最后通过 WindowSession 的 addToDisplay() 方法将 Window 添加进去，这个添加的过程是一个 IPC 的操作，具体运行在 WindowManagerService 的 Binder 线程池中。

**Activity 的 setContentView() 方法**

>当我们调用 Activity 的 setContentView() 方法的时候，最终会调用 PhoneWindow 的 setContentView() 方法，在 setContentView() 方法内部会先判断 DecorView 对象是否创建，如果没有创建会先创建一个 DecorView 对象，创建完 DecorView 对象后会获取到 ID 为 com.android.internal.R.id.content 的 ViewGroup，并将这个 View 赋值给 mContentParent,最后通过 LayoutInflater 的 inflate() 方法将 mContentParent 添加到 DecorView 中。

>当 setContentView() 方法调用完之后，我们的布局文件已经添加到 DecorView 中了，但是并没有和 Window 关联起来，那是因为关联的代码并不是在 onCreate() 中，而是在 onResume() 方法中，在 Activity 的 onResume() 方法中会使用 WindowManager 的 addView 方法将 DecorView 添加到 Window 中。

>关于 Android 中，在子线程中是不能更新 UI 的，但是在某些情况下，我们是可以在子线程中更新 UI 的，因为 Android 中对线程的监听是在 VIewRootImpl 中的 checkThread() 方法，而每当我们调用更新 UI 的方法，本质上都会去调用 ViewRootImpl 的 requestLayout() 方法，在 ViewRootImpl 的 requestLayout() 方法的内部会调用 checkThread() 这个方法来对更新 UI 的线程进行判断，然后在调用 WindowManagerService 的对应的方法去更新屏幕，那么只要我们在 ViewRootImpl 创建之前更新 UI 就不会触发那个更新 UI 的异常，结合上面的分析，ViewRootImpl 其实是在 onResume() 方法中被创建的，所以我们在 onCreate() 中更新 UI 是有一定几率不会触发异常的，为什么说一定几率呢？因为我们本来是子线程中更新 UI 的，如果我们的子线程在 onResume() 方法之后执行还是会触发子线程更新 UI 的异常。

>当我们启动一个 Activity 的时候，ActivityThread 会调用 performLaunchActivity() 方法，在这个方法这中会创建我们需要打开的 Activity 对象的实例，然后调用其 attach() 方法，在 attach() 方法内部会创建 PhoneWindow 对象的实例，然后再将 DecorView 这个对象通过 WindowManager 的 addView() 方法添加到 Window 中，这个时候 Window 就和我们的界面关联在一起了。具体参考 P306 - P308。


**添加系统级别的 Window**

>可以通过申请系统级别的权限来将一些 View 显示在系统进程中，如某些手机安全软件里面的回收内存的悬浮窗，具体声明方法可参考 P310。

**Toast 的实现原理**

>Toast 的内部实现也和 Window 离不开关系，不过在调用 WindowManagerService 的 addView() 方法之前，Toast 会调用调用 NotificationManagerService 的 enqueueToast() 方法，在 NotificationManagerService 内部会限定一共只能有 50 个 Toast 调用 show()，很显然这个过程也是一个 IPC，在 NotificationManagerService 的 enqueueToast() 方法中会回调 TN 的 show() 方法，而这个 TN 也是一个 Binder 对象，在 TN 的 show() 方法中就会去调用 WindowManagerService 的 addView() 方法。

>在 TN 的 show() 方法中使用了 Handler，所以如果想在子线程中显示 Toast 需要调用 Looper.prepare() 方法。

**关于 IPC 在 Android 中的应用**

>通过上面的分析不难发现，IPC 在 Android 系统中有多么的重要，无论是显示 View 还是 弹出 Toast，在底层都离不开 IPC，因为 Android 系统中有很多的线程，如果我们想显示一个 View 在屏幕上，真正显示的代码其实跟我们的 APP 没有任何关系，我只需要通过 IPC 来通知 WindowManagerService 来做对应的处理。所以说虽然我们平时写代码看不到 IPC 是因为 Android 将这些地方都隐藏起来了。

# 第九章 四大组件工作原理
