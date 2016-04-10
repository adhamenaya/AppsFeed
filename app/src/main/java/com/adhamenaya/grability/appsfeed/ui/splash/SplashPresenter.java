package com.adhamenaya.grability.appsfeed.ui.splash;

import javax.inject.Inject;

import com.adhamenaya.grability.appsfeed.ui.base.BasePresenter;

/**
 * Created by adhamenaya on 4/10/2016.
 */
public class SplashPresenter extends BasePresenter<SplashMvpView> {

    @Inject
    public SplashPresenter(){}

    @Override
    public void attachView(SplashMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadSplash(){
        checkViewAttached();
        getMvpView().showSplash();
    }
}
