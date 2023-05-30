package com.jc.weather.ui;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.jc.weather.databinding.ActivityWebBinding;
import com.jc.library.base.vb.BaseVBActivity;


/**
 * 网页
 *
 * @author jc
 */
public class WebActivity extends BaseVBActivity<ActivityWebBinding> {


    @Override
    public void initData() {}

    /**
     * 加载网页Url
     *
     * @param url
     */
    private void loadUrl(String url) {
        if (url == null) {
            return;
        }

        WebSettings webSetting = binding.webView.getSettings();
        //设置JS允许
        webSetting.setJavaScriptEnabled(true);
        //设置WebView是否使用viewport
        webSetting.setUseWideViewPort(true);
        //设置WebView是否使用预览模式加载界面。
        webSetting.setLoadWithOverviewMode(true);
        //设置WebView是否支持使用屏幕控件或手势进行缩放
        webSetting.setSupportZoom(true);
        //设置WebView是否使用其内置的变焦机制，该机制集合屏幕缩放控件使用
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);//APP缓存
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);//地理位置
        webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//读取缓存

        binding.webView.loadUrl(url);

        binding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoadingDialog();
                Log.d("webViewT", "加载开始");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissLoadingDialog();
                Log.d("webViewT", "加载完成");
            }


        });

        //进度
        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                super.onProgressChanged(webView, newProgress);
                Log.d("webViewT", "newProgress: " + newProgress);
            }
        });
    }
}
