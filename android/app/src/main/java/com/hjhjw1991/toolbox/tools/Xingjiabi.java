package com.hjhjw1991.toolbox.tools;

import android.graphics.Bitmap;

import com.hjhjw1991.toolbox.ui.XingjiabiActivity;

/**Value Over Price Tool
 * Created by HuangJun on 2016/9/26.
 */

public class Xingjiabi implements Tool {
    private Bitmap mIcon;
    private String mTag = "xingjiabi";
    private static final String mTitle = "性价比";
    public Xingjiabi(){
    }

    public Xingjiabi icon(Bitmap icon) {
        mIcon = icon;
        return this;
    }
    public void tag(String tag) {
        this.mTag = tag;
    }

    public String tag() {
        return mTag;
    }

    public Bitmap icon() {
        return mIcon;
    }

    public String title() {
        return mTitle;
    }

    @Override
    public Class getTargetActivity() {
        return XingjiabiActivity.class;
    }
}
