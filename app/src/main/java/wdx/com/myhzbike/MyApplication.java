package wdx.com.myhzbike;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by wdx on 2015/11/18.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }

}
