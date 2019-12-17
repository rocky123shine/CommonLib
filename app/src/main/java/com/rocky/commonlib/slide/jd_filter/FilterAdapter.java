package com.rocky.commonlib.slide.jd_filter;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.rocky.commonlib.R;
import com.rocky.commonlib.slide.jd_filter.dao.FilterDao;
import com.rocky.commonlib.slide.jd_filter.dao.OptionDao;
import com.rocky.commonlib.slide.tree.adapter.AbsTreeListAdapter;
import com.rocky.commonlib.slide.tree.dao.TreeListDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2019/11/15.
 * description：
 */
public class FilterAdapter extends AbsTreeListAdapter<FilterDao, OptionDao> {
    private List<TreeListDao<FilterDao, OptionDao>> selectList = new ArrayList<>();

    private List<TreeListDao<FilterDao, OptionDao>> treeList;
    private EditText et_low;
    private EditText et_max;
    private int groupIndex = -1;

    public String getEtLow() {
        if (groupIndex != -1) {
            return treeList.get(groupIndex).getInputMin();
        } else {
            return "";
        }
    }

    public String getEtMax() {
        if (groupIndex != -1) {
            return treeList.get(groupIndex).getInputMax();
        } else {
            return "";
        }
    }

    public void setTreeList(List<TreeListDao<FilterDao, OptionDao>> treeList) {
        this.treeList = new ArrayList<>(treeList);
        notifyDataSetChanged();
    }

    public void clearAll() {
        if (treeList != null) {
            treeList.clear();
            notifyDataSetChanged();
        }
    }

    public FilterAdapter(List<TreeListDao<FilterDao, OptionDao>> treeList) {
        super(treeList);
        this.treeList = treeList;
    }

    @Override
    public int groupLayoutId() {
        return R.layout.item_tree_title_layout;
    }

    @Override
    public int subLayoutId() {
        return R.layout.item_tree_child_layout;
    }

    @Override
    public void onBindGroupHolder(GroupItemViewHolder holder, FilterDao filterDao, final int groupIndex, int position) {
//        holder.setIsRecyclable(false);
        holder.itemView.findViewById(R.id.rl_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggleMoreHide(groupIndex);
            }
        });
        TextView mTvFilter = holder.itemView.findViewById(R.id.mTvFilter);
        mTvFilter.setText(filterDao.mTitle);

        CheckBox mCbDesc = holder.itemView.findViewById(R.id.mCbDesc);
        mCbDesc.setChecked(getmGroupItemStatus().get(groupIndex));
        View rll_input_price = holder.itemView.findViewById(R.id.rll_input_price);

        mCbDesc.setVisibility(treeList.get(groupIndex).isEnableExpand() ? View.VISIBLE : View.GONE);
        et_low = rll_input_price.findViewById(R.id.et_low);
        et_max = rll_input_price.findViewById(R.id.et_height);


        //移除监听
        if (et_low.getTag() instanceof TextWatcher) {
            et_low.removeTextChangedListener((TextWatcher) et_low.getTag());
        }
        if (et_max.getTag() instanceof TextWatcher) {
            et_max.removeTextChangedListener((TextWatcher) et_max.getTag());
        }


        //设置内容
        et_low.setText(treeList.get(groupIndex).getInputMin());
        et_max.setText(treeList.get(groupIndex).getInputMax());
        //添加新的监听


        TextWatcher etLowTW = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                treeList.get(FilterAdapter.this.groupIndex).setInputMin(s.toString());

            }
        };
        TextWatcher etMaxTW = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                treeList.get(FilterAdapter.this.groupIndex).setInputMax(s.toString());

//                Log.d("FilterAdapter", "groupIndex:" + groupIndex);
//                Log.d("FilterAdapter", "position:" + position);
//                notifyItemRangeChanged(position+1,position+treeList.get(FilterAdapter.this.groupIndex).getSubList().size());
//                notifyItemChanged(FilterAdapter.this.groupIndex);


            }
        };
