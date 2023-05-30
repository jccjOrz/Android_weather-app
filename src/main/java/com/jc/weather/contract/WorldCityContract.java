package com.jc.weather.contract;

import android.annotation.SuppressLint;
import com.jc.weather.api.ApiService;
import com.jc.weather.bean.WorldCityResponse;
import com.jc.library.base.BasePresenter;
import com.jc.library.base.BaseView;
import com.jc.library.newnet.NetworkApi;
import com.jc.library.newnet.observer.BaseObserver;


/**
 * 世界城市订阅器
 *
 * @author jc
 */
public class WorldCityContract {

    public static class WorldCityPresenter extends BasePresenter<IWorldCityView> {

        /**
         * 世界城市  V7
         *
         * @param range 类型
         */
        @SuppressLint("CheckResult")
        public void worldCity(String range) {
            ApiService service = NetworkApi.createService(ApiService.class, 4);//指明访问的地址
            service.worldCity(range).compose(NetworkApi.applySchedulers(new BaseObserver<WorldCityResponse>() {
                @Override
                public void onSuccess(WorldCityResponse worldCityResponse) {
                    if (getView() != null) {
                        getView().getWorldCityResult(worldCityResponse);
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

    public interface IWorldCityView extends BaseView {

        //热门城市返回数据 V7
        void getWorldCityResult(WorldCityResponse response);

        //错误返回
        void getDataFailed();
    }
}
