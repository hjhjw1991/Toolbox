package net.hjhjw1991.toolbox.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import net.hjhjw1991.settings.SettingsActivity;
import net.hjhjw1991.toolbox.BuildConfig;
import net.hjhjw1991.toolbox.R;
import net.hjhjw1991.toolbox.exception.AlreadyRegisteredException;
import net.hjhjw1991.toolbox.tools.OfflineTool;
import net.hjhjw1991.toolbox.tools.OnlineTool;
import net.hjhjw1991.toolbox.tools.Tool;
import net.hjhjw1991.toolbox.tools.Xingjiabi;

import java.util.HashMap;
import java.util.Map;

/**
 * The Toolbox.
 * I use two fragments to present online/offline tools,
 * each of whom has its own title and body,
 * occupying half of screen vertically.
 */
public class Toolbox extends AppCompatActivity {
    private static final String TAG = "Toolbox";

    private SparseArray<ToolFragment> mFragments;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private static final Map<Class<? extends Tool>, Integer> mOfflineToolMap;
    static {
        mOfflineToolMap = new HashMap<>();
        mOfflineToolMap.put(Xingjiabi.class, R.drawable.balance);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(SettingsActivity.ACTION_OPEN);
                startActivity(intent);
            }
        });
        mFragmentManager = getFragmentManager();
        mFragments = new SparseArray<>();
        initToolViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragments.clear();
        mFragments = null;
        mFragmentTransaction = null;
        mFragmentManager = null;
    }

    private void initToolViews() {
        if(mFragments.size() == 0) {
            ToolFragment tmp;
            tmp = new OfflineTool();
            initOfflineTool(tmp);
            mFragments.put(0, tmp);


            tmp = new OnlineTool();
            initOnlineTool(tmp);
            mFragments.put(1, tmp);

            // add fragments to toolbox area
            for(int k = 0; k < mFragments.size(); k++){
                mFragmentTransaction = mFragmentManager.beginTransaction();
                ToolFragment frag = mFragments.valueAt(k);
                mFragmentTransaction.add(R.id.content_toolbox, frag);
                mFragmentTransaction.commit();
            }
        }
    }

    private void initOfflineTool(ToolFragment tmp) {
        try {
            for(Class<? extends Tool> tool: mOfflineToolMap.keySet()) {
                Bitmap originIcon = BitmapFactory.decodeResource(getResources(), mOfflineToolMap.get(tool));
                Bitmap scaledIcon = Bitmap.createScaledBitmap(originIcon, 200, 200, true);// todo refactor
                tmp.register(tool.newInstance().setIcon(scaledIcon));
            }
        } catch(AlreadyRegisteredException | InstantiationException | IllegalAccessException e) {
            LOGD(e.toString());
        }
    }

    private void initOnlineTool(ToolFragment tmp){
        // todo initiate online tools
        LOGD("init online tools");
    }

    private void invalidateToolViews() {
        for(int k = 0; k < mFragments.size(); k++){
            ToolFragment frag = mFragments.valueAt(k);
            Log.d(TAG, frag.toString());
            if(frag.shouldShow() != frag.isVisible() && frag.getView() != null){
                frag.getView().setVisibility(frag.shouldShow()?View.VISIBLE:View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateToolViews();
    }

    public static void LOGD(Object frag) {
        if(BuildConfig.HJDEBUG){
            Log.d(TAG, frag.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbox, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(SettingsActivity.ACTION_OPEN);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
