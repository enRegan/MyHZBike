package wdx.com.myhzbike.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import butterknife.OnClick;
import wdx.com.myhzbike.R;
import wdx.com.myhzbike.utils.MyLogUtil;

/**
 * Created by Administrator on 2016/12/19.
 */
public class OclockView extends View{
    Oclock oclock;

    public OclockView(Context context) {
        super(context);
    }
    public OclockView(Context context, Oclock oclock) {
        super(context);
        this.oclock = oclock;
    }
    public OclockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint ciclePaint = new Paint();
        ciclePaint.setAntiAlias(true);
        ciclePaint.setStyle(Paint.Style.STROKE);
        ciclePaint.setStrokeWidth(10);
        ciclePaint.setColor(getResources().getColor(R.color.oclock_cicle));
        float centerX = 500;
        float centerY = 500;
        float inRadius = 50;
        float outRadius = 80;

        canvas.drawCircle(centerX, centerY, inRadius - 10, ciclePaint);
        float lenght = 30;
        float startX = centerX;
        float stopX = centerX;
        float startY = centerY - inRadius;
        float stopY = centerY - outRadius;
        float degree = 0;
        int n = 3;
        for(int i = 0; i < 13; i++){
            n = i == 0 | i < 4 | i > 10 ? 3 : -3;
            degree = (float)Math.sin(Math.toRadians(i * 30));
            startX = startX + (2 * n * degree);
            stopX = stopX + (6 * n * degree);
            startY = startY + (2 * n * degree);
            stopY = stopY + (6 * n * degree);
            canvas.drawLine(startX, startY, stopX, stopY, ciclePaint);
            MyLogUtil.LogD("wdx", " startX : " + startX);
            MyLogUtil.LogD("wdx", " stopX : " + stopX);
            MyLogUtil.LogD("wdx", " startY : " + startY);
            MyLogUtil.LogD("wdx", " stopY : " + stopY);
            MyLogUtil.LogD("wdx", " n : " + n);
//            canvas.rotate(1);
        }
        canvas.drawCircle(600, 600, 100, ciclePaint);

    }

}
