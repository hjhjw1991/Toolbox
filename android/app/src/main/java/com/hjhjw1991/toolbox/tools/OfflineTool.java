package com.hjhjw1991.toolbox.tools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import net.hjhjw1991.toolbox.R;
import com.hjhjw1991.toolbox.ui.ToolFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**offline tools
 * Created by huangjun on 16-9-26.
 */
public class OfflineTool extends ToolFragment {
    private OfflineAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public OfflineTool() {
        layout = R.layout.offline_tool;
        mRegisteredTool = new ArrayList<>();
        mRegisteredToolMap = new HashMap<>();
        mAdapter = new OfflineAdapter(mRegisteredTool);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(layout, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.offline_tool_list);
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("onDestroyView, check leaks");
    }

    public boolean shouldShow() {
        return true;
    }

    private class OfflineAdapter extends RecyclerView.Adapter {
        private class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        private ArrayList<Tool> mData;

        OfflineAdapter(ArrayList<Tool> data) {
            mData = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tool_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Tool t = mData.get(position);
            ImageButton ib = (ImageButton) holder.itemView.findViewById(R.id.icon_button);
            final Class target = t.getTargetActivity();
            ib.setImageBitmap(t.getIcon());
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), target);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }
    }
}
