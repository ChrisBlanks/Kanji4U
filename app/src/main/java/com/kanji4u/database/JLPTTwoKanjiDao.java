package com.kanji4u.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Kanji Data Access Object for database
@Dao
public interface JLPTTwoKanjiDao {

    /**
     * Get all kanji from kanji table
     * @return ArrayList of KanjiEntry objects
     */
    @Query("SELECT * FROM jlpt_two_kanji_entry")
    LiveData<List<JLPTTwoKanjiEntry>> getAll();

    /**
     * Select all KanjiEntry data with a matching SHIFT JIS code
     * @param jis_code
     * @return
     */
    @Query("SELECT * FROM jlpt_two_kanji_entry WHERE jis_code LIKE :jis_code")
    List<JLPTTwoKanjiEntry> findKanjiEntryByJIS(String jis_code);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(JLPTTwoKanjiEntry ... kanji); //... = variable # of argument

    @Delete
    void deleteKanjiEntry(JLPTTwoKanjiEntry ... kanji);

    @Update
    public void updateKanji(JLPTTwoKanjiEntry ... kanji);

    @Query("DELETE FROM jlpt_two_kanji_entry")
    public void deleteAllEntries();
}
