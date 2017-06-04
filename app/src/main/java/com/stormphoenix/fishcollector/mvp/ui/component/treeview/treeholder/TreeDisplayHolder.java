package com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.model.beans.FractureSurface;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.shared.constants.ModelConstant;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Developer on 16-12-26.
 * Wang Cheng is a intelligent Android developer.
 */

public class TreeDisplayHolder extends TreeNode.BaseNodeViewHolder<ITreeView.DataTreeItem> {
    public static final String TAG = "TreeAddDeleteHolder";

    private TextView nodeValue;
    private TextView nodeInfo;
    private PrintView arrowView;
    private Context context;

    public TreeDisplayHolder(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View createNodeView(final TreeNode node, final ITreeView.DataTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.tree_display_node, null, false);

        BaseModel attachedModel = value.getAttachedModel();

        // 节点名字
        nodeValue = (TextView) view.findViewById(R.id.node_value);
        nodeValue.setText(ModelConstantMap.getHolder(value.modelConstant).MODEL_NAME);

        // 节点信息
        nodeInfo = (TextView) view.findViewById(R.id.node_info);
        if (attachedModel instanceof MonitoringSite) {
            nodeInfo.setText(((MonitoringSite) attachedModel).getSite());
        } else if (attachedModel instanceof FractureSurface) {
            nodeInfo.setText(((FractureSurface) attachedModel).getPosition());
        }

        // 图标
        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
        iconView.setIconText(context.getResources().getString(ModelConstantMap.getHolder(ModelConstant.BENTHOS).iconResId));
        // 下拉箭头
        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }
}
