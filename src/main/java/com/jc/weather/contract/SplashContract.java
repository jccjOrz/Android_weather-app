package com.jc.weather.contract;

import android.annotation.SuppressLint;

import com.jc.weather.api.ApiService;
import com.jc.weather.bean.BiYingImgResponse;
import com.jc.library.base.BasePresenter;
import com.jc.library.base.BaseView;
import com.jc.library.bean.AppVersion;
import com.jc.library.newnet.NetworkApi;
import com.jc.library.newnet.observer.BaseObserver;

/**
 * 欢迎页订阅器
 *
 * @author jc
 */
public class SplashContract {

    public static class SplashPresenter extends BasePresenter<ISplashView> {

        /**
         * 获取最新的APP版本信息
         */
        @SuppressLint("CheckResult")
        public void getAppInfo() {//注意这里的4表示新的搜索城市地址接口
            ApiService service = NetworkApi.createService(ApiService.class, 5);
            service.getAppInfo().compose(NetworkApi.applySchedulers(new BaseObserver<AppVersion>() {
                @Override
                public void onSuccess(AppVersion appVersion) {
                    if (getView() != null) {
                        getView().getAppInfoResult(appVersion);
                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    if (getView() != null) {
                        getView().getDataFailed();
                    }
                }
            }));
        }

        /**
         * 获取必应  每日一图
         */
        @SuppressLint("CheckResult")
        public void biying() {
            ApiService service = NetworkApi.createService(ApiService.class, 1);
            service.biying().compose(NetworkApi.applySchedulers(new BaseObserver<BiYingImgResponse>() {
                @Override
                public void onSuccess(BiYingImgResponse biYingImgResponse) {
                    if (getView() != null) {
                        getView().getBiYingResult(biYingImgResponse);
                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    if (getView() != null) {
                        getView().getDataFailed();
                    }
                }
            }));
        }
    }

    public interface ISplashView extends BaseView {
        //APP信息返回
        void getAppInfoResult(AppVersion response);

        /**
         * 获取必应每日一图返回
         * @param response BiYingImgResponse
         */
        void getBiYingResult(BiYingImgResponse response);

        //错误返回
        void getDataFailed();


    }
}
