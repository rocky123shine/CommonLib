package com.rocky.commonlib;
import android.util.Log;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rocky.commonlib.base.BaseActivity;
import com.rocky.commonlib.base.adapter.RecyclerAdapter;
import com.rocky.commonlib.base.adapter.holder.BaseViewHolder;
import com.rocky.commonlib.base.adapter.util.RecyclerConfig;
import com.rocky.commonlib.net.CommonObserver;
import com.rocky.commonlib.net.NetWork;
import com.rocky.commonlib.net.RequestUtitl;
import com.rocky.commonlib.net.ResultModel;
import com.rocky.commonlib.slide.jd_filter.dao.FilterDao;
import com.rocky.commonlib.slide.jd_filter.dao.OptionDao;
import com.rocky.commonlib.slide.tree.dao.TreeListDao;
import com.rocky.commonlib.test.GoodsListHolder;
import com.rocky.commonlib.test.RockyStoreModel;
import com.rocky.commonlib.test.StoreService;
import com.rocky.commonlib.utils.FilterUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements BaseViewHolder.OnItemClickListener {

    RecyclerView rv_goods;
    DrawerLayout drawer_layout;
    protected RecyclerAdapter mAdapter;
    private RecyclerConfig mRecyclerConfig;
    private Map<String, Object> objectObjectHashMap;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        rv_goods = findViewById(R.id.rv_goods);
        drawer_layout = findViewById(R.id.drawer_layout);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        findViewById(R.id.tv_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterUtils.getInstance().show(drawer_layout);
            }
        });


        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (position == mAdapter.getItemCount()) {
                    return gridLayoutManager.getSpanCount();
                }

                Object object = mAdapter.getItem(position);
                if (object instanceof RockyStoreModel.GoodsListBean) {
                    return 1;
                } else {
                    return 2;
                }

            }
        });
        RecyclerConfig.Builder builder = new RecyclerConfig.Builder();
        builder
                .bind(RockyStoreModel.GoodsListBean.class, GoodsListHolder.class)
                .layoutManager(gridLayoutManager)
                .enableRefresh(false);

        buildConfig(builder.build());
        setupRecyclerView();


        objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("user_id", "1514");
        objectObjectHashMap.put("goods_id", "54910,54882,54862,54848,54836,54823,54819,54817,54815,54795,54793,54783,54776,54767,54757,54756,54734,54731,54710,54709,54702,54695,54687,54686,54684,56001,55998,55964,55962,55944,55894,55847,55846,55843,55753,55699,55356,55277,55271,55146,55100,55086,55073,55055,55042,55039,55038,54919,54359,54339,54337,54318,54314,54313,54312,54288,54269,54262,54253,54247,54246,53477,53438,33630,2531,2524,2169,1813,1811,1753,1752,1739,54685,54683,54682,54681,54679,54656,54654,54649,54647,54619,54611,54610,54556,54543,54541,54536,54522,54519,54515,54513,54441,54413,54408,54391,54378,1743,1749");
        objectObjectHashMap.put("page", "1");
        objectObjectHashMap.put("sort", "add_time");
        objectObjectHashMap.put("pagenum", "10");
        objectObjectHashMap.put("token", "v47d0uumubeh140ic95vgrfd14");


        request(objectObjectHashMap);
        FilterUtils.getInstance().setSelectedFilter(new FilterUtils.ISelectedFilter() {
            @Override
            public void selected(List<TreeListDao<FilterDao, OptionDao>> selectOptionDao) {
                FilterUtils.getInstance().closeDrawer(drawer_layout);

                objectObjectHashMap.clear();
                objectObjectHashMap.put("user_id", "1514");
                objectObjectHashMap.put("goods_id", "54910,54882,54862,54848,54836,54823,54819,54817,54815,54795,54793,54783,54776,54767,54757,54756,54734,54731,54710,54709,54702,54695,54687,54686,54684,56001,55998,55964,55962,55944,55894,55847,55846,55843,55753,55699,55356,55277,55271,55146,55100,55086,55073,55055,55042,55039,55038,54919,54359,54339,54337,54318,54314,54313,54312,54288,54269,54262,54253,54247,54246,53477,53438,33630,2531,2524,2169,1813,1811,1753,1752,1739,54685,54683,54682,54681,54679,54656,54654,54649,54647,54619,54611,54610,54556,54543,54541,54536,54522,54519,54515,54513,54441,54413,54408,54391,54378,1743,1749");
                objectObjectHashMap.put("page", "1");
                objectObjectHashMap.put("sort", "add_time");
                objectObjectHashMap.put("pagenum", "10");
                objectObjectHashMap.put("token", "v47d0uumubeh140ic95vgrfd14");
                for (TreeListDao<FilterDao, OptionDao> daoOptionDaoTreeListDao : selectOptionDao) {
                    String str = "";
                    String key = "";
                    key += daoOptionDaoTreeListDao.getGroupDao().mDesc;


                    for (OptionDao optionDao : daoOptionDaoTreeListDao.getGroupDao().mCheckOption) {
                        str += optionDao.mTitle + ",";
                    }
                    objectObjectHashMap.put(key, str.substring(0, str.length() - 1));
                }

                if (!FilterUtils.getInstance().getInputNum().equals("0")) {
                    objectObjectHashMap.put("price", FilterUtils.getInstance().getInputNum());
                }
                MainActivity.this.request(objectObjectHashMap);
            }
        });

    }

    private void request(Map<String, Object> map) {
        RequestUtitl.getInstance().request(
                NetWork.getInstance().retrofit().create(StoreService.class).rockyStore(map),
                this.<ResultModel<RockyStoreModel>>bindToLifecycle(),
                new CommonObserver<RockyStoreModel>() {
                    @Override
                    public void onNext(RockyStoreModel rockyStoreModel) {
                        super.onNext(rockyStoreModel);
                        Log.d("MainActivityccaaa", rockyStoreModel.getGoods_list().toString());

                        mAdapter.putField("COMMON_MODEL", rockyStoreModel);

                        mAdapter.addAll(rockyStoreModel.getGoods_list());
                        if (!hasFilter) {
                            FilterUtils.getInstance().setData(rockyStoreModel.getSecond(), drawer_layout);
                            hasFilter = true;
                        }

                    }
                }, RockyStoreModel.class);
    }

    private boolean hasFilter = false;

    protected void setupRecyclerView() {
        RecyclerConfig config = getRecyclerConfig();
        RecyclerView.LayoutManager layoutManager = config.getLayoutManager();
        if (layoutManager != null) {
            rv_goods.setLayoutManager(layoutManager);
        } else {
            rv_goods.setLayoutManager(new LinearLayoutManager(this));
        }
        rv_goods.setAdapter(mAdapter);
        RecyclerView.ItemAnimator itemAnimator = config.getItemAnimator();
        if (itemAnimator != null) {
            rv_goods.setItemAnimator(itemAnimator);
        }

        RecyclerView.ItemDecoration decoration = config.getDecoration();
        if (decoration != null) {
            rv_goods.addItemDecoration(decoration);
        }
    }

    protected RecyclerConfig getRecyclerConfig() {
        return mRecyclerConfig;
    }

    protected void buildConfig(RecyclerConfig config) {
        if (config.getViewHolderFactory() != null) {
            mAdapter = new RecyclerAdapter(config.getViewHolderFactory());
        } else {
            mAdapter = new RecyclerAdapter(this);
        }

        mAdapter.setOnItemClickListener(this);

        Map<Class, Class<? extends BaseViewHolder>> boundViewHolder = config.getBoundViewHolder();
        if (!boundViewHolder.isEmpty()) {
            for (Map.Entry<Class, Class<? extends BaseViewHolder>> entry : boundViewHolder
                    .entrySet()) {
                mAdapter.bind(entry.getKey(), entry.getValue());
            }
        }

        mRecyclerConfig = config;
    }

    @Override
    public void onItemClick(int position, View view) {

    }
}
