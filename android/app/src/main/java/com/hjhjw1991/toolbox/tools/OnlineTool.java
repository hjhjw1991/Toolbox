package com.hjhjw1991.toolbox.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hjhjw1991.settings.SettingManager;
import com.hjhjw1991.settings.SettingsActivity;

import net.hjhjw1991.toolbox.ui.ToolFragment;

/**
 * Created by huangjun on 16-9-26.
 */
public class OnlineTool extends ToolFragment {
    private int key;
    public OnlineTool(){
        layout = R.layout.online_tool;
        key = R.string.pref_work_mode;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layout, container, false);
    }

    public boolean shouldShow(){
        return SettingManager.getInstance(getActivity()).getBoolean(getResources().getString(key), SettingsActivity.DEFAULT_WORK_MODE);
    }
}
