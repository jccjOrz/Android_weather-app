package com.jc.weather.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jc.weather.R;
import com.jc.library.bean.ResidentCity;

import java.util.List;

/**
 * 常用城市列表适配器
 *
 * @author jc
 */
public class CommonlyCityAdapter extends BaseQuickAdapter<ResidentCity, BaseViewHolder> {
    public CommonlyCityAdapter(int layoutResId, @Nullable List<ResidentCity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResidentCity item) {
        helper.setText(R.id.tv_city_name, item.getLocation());
    }
}
