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
public interface JLPTThreeKanjiDao {

    /**
     * Get all kanji from kanji table
     * @return ArrayList of KanjiEntry objects
     */
    @Query("SELECT * FROM jlpt_three_kanji_entry")
    LiveData<List<JLPTThreeKanjiEntry>> getAll();

    /**
     * Select all KanjiEntry data with a matching SHIFT JIS code
     * @param jis_code
     * @return
     */
    @Query("SELECT * FROM jlpt_three_kanji_entry WHERE jis_code LIKE :jis_code")
    List<JLPTThreeKanjiEntry> findKanjiEntryByJIS(String jis_code);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(JLPTThreeKanjiEntry ... kanji); //... = variable # of argument

    @Delete
    void deleteKanjiEntry(JLPTThreeKanjiEntry ... kanji);

    @Update
    public void updateKanji(JLPTThreeKanjiEntry ... kanji);

    @Query("DELETE FROM jlpt_three_kanji_entry")
    public void deleteAllEntries();
}
