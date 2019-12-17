package com.rocky.commonlib.slide.tree.dao;

import java.util.List;

/**
 * Created by Kevin on 2019/1/22.
 */
public class TreeListDao<K, V> {

    /**
     * 配置项
     */
    // 是否可展开收起
    private boolean isEnableExpand = true;
    private boolean isEnableInput = false;
    // 默认是否展开状态
    private boolean isExpand = false;
    // 收起状态最少展示子item数量
    private int minCount = 6;

    private String maxChooseNum;
    private String showNum;
    private String inputMin = "";
    private String inputMax = "";
    private int hasSelectedNum =0;

    public void setHasSelectedNum(int hasSelectedNum) {
        if (hasSelectedNum<=0) {
            this.hasSelectedNum = 0;
            return;
        }
        this.hasSelectedNum = hasSelectedNum;
    }

    public int getHasSelectedNum() {
        return hasSelectedNum;
    }

    public void setInputMax(String inputMax) {
        this.inputMax = inputMax;
    }

    public void setInputMin(String inputMin) {
        this.inputMin = inputMin;
    }

    public String getInputMax() {
        return inputMax;
    }

    public String getInputMin() {
        return inputMin;
    }

    public void setMaxChooseNum(String maxChooseNum) {
        this.maxChooseNum = maxChooseNum;
    }

    public void setShowNum(String showNum) {
        this.showNum = showNum;
    }

    public String getMaxChooseNum() {
        return maxChooseNum;
    }

    public String getShowNum() {
        return showNum;
    }

    /**
     * 数据
     */
    // 组
    private K groupDao;
    // 子
    private List<V> subList;

    public TreeListDao() {
    }

    public TreeListDao(K groupDao, List<V> subList) {
        this.groupDao = groupDao;
        this.subList = subList;
    }

    public K getGroupDao() {
        return groupDao;
    }

    public void setGroupDao(K groupDao) {
        this.groupDao = groupDao;
    }

    public List<V> getSubList() {
        return subList;
    }

    public void setSubList(List<V> subList) {
        this.subList = subList;
    }

    public boolean isEnableExpand() {
        return isEnableExpand;
    }

    public void setEnableExpand(boolean enableExpand) {
        isEnableExpand = enableExpand;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public boolean isEnableInput() {
        return isEnableInput;
    }

    public void setEnableInput(boolean enableInput) {
        isEnableInput = enableInput;
    }
}
