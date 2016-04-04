package wdx.com.myhzbike;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wdx.com.myhzbike.iface.HttpCallBack;
import wdx.com.myhzbike.utils.MyHttpConnectionTask;
import wdx.com.myhzbike.utils.MyLogUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, OnGetPoiSearchResultListener, BaiduMap.OnMapStatusChangeListener, OnGetGeoCoderResultListener {
    private MapView mMapView;
    private BaiduMap mMap;
    private EditText et_search;
    private PoiSearch poiSearch;
    private GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    boolean isFirstLoc = true; // 是否首次定位

    private HashMap<Integer, ArrayList> allItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = (MapView)findViewById(R.id.bmapView);
        et_search = (EditText) findViewById(R.id.et_search);
        mMap = mMapView.getMap();
        // 开启定位图层
        mMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        et_search.addTextChangedListener(this);
        initLocation();

    }
    private void search(){
        MyHttpConnectionTask task = new MyHttpConnectionTask(new HttpCallBack() {
            @Override
            public void callBackDate(String result) {
//                MyLogUtil.LogV("wdx",result);
                Document document = Jsoup.parse(result);
                Elements elements = document.getElementsByTag("li");
                ArrayList<String> list = new ArrayList<>();
                int num = 0;
                for (Element element :elements) {
                    String s = element.attr("onclick");
                    if(s != null && !"".equals(s)){
                        list.add(s);
                    }
                }
                allItem = new HashMap<>();
                allItem.put(num++, list);
                for(int i = 0; i < allItem.size(); i++){
                    ArrayList<String> item = allItem.get(i);
                    for (String ss : item) {
                        try {
                            MyLogUtil.LogV("wdx", "sp  :  " + ss);
                            ss = ss.replace("%","\\");
                            MyLogUtil.LogV("wdx", "sp unicodeToUtf8  :  " + unicodeToUtf8(ss));

                        } catch (Exception e) {
                            MyLogUtil.LogV("wdx", "Exception : " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }


//                for (String onclickString : list){
//                    System.out.print(onclickString);
//                    try {
//                        MyLogUtil.LogV("wdx",onclickString);
//                        onclickString = onclickString.replace("%","\\");
//                        MyLogUtil.LogV("wdx","\\\\\\\\\\" + onclickString);
//                        onclickString = unicodeToUtf8(onclickString);
//                        MyLogUtil.LogV("wdx","utu" + onclickString);
//                        MyLogUtil.LogV("wdx","decode" + URLDecoder.decode(onclickString.replace("%","\\"),"unicode"));
//                    }catch (Exception e){
//                        MyLogUtil.LogV("wdx",e.getMessage());
//                        e.printStackTrace();
//                    }
//                    int position = 0;
//                    ArrayList<String> mys = new ArrayList<>();
//                    for (int p = 0; p < onclickString.length(); p++){
//                        if(p>0){
//                            position++;
//                        }
//                        MyLogUtil.LogV("wdx","p : " + p +"  position  :  " + position + "  onclickString" + onclickString);
//                        int p1 = onclickString.indexOf("'",position);
//                        if(p1 == -1){
//                            MyLogUtil.LogV("wdx","p1 = -1 ");
//                            break;
//                        }
//                        int p2 = onclickString.indexOf("'",++p1);
//                        if(p2 == -1){
//                            MyLogUtil.LogV("wdx","p2 = -1 ");
//                            break;
//                        }
//                        mys.add(onclickString.substring(p1,p2));
//                        position = p2;
//                    }
//                    for (String ss : mys){
//                        try {
//                            MyLogUtil.LogV("wdx","sp  :  " + ss);
//                            MyLogUtil.LogV("wdx","sp decode  :  " + URLDecoder.decode(URLDecoder.decode(ss,"utf-8"),"utf-8"));
//                        } catch (Exception e) {
//                        MyLogUtil.LogV("wdx", "Exception : " + e.getMessage());
//                        e.printStackTrace();
//                    }
//                    }
//                }
                StringBuilder sb = new StringBuilder(result);
                String name = sb.substring(sb.indexOf("javascript:window.parent.ZXCClick"));
            }
        }, "GET");
        String key = "武林广场";
        try {
            String keyEncoder = URLEncoder.encode(key ,"UTF-8");
            String[] params = new String[3];
            params[0] = "http://www.hzbus.cn/Page/BicyleNearby.aspx";
            params[1] = "w=300&x=120.15841085901&y=30.2674978758945&rnd=8" + "&nm=" + "%E6%AD%A6%E6%9E%97%E5%B9%BF%E5%9C%BA";
//            params[0] = "http://www.hzbus.cn/HCF.AXD";
//            params[1] = "rnd=8811" + "&n=" + "%E6%AD%A6" ;
//            "%u6b66%u6797%u5e7f%u573a"
//            "%E6%AD%A6%E6%9E%97%E5%B9%BF%E5%9C%BA"
//            http://www.hzbus.cn/HCF.AXD?n=%E6%AD%A6&rnd=8811
            MyLogUtil.LogV("wdx",params[0] + "?" + params[1]);
            task.execute(params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll,17);
                mMap.animateMapStatus(u);
                String message = "时间：" + location.getTime() + " 地点：" + location.getAddrStr();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

    }
//    61 ： GPS定位结果，GPS定位成功。
//    62 ： 无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。
//    63 ： 网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
//    65 ： 定位缓存的结果。
//    66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
//    67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
//    68 ： 网络连接失败时，查找本地离线定位时对应的返回结果。
//    161： 网络定位结果，网络定位定位成功。
//    162： 请求串密文解析失败。
//    167： 服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
//    502： key参数错误，请按照说明文档重新申请KEY。
//    505： key不存在或者非法，请按照说明文档重新申请KEY。
//    601： key服务被开发者自己禁用，请按照说明文档重新申请KEY。
//    602： key mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请KEY。
//    501～700：key验证失败，请按照说明文档重新申请KEY。

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
        }else if(id == R.id.action_refresh){
            search();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }
    /*
        http://www.hzbus.cn/HCF.AXD?n=%E6%AD%A6&rnd=8811 HTTP/1.1
        http://www.hzbus.cn/page/get3GPS.aspx?zxc=1&sid=0.26280068270460344 HTTP/1.1
        http://www.hzbus.cn/Page/BicyleNearby.aspx?nm=%E6%AD%A6&w=300&x=120.15841085901&y=30.2674978758945&rnd=8

    HCF.AXD 获取关键字返回结果 n 关键字符，中文要用encode转化
    page/get3GPS.aspx获取具体结果
     */
    @Override
    public void afterTextChanged(Editable s) {
        try {
            String n = URLEncoder.encode(s.toString(),"GB2312");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {

    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }

    /**
     *
     * @param theString
     * @return String
     */
    public static String unicodeToUtf8(String theString) {
        char aChar;
        if(theString==null){
            return "";
        }
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
            aChar = theString.charAt(x++);
            if (aChar == 'u') {
                // Read the xxxx
                int value = 0;
                for (int i = 0; i < 4; i++) {
                    aChar = theString.charAt(x++);
                    switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Malformed   \\uxxxx   encoding.");
                    }
                }
                outBuffer.append((char) value);
            } else {
                if (aChar == 't')
                    aChar = 't';
                else if (aChar == 'r')
                    aChar = 'r';
                else if (aChar == 'n')
                    aChar = 'n';
                else if (aChar == 'f')
                    aChar = 'f';
                outBuffer.append(aChar);
            }
        } else
        outBuffer.append(aChar);
    }
    return outBuffer.toString();
}

}
