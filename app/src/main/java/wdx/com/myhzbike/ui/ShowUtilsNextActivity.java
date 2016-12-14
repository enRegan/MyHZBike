package wdx.com.myhzbike.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wdx.com.myhzbike.R;

public class ShowUtilsNextActivity extends AppCompatActivity {
    private static final String SCHEMA ="com.wdx://message_private_url";
    private static final String PARAM_UID ="uid";
    private static final Uri PROFILE_URI = Uri.parse(SCHEMA);

    @InjectView(R.id.tv_show_text)
    TextView tvShowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_utils_next);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        Uri uri = intent.getData();
        String show = uri.toString();
        if (uri != null && PROFILE_URI.getScheme().equals(uri.getScheme())) {
            show = uri.getQueryParameter(PARAM_UID);
        }
//        if(show.contains(getResources().getString(R.string.free_style_scheme))){
//            show = show.substring(getResources().getString(R.string.free_style_scheme).length());
//        }
        tvShowText.setText(show);
    }
}
