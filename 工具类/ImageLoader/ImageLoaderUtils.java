package com.risenb.honourfamily.utils.imageloader;

/**
 * 图片加载工具类
 * Created by linzheng on 2016/3/28.
 */
public class ImageLoaderUtils {

    public static final class SingletonHolder{
        public static final ImageLoader instance = new ImageLoaderGlide();
    }

    public static ImageLoader getInstance(){
        return SingletonHolder.instance;
    }

}
