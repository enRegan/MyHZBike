package com.example.administrator.starwarstiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yalantis.starwars.TilesFrameLayout;
import com.yalantis.starwars.interfaces.TilesFrameLayoutListener;

public class TilesActivity extends AppCompatActivity implements TilesFrameLayoutListener, View.OnClickListener {
    private TilesFrameLayout mTilesFrameLayout;
    private TextView tv_wtf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiles);

        mTilesFrameLayout = (TilesFrameLayout) findViewById(R.id.tiles_frame_layout);
        tv_wtf = (TextView) findViewById(R.id.tv_wtf);
        mTilesFrameLayout.setOnAnimationFinishedListener(this);
        tv_wtf.setOnClickListener(this);



    }

    @Override
    protected void onResume() {
        super.onResume();
        mTilesFrameLayout.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTilesFrameLayout.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_wtf:
                mTilesFrameLayout.startAnimation();
                break;
            default:

                break;
        }
    }

    @Override
    public void onAnimationFinished() {

    }
}
