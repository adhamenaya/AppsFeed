package uk.co.ribot.androidboilerplate.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import javax.inject.Inject;

import uk.co.ribot.androidboilerplate.injection.ApplicationContext;

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RSS1.db";
    public static final int DATABASE_VERSION = 8;

    @Inject
    public DbOpenHelper(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            //Uncomment line below if you want to enable foreign keys
            //db.execSQL("PRAGMA foreign_keys=ON;");
            db.execSQL(Db.ApplicationTable.CREATE);
            db.execSQL(Db.CategoryTable.CREATE);
            db.execSQL(Db.CategoryTable.CREATE1);
            db.execSQL(Db.ImageTable.CREATE);
            //Add other tables here
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.beginTransaction();
        try{
            db.execSQL("DROP TABLE IF EXISTS "+Db.ApplicationTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+Db.CategoryTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+Db.CategoryTable.TABLE_CATE_APP);
            db.execSQL("DROP TABLE IF EXISTS "+Db.ImageTable.TABLE_NAME);

            // Re-create the tables
            onCreate(db);
        }finally {
            db.endTransaction();
        }
    }

}