package com.rocky.commonlib.slide.jd_filter.dao;

/**
 * @author
 * @date 2019/11/15.
 * description：
 */
public class OptionDao {

    public int _ID = 0;
    public String mTitle="";
    public String  mDesc;
    public boolean isCheck;

    public OptionDao(int _ID, String mTitle, String mDesc, boolean isCheck) {
        this._ID = _ID;
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.isCheck = isCheck;
    }
}
