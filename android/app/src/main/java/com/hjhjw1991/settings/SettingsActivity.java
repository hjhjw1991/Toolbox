package com.hjhjw1991.settings;


import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.hjhjw1991.settings.SettingManager;
import com.hjhjw1991.toolbox.R;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {
    public static final String ACTION_OPEN = "net.hjhjw1991.settings.SettingsActivity.ACTION_OPEN";

    public static final boolean DEFAULT_WORK_MODE = true;
    public static final boolean DEFAULT_CELLULAR_NETWORK = false;
    private CheckBoxPreference mNetwork;
    private CheckBoxPreference mWorkingMode;
    private SettingManager sm;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        sm = SettingManager.getInstance(mContext);
        initPreferenceViews();
    }

    protected void onDestroy(){
        super.onDestroy();
    }

    private void initPreferenceViews(){
        addPreferencesFromResource(R.xml.prefs);
        mWorkingMode = (CheckBoxPreference) findPreference(getResources().getString(R.string.pref_work_mode));
        mWorkingMode.setChecked(sm.getBoolean(mContext.getString(R.string.pref_work_mode), DEFAULT_WORK_MODE));
        mWorkingMode.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (mWorkingMode.isChecked()) {
                    mNetwork.setEnabled(true);
                } else {
                    mNetwork.setEnabled(false);
                    mNetwork.setShouldDisableView(true);
                }
                sm.putBoolean(mContext.getString(R.string.pref_work_mode), mWorkingMode.isChecked());
                return true;
            }
        });
        mNetwork = (CheckBoxPreference) findPreference(getResources().getString(R.string.pref_cellular_network));
        mNetwork.setChecked(sm.getBoolean(mContext.getString(R.string.pref_cellular_network), DEFAULT_CELLULAR_NETWORK));
        mNetwork.setEnabled(mWorkingMode.isChecked());
        mNetwork.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                sm.putBoolean(mContext.getString(R.string.pref_cellular_network), mNetwork.isChecked());
                return true;
            }
        });
    }
}
