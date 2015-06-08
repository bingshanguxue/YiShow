package com.bingshanguxue.yishow;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bingshanguxue.yishow.utils.StringUtils;
import com.bingshanguxue.yishow.wxapi.WXProxy;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;


public class MainActivity extends ActionBarActivity {

    private EditText etMessage;
    private Button btnShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = (EditText) findViewById(R.id.et_message);
        btnShare = (Button) findViewById(R.id.button_share);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString();
                if(TextUtils.isEmpty(message)){
                    Toast.makeText(MainActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                shareToWeiChatCircle(message);
            }
        });
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

    /**
     * 分享到朋友圈
     * */
    private void shareToWeiChatCircle(String text){
        WXProxy.getInstance(this).sendTextToWX(text, SendMessageToWX.Req.WXSceneTimeline);
    }
    private void shareToWeiChat(){
        WXProxy.getInstance(this).sendTextToWX(StringUtils.getRandomString(64), SendMessageToWX.Req.WXSceneSession);
    }

}
