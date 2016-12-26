package com.stormphoenix.fishcollector.mvp.presenter.interfaces.base;

/**
 * Created by Developer on 16-12-23.
 */

public interface RequestCallback<T> {
	// called when request is post
    void beforeRequest();

	// called when reponse is return success
    void success(T data);

	// called when error is occurred
    void onError(String errorMsg);
}
