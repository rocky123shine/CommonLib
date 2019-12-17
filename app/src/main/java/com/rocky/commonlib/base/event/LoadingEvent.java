package com.rocky.commonlib.base.event;


/**
 * @author
 * @date 2019/12/6.
 * description：
 */
public class LoadingEvent {
    private boolean loading = false;

    public LoadingEvent(boolean loading) {
        this.loading = loading;
    }

    public boolean isLoading() {
        return loading;
    }
}
