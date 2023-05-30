package com.jc.weather.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jc.weather.R;
import com.jc.weather.bean.NewSearchCityResponse;

import java.util.List;

/**
 * 搜索城市结果列表适配器  V7
 *
 * @author jc
 */
public class SearchCityAdapter extends BaseQuickAdapter<NewSearchCityResponse.LocationBean, BaseViewHolder> {
    public SearchCityAdapter(int layoutResId, @Nullable List<NewSearchCityResponse.LocationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewSearchCityResponse.LocationBean item) {
        //城市名称
        helper.setText(R.id.tv_city_name, item.getName());
    }
}
