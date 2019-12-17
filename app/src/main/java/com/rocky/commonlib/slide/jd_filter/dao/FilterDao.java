package com.rocky.commonlib.slide.jd_filter.dao;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2019/11/15.
 * description：
 */
public class FilterDao {

    public int _ID = 0;
    public String mTitle = "";
    public String mDesc;
    public List<OptionDao> mCheckOption = new ArrayList<>();
    public boolean isSingleCheck;


    public FilterDao(int _ID, String mTitle, String mDesc, List<OptionDao> mCheckOption, boolean isSingleCheck) {
        this._ID = _ID;
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        if (mCheckOption != null) {
            this.mCheckOption = mCheckOption;
        }
        this.isSingleCheck = isSingleCheck;
    }

    public int hasChangedNum() {
        if (mCheckOption == null) {
            return 0;
        }
        int i = 0;
        for (OptionDao optionDao : mCheckOption) {
            if (optionDao.isCheck) {
                i++;
            }
        }
        Log.d("FilterDao", "i:" + i);
        return i;
    }
}
