package uk.co.ribot.androidboilerplate.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Collection;
import java.util.List;
import java.util.Timer;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.data.model.AppCategory;
import uk.co.ribot.androidboilerplate.data.model.Application;
import uk.co.ribot.androidboilerplate.data.model.Category;
import uk.co.ribot.androidboilerplate.data.model.RssFeed;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper);
    }

    public BriteDatabase getGrabilityDb() {
        return mDb;
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    public Observable<Void> clearTables() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    Cursor cursor = mDb.query("SELECT name FROM sqlite_master WHERE type='table'");
                    while (cursor.moveToNext()) {
                        mDb.delete(cursor.getString(cursor.getColumnIndex("name")), null);
                    }
                    cursor.close();
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<Application> setApplications(final Collection<Application> newApplications) {
        return Observable.create(new Observable.OnSubscribe<Application>() {
            @Override
            public void call(Subscriber<? super Application> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Db.ApplicationTable.TABLE_NAME, null);
                    for (Application application: newApplications) {
                        long result = mDb.insert(Db.ApplicationTable.TABLE_NAME,
                                Db.ApplicationTable.toContentValues(application), SQLiteDatabase.CONFLICT_REPLACE);

                        if (result >= 0){
                            //Save the category in database
                            setCategories(application.category, application);

                            subscriber.onNext(application);
                        }
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Application>> getApplications(String categoryId) {

        return mDb.createQuery(Db.ApplicationTable.TABLE_NAME,
                "SELECT * FROM " + Db.ApplicationTable.TABLE_NAME +
                        " WHERE " + Db.ApplicationTable.COLUMN_ID + " in" +
                        " (SELECT " + Db.CategoryTable.COLUMN_CATE_APP_ID + " FROM " + Db.CategoryTable.TABLE_CATE_APP +
                        "  WHERE " + Db.CategoryTable.COLUMN_CATE_CATE_ID + " = ? )", new String[]{categoryId})
                .mapToList(new Func1<Cursor, Application>() {
                    @Override
                    public Application call(Cursor cursor) {
                        return Db.ApplicationTable.parseCursor(cursor);
                    }
                });
    }

    public void setCategories(final Category newCategory, final Application application){
       // Check the category is already inserted or not!
                Cursor cursor = mDb.query("SELECT * FROM "+Db.CategoryTable.TABLE_NAME + " WHERE "+Db.CategoryTable.COLUMN_CATEGORY_ID + " = ?",
                        new String[] {String.valueOf(newCategory.attributes.id)});
                Log.d("** cate",newCategory.attributes.label);

                // Check the result of selection
                if(cursor == null || cursor.getCount() == 0){
                    // Insert the category
                   long result =  mDb.insert(Db.CategoryTable.TABLE_NAME,Db.CategoryTable.toContentValues(newCategory));
                }

                // Insert the mapping between the ap and category
        // Try to insert the categories of the application
        AppCategory appCategory = new AppCategory();
        appCategory.mAppId = Long.parseLong(application.id.idAttributes.id);
        appCategory.mCategoryId = Long.parseLong(newCategory.attributes.id);

        mDb.insert(Db.CategoryTable.TABLE_CATE_APP, Db.CategoryTable.toContentValues(appCategory));


    }

    public Observable<List<Category>> getCategories(){
        return mDb.createQuery(Db.CategoryTable.TABLE_NAME,
                "SELECT * FROM "+ Db.CategoryTable.TABLE_NAME).mapToList(new Func1<Cursor, Category>() {
            @Override
            public Category call(Cursor cursor) {
                return Db.CategoryTable.parseCursor0(cursor);
            }
        });
    }
}
