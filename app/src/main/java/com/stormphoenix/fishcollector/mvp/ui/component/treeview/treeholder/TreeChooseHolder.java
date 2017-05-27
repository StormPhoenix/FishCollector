package com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCheckBox;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCompoundButton;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.model.beans.FractureSurface;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.shared.constants.ModelConstant;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by StormPhoenix on 17-3-19.
 * StormPhoenix is a intelligent Android developer.
 */

public class TreeChooseHolder extends TreeNode.BaseNodeViewHolder<ITreeView.TreeItem> {
    public static final String TAG = TreeChooseHolder.class.getSimpleName();
    private PrintView arrowView;
    private TextView nodeValue;
    private TextView nodeInfo;
    private SmoothCheckBox checkBox;

    private ItemChosenListener listener = null;

    public void setItemChosenListener(ItemChosenListener listener) {
        this.listener = listener;
    }

    public static interface ItemChosenListener extends TreeAddDeleteHolder.ItemAddDeleteListener {
        void onItemChosen(BaseModel baseModel);
    }

    public TreeChooseHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, ITreeView.TreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.tree_choose_node, null, false);
        // 节点名字
        nodeValue = (TextView) view.findViewById(R.id.node_value);
        nodeValue.setText(ModelConstantMap.getHolder(value.modelConstant).MODEL_NAME);
        // 图标
        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
        // 设置图标
        iconView.setIconText(context.getResources().getString(ModelConstantMap.getHolder(ModelConstant.BENTHOS).iconResId));
        // 下拉箭头
        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
        // 节点信息
        final BaseModel attachedModel = value.getAttachedModel();
        nodeInfo = (TextView) view.findViewById(R.id.node_info);
        if (attachedModel instanceof MonitoringSite) {
            nodeInfo.setText(((MonitoringSite) attachedModel).getSite());
        } else if (attachedModel instanceof FractureSurface) {
            nodeInfo.setText(((FractureSurface) attachedModel).getPosition());
        }

        checkBox = (SmoothCheckBox) view.findViewById(R.id.is_chosen);
        checkBox.setChecked(false);
        checkBox.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean b) {
                if (b && listener != null) {
                    listener.onItemChosen(attachedModel);
                }
            }
        });
        return view;
    }
}
