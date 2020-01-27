package com.hjhjw1991.toolbox.tools;

import com.hjhjw1991.toolbox.exception.AlreadyRegisteredException;

/**
 * Created by HuangJun on 2016/9/26.
 */
public interface ToolGroup extends Tool{
    void register(Tool tool) throws AlreadyRegisteredException;
    Tool unregister(int index);
    Tool unregister(String tag);
}
