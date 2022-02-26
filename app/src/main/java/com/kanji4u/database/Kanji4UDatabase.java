package com.kanji4u.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {KanjiEntry.class},version = 1)
@TypeConverters({DatabaseConverters.class})
public abstract class Kanji4UDatabase extends RoomDatabase {

    public abstract KanjiDao kanjiDao();

    private static Kanji4UDatabase instance;

    private static RoomDatabase.Callback kanji4UDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static synchronized Kanji4UDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                                            Kanji4UDatabase.class,
                                        "Kanji4U-Database").fallbackToDestructiveMigration()
                        .addCallback(kanji4UDatabaseCallback).build();
        }
        return instance;
    }


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        PopulateDbAsyncTask(Kanji4UDatabase instance){
            KanjiDao dao = instance.kanjiDao();
        }

        @Override
        protected Void doInBackground(Void ... voids){
            return null;
        }
    }
}
