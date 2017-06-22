package com.lz.eventbus3demo.bean;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/04/08
 *     desc   : 实现多选和单选的Bean基类
 *     version: 1.0
 * </pre>
 */
public class BaseSelectBean {

    //是否被选中
    protected boolean isSelected;


    public boolean isSelected() {
        return isSelected;
    }

    public BaseSelectBean setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }
}
