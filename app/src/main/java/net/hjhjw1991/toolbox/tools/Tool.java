package net.hjhjw1991.toolbox.tools;

import android.graphics.drawable.Drawable;

/**
 * Created by HuangJun on 2016/9/26.
 */
public interface Tool {
    public void setTag(String tag);
    public String getTag();
    public Drawable getIconDrawable();
    public String getTitle();
    public Class getTargetActivity();
}
