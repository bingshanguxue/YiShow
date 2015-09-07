package com.bingshanguxue.yishow.app.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bingshanguxue.yishow.R;
import com.bingshanguxue.yishow.ZZN;
import com.bingshanguxue.yishow.app.helper.UIHelper;
import com.bingshanguxue.yishow.utils.ToastUtil;
import com.bingshanguxue.yishow.wxapi.WXProxy;
import com.tencent.mm.sdk.openapi.SendMessageToWX;


public class MapActivity extends Activity {

    private EditText etMessage, etMfhRealeaseUrl;
    private Button btnShare, btnSend, btnWebpage, btnOneKeyShare, btnMfhRelease;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = (EditText) findViewById(R.id.et_message);
        btnShare = (Button) findViewById(R.id.button_share);
        btnShare.setOnClickListener(myOnClickListener);
        btnSend = (Button) findViewById(R.id.button_send);
        btnSend.setOnClickListener(myOnClickListener);
        btnWebpage = (Button) findViewById(R.id.button_webpage);
        btnWebpage.setOnClickListener(myOnClickListener);
        btnOneKeyShare = (Button) findViewById(R.id.button_onekey_share);
        btnOneKeyShare.setOnClickListener(myOnClickListener);


        etMfhRealeaseUrl = (EditText) findViewById(R.id.et_mfh_release_url);
        btnMfhRelease = (Button) findViewById(R.id.btn_mfh_release);
        btnMfhRelease.setOnClickListener(myOnClickListener);

        findViewById(R.id.button_test_0).setOnClickListener(myOnClickListener);
        findViewById(R.id.button_test_1).setOnClickListener(myOnClickListener);
    }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private View.OnClickListener myOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_share:{
                    Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    WXProxy.getInstance(MapActivity.this).sendWebpageToWX(ZZN.SINA_WEIBO_URL,
                            ZZN.NICKNAME, ZZN.EMAIL_ADDRESS_DEF,
                            thumb, SendMessageToWX.Req.WXSceneSession);
                }
                break;
                case R.id.button_webpage:{
                    Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    WXProxy.getInstance(MapActivity.this).sendWebpageToWX(ZZN.SINA_WEIBO_URL,
                            ZZN.NICKNAME, ZZN.EMAIL_ADDRESS_DEF,
                            thumb, SendMessageToWX.Req.WXSceneTimeline);
                }
                break;
                case R.id.button_send:{
                    String message = etMessage.getText().toString();
                    if(TextUtils.isEmpty(message)){
                        Toast.makeText(MapActivity.this, R.string.toast_message_empty, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    WXProxy.getInstance(MapActivity.this).sendTextToWX(message, SendMessageToWX.Req.WXSceneSession);
                }
                break;
                case R.id.button_onekey_share:{
                    String message = etMessage.getText().toString();
                    if(TextUtils.isEmpty(message)){
                        Toast.makeText(MapActivity.this, R.string.toast_message_empty, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    WXProxy.getInstance(MapActivity.this).sendTextToWX(message, SendMessageToWX.Req.WXSceneTimeline);
                }
                break;
                case R.id.button_test_0:{
                    UIHelper.redirectToActivity(MapActivity.this, ObservActivity.class);
                }
                break;
                case R.id.button_test_1:{
                    UIHelper.redirectToActivity(MapActivity.this, ObservScrollViewActivity.class);
                }
                break;
                case R.id.btn_mfh_release:{
                    String url = etMfhRealeaseUrl.getText().toString();
                    if(TextUtils.isEmpty(url)){
                        ToastUtil.showToast(R.string.toast_url_empty, R.drawable.ic_launcher_mfh);
                        return;
                    }

                    Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_mfh);
                    WXProxy.getInstance(MapActivity.this).sendWebpageToWX(url,
                            "满分家园", "品质新生活·Android版客户端v0.3.6,欢迎体验",
                            thumb, SendMessageToWX.Req.WXSceneSession);
                }
                break;
            }
        }
    };

}
