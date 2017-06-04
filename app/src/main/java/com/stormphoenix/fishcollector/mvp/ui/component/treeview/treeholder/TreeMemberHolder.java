package com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by StormPhoenix on 17-6-2.
 * StormPhoenix is a intelligent Android developer.
 */

public class TreeMemberHolder extends TreeNode.BaseNodeViewHolder<ITreeView.MemberTreeItem> {
    public static final String TAG = TreeMemberHolder.class.getSimpleName();

    public TreeMemberHolder(Context context) {
        super(context);
        this.context = context;
    }

    private TextView textUsername;
    private TextView textUserInfo;
    private Context context;

    @Override
    public View createNodeView(TreeNode node, ITreeView.MemberTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.tree_member_node, null, false);

        textUsername = (TextView) view.findViewById(R.id.text_username);
        textUserInfo = (TextView) view.findViewById(R.id.text_user_info);

        textUsername.setText(value.getUsername());
        return view;
    }
}
