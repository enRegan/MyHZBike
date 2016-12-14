package wdx.com.myhzbike.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wdx.com.myhzbike.R;
import wdx.com.myhzbike.utils.LinkifyUtil;

public class ShowUtilsActivity extends AppCompatActivity {
    private static final String SCHEMA ="com.wdx://message_private_url";
    private static final String PARAM_UID ="uid";
    @InjectView(R.id.tv_have_under_line)
    TextView tvHaveUnderLine;
    @InjectView(R.id.tv_no_have_under_line)
    TextView tvNoHaveUnderLine;
    @InjectView(R.id.tv_free_style)
    TextView tvFreeStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_utils);
        ButterKnife.inject(this);

//        LinkifyUtil.isHaveUnderLine = true;
        LinkifyUtil.addLinks(tvHaveUnderLine,LinkifyUtil.ALL, true);
        tvHaveUnderLine.setMovementMethod(LinkMovementMethod.getInstance());

//        LinkifyUtil.isHaveUnderLine = false;
        LinkifyUtil.addLinks(tvNoHaveUnderLine,LinkifyUtil.ALL, false);
        tvNoHaveUnderLine.setMovementMethod(LinkMovementMethod.getInstance());

        Pattern p = Pattern.compile("客服\\S*");
        String mentionsScheme = String.format("%s/?%s=",SCHEMA, PARAM_UID);
//        Pattern p = Pattern.compile("mm://\\S*");
//        getResources().getString(R.string.free_style_scheme);
        LinkifyUtil.addLinks(tvFreeStyle, p, mentionsScheme);
        tvFreeStyle.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
