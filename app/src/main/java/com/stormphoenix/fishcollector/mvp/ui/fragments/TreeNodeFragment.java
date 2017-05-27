package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.impls.TreeTaskViewImpl;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.unnamed.b.atv.model.TreeNode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-3-19.
 * StormPhoenix is a intelligent Android developer.
 */

public class TreeNodeFragment extends Fragment {
    @BindView(R.id.tree_node_wrapper)
    FrameLayout treeNodeWrapper;

    ITreeView treeView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tree_node, container, false);
        ButterKnife.bind(this, view);
        loadTreeInfo(container);
        return view;
    }

    private void loadTreeInfo(final ViewGroup container) {

        treeView = new TreeTaskViewImpl(getActivity());
        treeView.setNodeClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.create().show();
            }
        });
//        TreeChooseHolder.ItemChosenListener listener = new TreeChooseHolder.ItemChosenListener() {
//            @Override
//            public void onItemChosen(BaseModel baseModel) {
//                // 显示联系人列表
//                if (MembersConstants.accounts == null) {
//                    Snackbar.make(treeNodeWrapper, getString(R.string.members_message_is_not_get), Snackbar.LENGTH_LONG).show();
//                } else {
//                    MembersAdapter adapter = new MembersAdapter(getActivity(), new ArrayList<Account>());
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setCancelable(true);
//                    builder.setTitle(getString(R.string.chose_member));
//                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_members_list, container, false);
//                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.members_dialog);
//                    adapter.setData(MembersConstants.accounts);
//                    recyclerView.setAdapter(adapter);
//                    builder.setView(view);
//                    builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    builder.create().show();
//                }
//            }
//
//            @Override
//            public void onItemAddBtnClicked(TreeNode node, String key, String value) {
//            }
//
//            @Override
//            public void onItemDeleteBtnClicked(TreeNode node) {
//            }
//        };
//        treeView.setItemOprationListener(listener);
//        treeView.setItemOprationListener(listener);
//        treeView.setNodeClickListener(nodeClickListener);
        treeView.buildTree();
        treeNodeWrapper.addView(treeView.getView());
    }
}
