package com.stormphoenix.fishcollector.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.network.model.Acc;

import java.util.List;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class GroupAdapter extends BaseRecyclerAdapter<Acc> {

    public GroupAdapter(Context context, List<Acc> members) {
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
        AccountHolder holder = new AccountHolder(mContext, view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AccountHolder accountHolder = (AccountHolder) holder;
        accountHolder.bind(data.get(position), position);
    }

    public static class AccountHolder extends RecyclerView.ViewHolder {
        Context context = null;
        TextView name;
        TextView memberPos;

        public AccountHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            name = (TextView) itemView.findViewById(R.id.text_username);
            memberPos = (TextView) itemView.findViewById(R.id.text_user_position);
        }

        public void bind(Acc account, int pos) {
            name.setText(account.name);
            if (pos == 0) {
                memberPos.setText("组长");
                memberPos.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                memberPos.setText("组员");
                memberPos.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        }
    }
}
