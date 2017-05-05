package net.hjhjw1991.toolbox.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import net.hjhjw1991.toolbox.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**Value Over Price UI
 * Created by HuangJun on 2016/9/26.
 */
public class XingjiabiActivity extends Activity {
    private static final String TAG = XingjiabiActivity.class.getSimpleName();
    private ArrayList<Good> mGoods;
    private Comparator<Good> mComparator;
    private PriceAscend pa;
    private PriceDescend pd;
    private boolean isPriceAscend = false;
    private XingjiabiAscend xa;
    private XingjiabiDescend xd;
    private boolean isXingjiabiDescend = false;

    private ListView mGoodList;
    private GoodAdapter mAdapter;
    private TextView mTopbarPrice;
    private TextView mTopbarXingjiabi;
    private EditText mGoodNameView;
    private EditText mGoodPriceView;
    private EditText mGoodAmountView;
    private TextView mGoodAddHint;

    private Handler mHandler;
    private static final int MSG_UPDATE_ITEM = 1;
    private static final int MSG_UPDATE_ITEM_ORDER = 2;

    public ArrayList<Good> getGoods() {
        return mGoods;
    }

    public XingjiabiActivity() {
        super();
        mGoods = new ArrayList<>();
        mComparator = xd = new XingjiabiDescend();
        xa = new XingjiabiAscend();
        pd = new PriceDescend();
        pa = new PriceAscend();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xingjiabi);
        initViews();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case MSG_UPDATE_ITEM:
                        onDataSetChanged();
                        performSort();
                        break;
                    case MSG_UPDATE_ITEM_ORDER:
                        performSort();
                        break;
                    default:
                        Log.i(TAG, "unhandled message " + msg.what);
                }
            }
        };
    }

    private void initViews(){
        mTopbarPrice = (TextView) findViewById(R.id.good_topbar_price);
        mTopbarPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPriceAscend = !isPriceAscend;
                priceAscend(isPriceAscend);
            }
        });
        mTopbarXingjiabi = (TextView) findViewById(R.id.good_topbar_xingjiabi);
        mTopbarXingjiabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isXingjiabiDescend = !isXingjiabiDescend;
                xingjiabiDescend(isXingjiabiDescend);
            }
        });

        mGoodList = (ListView) findViewById(R.id.good_list);
        mAdapter = new GoodAdapter(mGoods);
        mGoodList.setAdapter(mAdapter);

        mGoodAddHint = (TextView) findViewById(R.id.good_add_hint);
        mGoodAddHint.setVisibility(View.VISIBLE);

        Button mSendButton = (Button) findViewById(R.id.good_add_add);
        mGoodNameView = (EditText) findViewById(R.id.good_add_name);
        mGoodPriceView = (EditText) findViewById(R.id.good_add_price);
        mGoodAmountView = (EditText) findViewById(R.id.good_add_amount);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TextUtils.isEmpty(mGoodNameView.getText())
                        || TextUtils.isEmpty(mGoodPriceView.getText())
                        || TextUtils.isEmpty(mGoodAmountView.getText())))
                {
                    Good good = new Good(mGoodNameView.getText().toString());
                    good.setPrice(Float.parseFloat(mGoodPriceView.getText().toString()))
                            .setAmount(Integer.parseInt(mGoodAmountView.getText().toString()))
                            .commit();
                    add(good);
                    mGoodPriceView.setText("");
                    mGoodAmountView.setText("");
                }
            }
        });

        mGoodNameView.setText("挂面");
        mGoodPriceView.setText("100");
        mGoodAmountView.setText("250");
    }

    public void add(Good good) {
        mGoods.add(good);
        mHandler.sendEmptyMessage(MSG_UPDATE_ITEM);
    }

    public void remove(int index) {
        Good deleted = mGoods.remove(index);
        Log.i(TAG, "delete: " + index + " " + deleted.price + " " + deleted.xingjiabi);
        mHandler.sendEmptyMessage(MSG_UPDATE_ITEM);
    }

    private void onDataSetChanged(){
        if(mGoods.isEmpty() && !mGoodAddHint.isShown()){
            mGoodAddHint.setVisibility(View.VISIBLE);
        }else if(!mGoods.isEmpty() && mGoodAddHint.isShown()){
            mGoodAddHint.setVisibility(View.GONE);
        }
    }

    private void performSort(){
        Collections.sort(mGoods, mComparator);
        Log.d(TAG, "performSort");
        mAdapter.notifyDataSetChanged();
    }

    public void priceAscend(boolean ascend){
        if(ascend){
            mComparator = pa;
        }else{
            mComparator = pd;
        }
        mHandler.sendEmptyMessage(MSG_UPDATE_ITEM_ORDER);
    }

    public void xingjiabiDescend(boolean descend){
        if(descend){
            mComparator = xd;
        }else{
            mComparator = xa;
        }
        mHandler.sendEmptyMessage(MSG_UPDATE_ITEM_ORDER);
    }


    /**
     * 商品类
     */
    public class Good {
        public float xingjiabi;
        private float price;
        public float weight;
        private int amount;
        public String name;

        public Good setPrice(float price) {
            this.price = price;
            return this;
        }

        public Good setAmount(int amount) {
            if (amount != this.amount) {
                this.amount = amount;
            }
            return this;
        }

        public Good(String name) {
            this.name = name;
        }

        public Good commit() {
            if (price > 0 && amount > 0) {
                xingjiabi = amount / price;
            } else if (price <= 0) {
                xingjiabi = (Integer.MAX_VALUE >> 4);
            } else {
                xingjiabi = 0;
            }
            return this;
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

    private class GoodAdapter extends BaseAdapter {
        private ArrayList<Good> items;

        GoodAdapter(ArrayList<Good> data) {
            this.items = data;
        }

        @Override
        public int getCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public Object getItem(int i) {
            return items == null || i>=items.size()? null : items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null || view.getTag() == null) {
                holder = new ViewHolder();
                holder.v = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.good_item, null);
                holder.v.setTag(holder);
                holder.v.setOnTouchListener(new View.OnTouchListener() {
                    private int mLastX;
                    private LinearLayout view;

                    public boolean onTouch(View v, MotionEvent event) {
                        // todo 添加动画: 左滑, 右滑
                        view = (LinearLayout) v;
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                mLastX = x;
                                break;
                            }
                            case MotionEvent.ACTION_UP: {
                                int deltaX = x - mLastX;
                                TextView tv = (TextView) view.findViewById(R.id.good_delete);
                                if (deltaX < -tv.getMeasuredWidth() * 0.8 && !tv.isShown()) {
                                    tv.setVisibility(View.VISIBLE);
                                } else if (deltaX > tv.getMeasuredWidth() * 0.8 && tv.isShown()) {
                                    tv.setVisibility(View.GONE);
                                }
                                break;
                            }
                            default:
                                break;
                        }
                        return true;
                    }
                });
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.item = items.get(i);
            holder.mapItemToView(i);
            return holder.v;
        }

        private class ViewHolder {
            LinearLayout v;
            Good item;
            TextView name;
            TextView index;
            TextView price;
            TextView xingjiabi;
            TextView delete;
            public void mapItemToView(final int i){
                name = (TextView) v.findViewById(R.id.good_name);
                name.setText(item.name);
                index = (TextView) v.findViewById(R.id.good_index);
                index.setText(String.valueOf(i));
                price = (TextView) v.findViewById(R.id.good_price);
                price.setText(String.valueOf(item.price));
                xingjiabi = (TextView) v.findViewById(R.id.good_xingjiabi);
                xingjiabi.setText(String.valueOf(item.xingjiabi));
                delete = (TextView) v.findViewById(R.id.good_delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remove(i);
                        v.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
}
