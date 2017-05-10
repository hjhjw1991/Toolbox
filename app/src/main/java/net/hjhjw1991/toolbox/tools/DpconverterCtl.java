package net.hjhjw1991.toolbox.tools;

import net.hjhjw1991.toolbox.ui.DpconverterActivity;

/**
 * dp px sp dip converter
 * 用dp描述的文件经过换算后变成在特定dpi屏幕下的px, 这个px乘以该屏幕的像素密度的倒数即 对角线inch/对角线px, 就得到显示出来的inch数.
 * 相同dp数的图片, 在不同设备上(放置了不同px的图片)显示出来的inch数相同.
 * <p>
 * Created by huangjun on 17-5-8.
 */


/*
desc(dpi)           screen_size     pixel_density       minimum dp screen size
ldpi                small           120                 426*320
mdpi                normal          160                 470*320         --baseline
hdpi                large           240                 640*480
xhdpi               xlarge          320                 960*720
xxhdpi              xxlarge         480                 1280*960
xxxhdpi             xxxlarge        640                 1920*1440
 */

public class DpconverterCtl extends BaseTool {

    @Override
    public Class getTargetActivity() {
        return DpconverterActivity.class;
    }

    /**
     * Dp 2 px float.
     *
     * @param dp  the dp
     * @param dpi the dpi
     * @return the float
     */
    public static float Dp2Px(float dp, float dpi) {
        float px = dp * (dpi / 160);
        return px;
    }

    /**
     * Px 2 dp float.
     *
     * @param px  the px
     * @param dpi the dpi
     * @return the float
     */
    public static float Px2Dp(float px, float dpi) {
        if (dpi <= 0) return 0;
        float dp = px * 160 / dpi;
        return dp;
    }

    /**
     * Dpi by px dp float.
     *
     * @param px the px
     * @param dp the dp
     * @return the float
     */
    public static float DpiByPxDp(float px, float dp) {
        if (dp <= 0) return 0;
        float dpi = px * 160 / dp;
        return dpi;
    }
}
