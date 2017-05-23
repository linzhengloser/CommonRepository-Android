# 使用方法
``` java
//加载一般图片
ImageLoader.getInstance().loadImage(imageView,imageUrl);
//加载圆形图片,使用 CirlceImageView显示(https://github.com/hdodenhof/CircleImageView)
ImageLoader.getInstance().loadImage(circleImageView,imageUrl,ImageLoaderOptions.createLoad2CircleImageViewOptions());
//加载本地路径的图片
ImageLoader.getInstance().loadImage(circleImageView,imageUrl,ImageLoaderOptions.createLoadNativeImageOptions());
```