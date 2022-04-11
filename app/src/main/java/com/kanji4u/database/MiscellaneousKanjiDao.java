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
public interface MiscellaneousKanjiDao {

    /**
     * Get all kanji from kanji table
     * @return ArrayList of KanjiEntry objects
     */
    @Query("SELECT * FROM miscellaneous_kanji_entry")
    LiveData<List<MiscellaneousKanjiEntry>> getAll();

    /**
     * Select all KanjiEntry data with a matching SHIFT JIS code
     * @param jis_code
     * @return
     */
    @Query("SELECT * FROM miscellaneous_kanji_entry WHERE jis_code LIKE :jis_code")
    List<MiscellaneousKanjiEntry> findKanjiEntryByJIS(String jis_code);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MiscellaneousKanjiEntry ... kanji); //... = variable # of argument

    @Delete
    void deleteKanjiEntry(MiscellaneousKanjiEntry ... kanji);

    @Update
    public void updateKanji(MiscellaneousKanjiEntry ... kanji);

}
