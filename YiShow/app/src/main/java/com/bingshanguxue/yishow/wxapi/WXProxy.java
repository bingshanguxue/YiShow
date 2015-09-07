package com.bingshanguxue.yishow.wxapi;

import android.content.Context;
import android.graphics.Bitmap;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;

/**
 * Created by Administrator on 2015/6/8.
 */
public class WXProxy {
    private static final String TRANSACTION_TYPE_TEXT = "text";
    private static final String TRANSACTION_TYPE_WEBPAGE = "webpage";

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
     * @param text content
     * @param scene SendMessageToWX.Req.WXSceneTimeline/SendMessageToWX.Req.WXSceneSession
     * */
    public void sendTextToWX(String text, int scene){
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = text;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(TRANSACTION_TYPE_TEXT);
        req.message = msg;
        req.scene = scene;

        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    /**
     * 发送文本到微信
     * @param webpageUrl content
     * @param scene SendMessageToWX.Req.WXSceneTimeline/SendMessageToWX.Req.WXSceneSession
     * */
    public void sendWebpageToWX(String webpageUrl, String title, String description, Bitmap thumb, int scene){
        // 初始化一个WXTextObject对象
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webpageUrl;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(TRANSACTION_TYPE_WEBPAGE);
        req.message = msg;
        req.scene = scene;

        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
