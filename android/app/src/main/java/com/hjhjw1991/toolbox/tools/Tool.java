package com.hjhjw1991.toolbox.tools;

import android.graphics.Bitmap;

/**
 * Created by HuangJun on 2016/9/26.
 */
public interface Tool {
    void tag(String tag);
    Tool icon(Bitmap icon);
    String tag();
    Bitmap icon();
    String title();
    Class getTargetActivity();
}
