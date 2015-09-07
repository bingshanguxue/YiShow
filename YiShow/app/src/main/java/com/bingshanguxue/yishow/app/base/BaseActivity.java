package com.bingshanguxue.yishow.app.base;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import butterknife.ButterKnife;


/**
 * Activity基类·定位
 * */
public class BaseActivity extends AppCompatActivity implements AMapLocationListener {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private static final int AMAP_LOCATION_INTERVAL = 2;

    private LocationManagerProxy mAMapLocationManager;

    protected int getLayoutResId(){return 0;}
    protected void initToolBar(){
    }
    protected boolean isAMapLocationEnable(){
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        ButterKnife.bind(this);

        initToolBar();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isAMapLocationEnable()){
            initAMapLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAMapLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // TODO,双击退出应用
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /**
     * 初始化定位
     */
    private void initAMapLocation() {

        mAMapLocationManager = LocationManagerProxy.getInstance(this);

        //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法
        //其中如果间隔时间为-1，则定位只定一次
        mAMapLocationManager.requestLocationData(
                LocationProviderProxy.AMapNetwork, AMAP_LOCATION_INTERVAL*1000, 15, this);

        mAMapLocationManager.setGpsEnable(false);
    }

    /**
     * 停止定位，并销毁定位资源
     * */
    private void stopAMapLocation() {
        if (mAMapLocationManager != null) {
            mAMapLocationManager.removeUpdates(this);
            mAMapLocationManager.destory();
        }
        mAMapLocationManager = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0){
            Log.d("Nat", "onLocationChanged: " + aMapLocation.toString());
//            LocationUtil.saveLastLocationInfo(BaseActivity.this,
//                    aMapLocation.getLatitude(), aMapLocation.getLongitude());
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
