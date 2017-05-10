package net.hjhjw1991.toolbox.tools;

/**
 * Created by HuangJun on 2016/9/26.
 */
public interface Tool {
    void setTag(String tag);
    String getTag();
    void setTitle(String title);
    String getTitle();
    Class getTargetActivity();
}
