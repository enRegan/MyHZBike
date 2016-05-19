package wdx.com.myhzbike.utils;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import wdx.com.myhzbike.iface.HttpCallBack;

/**
 * Created by Administrator on 2016/1/4.
 */
public class MyHttpConnectionTask extends AsyncTask<String, Integer, String> {
    private HttpCallBack httpCallBack;
    private volatile boolean bRunning = true;
    private String type = "";

    public MyHttpConnectionTask(HttpCallBack httpCallBack, String type) {
        super();
        this.httpCallBack = httpCallBack;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            if (!bRunning) return;
            if (result != null && !"".equalsIgnoreCase(result)) {
                if (httpCallBack != null) {
                    httpCallBack.callBackDate(result);
                }
            } else {
                MyLogUtil.LogD("获取数据失败,请重试!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        bRunning = false;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }


    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            HttpURLConnection httpConn = null;
            if("GET".equals(type)){
                String currentUrl = params[0] + "?" + params[1];
                URL url = new URL(currentUrl);
                httpConn = (HttpURLConnection) url.openConnection();
                httpConn.setConnectTimeout(5*1000);
                httpConn.setRequestMethod("GET");
            }else{
                String content;
                byte[] requestStringBytes = new byte[0];
                if (params.length >= 2) {
                    content = params[1];
                    MyLogUtil.LogV("wdx", "content : " + content);
                    requestStringBytes = content.getBytes(HTTP.UTF_8);
                }
                String byyte = "";
                for(int i = 0; i < requestStringBytes.length; i++){
                    byyte += requestStringBytes[i];
                }
                MyLogUtil.LogV("wdx", "byyte : " + byyte);


                String currentUrl = params[0];
                URL url = new URL(currentUrl);
                httpConn = (HttpURLConnection) url.openConnection();
                httpConn.setDoOutput(true);
                httpConn.setDoInput(true);
                httpConn.setUseCaches(false);
                httpConn.setRequestProperty("Charsert", "UTF-8");
                httpConn.setRequestMethod("POST");
                httpConn.setConnectTimeout(5 * 1000);
                httpConn.setReadTimeout(10 * 1000);
                httpConn.setRequestProperty(HTTP.CONTENT_LEN, String.valueOf(requestStringBytes.length));
                httpConn.setRequestProperty(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");

                OutputStream outputStream = httpConn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputStream);
                int len = 0;
                int left = requestStringBytes.length;
                while (bRunning && left > 0) {
                    if (left < 1024) {
                        dos.write(requestStringBytes, len, left);
                        len += left;
                    } else {
                        dos.write(requestStringBytes, len, 1024);
                        len += 1024;
                    }
                    left = requestStringBytes.length - len;
                }

                dos.flush();
                dos.close();

            }


            if (!bRunning) return "";

            int responseCode = httpConn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuffer sb = new StringBuffer();
                String readLine;

                InputStream inputstream = httpConn.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputstream, HTTP.UTF_8);
                BufferedReader responseReader = new BufferedReader(reader);

                while (bRunning && (readLine = responseReader.readLine()) != null) {
//                    sb.append(readLine).append("\n");
                    sb.append(readLine);
                }
                responseReader.close();
                result = sb.toString();


//                MyLogUtil.LogE("result=" + result);

                if (result == null || result.length() == 0) {
                    result = "";
                }

            } else if (responseCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
                MyLogUtil.LogD("wdx", "网络超时");
                result = null;
            } else {
                MyLogUtil.LogD("wdx", "网络错误");
                result = null;
            }

        } catch (Exception e) {
            MyLogUtil.LogD("wdx", "" + e.toString());
            result = "";
        } finally {
            if (!bRunning) return "";

            return result;
        }
    }
}
