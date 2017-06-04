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
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeViewListener;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.shared.constants.ModelConstant;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by StormPhoenix on 17-6-2.
 * StormPhoenix is a intelligent Android developer.
 */

public class TreeAddHolder extends TreeNode.BaseNodeViewHolder<ITreeView.DataTreeItem> {
    public static final String TAG = "TreeAddDeleteHolder";

    private TextView tvValue;
    private TextView tvInfo;
    private PrintView arrowView;
    private Context context;

    private TreeAddHolder.ItemAddListener listener = null;

    public TreeAddHolder(Context context) {
        super(context);
        this.context = context;
    }

    public void setItemOperationListener(TreeAddHolder.ItemAddListener listener) {
        this.listener = listener;
    }

    @Override
    public View createNodeView(final TreeNode node, final ITreeView.DataTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.tree_add_node, null, false);

        BaseModel attachedModel = value.getAttachedModel();
        // 节点名字
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(ModelConstantMap.getHolder(value.modelConstant).MODEL_NAME);
        // 节点信息
        tvInfo = (TextView) view.findViewById(R.id.node_info);
        if (attachedModel instanceof MonitoringSite) {
            tvInfo.setText(((MonitoringSite) attachedModel).getSite());
        } else if (attachedModel instanceof FractureSurface) {
            tvInfo.setText(((FractureSurface) attachedModel).getPosition());
        }

        // 图标
        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
//        iconView.setIconText(context.getResources().getString(ModelConstantMap.getHolder(value.modelConstant).iconResId));
        iconView.setIconText(context.getResources().getString(ModelConstantMap.getHolder(ModelConstant.BENTHOS).iconResId));

        // 下拉箭头
        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);

        view.findViewById(R.id.btn_addFolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemAddBtnClicked(node, "model_id", value.modelConstant);
                }
            }
        });
        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static interface ItemAddListener extends TreeViewListener {
        void onItemAddBtnClicked(TreeNode node, String key, String value);
    }
}
