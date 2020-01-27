package com.hjhjw1991.toolbox.ui;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.hjhjw1991.toolbox.exception.AlreadyRegisteredException;
import com.hjhjw1991.toolbox.tools.Tool;
import com.hjhjw1991.toolbox.tools.ToolGroup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huangjun on 16-9-26.
 */
public class ToolFragment extends Fragment implements ToolGroup {
    protected int layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean shouldShow(){
        return false;
    }
    public int getLayout(){
        return layout;
    }

    protected ArrayList<Tool> mRegisteredTool;
    protected HashMap<String, Tool> mRegisteredToolMap;

    @Override
    public void register(Tool tool) throws AlreadyRegisteredException {
        if(mRegisteredToolMap.containsKey(tool.tag())){
            throw new AlreadyRegisteredException(tool.tag());
        }
        mRegisteredToolMap.put(tool.tag(), tool);
        mRegisteredTool.add(tool);
    }

    @Override
    public Tool unregister(int index) {
        if (mRegisteredTool != null && index < mRegisteredTool.size()) {
            Tool tool = mRegisteredTool.remove(index);
            mRegisteredToolMap.remove(tool.tag());
            return tool;
        }
        return null;
    }

    @Override
    public Tool unregister(String tag) {
        if (mRegisteredTool != null && mRegisteredToolMap.containsKey(tag)) {
            Tool tool = mRegisteredToolMap.remove(tag);
            mRegisteredTool.remove(tool);
            return tool;
        }
        return null;
    }

    @Override
    public void tag(String tag) {

    }

    @Override
    public Tool icon(Bitmap icon) {
        return null;
    }

    @Override
    public String tag() {
        return null;
    }

    @Override
    public Bitmap icon() {
        return null;
    }

    @Override
    public String title() {
        return null;
    }

    @Override
    public Class getTargetActivity() {
        return null;
    }
}
