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
public interface JLPTOneKanjiDao {

    /**
     * Get all JLPT 1 kanji from kanji table
     * @return ArrayList of KanjiEntry objects
     */
    @Query("SELECT * FROM jlpt_one_kanji_entry")
    LiveData<List<JLPTOneKanjiEntry>> getAll();

    /**
     * Select all KanjiEntry data with a matching SHIFT JIS code
     * @param jis_code
     * @return
     */
    @Query("SELECT * FROM jlpt_one_kanji_entry WHERE jis_code LIKE :jis_code")
    List<JLPTOneKanjiEntry> findKanjiEntryByJIS(String jis_code);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(JLPTOneKanjiEntry ... kanji); //... = variable # of argument

    @Delete
    void deleteKanjiEntry(JLPTOneKanjiEntry ... kanji);

    @Update
    public void updateKanji(JLPTOneKanjiEntry ... kanji);

    @Query("DELETE FROM jlpt_one_kanji_entry")
    public void deleteAllEntries();
}
