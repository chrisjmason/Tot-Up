package com.tot_up.chris.tot_up.base;

public class BasePresenter<T extends MvpInterface.View> implements MvpInterface.Presenter<T> {

    private T view;

    public void attachView(T view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public T getView(){
        return view;
    }
}
