package com.hjhjw1991.toolbox.exception;

/**
 * Created by HuangJun on 2016/9/26.
 */
public class AlreadyRegisteredException extends ToolboxException {
    private String msg;
    public AlreadyRegisteredException(String tag) {
        msg = "Tool having tag " + tag + " was already registered";
    }

    public String toString(){
        return super.toString() + msg;
    }

    public int hashCode(){
        return AlreadyRegisteredException.class.hashCode() + msg.hashCode();
    }
}
