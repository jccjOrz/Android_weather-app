package com.jc.weather.contract;

import android.annotation.SuppressLint;

import com.jc.weather.api.ApiService;
import com.jc.weather.bean.AirNowResponse;
import com.jc.weather.bean.MoreAirFiveResponse;
import com.jc.weather.bean.NewSearchCityResponse;
import com.jc.library.base.BasePresenter;
import com.jc.library.base.BaseView;
import com.jc.library.newnet.NetworkApi;
import com.jc.library.newnet.observer.BaseObserver;

/**
 * 更多空气质量数据订阅器
 *
 * @author jc
 */

public class MoreAirContract {

    public static class MoreAirPresenter extends BasePresenter<IMoreAirView> {


        /**
         * 搜索城市  搜索站点的城市id，用于查询空气质量
         *
         * @param location 城市名
         */
        @SuppressLint("CheckResult")
        public void searchCityId(String location) {//注意这里的4表示新的搜索城市地址接口
            ApiService service = NetworkApi.createService(ApiService.class, 4);//指明访问的地址
            service.newSearchCity(location, "exact")
                    .compose(NetworkApi.applySchedulers(new BaseObserver<NewSearchCityResponse>() {
                        @Override
                        public void onSuccess(NewSearchCityResponse newSearchCityResponse) {
                            if (getView() != null) {
                                getView().getSearchCityIdResult(newSearchCityResponse);
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
         * 空气质量  V7
         *
         * @param location 城市id
         */
        @SuppressLint("CheckResult")
        public void air(String location) {
            ApiService service = NetworkApi.createService(ApiService.class, 3);
            service.airNowWeather(location).compose(NetworkApi.applySchedulers(new BaseObserver<AirNowResponse>() {
                        @Override
                        public void onSuccess(AirNowResponse airNowResponse) {
                            if (getView() != null) {
                                getView().getMoreAirResult(airNowResponse);
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
         * 五天空气质量数据  V7
         *
         * @param location 城市id
         */
        @SuppressLint("CheckResult")
        public void airFive(String location) {
            ApiService service = NetworkApi.createService(ApiService.class, 3);
            service.airFiveWeather(location)
                    .compose(NetworkApi.applySchedulers(new BaseObserver<MoreAirFiveResponse>() {
                        @Override
                        public void onSuccess(MoreAirFiveResponse moreAirFiveResponse) {
                            if (getView() != null) {
                                getView().getMoreAirFiveResult(moreAirFiveResponse);
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

    public interface IMoreAirView extends BaseView {

        //搜索城市Id
        void getSearchCityIdResult(NewSearchCityResponse response);

        //空气质量返回数据 V7
        void getMoreAirResult(AirNowResponse response);

        //五天空气质量数据返回 V7
        void getMoreAirFiveResult(MoreAirFiveResponse response);

        //错误返回
        void getDataFailed();


    }
}