//
        et_low.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("FilterAdapter", "low");
                for (OptionDao dao : treeList.get(FilterAdapter.this.groupIndex).getSubList()) {
                    dao.isCheck = false;
                    treeList.get(FilterAdapter.this.groupIndex).setHasSelectedNum(treeList.get(FilterAdapter.this.groupIndex).getHasSelectedNum() - 1);
                }
                notifyItemRangeChanged(position + 1, position + treeList.get(FilterAdapter.this.groupIndex).getSubList().size());

                return false;
            }
        });
        et_max.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("FilterAdapter", "max");
                for (OptionDao dao : treeList.get(FilterAdapter.this.groupIndex).getSubList()) {
                    dao.isCheck = false;
                    treeList.get(FilterAdapter.this.groupIndex).setHasSelectedNum(treeList.get(FilterAdapter.this.groupIndex).getHasSelectedNum() - 1);
                }
                notifyItemRangeChanged(position + 1, position + treeList.get(FilterAdapter.this.groupIndex).getSubList().size());

                return false;
            }
        });
//


        et_low.addTextChangedListener(etLowTW);
        et_max.addTextChangedListener(etMaxTW);
        //设置tag
        et_low.setTag(etLowTW);
        et_max.setTag(etMaxTW);


        if (treeList.get(groupIndex).isEnableInput()) {//要显示输入框
            this.groupIndex = groupIndex;

            rll_input_price.setVisibility(View.VISIBLE);

        } else {
            rll_input_price.setVisibility(View.GONE);
        }

    }


    @Override
    public void onBindSubHolder(final SubItemViewHolder holder, final OptionDao optionDao, int subIndex, final int groupIndex, int position) {
        if (optionDao == null) return;

        final CheckBox mCbSub = holder.itemView.findViewById(R.id.mCbSub);

        mCbSub.setText(optionDao.mTitle);
        mCbSub.setChecked(optionDao.isCheck);
        mCbSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FilterAdapter", "position:" + position);

                boolean check = mCbSub.isChecked();
                if (treeList.get(groupIndex).getGroupDao().isSingleCheck) {
                    for (OptionDao dao : treeList.get(groupIndex).getSubList()) {
                        dao.isCheck = false;
                        treeList.get(groupIndex).setHasSelectedNum(treeList.get(groupIndex).getHasSelectedNum() - 1);
                    }

                }
                optionDao.isCheck = check;
                if (optionDao.isCheck) {
                    treeList.get(groupIndex).setHasSelectedNum(treeList.get(groupIndex).getHasSelectedNum() + 1);
                } else {
                    treeList.get(groupIndex).setHasSelectedNum(treeList.get(groupIndex).getHasSelectedNum() - 1);
                }
                if (optionDao.isCheck) {
                    if (Integer.valueOf(treeList.get(groupIndex).getMaxChooseNum()) < treeList.get(groupIndex).getHasSelectedNum()) {
                        optionDao.isCheck = false;
                        treeList.get(groupIndex).setHasSelectedNum(treeList.get(groupIndex).getHasSelectedNum() - 1);
                        Snackbar.make(holder.itemView, "该最多可选择" + treeList.get(groupIndex).getMaxChooseNum() + "项", Snackbar.LENGTH_SHORT).show();

                    }
                }
                if (FilterAdapter.this.groupIndex == groupIndex) {
                    treeList.get(FilterAdapter.this.groupIndex).setInputMin("");
                    treeList.get(FilterAdapter.this.groupIndex).setInputMax("");
                }


                notifyDataSetChanged();
            }
        });


    }

    public List<TreeListDao<FilterDao, OptionDao>> getSelectOptionDao() {
        selectList.clear();

        for (TreeListDao<FilterDao, OptionDao> item : treeList) {
            List<OptionDao> list = new ArrayList<>();
            for (OptionDao it : item.getSubList()) {
                if (it.isCheck) {
                    list.add(it);
                }
            }
            if (list.size() > 0) {
                item.getGroupDao().mCheckOption = list;
                selectList.add(item);
            }
        }
        return selectList;
    }

    public TreeListDao<FilterDao, OptionDao> getGroupDao(int groupIndex) {
        if (groupIndex >= treeList.size()) {
            return null;
        }
        return treeList.get(groupIndex);
    }

    @Override
    public void toggleMoreHide(int groupIndex) {
        super.toggleMoreHide(groupIndex);
    }
}
