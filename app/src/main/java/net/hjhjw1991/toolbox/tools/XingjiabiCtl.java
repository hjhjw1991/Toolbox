package net.hjhjw1991.toolbox.tools;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import net.hjhjw1991.toolbox.ui.XingjiabiActivity;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**Value Over Price Tool
 * Created by HuangJun on 2016/9/26.
 */

public class XingjiabiCtl extends BaseTool {
    private static final String sTitle = "性价比";
    private static final String sTag = "Xingjiabi";
    private List<Good> mGoods;
    private Comparator<Good> mComparator;
    private PriceAscend pa;
    private PriceDescend pd;
    private XingjiabiAscend xa;
    private XingjiabiDescend xd;
    public XingjiabiCtl(){
        mGoods = new ArrayList<>();
        mComparator = xd = new XingjiabiDescend();
        xa = new XingjiabiAscend();
        pd = new PriceDescend();
        pa = new PriceAscend();
    }

    public List<Good> getGoods() {
        return mGoods;
    }

    public String getTag() {
        return sTag;
    }

    @Override
    public String getTitle() {
        return sTitle;
    }

    @Override
    public Class getTargetActivity() {
        return XingjiabiActivity.class;
    }

    public void setPriceAscend(boolean isAscend) {
        if (isAscend) {
            mComparator = pa;
        } else {
            mComparator = pd;
        }
    }

    public void setXingjiabiAscend(boolean isAscend) {
        if (isAscend) {
            mComparator = xa;
        } else {
            mComparator = xd;
        }
    }

    public boolean isEmpty() {
        return mGoods == null || mGoods.size() == 0;
    }

    public void add(Good good) {

    }

    public Good remove(int index) {
        if (!isEmpty() && index >= 0 && index < mGoods.size()) {
            Good deleted = mGoods.remove(index);
            Log.i(sTag, "delete: " + index + " " + deleted.price + " " + deleted.xingjiabi);
            return deleted;
        }
        return null;
    }

    public void performSort(){
        Collections.sort(mGoods, mComparator);
        Log.d(sTag, "performSort");
    }
    /**
     * 商品类
     */
    public static class Good {
        private float xingjiabi;
        private float price;
        private float weight;
        private int amount;
        public String name;

        public static class Builder {
            private float price=0;
            private float weight=0;
            private int amount=0;
            private String name="default";
            public Builder price(float price) {
                this.price = price;
                return this;
            }
            public Builder weight(float weight) {
                this.weight = weight;
                return this;
            }
            public Builder amount(int amount) {
                this.amount = amount;
                return this;
            }
            public Builder name(String name) {
                this.name = name;
                return this;
            }
            public Good build() {
                Good good = new Good(name);
                float xingjiabi;
                if (price > 0 && amount > 0) {
                    xingjiabi = amount / price;
                } else if (price <= 0) {
                    xingjiabi = (Integer.MAX_VALUE >> 4);
                } else {
                    xingjiabi = 0;
                }
                good.setAmount(amount);
                good.setPrice(price);
                good.setXingjiabi(xingjiabi);
                return good;
            }
        }

        void setXingjiabi(float xingjiabi) {
            this.xingjiabi = xingjiabi;
        }

        public float getXingjiabi() {
            return xingjiabi;
        }

        void setPrice(float price) {
            this.price = price;
        }

        public float getPrice() {
            return price;
        }

        void setAmount(int amount) {
            if (amount != this.amount) {
                this.amount = amount;
            }
        }

        Good(String name) {
            this.name = name;
        }
    }

    private class XingjiabiDescend implements Comparator<Good> {
        @Override
        public int compare(Good lhs, Good rhs) {
            return (rhs.xingjiabi - lhs.xingjiabi) > 0 ? 1 : -1;
        }
    }

    private class XingjiabiAscend extends XingjiabiDescend{
        public int compare(Good lhs, Good rhs){
            return -super.compare(lhs, rhs);
        }
    }

    private class PriceAscend implements Comparator<Good> {
        @Override
        public int compare(Good lhs, Good rhs) {
            return (rhs.price - lhs.price) > 0 ? -1 : 1;
        }
    }

    private class PriceDescend extends PriceAscend{
        public int compare(Good lhs, Good rhs){
            return -super.compare(lhs, rhs);
        }
    }
}
