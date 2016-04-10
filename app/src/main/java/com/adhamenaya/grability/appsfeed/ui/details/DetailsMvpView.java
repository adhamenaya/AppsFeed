package com.adhamenaya.grability.appsfeed.ui.details;

import com.adhamenaya.grability.appsfeed.data.model.Application;
import com.adhamenaya.grability.appsfeed.ui.base.MvpView;

/**
 * Created by adhamenaya on 4/9/2016.
 */
public interface DetailsMvpView extends MvpView {

    public void showDetails(Application application);
}
