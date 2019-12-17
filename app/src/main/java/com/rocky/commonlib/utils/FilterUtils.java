package com.rocky.commonlib.utils;

import android.text.TextUtils;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.rocky.commonlib.R;
import com.rocky.commonlib.slide.jd_filter.FilterAdapter;
import com.rocky.commonlib.slide.jd_filter.dao.FilterDao;
import com.rocky.commonlib.slide.jd_filter.dao.OptionDao;
import com.rocky.commonlib.slide.tree.ItemStatus;
import com.rocky.commonlib.slide.tree.dao.TreeListDao;
import com.rocky.commonlib.test.SecondBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2019/11/14.
 * description：
 */
public class FilterUtils {
    private static final FilterUtils ourInstance = new FilterUtils();
    private FilterAdapter filerAdapter1;

    private List<SecondBean> secondBeans = new ArrayList<>();

    public static FilterUtils getInstance() {
        return ourInstance;
    }

    private FilterUtils() {
    }

    public void show(DrawerLayout drawerLayout) {


        initDrawerLayout(drawerLayout);
    }


    public void setData(List<SecondBean> list, DrawerLayout drawerLayout) {
        secondBeans = new ArrayList<>(list);

        initDrawerView(drawerLayout);


    }

    private List<TreeListDao<FilterDao, OptionDao>> selectList = new ArrayList<>();


    public String etLow = "";
    public String etMax = "";

    public String getInputNum() {
        if (TextUtils.isEmpty(etLow) && TextUtils.isEmpty(etMax)) {
            return "0";
        }
        if (TextUtils.isEmpty(etLow) && !TextUtils.isEmpty(etMax)) {
            return "0-" + etMax;
        }
        if (!TextUtils.isEmpty(etLow) && !TextUtils.isEmpty(etMax)) {
            if (etLow.compareTo(etMax) > 0) {
                return etMax + "-" + etLow;
            } else {
                return etLow + "-" + etMax;
            }
        }
        return etLow + "-0";
    }

    private void initDrawerView(final DrawerLayout drawerLayout) {
        drawerLayout.findViewById(R.id.iv_back_drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterUtils.this.closeDrawer(drawerLayout);
            }
        });
        drawerLayout.findViewById(R.id.tv_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterUtils.this.clearSelect();
                filerAdapter1.setData(FilterUtils.this.initData());
            }
        });

        drawerLayout.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etLow = filerAdapter1.getEtLow();
                etMax = filerAdapter1.getEtMax();
                if (selectedFilter != null) {
                    selectList = new ArrayList<>(filerAdapter1.getSelectOptionDao());
                    selectedFilter.selected(selectList);
                }

            }
        });
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(drawerLayout.getContext(), 3);

        RecyclerView rv_select1 = drawerLayout.findViewById(R.id.rv_select);
        rv_select1.setLayoutManager(gridLayoutManager1);

        filerAdapter1 = new FilterAdapter(new ArrayList<TreeListDao<FilterDao, OptionDao>>());
        gridLayoutManager1.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (filerAdapter1.getItemViewType(position) == ItemStatus.VIEW_TYPE_SUB_ITEM) {
                    return 1;
                }
                return 3;
            }
        });
        rv_select1.setAdapter(filerAdapter1);

        filerAdapter1.setData(initData());


    }

    private List<TreeListDao<FilterDao, OptionDao>> initData() {
        List<TreeListDao<FilterDao, OptionDao>> list = new ArrayList<>();

        for (int i = 0, size = secondBeans.size(); i < size; i++) {
            TreeListDao<FilterDao, OptionDao> dao = new TreeListDao<FilterDao, OptionDao>();
            SecondBean secondBean = secondBeans.get(i);
            FilterDao filterDao = new FilterDao(i, secondBean.getText(), secondBean.getKey(), null, secondBean.isIs_single());
            List<OptionDao> subs = new ArrayList<>();
            for (int j = 0, size1 = secondBean.getValue().size(); j < size1; j++) {
                OptionDao subListDao = new OptionDao(j, secondBean.getValue().get(j), secondBean.getValue().get(j), false);
                subs.add(subListDao);
            }
            dao.setGroupDao(filterDao);
            dao.setSubList(subs);
            /**
             * text : 性别
             * value : ["女","男","儿童","中性"]
             * key : sex
             * type : 0 没有打开  1 打开  2 带输入 展开
             * num : 0  外面显示几个
             * is_single : false   是否 可多选
             * choose : 4 最多可选择几个
             */
            dao.setEnableExpand(secondBean.getType() != 0);
            dao.setEnableInput(secondBean.getType() == 2);

            dao.setMaxChooseNum(secondBean.getChoose());
            dao.setShowNum(secondBean.getNum() + "");
            dao.setMinCount(secondBean.getNum());

            list.add(dao);

        }

        for (TreeListDao<FilterDao, OptionDao> dao : selectList) {
            //数据反选回去
            for (TreeListDao<FilterDao, OptionDao> item : list) {
                if (dao.getGroupDao().mTitle.equals(item.getGroupDao().mTitle)) {
                    item.getGroupDao().mCheckOption = new ArrayList<>(dao.getGroupDao().mCheckOption);
                    item.setSubList(dao.getSubList());
                    item.setHasSelectedNum(dao.getHasSelectedNum());
                }
            }
        }

        return list;
    }


//    抽屉

    private void initDrawerLayout(DrawerLayout drawerLayout) {
        toggleRightSliding(drawerLayout);
    }


    /**
     * // 通过代码：根据重力方向打开指定抽屉
     * drawerLayout.openDrawer(Gravity.LEFT);
     * // 设置抽屉阴影
     * drawerLayout.setDrawerShadow(R.drawable.ic_launcher, Gravity.LEFT);
     * // 设置抽屉空余处颜色
     * drawerLayout.setScrimColor(Color.BLUE);
     */

    private void toggleRightSliding(DrawerLayout drawerLayout) {//该方法控制右侧边栏的显示和隐藏
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);//关闭抽屉
            isOpne = false;
        } else {
            drawerLayout.openDrawer(GravityCompat.END);//打开抽屉

            try {
                filerAdapter1.setData(initData());
            } catch (Exception e) {

            }

            isOpne = true;
        }
    }

    public boolean isOpne = false;

    public boolean closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);//关闭抽屉
            isOpne = false;
            return true;
        }
        return false;
    }

    public void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.END);
        filerAdapter1.setData(initData());

        isOpne = true;

    }

    private ISelectedFilter selectedFilter;

    public void setSelectedFilter(ISelectedFilter selectedFilter) {
        this.selectedFilter = selectedFilter;
    }

    public void clearSelect() {
        selectList.clear();
    }

    public interface ISelectedFilter {
        void selected(List<TreeListDao<FilterDao, OptionDao>> selectOptionDao);
    }
}
