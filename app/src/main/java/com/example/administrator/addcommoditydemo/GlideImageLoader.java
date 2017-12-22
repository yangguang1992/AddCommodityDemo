package com.example.administrator.addcommoditydemo;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;

/**
 * 图片加载工具
 * Created by xingyun on 2016/10/27.
 */
public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(App.app)//
                .load(path)
                .placeholder(R.mipmap.add)//
                .error(R.mipmap.add)//
                .override(width,height)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)  //这段代码会导致多选图片界面重复刷新 注释
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
