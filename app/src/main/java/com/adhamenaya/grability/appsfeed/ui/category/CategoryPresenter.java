package com.adhamenaya.grability.appsfeed.ui.category;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import com.adhamenaya.grability.appsfeed.data.DataManager;
import com.adhamenaya.grability.appsfeed.data.model.Category;
import com.adhamenaya.grability.appsfeed.ui.base.BasePresenter;

/**
 * Created by adhamenaya on 4/8/2016.
 */
public class CategoryPresenter extends BasePresenter<CategoryMvpView> {


    DataManager mDataManager;
    Subscription mSubscription;

    @Inject
    public CategoryPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(CategoryMvpView categoryMvpView) {
        super.attachView(categoryMvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if(mSubscription!=null) mSubscription.unsubscribe();
    }

    public void loadCategories(){

        checkViewAttached();

        mSubscription = mDataManager.getCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Category>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("error",e.getMessage());
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Category> categories) {
                        Log.d("categories", categories.size()+"");

                        if(categories.isEmpty())
                            getMvpView().showCategoriesEmpty();
                        else
                            getMvpView().showCategories(categories);
                    }
                });
    }
}
