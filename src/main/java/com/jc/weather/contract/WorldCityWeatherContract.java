package com.jc.weather.contract;

import android.annotation.SuppressLint;

import com.jc.weather.api.ApiService;
import com.jc.weather.bean.DailyResponse;
import com.jc.weather.bean.HourlyResponse;
import com.jc.weather.bean.NowResponse;
import com.jc.library.base.BasePresenter;
import com.jc.library.base.BaseView;
import com.jc.library.newnet.NetworkApi;
import com.jc.library.newnet.observer.BaseObserver;

/**
 * 世界城市天气订阅器
 *
 * @author jc
 */
public class WorldCityWeatherContract {

    public static class WorldCityWeatherPresenter extends BasePresenter<IWorldCityWeatherView> {

        /**
         * 实况天气  V7版本
         *
         * @param location 城市名
         */
        @SuppressLint("CheckResult")
        public void nowWeather(String location) {//这个3 表示使用新的V7API访问地址
            ApiService service = NetworkApi.createService(ApiService.class, 3);
            service.nowWeather(location).compose(NetworkApi.applySchedulers(new BaseObserver<NowResponse>() {
                @Override
                public void onSuccess(NowResponse nowResponse) {
                    if (getView() != null) {
                        getView().getNowResult(nowResponse);
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
         * 天气预报  V7版本   7d 表示天气的数据 为了和之前看上去差别小一些，这里先用七天的
         *
         * @param location 城市名
         */
        @SuppressLint("CheckResult")
        public void dailyWeather(String location) {//这个3 表示使用新的V7API访问地址
            ApiService service = NetworkApi.createService(ApiService.class, 3);
            service.dailyWeather("7d", location).compose(NetworkApi.applySchedulers(new BaseObserver<DailyResponse>() {
                @Override
                public void onSuccess(DailyResponse dailyResponse) {
                    if (getView() != null) {
                        getView().getDailyResult(dailyResponse);
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
         * 逐小时预报（未来24小时）
         *
         * @param location 城市名
         */
        @SuppressLint("CheckResult")
        public void hourlyWeather(String location) {
            ApiService service = NetworkApi.createService(ApiService.class, 3);
            service.hourlyWeather(location).compose(NetworkApi.applySchedulers(new BaseObserver<HourlyResponse>() {
                @Override
                public void onSuccess(HourlyResponse hourlyResponse) {
                    if (getView() != null) {
                        getView().getHourlyResult(hourlyResponse);
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

    public interface IWorldCityWeatherView extends BaseView {
        /*               以下为V7版本新增               */
        //实况天气
        void getNowResult(NowResponse response);

        //天气预报  7天
        void getDailyResult(DailyResponse response);

        //逐小时天气预报
        void getHourlyResult(HourlyResponse response);

        //错误返回
        void getDataFailed();
    }
}
