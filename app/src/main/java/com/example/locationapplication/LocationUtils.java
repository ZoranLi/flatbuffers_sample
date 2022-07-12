package com.example.locationapplication;

import static android.content.Context.LOCATION_SERVICE;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.CellIdentityCdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellLocation;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by MoLin on 16/9/25.
 */
public class LocationUtils {

    private volatile static LocationUtils uniqueInstance;

    private LocationHelper mLocationHelper;

    private MyLocationListener myLocationListener;

    private LocationManager mLocationManager;

    private Context mContext;

    private LocationUtils(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);
    }

    //采用Double CheckLock(DCL)实现单例
    public static LocationUtils getInstance(Context context) {
        if (uniqueInstance == null) {
            synchronized (LocationUtils.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new LocationUtils(context);
                }
            }
        }
        return uniqueInstance;
    }

    /**
     * 初始化位置信息
     *
     * @param locationHelper 传入位置回调接口
     */
    public void initLocation(LocationHelper locationHelper) {
        Location location = null;
        mLocationHelper = locationHelper;
        if (myLocationListener == null) {
            myLocationListener = new MyLocationListener();
        }
        // 需要检查权限,否则编译报错,想抽取成方法都不行,还是会报错。只能这样重复 code 了。
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (!mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            location = getLastLocation();
            Log.e("MoLin", "LocationManager.NETWORK_PROVIDER");
            if (location != null) {
                locationHelper.UpdateLastLocation(location);
            }
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0, 0, myLocationListener);
        } else {
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            location = getLastLocation();


            if (location != null) {
                getAddress(location.getLatitude(), location.getLongitude());
                locationHelper.UpdateLastLocation(location);
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, myLocationListener);
        }
    }

    private Location getLastLocation() {
        Location location = null;

        LocationManager locationManager = (LocationManager) SDWanVPNApplication.getAppContext()
                .getSystemService(LOCATION_SERVICE);
        if (locationManager == null) {
            return null;
        }
        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (location == null || l.getAccuracy() < location.getAccuracy()) {
                // Found best last known location: %s", l);
                location = l;
            }
        }
        return location;

    }


    public static String getAddress(double latitude, double longitude) {
        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(SDWanVPNApplication.getAppContext());
        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addressList != null) {
            for (Address address : addressList) {
                Log.d("aaaa->", String.format("address: %s", address.toString()));
                return String.format("address: %s", address.toString());
            }
        }
        return null;
    }


    private class MyLocationListener implements LocationListener {
        //定位服务状态改变会触发词函数
        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            Log.e("MoLin", "onStatusChanged!");
            if (mLocationHelper != null) {
                mLocationHelper.UpdateStatus(provider, status, extras);
            }
        }

        // 定位功能开启时会触发该函数
        @Override
        public void onProviderEnabled(String provider) {
            Log.e("MoLin", "onProviderEnabled!" + provider);
        }

        // 定位功能关闭时会触发该函数
        @Override
        public void onProviderDisabled(String provider) {
            Log.e("MoLin", "onProviderDisabled!" + provider);
        }

        // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            Log.e("MoLin", "onLocationChanged!");
            if (mLocationHelper != null) {
                mLocationHelper.UpdateLocation(location);
            }
        }
    }

    // 移除定位监听
    public void removeLocationUpdatesListener() {
        // 需要检查权限,否则编译不过
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(myLocationListener);
        }
    }


    /**
     * 判断是否开启了GPS或网络定位开关
     */
    public boolean isLocationProviderEnabled() {
        boolean result = false;
        LocationManager locationManager = (LocationManager) SDWanVPNApplication.getAppContext()
                .getSystemService(LOCATION_SERVICE);
        if (locationManager == null) {
            return false;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            result = true;
        }
        return result;
    }

    /**
     * 跳转到设置界面，引导用户开启定位服务
     */
    private void openLocationServer() {
//        Intent i = new Intent();
//        i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivityForResult(i, REQUEST_CODE_LOCATION_SERVER);
    }


    public static void getLocationGSM() {
        TelephonyManager telephonyManager = (TelephonyManager) SDWanVPNApplication.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) telephonyManager.getCellLocation();
            int cid = cdmaCellLocation.getBaseStationId(); //获取cdma基站识别标号 BID
            int lac = cdmaCellLocation.getNetworkId(); //获取cdma网络编号NID
            int sid = cdmaCellLocation.getSystemId(); //用谷歌API的话cdma网络的mnc要用这个getSystemId()取得→SID
            List<CellInfo> infoLists = telephonyManager.getAllCellInfo();

            for (CellInfo info : infoLists) {
                CellInfoCdma cellInfoCdma = (CellInfoCdma) info;
                CellIdentityCdma cellIdentityCdma = cellInfoCdma.getCellIdentity();
                CellSignalStrengthCdma cellSignalStrengthCdma = cellInfoCdma.getCellSignalStrength();
                int strength = cellSignalStrengthCdma.getCdmaDbm();
                int cid1 = cellIdentityCdma.getBasestationId();
                // 处理 strength和id数据
                Log.e("", "getLocationGSM CellInfo: " + strength + ":" + cid1);
            }
        } else {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
            int cid = gsmCellLocation.getCid(); //获取gsm基站识别标号
            int lac = gsmCellLocation.getLac(); //获取gsm网络编号
            Log.e("", "getLocationGSM: " + cid + ":" + lac);
        }

        telephonyManager.listen(new PhoneStateListener(){
            @Override
            public void onCellLocationChanged(CellLocation location) {
                super.onCellLocationChanged(location);
            }

            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);
            }

            @Override
            public void onCellInfoChanged(List<CellInfo> cellInfo) {
                super.onCellInfoChanged(cellInfo);
            }

        }, PhoneStateListener.LISTEN_CELL_LOCATION);
    }



    /**
     * 获取JSON形式的基站信息
     * @param mcc 移动国家代码（中国的为460）
     * @param mnc 移动网络号码（中国移动为0，中国联通为1，中国电信为2）；
     * @param lac 位置栏码
     * @param cid 基站编号
     * @return json
     * @throws JSONException
     */
    private String getJsonCellPos(int mcc, int mnc, int lac, int cid) throws JSONException {
        JSONObject jsonCellPos = new JSONObject();
        jsonCellPos.put("version", "1.1.0");
        jsonCellPos.put("host", "maps.google.com");
        JSONArray array = new JSONArray();
        JSONObject json1 = new JSONObject();
        json1.put("location_area_code", "" + lac + "");
        json1.put("mobile_country_code", "" + mcc + "");
        json1.put("mobile_network_code", "" + mnc + "");
        json1.put("age", 0);
        json1.put("cell_id", "" + cid + "");
        array.put(json1);
        jsonCellPos.put("cell_towers", array);
        return jsonCellPos.toString();
    }


    /**
     * 调用第三方公开的API依据基站信息查找基站的经纬度值及地址信息
     */
    public String httpPost(String url, String jsonCellPos) throws IOException{
        byte[] data = jsonCellPos.toString().getBytes();
        URL realUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) realUrl.openConnection();
        httpURLConnection.setConnectTimeout(6 * 1000);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        httpURLConnection.setRequestProperty("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
        httpURLConnection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        httpURLConnection.setRequestProperty("Host", "www.minigps.net");
        httpURLConnection.setRequestProperty("Referer", "http://www.minigps.net/map.html");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.94 Safari/537.4X-Requested-With:XMLHttpRequest");
        httpURLConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        httpURLConnection.setRequestProperty("Host", "www.minigps.net");
        DataOutputStream outStream = new DataOutputStream(httpURLConnection.getOutputStream());
        outStream.write(data);
        outStream.flush();
        outStream.close();
        if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpURLConnection.getInputStream();
            return new String(read(inputStream));
        }
        return null;
    }

    /**
     * 读取IO流并以byte[]形式存储
     * @param inputSream InputStream
     * @return byte[]
     * @throws IOException
     */
    public byte[] read(InputStream inputSream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024];
        while ((len = inputSream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inputSream.close();
        return outStream.toByteArray();
    }
}



