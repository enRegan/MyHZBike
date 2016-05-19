package wdx.com.myhzbike.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import wdx.com.myhzbike.R;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tv_bd)
    TextView tvBd;
    @InjectView(R.id.tv_gd)
    TextView tvGd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.tv_bd, R.id.tv_gd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bd:
                startActivity(new Intent(MainActivity.this,BDMainActivity.class));
                break;
            case R.id.tv_gd:
                startActivity(new Intent(MainActivity.this,GDMainActivity.class));
                break;
        }
    }
}
