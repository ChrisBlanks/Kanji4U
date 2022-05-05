package com.kanji4u.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {KanjiEntry.class, JLPTOneKanjiEntry.class,JLPTTwoKanjiEntry.class,
                        JLPTThreeKanjiEntry.class,JLPTFourKanjiEntry.class,MiscellaneousKanjiEntry.class},
        version = 3)
@TypeConverters({DatabaseConverters.class})
public abstract class Kanji4UDatabase extends RoomDatabase {

    public abstract KanjiDao kanjiDao();

    public abstract JLPTOneKanjiDao jlptOneKanjiDao();
    public abstract JLPTTwoKanjiDao jlptTwoKanjiDao();
    public abstract JLPTThreeKanjiDao jlptThreeKanjiDao();
    public abstract JLPTFourKanjiDao jlptFourKanjiDao();
    public abstract MiscellaneousKanjiDao miscellaneousKanjiDao();

    private static String DB_NAME = "Kanji4U-Database";

    private static Kanji4UDatabase instance;

    private static RoomDatabase.Callback kanji4UDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    /**
     * Return singleton instance of kanji database
     * @param context
     * @return
     */
    public static synchronized Kanji4UDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                                            Kanji4UDatabase.class,
                                            Kanji4UDatabase.DB_NAME
                                        ).fallbackToDestructiveMigration()
                        .addCallback(kanji4UDatabaseCallback).build();
        }
        return instance;
    }

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        PopulateDbAsyncTask(Kanji4UDatabase instance){
            KanjiDao dao = instance.kanjiDao();

            JLPTOneKanjiDao jlptOneKanjiDao = instance.jlptOneKanjiDao();
            JLPTTwoKanjiDao jlptTwoKanjiDao = instance.jlptTwoKanjiDao();
            JLPTThreeKanjiDao jlptThreeKanjiDao = instance.jlptThreeKanjiDao();
            JLPTFourKanjiDao jlptFourKanjiDao = instance.jlptFourKanjiDao();
            MiscellaneousKanjiDao miscellaneousKanjiDao = instance.miscellaneousKanjiDao();
        }

        @Override
        protected Void doInBackground(Void ... voids){
            return null;
        }
    }
}
