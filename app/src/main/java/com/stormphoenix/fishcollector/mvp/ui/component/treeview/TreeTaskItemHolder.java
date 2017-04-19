package com.stormphoenix.fishcollector.mvp.ui.component.treeview;

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
 * Created by StormPhoenix on 17-3-19.
 * StormPhoenix is a intelligent Android developer.
 */

public class TreeTaskItemHolder extends TreeNode.BaseNodeViewHolder<ITreeView.TreeItem> {
    public static final String TAG = TreeTaskItemHolder.class.getSimpleName();
    private TextView tvValue;
    private TextView isDispatched;

    private OnTaskDispatchedListener listener = null;
    private PrintView arrowView;

    public void setOnTaskDispatchedListener(OnTaskDispatchedListener listener) {
        this.listener = listener;
    }

    public static interface OnTaskDispatchedListener extends TreeItemHolder.ItemOperationListener {
        void onTaskDispatched(BaseModel baseModel);
    }

    public TreeTaskItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, ITreeView.TreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_task_node, null, false);
        // 节点名字
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(ModelConstantMap.getHolder(value.modelConstant).MODEL_NAME);
        // 图标
        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
//        iconView.setIconText(context.getResources().getString(ModelConstantMap.getHolder(value.modelConstant).iconResId));
        iconView.setIconText(context.getResources().getString(ModelConstantMap.getHolder(ModelConstant.BENTHOS).iconResId));
        // 下拉箭头
        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
        // 节点信息
        final BaseModel attachedModel = value.getAttachedModel();
        TextView tvInfo = (TextView) view.findViewById(R.id.node_info);
        if (attachedModel instanceof MonitoringSite) {
            tvInfo.setText(((MonitoringSite) attachedModel).getSite());
        } else if (attachedModel instanceof FractureSurface) {
            tvInfo.setText(((FractureSurface) attachedModel).getPosition());
        }

        isDispatched = (TextView) view.findViewById(R.id.is_dispatched);
        isDispatched.setText(context.getString(R.string.undispatched));

        isDispatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTaskDispatched(attachedModel);
                }
            }
        });
        return view;
    }
}
