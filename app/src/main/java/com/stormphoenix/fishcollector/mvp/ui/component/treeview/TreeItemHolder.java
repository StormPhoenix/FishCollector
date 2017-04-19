package com.stormphoenix.fishcollector.mvp.ui.component.treeview;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

public class TreeItemHolder extends TreeNode.BaseNodeViewHolder<ITreeView.TreeItem> {
    public static final String TAG = "TreeItemHolder";

    private TextView tvValue;
    private TextView tvInfo;
    private PrintView arrowView;
    private Context context;

    private ItemOperationListener listener = null;

    public TreeItemHolder(Context context) {
        super(context);
        this.context = context;
    }

    public void setItemOperationListener(ItemOperationListener listener) {
        this.listener = listener;
    }

    @Override
    public View createNodeView(final TreeNode node, final ITreeView.TreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);

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
                    listener.onItemAddBtnClicked(node, "model_name", value.modelConstant);
                }
            }
        });

        view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getString(R.string.delete));
                builder.setMessage(context.getString(R.string.sure_to_delete));
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (listener != null) {
                            listener.onItemDeleteBtnClicked(node);
                        }
                        getTreeView().removeNode(node);
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
        });
        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static interface ItemOperationListener {
        void onItemAddBtnClicked(TreeNode node, String key, String value);

        void onItemDeleteBtnClicked(TreeNode node);
    }
}
