package com.jc.weather.contract;

import android.annotation.SuppressLint;

import com.jc.weather.api.ApiService;
import com.jc.weather.bean.BiYingImgResponse;
import com.jc.weather.bean.WallPaperResponse;
import com.jc.library.base.BasePresenter;
import com.jc.library.base.BaseView;
import com.jc.library.newnet.NetworkApi;
import com.jc.library.newnet.observer.BaseObserver;

/**
 * 壁纸订阅器
 *
 * @author jc
 */
public class WallPaperContract {

    public static class WallPaperPresenter extends BasePresenter<IWallPaperView> {


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


        /**
         * 获取壁纸数据
         */
        @SuppressLint("CheckResult")
        public void getWallPaper() {
            // 6 表示访问网络壁纸接口
            ApiService service = NetworkApi.createService(ApiService.class, 6);
            service.getWallPaper().compose(NetworkApi.applySchedulers(new BaseObserver<WallPaperResponse>() {
                @Override
                public void onSuccess(WallPaperResponse wallPaperResponse) {
                    if (getView() != null) {
                        getView().getWallPaperResult(wallPaperResponse);
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

    public interface IWallPaperView extends BaseView {

        /**
         * 获取必应每日一图返回
         * @param response BiYingImgResponse
         */
        void getBiYingResult(BiYingImgResponse response);

        /**
         * 壁纸数据返回
         * @param response WallPaperResponse
         */
        void getWallPaperResult(WallPaperResponse response);

        /**
         * 错误返回
         */
        void getDataFailed();


    }
}
