package net.hjhjw1991.toolbox.tools;

import net.hjhjw1991.toolbox.exception.AlreadyRegisteredException;
import net.hjhjw1991.toolbox.tools.Tool;

/**
 * Created by HuangJun on 2016/9/26.
 */
public interface ToolGroup {
    void register(Tool tool) throws AlreadyRegisteredException;
    Tool unregister(int index);
    Tool unregister(String tag);
}
