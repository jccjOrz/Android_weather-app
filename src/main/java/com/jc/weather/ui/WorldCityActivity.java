package com.jc.weather.ui;

import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.jc.weather.R;
import com.jc.weather.adapter.CountryAdapter;
import com.jc.weather.databinding.ActivityWorldCityBinding;
import com.jc.weather.utils.StatusBarUtil;
import com.jc.library.base.vb.BaseVBActivity;
import com.jc.library.bean.Country;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * 世界城市  列举世界上两百多个国家其中包括地区，
 *
 * @author jc
 */
public class WorldCityActivity extends BaseVBActivity<ActivityWorldCityBinding> {

    /**
     * 国家/地区列表适配器
     */
    CountryAdapter mAdapter;
    List<Country> mList = new ArrayList<>();

    @Override
    public void initData() {
        //白色底  状态栏
        StatusBarUtil.setStatusBarColor(context, R.color.white);
        //状态栏 黑色字体
        StatusBarUtil.StatusBarLightMode(context);
        Back(binding.toolbar);
        initList();
    }

    /**
     * 初始化列表数据
     */
    private void initList() {
        mList = LitePal.findAll(Country.class);
        mAdapter = new CountryAdapter(R.layout.item_country_list, mList);
        binding.rv.setLayoutManager(new LinearLayoutManager(context));
        binding.rv.setAdapter(mAdapter);
        mAdapter.addChildClickViewIds(R.id.tv_country_name);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent intent = new Intent(context, WorldCityListActivity.class);
            intent.putExtra("name", mList.get(position).getName());
            intent.putExtra("code", mList.get(position).getCode());
            startActivity(intent);
        });
    }
}
