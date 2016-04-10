package com.adhamenaya.grability.appsfeed.ui.main;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import com.adhamenaya.grability.appsfeed.data.DataManager;
import com.adhamenaya.grability.appsfeed.data.model.Application;
import com.adhamenaya.grability.appsfeed.ui.base.BasePresenter;

public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadApplications(String categoryId) {
        checkViewAttached();
        mSubscription = mDataManager.getApplications(categoryId,-1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Application>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the applications.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Application> applications) {
                        if (applications.isEmpty()) {
                            getMvpView().showApplicationsEmpty();
                        } else {
                            getMvpView().showApplications(applications);
                        }
                    }
                });
    }
}
