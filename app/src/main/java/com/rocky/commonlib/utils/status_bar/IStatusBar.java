package com.rocky.commonlib.utils.status_bar;

import android.view.Window;

interface IStatusBar {
    void setStatusBarColor(Window window, int color, boolean lightStatusBar);
}
