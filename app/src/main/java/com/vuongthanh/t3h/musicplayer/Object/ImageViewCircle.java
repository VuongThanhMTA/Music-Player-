package com.vuongthanh.t3h.musicplayer.Object;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.vuongthanh.t3h.musicplayer.R;

public class ImageViewCircle extends AppCompatImageView {
    private Paint paint;
    public ImageViewCircle(Context context) {
        super(context);
        init();
    }

    public ImageViewCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        readAttr(attrs);
    }

    public ImageViewCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        readAttr(attrs);
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }
    private void readAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().getResources().obtainAttributes(attrs,R.styleable.CircleImage);
        int color = typedArray.getColor(R.styleable.CircleImage_color, Color.BLACK);
        paint.setColor(color);
        typedArray.recycle();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = View.MeasureSpec.getSize(widthMeasureSpec);
        int h = View.MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(h,h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = getWidth()/2;
        int stroke = getWidth()/3;
        int radius = getWidth()/2 +getWidth()/5;
        paint.setStrokeWidth(20);
        canvas.drawCircle(size,size,radius,paint);
//        Rect rect = new Rect(0,0,getWidth(),getHeight());
//        RectF rectF = new RectF(rect);
//        canvas.drawRoundRect(rectF,radius,radius,paint);
    }

}