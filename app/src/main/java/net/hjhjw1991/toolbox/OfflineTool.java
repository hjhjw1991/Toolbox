package net.hjhjw1991.toolbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangjun on 16-9-26.
 */
public class OfflineTool extends ToolFragment {
    public OfflineTool(){
        layout = R.layout.offline_tool;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layout, container, false);
    }

    public boolean shouldShow(){
        return true;
    }
}
