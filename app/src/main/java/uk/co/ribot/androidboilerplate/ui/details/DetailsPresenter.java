package uk.co.ribot.androidboilerplate.ui.details;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.Application;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;
import uk.co.ribot.androidboilerplate.ui.main.MainMvpView;

/**
 * Created by adhamenaya on 4/9/2016.
 */
public class DetailsPresenter extends BasePresenter<DetailsMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public DetailsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(DetailsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadApplication(String categoryId,int applicationId) {
        checkViewAttached();
        mSubscription = mDataManager.getApplications(categoryId,applicationId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Application>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Application> applications) {
                       if(applications!=null && applications.size()>0)
                        getMvpView().showDetails(applications.get(0));
                    }
                });
    }
}
