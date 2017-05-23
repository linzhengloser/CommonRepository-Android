package com.risenb.honourfamily.utils.imageloader;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/05/23
 *     desc   : 图片加载类
 *     version: 1.0
 * </pre>
 */
public class ImageLoaderUtils {

    public static final class SingletonHolder{
        public static final ImageLoader instance = new ImageLoaderGlide();
    }

    public static ImageLoader getInstance(){
        return SingletonHolder.instance;
    }

}
