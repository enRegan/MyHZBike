package wdx.com.myhzbike.widght;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import wdx.com.myhzbike.application.MyApplication;
import wdx.com.myhzbike.utils.MyLogUtil;

/**
 * Created by wdx on 2016/12/13.
 */
public class MyClockView extends View {
    Paint paintCircle;
    Paint paintCircle2;

    public MyClockView(Context context) {
        super(context);
        initView();
    }
    public MyClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        paintCircle = new Paint();
        paintCircle.setColor(getResources().getColor(android.R.color.holo_blue_light));
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setStrokeWidth((MyApplication.width / 100) * 1.0f);
        MyLogUtil.LogD("circle" + paintCircle.getStrokeWidth() + "        " + (MyApplication.width / 10) * 1.0f);

//        paintCircle.setStrokeWidth(30.0f);
//        paintCircle2 = new Paint();
//        paintCircle2.setColor(getResources().getColor(android.R.color.white));
//        paintCircle2.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(MyApplication.width / 2, MyApplication.height / 4, MyApplication.width / 4, paintCircle);
//        canvas.drawCircle(MyApplication.width / 2, MyApplication.height / 4, MyApplication.width / 5, paintCircle2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
