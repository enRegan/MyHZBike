package wdx.com.myhzbike.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wdx on 2016/5/26.
 */
public class ToastManager {
    /**
     *
     * @param context
     * @param text
     */
    public static void toast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
