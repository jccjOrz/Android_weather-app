package com.jc.weather;

import android.app.Application;

import com.jc.library.newnet.INetworkRequiredInfo;


/**
 * 网络访问信息
 * @author jc
 */
public class NetworkRequiredInfo implements INetworkRequiredInfo {

    private final Application application;

    public NetworkRequiredInfo(Application application){
        this.application = application;
    }

    /**
     * 版本名
     */
    @Override
    public String getAppVersionName() {
        return com.jc.weather.BuildConfig.VERSION_NAME;
    }
    /**
     * 版本号
     */
    @Override
    public String getAppVersionCode() {
        return String.valueOf(com.jc.weather.BuildConfig.VERSION_CODE);
    }

    /**
     * 是否为debug
     */
    @Override
    public boolean isDebug() {
        return com.jc.weather.BuildConfig.DEBUG;
    }

    /**
     * 应用全局上下文
     */
    @Override
    public Application getApplicationContext() {
        return application;
    }
}
