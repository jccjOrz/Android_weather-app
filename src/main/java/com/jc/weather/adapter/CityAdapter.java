package com.jc.weather.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jc.weather.R;
import com.jc.weather.bean.CityResponse;

import java.util.List;

/**
 * 市列表适配器
 *
 * @author jc
 */
public class CityAdapter extends BaseQuickAdapter<CityResponse.CityBean, BaseViewHolder> {
    public CityAdapter(int layoutResId, @Nullable List<CityResponse.CityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityResponse.CityBean item) {
        //市名称
        helper.setText(R.id.tv_city, item.getName());
    }
}
