package com.kanji4u.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {KanjiEntry.class},version = 1)
@TypeConverters({DatabaseConverters.class})
public abstract class Kanji4UDatabase extends RoomDatabase {

    public abstract KanjiDao kanjiDao();

}
