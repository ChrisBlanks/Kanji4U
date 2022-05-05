package com.kanji4u.database;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DatabaseViewModal extends AndroidViewModel {
    private Kanji4URepository repo;
    private LiveData<List<KanjiEntry>> kanjiList;

    private LiveData< List<JLPTOneKanjiEntry>>  jlptOneKanjiList;
    private LiveData< List<JLPTTwoKanjiEntry>>  jlptTwoKanjiList;
    private LiveData< List<JLPTThreeKanjiEntry>>  jlptThreeKanjiList;
    private LiveData< List<JLPTFourKanjiEntry>>  jlptFourKanjiList;
    private LiveData< List<MiscellaneousKanjiEntry>>  miscellaneousKanjiList;

    public DatabaseViewModal(@NonNull Application app){
        super(app);

        this.repo = new Kanji4URepository(app);

        this.jlptOneKanjiList = this.repo.getAllJLPTOneKanji();
        this.jlptTwoKanjiList = this.repo.getAllJLPTTwoKanji();
        this.jlptThreeKanjiList = this.repo.getAllJLPTThreeKanji();
        this.jlptFourKanjiList = this.repo.getAllJLPTFourKanji();
        this.miscellaneousKanjiList = this.repo.getAllMiscellaneousKanji();

    }

    //kanji dao methods
    public void insert(DBKanji kanji, KanjiEntryType entryType){
        this.repo.insert(kanji,entryType);
    }
    public void update(DBKanji kanji, KanjiEntryType entryType){
        this.repo.update(kanji,entryType);
    }
    public void delete(DBKanji kanji, KanjiEntryType entryType){
        this.repo.delete(kanji,entryType);
    }

    public void deleteAllKanjiEntries(){
        Log.i("Delete All Kanji", "Clearing all tables.");
        this.repo.clearAllTables();
    }

    public LiveData<List<JLPTOneKanjiEntry>> getAllJLPTOneKanji(){
        return this.jlptOneKanjiList;
    }
    public LiveData<List<JLPTTwoKanjiEntry>> getAllJLPTTwoKanji(){
        return this.jlptTwoKanjiList;
    }

    public LiveData<List<JLPTThreeKanjiEntry>> getAllJLPTThreeKanji(){
        return this.jlptThreeKanjiList;
    }

    public LiveData<List<JLPTFourKanjiEntry>> getAllJLPTFourKanji(){
        return this.jlptFourKanjiList;
    }

    public LiveData<List<MiscellaneousKanjiEntry>> getAllMiscellaneousKanji(){
        return this.miscellaneousKanjiList;
    }
}
