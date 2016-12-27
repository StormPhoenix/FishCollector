package com.stormphoenix.fishcollector.mvp.ui.component.treeview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.shared.constants.ModelConstant;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Developer on 16-12-26.
 * Wang Cheng is a intelligent Android developer.
 */

public class TreeItemHolder extends TreeNode.BaseNodeViewHolder<ITreeView.TreeItem> {
    private TextView tvValue;
    private PrintView arrowView;
    private Context context;

    public TreeItemHolder(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View createNodeView(final TreeNode node, ITreeView.TreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        // 节点名字
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(ModelConstantMap.getHolder(ModelConstant.BENTHOS).MODEL_NAME);

        // 图标
        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
        iconView.setIconText(context.getResources().getString(ModelConstantMap.getHolder(ModelConstant.BENTHOS).iconResId));

        // 下拉箭头
        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);

        view.findViewById(R.id.btn_addFolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreeNode newFolder = new TreeNode(new ITreeView.TreeItem(ModelConstant.BENTHOS));
                getTreeView().addNode(node, newFolder);
            }
        });

        view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTreeView().removeNode(node);
            }
        });

        if (node.getLevel() == 1) {
            view.findViewById(R.id.btn_delete).setVisibility(View.GONE);
        }
        return view;
    }
}
