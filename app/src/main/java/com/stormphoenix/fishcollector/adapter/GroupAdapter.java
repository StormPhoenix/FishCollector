package com.stormphoenix.fishcollector.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.network.model.Group;

import java.util.List;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class GroupAdapter extends BaseRecyclerAdapter<Group> {

    public GroupAdapter(Context context, List<Group> members) {
        super(context, members);
        mContext = context;
        data = members;
    }

    @Override
    protected Animator[] getAnimators(View itemView) {
        return new Animator[0];
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_groups, parent, false);
        GroupHolder holder = new GroupHolder(mContext, view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GroupHolder groupHolder = (GroupHolder) holder;
        groupHolder.bind(data.get(position));
    }

    public static class GroupHolder extends RecyclerView.ViewHolder {
        Context context = null;
        TextView name;

        public GroupHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            name = (TextView) itemView.findViewById(R.id.text_group_name);
        }

        public void bind(Group group) {
            name.setText(group.groupName);
        }
    }
}
