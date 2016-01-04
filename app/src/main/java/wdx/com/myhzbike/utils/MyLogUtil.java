package wdx.com.myhzbike.utils;

import android.util.Log;

/**
 * 
 * 基础功能类
 * @author WDX
 */
public class MyLogUtil {
	private static boolean bDebug = true;
	private static int num = 1500;
	
	public static void LogV(String msg){
		if(bDebug && msg != null){
			while(msg.length() > num){
		    	Log.v("wdx", msg.substring(0, num));
		    	msg = msg.substring(num);
		    }
			Log.v("wdx", msg);
		}
    }
	
	public static void LogD(String msg){
		if(bDebug && msg != null){
			while(msg.length() > num){
		    	Log.d("wdx", msg.substring(0, num));
		    	msg = msg.substring(num);
		    }
			Log.d("wdx", msg);
		}
    }
	
	public static void LogI(String msg){
		if(bDebug && msg != null){
			while(msg.length() > num){
		    	Log.i("wdx", msg.substring(0, num));
		    	msg = msg.substring(num);
		    }
			Log.i("wdx", msg);
		}
    }
	
	public static void LogW(String msg){
		if(bDebug && msg != null){
			while(msg.length() > num){
		    	Log.w("wdx", msg.substring(0, num));
		    	msg = msg.substring(num);
		    }
			Log.w("wdx", msg);
		}
	}
	
	public static void LogE(String msg){
		if(msg != null){
			while(msg.length() > num){
		    	Log.e("wdx", msg.substring(0, num));
		    	msg = msg.substring(num);
		    }
			Log.e("wdx", msg);
		}
	}
	
	public static void LogV(String key, String msg){
		if(key != null && "heart".equalsIgnoreCase(key)) return;
		
		if(key != null && key.length() > 0){
			if(bDebug && msg != null){
				while(msg.length() > num){
			    	Log.v(key, msg.substring(0, num));
			    	msg = msg.substring(num);
			    }
				Log.v(key, msg);
			}
		}
    }
	
	public static void LogD(String key, String msg){
		if(key != null && key.length() > 0){
			if(bDebug && msg != null){
				while(msg.length() > num){
			    	Log.d(key, msg.substring(0, num));
			    	msg = msg.substring(num);
			    }
				Log.d(key, msg);
			}
		}
    }
	
}
