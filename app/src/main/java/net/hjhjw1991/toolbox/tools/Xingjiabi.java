package net.hjhjw1991.toolbox.tools;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import net.hjhjw1991.toolbox.ui.XingjiabiActivity;

import java.lang.ref.SoftReference;

/**Value Over Price Tool
 * Created by HuangJun on 2016/9/26.
 */

public class Xingjiabi implements Tool {
    private Bitmap mIcon;
    private String mTag = "xingjiabi";
    private static final String mTitle = "性价比";
    public Xingjiabi(){
    }

    public Xingjiabi setIcon(Bitmap icon) {
        mIcon = icon;
        return this;
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
    public Bitmap getIcon() {
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
