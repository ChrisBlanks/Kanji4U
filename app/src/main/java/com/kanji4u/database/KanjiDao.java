package com.kanji4u.database;

import androidx.room.Query;

import java.util.List;

//Kanji Data Access Object for database
public interface KanjiDao {

    /**
     * Get all kanji from kanji table
     * @return
     */
    @Query("SELECT * FROM kanji")
    List<KanjiEntry> getAll();

}
