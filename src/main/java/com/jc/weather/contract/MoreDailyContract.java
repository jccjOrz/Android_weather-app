package com.jc.weather.contract;

import android.annotation.SuppressLint;

import com.jc.weather.api.ApiService;
import com.jc.weather.bean.DailyResponse;
import com.jc.library.base.BasePresenter;
import com.jc.library.base.BaseView;
import com.jc.library.newnet.NetworkApi;
import com.jc.library.newnet.observer.BaseObserver;

/**
 * 更多天气预报订阅器
 *
 * @author jc
 */
public class MoreDailyContract {

    public static class MoreDailyPresenter extends BasePresenter<IMoreDailyView> {

        /**
         * 更多天气预报  V7
         *
         * @param location 城市id
         */
        @SuppressLint("CheckResult")
        public void dailyWeather(String location) {
            ApiService service = NetworkApi.createService(ApiService.class, 3);
            service.dailyWeather("15d", location).compose(NetworkApi.applySchedulers(new BaseObserver<DailyResponse>() {
                @Override
                public void onSuccess(DailyResponse dailyResponse) {
                    if (getView() != null) {
                        getView().getMoreDailyResult(dailyResponse);
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

    public interface IMoreDailyView extends BaseView {

        //更多天气预报返回数据 V7
        void getMoreDailyResult(DailyResponse response);

        //错误返回
        void getDataFailed();
    }
}
