package net.hjhjw1991.toolbox.tools;

/**base tool
 * Created by huangjun on 17-5-8.
 */

abstract class BaseTool implements Tool {

    private String mTag;
    private String mTitle;

    @Override
    public void setTag(String tag) {
        mTag = tag;
    }

    @Override
    public String getTag() {
        return mTag;
    }

    @Override
    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public abstract Class getTargetActivity();
}
