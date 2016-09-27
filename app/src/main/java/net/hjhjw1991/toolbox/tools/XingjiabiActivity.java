package net.hjhjw1991.toolbox.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

/**
 * Created by HuangJun on 2016/9/26.
 */
public class XingjiabiActivity extends Activity {
    private ArrayList<Good> mGoods;
    private ListView mGoodList;
    private GoodAdapter mAdapter;
    private Comparator<Good> mComparator;
    private TextView mTopbarPrice;
    private TextView mTopbarXingjiabi;
    private PriceAscend pa;
    private PriceDescend pd;
    private boolean isPriceAscend = false;
    private XingjiabiAscend xa;
    private XingjiabiDescend xd;
    private boolean isXingjiabiDescend = false;
    private EditText mGoodNameView;
    private EditText mGoodPriceView;
    private EditText mGoodAmountView;
    private TextView mGoodAddHint;

    public ArrayList<Good> getGoods() {
        return mGoods;
    }

    public XingjiabiActivity() {
        super();
        mGoods = new ArrayList<>();
        mComparator = xd = new XingjiabiDescend();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xingjiabi);
        initViews();
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
                    good.setPrice(Float.valueOf(mGoodPriceView.getText().toString()))
                            .setAmount(Integer.valueOf(mGoodAmountView.getText().toString()));
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
        onDataSetChanged();
    }

    public void remove(int index) {
        mGoods.remove(index);
        onDataSetChanged();
    }

    private void onDataSetChanged(){
        if(mGoods.isEmpty() && !mGoodAddHint.isShown()){
            mGoodAddHint.setVisibility(View.VISIBLE);
        }else if(!mGoods.isEmpty() && mGoodAddHint.isShown()){
            mGoodAddHint.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void onDataOrderChanged(){//todo why not working?
        Collections.sort(mGoods, mComparator);
        Log.d("sss", "price ascend changed " + isPriceAscend);
        for(Good good: mGoods){
            Log.d("sss", good.price+"");
        }
        for(int i=0;i<mAdapter.getCount();i++){
            Good good = (Good) mAdapter.getItem(i);
            Log.d("sss", good.price+"");
        }
        mAdapter.notifyDataSetChanged();
    }

    public void priceAscend(boolean ascend){
        if(pa == null){
            pa = new PriceAscend();
        }
        if(pd == null){
            pd = new PriceDescend();
        }
        if(ascend){
            mComparator = pa;
        }else{
            mComparator = pd;
        }
        onDataOrderChanged();
    }

    public void xingjiabiDescend(boolean descend){
        if(xa == null){
            xa = new XingjiabiAscend();
        }
        if(xd == null){
            xd = new XingjiabiDescend();
        }
        if(descend){
            mComparator = xd;
        }else{
            mComparator = xa;
        }
        onDataOrderChanged();
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
            onDataChanged();
            return this;
        }

        public Good setAmount(int amount) {
            if (amount != this.amount) {
                this.amount = amount;
                onDataChanged();
            }
            return this;
        }

        public Good(String name) {
            this.name = name;
        }

        private void onDataChanged() {
            if (price > 0 && amount > 0) {
                xingjiabi = amount / price;
            } else if (price <= 0) {
                xingjiabi = (Integer.MAX_VALUE >> 4);
            } else {
                xingjiabi = 0;
            }
        }
    }

    private class XingjiabiDescend implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            if (lhs instanceof Good && rhs instanceof Good) {
                Good l = (Good) lhs;
                Good r = (Good) rhs;
                return (r.xingjiabi - l.xingjiabi) > 0 ? 1 : -1;
            }
            return 1;
        }
    }

    private class XingjiabiAscend extends XingjiabiDescend{
        public int compare(Object lhs, Object rhs){
            return -super.compare(lhs, rhs);
        }
    }

    private class PriceAscend implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            if (lhs instanceof Good && rhs instanceof Good) {
                Good l = (Good) lhs;
                Good r = (Good) rhs;
                return (r.price - l.price) > 0 ? -1 : 1;
            }
            return 1;
        }
    }

    private class PriceDescend extends PriceAscend{
        public int compare(Object lhs, Object rhs){
            return -super.compare(lhs, rhs);
        }
    }

    private class GoodAdapter extends BaseAdapter {
        private ArrayList<Good> items = mGoods;

        public GoodAdapter(ArrayList data) {
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
                holder.item = items.get(i);
                holder.v.setTag(holder);
                holder.v.setOnTouchListener(new View.OnTouchListener() {
                    private int mLastX;
                    private LinearLayout view;

                    public boolean onTouch(View v, MotionEvent event) {
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
                    }
                });
            }
        }
    }
}
