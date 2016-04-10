package com.adhamenaya.grability.appsfeed.ui.main;

import java.util.List;

import com.adhamenaya.grability.appsfeed.data.model.Application;
import com.adhamenaya.grability.appsfeed.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showApplications(List<Application> applications);

    void showApplicationsEmpty();

    void showError();

}
