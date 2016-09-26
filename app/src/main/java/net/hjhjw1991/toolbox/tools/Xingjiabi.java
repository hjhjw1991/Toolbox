package net.hjhjw1991.toolbox.tools;

import android.graphics.drawable.Drawable;

/**
 * Created by HuangJun on 2016/9/26.
 */

public class Xingjiabi implements Tool {
    private Drawable mIcon;
    private String mTag = "xingjiabi";
    private static final String mTitle = "性价比";
    public Xingjiabi(Drawable icon){
        mIcon = icon;
    }
    @Override
    public void setTag(String tag) {
        this.mTag = tag;
    }

    @Override
    public String getTag() {
        return mTag;
    }

    @Override
    public Drawable getIconDrawable() {
        return mIcon;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public Class getTargetActivity() {
        return XingjiabiActivity.class;
    }
}
