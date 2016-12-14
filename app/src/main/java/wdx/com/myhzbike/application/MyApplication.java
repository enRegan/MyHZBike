package wdx.com.myhzbike.application;

import android.app.Application;
import android.text.BoringLayout;
import android.view.Window;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by wdx on 2015/11/18.
 */
public class MyApplication extends Application {
    public static int width = 0;
    public static int height = 0;
    public static float density = 0;
    public static int densityDpi = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);

    }

}
