package uk.co.ribot.androidboilerplate.injection.component;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.SyncService;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.remote.RssService;
import uk.co.ribot.androidboilerplate.injection.ApplicationContext;
import uk.co.ribot.androidboilerplate.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    RssService rssService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    Bus eventBus();
    Picasso picasso();

}
