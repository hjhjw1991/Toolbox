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
import net.hjhjw1991.toolbox.tools.XingjiabiCtl;
import net.hjhjw1991.toolbox.tools.XingjiabiCtl.Good;

import java.util.List;

/**Value Over Price UI
 * Created by HuangJun on 2016/9/26.
 */
public class XingjiabiActivity extends Activity {
    private static final String TAG = XingjiabiActivity.class.getSimpleName();
    private XingjiabiCtl mXingjiabiCtl;
    private boolean isPriceAscend = false;
    private boolean isXingjiabiDescend = false;

    private ListView mGoodList; // todo refactor to recyclerview
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

    public XingjiabiActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xingjiabi);
        mXingjiabiCtl = new XingjiabiCtl();
        initViews();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case MSG_UPDATE_ITEM:
                        onDataSetChanged();
                        updateOrder();
                        break;
                    case MSG_UPDATE_ITEM_ORDER:
                        updateOrder();
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
        mAdapter = new GoodAdapter(mXingjiabiCtl.getGoods());
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
                    Good good = new Good.Builder().name(mGoodNameView.getText().toString())
                            .price(Float.parseFloat(mGoodPriceView.getText().toString()))
                            .amount(Integer.parseInt(mGoodAmountView.getText().toString()))
                            .build();
                    add(good);
                    // we don't clear name because we often compare goods after the same name
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
        mXingjiabiCtl.add(good);
        mHandler.sendEmptyMessage(MSG_UPDATE_ITEM);
    }

    public void remove(int index) {
        mXingjiabiCtl.remove(index);
        mHandler.sendEmptyMessage(MSG_UPDATE_ITEM);
    }

    private void onDataSetChanged(){
        if(mXingjiabiCtl.isEmpty() && !mGoodAddHint.isShown()){
            mGoodAddHint.setVisibility(View.VISIBLE);
        }else if(!mXingjiabiCtl.isEmpty() && mGoodAddHint.isShown()){
            mGoodAddHint.setVisibility(View.GONE);
        }
    }

    private void updateOrder(){
        mXingjiabiCtl.performSort();
        mAdapter.notifyDataSetChanged();
    }

    public void priceAscend(boolean ascend){
        mXingjiabiCtl.setPriceAscend(ascend);
        mHandler.sendEmptyMessage(MSG_UPDATE_ITEM_ORDER);
    }

    public void xingjiabiDescend(boolean descend){
        mXingjiabiCtl.setXingjiabiAscend(!descend);
        mHandler.sendEmptyMessage(MSG_UPDATE_ITEM_ORDER);
    }

    private class GoodAdapter extends BaseAdapter {
        private List<Good> items;

        GoodAdapter(List<Good> data) {
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
            void mapItemToView(final int i){
                name = (TextView) v.findViewById(R.id.good_name);
                name.setText(item.name);
                index = (TextView) v.findViewById(R.id.good_index);
                index.setText(String.valueOf(i));
                price = (TextView) v.findViewById(R.id.good_price);
                price.setText(String.valueOf(item.getPrice()));
                xingjiabi = (TextView) v.findViewById(R.id.good_xingjiabi);
                xingjiabi.setText(String.valueOf(item.getXingjiabi()));
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
