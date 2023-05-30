package com.jc.weather.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.jc.weather.R;
import com.jc.weather.adapter.MoreLifestyleAdapter;
import com.jc.weather.bean.LifestyleResponse;
import com.jc.weather.contract.MoreLifestyleContract;
import com.jc.weather.databinding.ActivityMoreLifestyleBinding;
import com.jc.weather.utils.CodeToStringUtils;
import com.jc.weather.utils.Constant;
import com.jc.weather.utils.StatusBarUtil;
import com.jc.weather.utils.ToastUtils;
import com.jc.library.mvp.MvpVBActivity;
import java.util.List;


import static com.jc.library.utils.RecyclerViewAnimation.runLayoutAnimation;

/**
 * 更多生活指数信息
 *
 * @author jc
 */
public class MoreLifestyleActivity extends MvpVBActivity<ActivityMoreLifestyleBinding, MoreLifestyleContract.MoreLifestylePresenter> implements MoreLifestyleContract.IMoreLifestyleView {

    @Override
    public void initData() {
        //透明状态栏
        StatusBarUtil.transparencyBar(context);
        Back(binding.toolbar);
        showLoadingDialog();
        binding.tvTitle.setText(getIntent().getStringExtra("cityName"));
        //更多生活指数
        mPresent.lifestyle(getIntent().getStringExtra("locationId"));
    }

    @Override
    protected MoreLifestyleContract.MoreLifestylePresenter createPresent() {
        return new MoreLifestyleContract.MoreLifestylePresenter();
    }

    /**
     * 更多生活质量数据返回
     */
    @Override
    public void getMoreLifestyleResult(LifestyleResponse response) {
        dismissLoadingDialog();
        if (response.getCode().equals(Constant.SUCCESS_CODE)) {
            List<LifestyleResponse.DailyBean> data = response.getDaily();
            if (data != null && data.size() > 0) {
                MoreLifestyleAdapter adapter = new MoreLifestyleAdapter(R.layout.item_more_lifestyle_list, data);
                binding.rv.setLayoutManager(new LinearLayoutManager(context));
                binding.rv.setAdapter(adapter);
                runLayoutAnimation(binding.rv);
            } else {
                ToastUtils.showShortToast(context, "生活质量数据为空");
            }
        } else {
            ToastUtils.showShortToast(context, CodeToStringUtils.WeatherCode(response.getCode()));
        }
    }

    /**
     * 其他异常返回
     */
    @Override
    public void getDataFailed() {
        dismissLoadingDialog();
        ToastUtils.showShortToast(context, "更多天气数据获取异常");
    }

}
