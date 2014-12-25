package com.mars.framework.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by mars on 14/12/8.
 */
public class ImageUtil {

    /**
     * 加载带loading图的图片
     * @param imageUrl
     * @param imageView
     * @param loadingResId 默认图id
     */
    public static void displayImageView(String imageUrl,ImageView imageView,int loadingResId){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadingResId)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }

    /**
     * 加载带loading,空数据的图的图片
     * @param imageUrl
     * @param imageView
     * @param loadingResId
     * @param emptyResId
     */
    public static void displayImageView(String imageUrl,ImageView imageView,int loadingResId,int emptyResId){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadingResId)
                .showImageForEmptyUri(emptyResId)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }


    /**
     * 加载带各种状态图的图片
     * @param imageUrl
     * @param imageView
     * @param loadingResId
     * @param emptyResId
     * @param failResId
     */
    public static void displayImageView(String imageUrl,ImageView imageView,int loadingResId,int emptyResId,int failResId){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadingResId)
                .showImageForEmptyUri(emptyResId)
                .showImageOnFail(failResId)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }


    /**
     * 加载显示不带默认图的图片
     * @param imageUrl
     * @param imageView
     */
    public static void displayImageView(String imageUrl,ImageView imageView){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }

    /**
     * 加载带回调接口的图片
     * @param imageUrl
     * @param imageView
     * @param options
     * @param listener
     */
    public static void displayImageView(String imageUrl,ImageView imageView,DisplayImageOptions options,ImageLoadingListener listener){
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options,listener);
    }

}
