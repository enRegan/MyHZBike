package wdx.com.myhzbike.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import wdx.com.myhzbike.R;
import wdx.com.myhzbike.application.MyApplication;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tv_bd)
    TextView tvBd;
    @InjectView(R.id.tv_gd)
    TextView tvGd;
    @InjectView(R.id.tv_show_view)
    TextView tvShowView;
    @InjectView(R.id.tv_show_utils)
    TextView tvShowUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        MyApplication.width = metric.widthPixels;     // 屏幕宽度（像素）
        MyApplication.height = metric.heightPixels;   // 屏幕高度（像素）
        MyApplication.density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        MyApplication.densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
    }

    @OnClick({R.id.tv_bd, R.id.tv_gd, R.id.tv_show_view, R.id.tv_show_utils})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bd:
                startActivity(new Intent(MainActivity.this,BDMainActivity.class));
                break;
            case R.id.tv_gd:
                startActivity(new Intent(MainActivity.this,GDMainActivity.class));
                break;
            case R.id.tv_show_view:
                startActivity(new Intent(MainActivity.this,ShowViewActivity.class));
                break;
            case R.id.tv_show_utils:
                startActivity(new Intent(MainActivity.this,ShowUtilsActivity.class));
                break;
        }
    }
}
