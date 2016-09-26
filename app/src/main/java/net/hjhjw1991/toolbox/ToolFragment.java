package net.hjhjw1991.toolbox;

import android.app.Fragment;
import android.os.Bundle;
/**
 * Created by huangjun on 16-9-26.
 */
public class ToolFragment extends Fragment {
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
}
