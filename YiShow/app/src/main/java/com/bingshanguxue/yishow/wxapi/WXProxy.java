package com.bingshanguxue.yishow.wxapi;

import android.content.Context;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

/**
 * Created by Administrator on 2015/6/8.
 */
public class WXProxy {
    private static IWXAPI api;

    private static WXProxy instance;
    public static WXProxy getInstance(Context context){
        if (instance == null){
            return new WXProxy(context);
        }

        return instance;
    }


    public WXProxy(Context context){
        api = WXAPIFactory.createWXAPI(context, WXConstants.APP_ID);
    }

    /**
     * 发送文本到微信
     * */
    public void sendTextToWX(String text, int scene){
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = scene;

//        IWXAPI api = WXAPIFactory.createWXAPI(getContext(), WeiXinConstants.APP_ID);
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
