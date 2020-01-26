package net.hjhjw1991.toolbox.tools;

import android.graphics.Bitmap;

/**
 * Created by HuangJun on 2016/9/26.
 */
public interface Tool {
    void setTag(String tag);
    Tool setIcon(Bitmap icon);
    String getTag();
    Bitmap getIcon();
    String getTitle();
    Class getTargetActivity();
}
