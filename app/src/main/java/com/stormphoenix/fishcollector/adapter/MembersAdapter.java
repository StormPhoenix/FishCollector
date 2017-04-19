package com.stormphoenix.fishcollector.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.model.beans.Account;

import java.util.List;

/**
 * Created by StormPhoenix on 17-3-17.
 * StormPhoenix is a intelligent Android developer.
 */

public class MembersAdapter extends BaseRecyclerAdapter<Account> {

    public void setListener(OnMemberItemClickListener listener) {
        this.listener = listener;
    }

    public static interface OnMemberItemClickListener {
        public void onMemberItemClick(Account account);
    }

    private OnMemberItemClickListener listener = null;
    private Context context = null;
    private List<Account> members = null;

    public MembersAdapter(Context context, List<Account> members) {
        super(context, members);
        this.context = context;
        this.members = members;
    }

    public void setMembers(List<Account> members) {
        this.members.clear();
        this.members.addAll(members);
    }

    @Override
    public MembersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_members, parent, false);
        MembersHolder holder = new MembersHolder(context, view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MembersHolder viewHolder = (MembersHolder) holder;
        viewHolder.bind(members.get(position));
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMemberItemClick(members.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    @Override
    protected Animator[] getAnimators(View itemView) {
        return new Animator[0];
    }

    public static class MembersHolder extends RecyclerView.ViewHolder {
        Context context = null;
        TextView name;
        TextView isDispatched;

        public MembersHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            name = (TextView) itemView.findViewById(R.id.text_member_name);
            isDispatched = (TextView) itemView.findViewById(R.id.text_is_dispatched);
        }

        public void bind(Account account) {
            name.setText(account.getUsername());
            if (account.isDespatched()) {
                isDispatched.setText(context.getString(R.string.is_dispatched));
            } else {
                isDispatched.setText(context.getString(R.string.no_dispatched));
            }
        }
    }
}
