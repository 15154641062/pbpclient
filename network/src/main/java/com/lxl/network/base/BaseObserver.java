package com.lxl.network.base;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull BaseResponse<T> tBaseResponse) {
        onSuccess(tBaseResponse);
    }

    @Override
    public void onError(Throwable e) {
        onFailed(e);
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(BaseResponse<T> tBaseResponse);
    protected abstract void onFailed(Throwable e);
}