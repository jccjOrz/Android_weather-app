package com.jc.weather.ui;

import android.graphics.Typeface;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.jc.weather.R;
import com.jc.weather.adapter.HourlyWorldCityAdapter;
import com.jc.weather.bean.DailyResponse;
import com.jc.weather.bean.HourlyResponse;
import com.jc.weather.bean.NowResponse;
import com.jc.weather.contract.WorldCityWeatherContract;
import com.jc.weather.databinding.ActivityWorldCityWeatherBinding;
import com.jc.weather.utils.CodeToStringUtils;
import com.jc.weather.utils.Constant;
import com.jc.weather.utils.StatusBarUtil;
import com.jc.weather.utils.ToastUtils;
import com.jc.weather.utils.WeatherUtil;
import com.jc.library.mvp.MvpVBActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * 世界城市天气
 *
 * @author jc
 */
public class WorldCityWeatherActivity extends MvpVBActivity<ActivityWorldCityWeatherBinding, WorldCityWeatherContract.WorldCityWeatherPresenter>
        implements WorldCityWeatherContract.IWorldCityWeatherView {

    HourlyWorldCityAdapter mAdapter;//逐小时列表适配器
    List<HourlyResponse.HourlyBean> mList = new ArrayList<>();//列表数据

    @Override
    public void initData() {
        initView();
    }

    /**
     * 初始化页面
     */
    private void initView() {
        //设置状态栏背景颜色
        StatusBarUtil.transparencyBar(context);
        Back(binding.toolbar);
        //加载弹窗
        showLoadingDialog();

        mAdapter = new HourlyWorldCityAdapter(R.layout.item_weather_hourly_world_list, mList);
        binding.rvHourly.setLayoutManager(new LinearLayoutManager(context));
        binding.rvHourly.setAdapter(mAdapter);

        //获取上一个页面传递过来的城市id
        String locationId = getIntent().getStringExtra("locationId");
        //城市名称显示
        binding.tvTitle.setText(getIntent().getStringExtra("name"));
        //查询实况天气
        mPresent.nowWeather(locationId);
        //查询天气预报
        mPresent.dailyWeather(locationId);
        //查询逐小时天气预报
        mPresent.hourlyWeather(locationId);
    }

    @Override
    protected WorldCityWeatherContract.WorldCityWeatherPresenter createPresent() {
        return new WorldCityWeatherContract.WorldCityWeatherPresenter();
    }

    /**
     * 实况天气返回  V7
     */
    @Override
    public void getNowResult(NowResponse response) {
        if (response.getCode().equals(Constant.SUCCESS_CODE)) {
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/GenJyuuGothic-ExtraLight.ttf");
            binding.tvTemperature.setText(response.getNow().getTemp() + "°");
            binding.tvTemperature.setTypeface(typeface);//使用字体
            int code = Integer.parseInt(response.getNow().getIcon());//获取天气状态码，根据状态码来显示图标
            WeatherUtil.changeIcon(binding.ivWeatherState, code);//调用工具类中写好的方法

            binding.tvWeatherState.setText("当前：" + response.getNow().getText());
            binding.tvWindState.setText(response.getNow().getWindDir() + "   " + response.getNow().getWindScale() + "级");
        } else {
            ToastUtils.showShortToast(context, CodeToStringUtils.WeatherCode(response.getCode()));
        }
    }

    /**
     * 天气预报 V7
     *
     * @param response
     */
    @Override
    public void getDailyResult(DailyResponse response) {
        if (response.getCode().equals(Constant.SUCCESS_CODE)) {
            if (response.getDaily() != null && response.getDaily().size() > 0) {
                binding.tvTemMax.setText(response.getDaily().get(0).getTempMax());
                binding.tvTemMin.setText(" / " + response.getDaily().get(0).getTempMin());
            } else {
                ToastUtils.showShortToast(context, "暂无天气预报数据");
            }
        } else {
            ToastUtils.showShortToast(context, CodeToStringUtils.WeatherCode(response.getCode()));
        }
    }

    /**
     * 逐小时天气预报 V7
     */
    @Override
    public void getHourlyResult(HourlyResponse response) {
        if (response.getCode().equals(Constant.SUCCESS_CODE)) {
            List<HourlyResponse.HourlyBean> data = response.getHourly();
            if (data != null && data.size() > 0) {
                mList.clear();
                mList.addAll(data);
                mAdapter.notifyDataSetChanged();
                dismissLoadingDialog();
            } else {
                ToastUtils.showShortToast(context, "逐小时天气查询不到");
            }
        } else {
            ToastUtils.showShortToast(context, CodeToStringUtils.WeatherCode(response.getCode()));
        }
    }

    //异常返回
    @Override
    public void getDataFailed() {
        dismissLoadingDialog();
        ToastUtils.showShortToast(context, "请求超时");
    }
}
