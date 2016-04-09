package uk.co.ribot.androidboilerplate.data;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Func1;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.model.Application;
import uk.co.ribot.androidboilerplate.data.model.Category;
import uk.co.ribot.androidboilerplate.data.model.RssFeed;
import uk.co.ribot.androidboilerplate.data.remote.RssService;
import uk.co.ribot.androidboilerplate.util.EventPosterHelper;

@Singleton
public class DataManager {

    private final RssService mRssService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final EventPosterHelper mEventPoster;

    @Inject
    public DataManager(RssService rssService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper, EventPosterHelper eventPosterHelper) {
        mRssService = rssService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
        mEventPoster = eventPosterHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Application> saveApplications() {
        return mRssService.getRssFeed()
                .concatMap(new Func1<RssFeed, Observable<Application>>() {
                    @Override
                    public Observable<Application> call(RssFeed rssFeed) {
                        return mDatabaseHelper.setApplications(rssFeed.feed.getApplications());
                    }
                });
    }

    public Observable<List<Application>> getApplications(String categoryId,int applicationId) {
        return mDatabaseHelper.getApplications(categoryId,applicationId).distinct();
    }

    public Observable<List<Category>> getCategories(){
        return mDatabaseHelper.getCategories();
    }

    /// Helper method to post events from doOnCompleted.
    private Action0 postEventAction(final Object event) {
        return new Action0() {
            @Override
            public void call() {
                mEventPoster.postEventSafely(event);
            }
        };
    }

}
