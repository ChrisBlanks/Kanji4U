package com.kanji4u.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {KanjiEntry.class},version = 1)
public abstract class Kanji4UDatabase extends RoomDatabase {

    public abstract KanjiDao kanjiDao();

}
