# 使用方法
``` java
//加载一般图片
ImageLoader.getInstance().loadImage(imageView,imageUrl);
//加载圆形图片,使用 CirlceImageView显示(https://github.com/hdodenhof/CircleImageView)
ImageLoader.getInstance().loadImage(circleImageView,imageUrl,ImageLoaderOptions.createLoad2CircleImageViewOptions());
//加载本地路径的图片
ImageLoader.getInstance().loadImage(circleImageView,imageUrl,ImageLoaderOptions.createLoadNativeImageOptions());
```

# 注意
Glide4 中已经提供了类似的封装方式，所以可以直接使用 Glide 的封装。具体查看 Glide4 的文档。