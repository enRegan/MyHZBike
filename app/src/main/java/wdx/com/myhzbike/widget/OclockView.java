package wdx.com.myhzbike.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import wdx.com.myhzbike.utils.MyLogUtil;

/**
 * Created by Administrator on 2016/12/19.
 */
public class OclockView extends View{
    Oclock oclock;
    int DEFAULT_HEIGHT = 800;
    int widthS = 0;
    int heightS = 0;

    float centerX = 500;
    float centerY = 500;
    float inRadius = 250;
    float lenght = 50;
    float outRadius = inRadius + lenght;


    public OclockView(Context context) {
        this(context, new Oclock());
    }

    public OclockView(Context context, Oclock oclock) {
        super(context);
        this.oclock = oclock;
        init(oclock);
    }

    public OclockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OclockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        widthS = dm.widthPixels;
        heightS = dm.heightPixels;
        MyLogUtil.LogD("widthS : " + widthS);
        MyLogUtil.LogD("heightS : " + heightS);
        DEFAULT_HEIGHT = heightS / 2;
        centerX = widthS / 2;
        centerY = DEFAULT_HEIGHT / 2;
        inRadius = widthS / 4;
        lenght = widthS / 20;
        outRadius = inRadius + lenght;
    }

    private void init(Oclock oclock){
        if(oclock.getWidth() > 0 && oclock.getHeight() > 0){
            widthS = oclock.getWidth();
            heightS = oclock.getHeight();
        }else{
            init();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.rgb(1,129,113));

        Paint ciclePaint = new Paint();
        ciclePaint.setAntiAlias(true);
        ciclePaint.setStyle(Paint.Style.STROKE);
        ciclePaint.setStrokeWidth(3);

//        ciclePaint.setColor(getResources().getColor(R.color.oclock_cicle));
        ciclePaint.setColor(Color.rgb(35, 147, 133));


        canvas.drawCircle(centerX, centerY, inRadius - 50, ciclePaint);

        ciclePaint.setStrokeWidth(5);
        float startX = centerX;
        float stopX = centerX;
        float startY = centerY - inRadius;
        float stopY = centerY - outRadius;
        float degreeX = 0;
        float degreeY = 0;
        int count = 180;
        for(int i = 0; i < count; i++){
            degreeX = (float)Math.sin(Math.toRadians(i * (360 / count)));
            degreeY = (float)Math.cos(Math.toRadians(i * (360 / count)));
            startX = startX + (inRadius * degreeX);
            stopX = stopX + (outRadius * degreeX);
            startY = startY + (inRadius - inRadius * degreeY);
            stopY = stopY + (outRadius - outRadius * degreeY);
            canvas.drawLine(startX, startY, stopX, stopY, ciclePaint);
            MyLogUtil.LogD("wdx", " startX : " + startX);
            MyLogUtil.LogD("wdx", " stopX : " + stopX);
            MyLogUtil.LogD("wdx", " startY : " + startY);
            MyLogUtil.LogD("wdx", " stopY : " + stopY);
            MyLogUtil.LogD("wdx", " degreeX : " + degreeX);
            MyLogUtil.LogD("wdx", " degreeY : " + degreeY);
            MyLogUtil.LogD("wdx", " \n");

            startX = centerX;
            stopX = centerX;
            startY = centerY - inRadius;
            stopY = centerY - outRadius;
//            degreeX = 0;
//            degreeY = 0;
//            canvas.rotate(1);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize, DEFAULT_HEIGHT);
        }
        else if(widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
        else if(heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize, DEFAULT_HEIGHT);
        }
    }
}
