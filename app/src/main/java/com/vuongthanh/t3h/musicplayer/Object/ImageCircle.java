package com.vuongthanh.t3h.musicplayer.Object;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.vuongthanh.t3h.musicplayer.R;

public class ImageCircle extends AppCompatImageView {
    private Paint paint;
    public ImageCircle(Context context) {
        super(context);
        init();
    }

    public ImageCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        readAttr(attrs);
    }

    public ImageCircle(Context context, AttributeSet attrs, int defStyleAttr) {
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
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(h,h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = getWidth()/2;
        int stroke = getWidth()/3;
        int radius = getWidth()/2+getWidth()/7;
        paint.setStrokeWidth(stroke);
//        Rect rect = new Rect(0,0,getWidth(),getHeight());
//        canvas.drawRect(rect,paint);
        canvas.drawCircle(size,size,radius,paint);

    }

}