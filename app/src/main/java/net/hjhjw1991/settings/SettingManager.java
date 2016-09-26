package net.hjhjw1991.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by huangjun on 16-9-26.
 */
public class SettingManager {
    private static volatile SettingManager instance;
    private Context mContext;
    private SharedPreferences mPreference;
    private SharedPreferences.Editor mEditor;


    public static SettingManager getInstance(Context ctx){
        if(instance == null){
            synchronized (SettingManager.class){
                if(instance == null){
                    instance = new SettingManager(ctx);
                }
            }
        }
        return instance;
    }

    private SettingManager(Context ctx) {
        mContext = ctx;
        mPreference = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = mPreference.edit();
    }

    public boolean getBoolean(String key, boolean defValue){
        return mPreference.getBoolean(key, defValue);
    }

    public void putBoolean(String key, boolean value){
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }
}
