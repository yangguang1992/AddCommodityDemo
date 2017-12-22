package com.example.administrator.addcommoditydemo;

import android.app.Application;
import android.content.Context;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

/**
 * Created by ${李小磊} on 2017/12/22/022.
 */

public class App extends Application {
    public static App app = null;
    private static Context mContext;
    private static App mAppContext;
    public static Context getContext() {
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mContext = getApplicationContext();
        mAppContext = this;
        mContext = getApplicationContext();
        setImagePicker();
    }
    /**
     * 初始化多选图片框架
     */
    private void setImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        //        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        //        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        //        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        //        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }
    public synchronized static App getInstance() {
        return mAppContext;
    }
}
