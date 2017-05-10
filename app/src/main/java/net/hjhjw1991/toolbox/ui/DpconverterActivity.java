package net.hjhjw1991.toolbox.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import net.hjhjw1991.toolbox.R;

/**dp sp px converter
 * sp与dp概念完全一样, 只不过是专门用来描述字体大小
 * Created by huangjun on 17-5-8.
 */

public class DpconverterActivity extends Activity {

    EditText mDpView;
    EditText mSpView;
    EditText mPxView;
    EditText mDpiView; // todo should be spanner default this cellphone's dpi
    EditText mFolderNameView; // todo should be spanner

    private static final int MSG_UPDATE = 0;
    Handler mHandler;
    TextWatcher mWatcher;
    int mCurrentFocus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dpconverter);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case MSG_UPDATE:
                        removeMessages(MSG_UPDATE);
                        // todo update all content
                        break;
                    default:
                        Log.i("hhh", "unknown message");
                }
            }
        };
    }

    void initViews() {
        mWatcher = new OnTextChangedListener();
        mDpView = (EditText) findViewById(R.id.dp_value);
        mDpView.addTextChangedListener(mWatcher);
        mSpView = (EditText) findViewById(R.id.dp_value);
        mSpView.addTextChangedListener(mWatcher);
        mPxView = (EditText) findViewById(R.id.dp_value);
        mPxView.addTextChangedListener(mWatcher);

        mDpiView = (EditText) findViewById(R.id.dp_value);
        mFolderNameView = (EditText) findViewById(R.id.dp_value);
    }

    public class OnTextChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mHandler.sendEmptyMessage(MSG_UPDATE);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
