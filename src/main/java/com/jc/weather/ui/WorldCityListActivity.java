package com.jc.weather.ui;

import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.jc.weather.R;
import com.jc.weather.adapter.WorldCityAdapter;
import com.jc.weather.bean.WorldCityResponse;
import com.jc.weather.contract.WorldCityContract;
import com.jc.weather.databinding.ActivityWorldCityListBinding;
import com.jc.weather.utils.CodeToStringUtils;
import com.jc.weather.utils.Constant;
import com.jc.weather.utils.StatusBarUtil;
import com.jc.weather.utils.ToastUtils;
import com.jc.library.mvp.MvpVBActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * 世界城市列表 每个国家20个Top城市
 *
 * @author jc
 */
public class WorldCityListActivity extends MvpVBActivity<ActivityWorldCityListBinding, WorldCityContract.WorldCityPresenter>
        implements WorldCityContract.IWorldCityView {

    private WorldCityAdapter mCityAdapter;

    List<WorldCityResponse.TopCityListBean> mList = new ArrayList<>();

    @Override
    public void initData() {
        //白色底  状态栏
        StatusBarUtil.setStatusBarColor(context, R.color.white);
        //状态栏 黑色字体
        StatusBarUtil.StatusBarLightMode(context);
        Back(binding.toolbar);

        binding.tvTitle.setText(getIntent().getStringExtra("name"));
        String code = getIntent().getStringExtra("code");
        showLoadingDialog();

        initList(code);
    }

    /**
     * 初始化数据列表
     *
     * @param code 国家/地区 代码
     */
    private void initList(String code) {
        mCityAdapter = new WorldCityAdapter(R.layout.item_city_list, mList);
        binding.rv.setLayoutManager(new LinearLayoutManager(context));
        binding.rv.setAdapter(mCityAdapter);
        mCityAdapter.notifyDataSetChanged();
        mCityAdapter.addChildClickViewIds(R.id.tv_city);//添加点击事件
        mCityAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent intent = new Intent(context, WorldCityWeatherActivity.class);
            intent.putExtra("name", mList.get(position).getName());
            intent.putExtra("locationId", mList.get(position).getId());
            startActivity(intent);
        });
        mPresent.worldCity(code);//请求国家数据
    }

    @Override
    protected WorldCityContract.WorldCityPresenter createPresent() {
        return new WorldCityContract.WorldCityPresenter();
    }

    /**
     * 世界城市返回
     *
     * @param response
     */
    @Override
    public void getWorldCityResult(WorldCityResponse response) {
        dismissLoadingDialog();
        if (response.getCode().equals(Constant.SUCCESS_CODE)) {
            List<WorldCityResponse.TopCityListBean> data = response.getTopCityList();
            if (data != null && data.size() > 0) {
                mList.clear();
                mList.addAll(data);
                mCityAdapter.notifyDataSetChanged();
            } else {
                ToastUtils.showShortToast(context, "没找到城市数据");
            }

        } else {
            ToastUtils.showShortToast(context, CodeToStringUtils.WeatherCode(response.getCode()));
        }
    }

    /**
     * 失败异常返回
     */
    @Override
    public void getDataFailed() {
        dismissLoadingDialog();
        ToastUtils.showShortToast(context, "其他异常");
    }

}
