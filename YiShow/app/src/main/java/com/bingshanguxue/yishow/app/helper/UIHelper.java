package com.bingshanguxue.yishow.app.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.bingshanguxue.yishow.R;
import com.bingshanguxue.yishow.app.AppContext;
import com.bingshanguxue.yishow.app.AppManager;
import com.bingshanguxue.yishow.app.dialog.CommonDialog;
import com.bingshanguxue.yishow.utils.StringUtils;
import com.bingshanguxue.yishow.utils.TDevice;

import java.net.URLDecoder;

/**
 * 界面帮助类
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年10月10日 下午3:33:36
 * 
 */
public class UIHelper {

    /** 全局web样式 */
    // 链接样式文件，代码块高亮的处理
    public final static String linkCss = "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/client.js\"></script>"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>"
            + "<script type=\"text/javascript\">function showImagePreview(var url){window.location.url= url;}</script>";
    public final static String WEB_STYLE = linkCss
            + "<style>* {font-size:16px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} "
            + "img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} "
            + "pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;overflow: auto;} "
            + "a.tag {font-size:15px;text-decoration:none;background-color:#cfc;color:#060;border-bottom:1px solid #B1D3EB;border-right:1px solid #B1D3EB;color:#3E6D8E;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;position:relative}</style>";

    public static final String WEB_LOAD_IMAGES = "<script type=\"text/javascript\"> var allImgUrls = getAllImgSrc(document.body.innerHTML);</script>";

    private static final String SHOWIMAGE = "ima-api:action=showImage&data=";

    /**
     * 跳转页面
     * */
    public static void redirectToActivity(Context context, java.lang.Class<?> cls){
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    /**
     * 组合动态的回复文本
     * 
     * @param name
     * @param body
     * @return
     */
    public static SpannableStringBuilder parseActiveReply(String name,
            String body) {
        Spanned span = Html.fromHtml(body.trim());
        SpannableStringBuilder sp = new SpannableStringBuilder(name + "：");
        sp.append(span);
        // 设置用户名字体加粗、高亮
        // sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
        // name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#576B95")), 0,
                name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }

    /**
     * 打开内置浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
//        if (StringUtils.isImgUrl(url)) {
//            ImagePreviewActivity.showImagePrivew(context, 0,
//                    new String[] { url });
//            return;
//        }


        try {
            // 启用外部浏览器
             Uri uri = Uri.parse(url);
             Intent it = new Intent(Intent.ACTION_VIEW, uri);
             context.startActivity(it);
//            Bundle bundle = new Bundle();
//            bundle.putString(BrowserFragment.BROWSER_KEY, url);
//            showSimpleBack(context, SimpleBackPage.BROWSER, bundle);
        } catch (Exception e) {
            e.printStackTrace();
//            AppContext.showToastShort("无法浏览此网页");
        }
    }

    /**
     * 发送App异常崩溃报告
     *
     * @param cont
     * @param crashReport
     */
    public static void sendAppCrashReport(final Context context,
                                          final String crashReport) {
        CommonDialog dialog = new CommonDialog(context);

        dialog.setTitle(R.string.app_error);
        dialog.setMessage(R.string.app_error_message);
        dialog.setPositiveButton(R.string.submit_report,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 发送异常报告
                        TDevice.sendEmail(context, "OSCAndroid客户端耍脾气 - 症状诊断报告",
                                crashReport, "apposchina@163.com");
                        // 退出
                        AppManager.getAppManager().AppExit(context);
                    }
                });
        dialog.setNegativeButton(R.string.cancle,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 退出
                        AppManager.getAppManager().AppExit(context);
                    }
                });
        dialog.show();
    }

    public static void sendAppCrashReport(final Context context) {
        CommonDialog dialog = new CommonDialog(context);
        dialog.setTitle(R.string.app_error);
        dialog.setMessage(R.string.app_error_message);
        dialog.setNegativeButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(-1);
                    }
                });
        dialog.show();
    }


}
