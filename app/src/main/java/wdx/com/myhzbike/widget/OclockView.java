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
        ciclePaint.setStyle(Paint.Style.STROKE);
        ciclePaint.setStrokeWidth(10);
        ciclePaint.setColor(getResources().getColor(android.R.color.holo_red_dark));

        canvas.drawCircle(300, 300, 100, ciclePaint);
        float startX = 500;
        float startY = 530;
        float stopX = 500;
        float stopY = 500;
        float degree = 0;
        int n = 3;
        for(int i = 0; i < 13; i++){
            n = i == 0 | i < 4 | i > 10 ? 3 : -3;
            MyLogUtil.LogD("wdx", " startX : " + startX);
            MyLogUtil.LogD("wdx", " stopX : " + stopX);
            MyLogUtil.LogD("wdx", " startY : " + startY);
            MyLogUtil.LogD("wdx", " stopY : " + stopY);
            MyLogUtil.LogD("wdx", " n : " + n);
            degree = (float)Math.sin(Math.toRadians(i * 30));
            startX = startX + (2 * n * degree);
            stopX = stopX + (6 * n * degree);
            startY = startY + (2 * n * degree);
            stopY = stopY + (6 * n * degree);
            canvas.drawLine(startX, startY, stopX, stopY, ciclePaint);
//            canvas.rotate(1);
        }
        canvas.drawCircle(600, 600, 100, ciclePaint);

    }

}
