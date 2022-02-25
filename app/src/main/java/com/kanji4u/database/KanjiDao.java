package com.kanji4u.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.kanji4u.kanji.Kanji;

import java.util.List;

//Kanji Data Access Object for database
@Dao
public interface KanjiDao {

    /**
     * Get all kanji from kanji table
     * @return ArrayList of KanjiEntry objects
     */
    @Query("SELECT * FROM kanji_entry")
    List<KanjiEntry> getAll();

    /**
     * Select all KanjiEntry data with a matching SHIFT JIS code
     * @param jis_code
     * @return
     */
    @Query("SELECT * FROM kanji_entry WHERE jis_code LIKE :jis_code")
    List<KanjiEntry> findKanjiEntryByJIS(String jis_code);

    /**
     * Select all KanjiEntry data that a matching jlpt level
     * @param jlpt_level
     * @return
     */
    @Query("SELECT * FROM kanji_entry WHERE jlpt_level LIKE :jlpt_level")
    List<KanjiEntry> findKanjiEntryJLPTLevel(String jlpt_level);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(KanjiEntry ... kanji); //... = variable # of argument

    @Delete
    void deleteKanjiEntry(KanjiEntry kanji);

    @Update
    public void updateUsers(KanjiEntry ... kanji);

}
