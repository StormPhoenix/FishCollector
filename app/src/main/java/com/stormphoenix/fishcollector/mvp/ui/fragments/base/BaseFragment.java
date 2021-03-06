package com.stormphoenix.fishcollector.mvp.ui.fragments.base;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.presenter.impls.SubmitPresenterImpl;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.SubmitPresenter;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.BasePresenter;
import com.stormphoenix.fishcollector.mvp.ui.dialog.ProgressDialogGenerator;
import com.stormphoenix.fishcollector.mvp.view.SubmitSingleModelView;
import com.stormphoenix.fishcollector.shared.JsonParser;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by Developer on 16-12-21.
 * Wang Cheng is a intelligent Android developer.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    public static final int HIDE_PROGRESS_AND_SUCCESS = 0;
    public static final int HIDE_PROGRESS_BUT_FAILED = 1;
    private static final String TAG = "BaseFragment";
    protected T mPresenter;
    protected View mFragmentView;
    protected Subscription mSubscription;
    protected BaseModel attachedBean = null;
    protected ProgressDialogGenerator submitDialogGenerator;
    protected SubmitSingleModelView submitSingleModelView;
    protected SubmitPresenter submitPresenter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HIDE_PROGRESS_AND_SUCCESS:
                    submitSingleModelView.hideProgress();
                    submitSingleModelView.onSubmitSuccess();
                    break;
                case HIDE_PROGRESS_BUT_FAILED:
                    submitSingleModelView.hideProgress();
                    submitSingleModelView.onSubmitError((String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    public void setModel(BaseModel model) {
        this.attachedBean = model;
    }

    public void save() {
        DbManager manager = new DbManager(getActivity());
        if (attachedBean != null) {
            manager.save(attachedBean);
        }
    }

    public void updateData() {
    }

    public void updateModelByJson(String modelJson) {
        Class<? extends BaseModel> modelClass = attachedBean.getClass();
        BaseModel newModel = (BaseModel) JsonParser.getInstance().fromJson(modelJson, modelClass);

        Field[] fields = modelClass.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                if (field.getAnnotation(Expose.class) != null) {
                    try {
                        field.setAccessible(true);
                        field.set(attachedBean, field.get(newModel));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        newModel = null;
        System.gc();
        refreshFragment();
    }

    protected abstract void refreshFragment();

    protected abstract int getLayoutId();

    protected abstract void initVariables();

    protected abstract void initViews(View view);

    @Override
    public void onStart() {
        Log.d(TAG, "onAttach: ");
        /**
         * *********** 提交信息代码 *************
         **/
        submitDialogGenerator = new ProgressDialogGenerator(getActivity());
        submitSingleModelView = new SubmitSingleModelView() {
            @Override
            public void onSubmitSuccess() {
                Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSubmitError(String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showProgress() {
                submitDialogGenerator.title(getResources().getString(R.string.submiting));
                submitDialogGenerator.content(getResources().getString(R.string.please_waiting));
                submitDialogGenerator.circularProgress();
                submitDialogGenerator.cancelable(false);
                submitDialogGenerator.show();
            }

            @Override
            public void hideProgress() {
                submitDialogGenerator.cancel();
            }
        };
        submitPresenter = new SubmitPresenterImpl(mHandler, submitSingleModelView);
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mFragmentView);
            initVariables();
            initViews(mFragmentView);
        }
        return mFragmentView;
    }

    public void uploadModel() {
        if (attachedBean.checkValue()) {
            uploadModel(attachedBean);
        } else {
            Snackbar.make(mFragmentView, "数据不完善，无法提交", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void downloadPhotos(List<String> photoNames) {
    }

    public BaseModel getAttachedBean() {
        return attachedBean;
    }

    protected void uploadModel(BaseModel model) {
        if (model != null) {
            submitPresenter.submitModel(model.getClass().getSimpleName(), model);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: onDestory");
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }

}
