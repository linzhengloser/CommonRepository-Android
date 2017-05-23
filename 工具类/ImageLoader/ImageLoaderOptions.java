package com.risenb.honourfamily.utils.imageloader;


import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.risenb.honourfamily.R;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/05/23
 *     desc   : 图片加载参数
 *     version: 1.0
 * </pre>
 */
public class ImageLoaderOptions {

    public static ImageLoaderOptions sDefaultImageLoaderOptions;

    static{
        sDefaultImageLoaderOptions = createDefaultImageLoaderOptions();
    }


    /**
     * 图片加载中要显示的图片
     */
    private int loadingResId;

    /**
     * 图片加载出错要显示的图片
     */
    private int loadErrorResId;

    /**
     * 显示缩略图的比例
     */
    private int loadThumbnail;

    /**
     * 是否需要设置ImageView 为 fitXY
     */
    private boolean isFitXY;

    /**
     * 设置图片显示宽度 width 和 height 需要同时设置 否者无效
     */
    private int width;

    /**
     * 设置图片显示的高度
     */
    private int height;

    /**
     * Glide中的 Transformation
     */
    private Transformation bitmapTransformation;

    private SimpleTarget simpleTarget;

    /**
     * 是否是加载到CircleImageView
     */
    private boolean isLoad2CircleImageView = false;

    /**
     * url 是否是一个网络路径
     * 在加载本地图片的时候 需要把这个值设置成false
     */
    private boolean isNetworkUrl = true;

    public boolean isNetworkUrl() {
        return isNetworkUrl;
    }

    public ImageLoaderOptions setNetworkUrl(boolean networkUrl) {
        isNetworkUrl = networkUrl;
        return this;
    }

    public int getLoadingResId() {
        return loadingResId;
    }

    public ImageLoaderOptions setLoadingResId(int loadingResId) {
        this.loadingResId = loadingResId;
        return this;
    }

    public int getLoadErrorResId() {
        return loadErrorResId;
    }

    public ImageLoaderOptions setLoadErrorResId(int loadErrorResId) {
        this.loadErrorResId = loadErrorResId;
        return this;
    }

    public int getLoadThumbnail() {
        return loadThumbnail;
    }

    public ImageLoaderOptions setLoadThumbnail(int loadThumbnail) {
        this.loadThumbnail = loadThumbnail;
        return this;
    }

    public boolean isFitXY() {
        return isFitXY;
    }

    public ImageLoaderOptions setFitXY(boolean fitXY) {
        isFitXY = fitXY;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public ImageLoaderOptions setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public ImageLoaderOptions setHeight(int height) {
        this.height = height;
        return this;
    }

    public Transformation getBitmapTransformation() {
        return bitmapTransformation;
    }

    public ImageLoaderOptions setBitmapTransformation(Transformation bitmapTransformation) {
        this.bitmapTransformation = bitmapTransformation;
        return this;
    }

    public boolean isLoad2CircleImageView() {
        return isLoad2CircleImageView;
    }

    public ImageLoaderOptions setLoad2CircleImageView(boolean load2CircleImageView) {
        isLoad2CircleImageView = load2CircleImageView;
        return this;
    }

    public SimpleTarget getSimpleTarget() {
        return simpleTarget;
    }

    public ImageLoaderOptions setSimpleTarget(SimpleTarget simpleTarget) {
        this.simpleTarget = simpleTarget;
        return this;
    }

    /**
     * 创建默认的 ImageLoaderOptions 对象
     * @return
     */
    public static ImageLoaderOptions createDefaultImageLoaderOptions(){
        return new ImageLoaderOptions()
                .setLoadErrorResId(R.mipmap.ic_launcher)
                .setLoadingResId(R.mipmap.ic_launcher);
    }

    /**
     * 创建加载本地图片的 ImageLoaderOptions
     * @return
     */
    public static ImageLoaderOptions createLoadNativeImageOptions(){
        return createDefaultImageLoaderOptions().setNetworkUrl(false);
    }

    /**
     * 创建加载到 CircleImageView 的 ImageLoaderOptions
     * @return
     */
    public static ImageLoaderOptions createLoad2CircleImageViewOptions(){
        return createDefaultImageLoaderOptions().setLoad2CircleImageView(true);
    }

    /**
     * 创建 带有 Transformation 的 imageLoaderOptions
     * @param transformation
     * @return
     */
    public static ImageLoaderOptions createGlideTransformationOptions(Transformation transformation){
        return createDefaultImageLoaderOptions().setBitmapTransformation(transformation);
    }

    /**
     * 创建 带有 SimpleTarget 的 ImageLoaderOptions
     * @param simpleTarget
     * @return
     */
    public static ImageLoaderOptions createGlideSimpleTargetOptions(SimpleTarget simpleTarget){
        return createDefaultImageLoaderOptions().setSimpleTarget(simpleTarget);
    }


}
