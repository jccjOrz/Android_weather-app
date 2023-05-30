package com.jc.weather.ui;

import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.jc.weather.R;
import com.jc.weather.bean.WarningResponse;
import com.jc.weather.databinding.ActivityWarnBinding;
import com.jc.weather.utils.DateUtils;
import com.jc.weather.utils.StatusBarUtil;
import com.jc.weather.utils.WeatherUtil;
import com.jc.library.base.vb.BaseVBActivity;
import java.util.List;

import static com.jc.library.utils.RecyclerViewAnimation.runLayoutAnimation;

/**
 * 灾害预警详情信息页面
 *
 * @author jc
 */
public class WarnActivity extends BaseVBActivity<ActivityWarnBinding> {

    @Override
    public void initData() {
        //透明状态栏
        StatusBarUtil.transparencyBar(context);
        Back(binding.toolbar);
        WarningResponse data = new Gson().fromJson(getIntent().getStringExtra("warnBodyString"), WarningResponse.class);
        WarnAdapter mAdapter = new WarnAdapter(R.layout.item_warn_list, data.getWarning());
        binding.rv.setLayoutManager(new LinearLayoutManager(context));
        binding.rv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        runLayoutAnimation(binding.rv);
    }

    /**
     * 内部适配器
     */
    public static class WarnAdapter extends BaseQuickAdapter<WarningResponse.WarningBean, BaseViewHolder> {

        public WarnAdapter(int layoutResId, @Nullable List<WarningResponse.WarningBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, WarningResponse.WarningBean item) {

            TextView tvTime = helper.getView(R.id.tv_time);
            String time = DateUtils.updateTime(item.getPubTime());
            tvTime.setText("预警发布时间：" + WeatherUtil.showTimeInfo(time) + time);

            helper.setText(R.id.tv_city, item.getSender())//地区
                    .setText(R.id.tv_type_name_and_level,
                            item.getTypeName() + item.getLevel() + "预警")//预警类型名称和等级
                    .setText(R.id.tv_content, item.getText());//预警详情内容
        }
    }
}
