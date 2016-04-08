package uk.co.ribot.androidboilerplate.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import uk.co.ribot.androidboilerplate.data.model.AppCategory;
import uk.co.ribot.androidboilerplate.data.model.Application;
import uk.co.ribot.androidboilerplate.data.model.Application.*;
import uk.co.ribot.androidboilerplate.data.model.Category;

public class Db {

    public Db() { }
    public abstract static class CategoryTable {
        public static final String TABLE_NAME = "grability_category";
        public static final String TABLE_CATE_APP = "grability_cate_app";

        // Table of categories
        public static final String COLUMN_CATEGORY_ID = "id";
        public static final String COLUMN_CATEGORY_NAME = "name";

        // Table of categories apps
        public static final String COLUMN_CATE_APP_ID = "app_id";
        public static final String COLUMN_CATE_CATE_ID = "cate_id";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_CATEGORY_ID + " LONG PRIMARY KEY, " +
                        COLUMN_CATEGORY_NAME + " TEXT NOT NULL" +
                        " ); ";

        public static final String CREATE1 =
                "CREATE TABLE " + TABLE_CATE_APP + " (" +
                        COLUMN_CATE_APP_ID + " LONG NOT NULL, " +
                        COLUMN_CATE_CATE_ID + " LONG NOT NULL" +
                        " ); ";

        public static ContentValues toContentValues(Category category) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORY_ID, category.attributes.id);
            values.put(COLUMN_CATEGORY_NAME, category.attributes.label);
            return values;
        }

        public static ContentValues toContentValues(AppCategory appCategory) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CATE_APP_ID, appCategory.mAppId);
            values.put(COLUMN_CATE_CATE_ID, appCategory.mCategoryId);
            return values;
        }

        public static Category parseCursor0(Cursor cursor) {
            Category category = new Category();
            category.attributes = new Category.CategoryAttribute();
            category.attributes.id= String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)));
            category.attributes.label = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_NAME));
            return category;
        }

        public static AppCategory parseCursor1(Cursor cursor) {
            AppCategory appCategory = new AppCategory();
            appCategory.mAppId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID));
            appCategory.mCategoryId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID));

            return appCategory;
        }

    }

    public abstract static class ApplicationTable {
        public static final String TABLE_NAME = "grability_app";

        public static final String TABLE_IMAGE = "grability_app_image";

        // Table of applications
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_CURRENCY = "currency";

        // Table of application images
        public static final String COLUMN_IMAGE_APP_ID = "id";
        public static final String COLUMN_IMAGE_PATH = "name";
        public static final String COLUMN_IMAGE_HEIGHT = "height";


        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " LONG PRIMARY KEY, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_SUMMARY + " TEXT NOT NULL, " +
                        COLUMN_PRICE + " LONG NOT NULL, " +
                        COLUMN_CURRENCY + " TEXT NOT NULL " +
                " ); ";

        public static final String CREATE4 =
                "CREATE TABLE " + TABLE_IMAGE + " (" +
                        COLUMN_IMAGE_APP_ID + " LONG NOT NULL, " +
                        COLUMN_IMAGE_PATH + " TEXT NOT NULL, " +
                        COLUMN_IMAGE_HEIGHT + " LONG NOT NULL" +
                        " ); ";

        public static ContentValues toContentValues(Application application) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, application.id.idAttributes.id);
            values.put(COLUMN_NAME, application.name.label);
            values.put(COLUMN_SUMMARY, application.summary.label);
            values.put(COLUMN_PRICE, application.price.attributes.amount);
            values.put(COLUMN_CURRENCY, application.price.attributes.currency);

            return values;
        }

        public static Application parseCursor(Cursor cursor) {
            Application application = new Application();
            application.id = new Application.Id();
            application.name = new Application.Name();
            application.summary = new Application.Summary();
            application.price = new Application.Price();

            application.id.idAttributes.id = String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            application.name.label = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            application.summary.label = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUMMARY));
            application.price.attributes.amount = String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
            application.price.attributes.currency = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CURRENCY));

            return application;
        }
    }
}
