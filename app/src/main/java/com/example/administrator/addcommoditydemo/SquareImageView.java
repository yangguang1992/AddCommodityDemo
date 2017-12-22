package com.example.administrator.addcommoditydemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * 正方形ImageView
 * Created by xingyun on 2016/11/2.
 */
public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        super(context);
    }
    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = Math.round(width*1);
        setMeasuredDimension(width,height);
    }
}
