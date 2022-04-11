package com.kanji4u.database;

import android.app.Application;

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

        this.kanjiList = this.repo.getAllKanji();

        this.jlptOneKanjiList = this.repo.getAllJLPTOneKanji();
        this.jlptTwoKanjiList = this.repo.getAllJLPTTwoKanji();
        this.jlptThreeKanjiList = this.repo.getAllJLPTThreeKanji();
        this.jlptFourKanjiList = this.repo.getAllJLPTFourKanji();
        this.miscellaneousKanjiList = this.repo.getAllMiscellaneousKanji();

    }

    //kanji dao methods
    public void insert(KanjiEntry kanji){
        this.repo.insert(kanji);
    }
    public void update(KanjiEntry kanji){ this.repo.update(kanji); }
    public void delete(KanjiEntry kanji){
        this.repo.delete(kanji);
    }
    public LiveData<List<KanjiEntry>> getAllKanji(){
        return this.kanjiList;
    }

    //1
    public void insertJLPTOneKanji(JLPTOneKanjiEntry kanji){
        this.repo.insert(kanji);
    }
    public void updateJLPTOneKanji(JLPTOneKanjiEntry kanji){ this.repo.update(kanji); }
    public LiveData<List<JLPTOneKanjiEntry>> getAllJLPTOneKanji(){
        return this.jlptOneKanjiList;
    }

    //2
    public void insertJLPTTwoKanji(JLPTTwoKanjiEntry kanji){
        this.repo.insert(kanji);
    }
    public void updateJLPTTwoKanji(JLPTTwoKanjiEntry kanji){ this.repo.update(kanji); }
    public LiveData<List<JLPTTwoKanjiEntry>> getAllJLPTTwoKanji(){
        return this.jlptTwoKanjiList;
    }

    //3
    public void insertJLPTThreeKanji(JLPTThreeKanjiEntry kanji){
        this.repo.insert(kanji);
    }
    public void updateJLPTThreeKanji(JLPTThreeKanjiEntry kanji){ this.repo.update(kanji); }
    public LiveData<List<JLPTThreeKanjiEntry>> getAllJLPTThreeKanji(){ return this.jlptThreeKanjiList; }

    //4
    public void insertJLPTFourKanji(JLPTFourKanjiEntry kanji){
        this.repo.insert(kanji);
    }
    public void updateJLPTFourKanji(JLPTFourKanjiEntry kanji){ this.repo.update(kanji); }
    public LiveData<List<JLPTFourKanjiEntry>> getAllJLPTFourKanji(){ return this.jlptFourKanjiList; }

    //miscellaneous
    public void insertMiscellaneousKanji(MiscellaneousKanjiEntry kanji){
        this.repo.insert(kanji);
    }
    public void updateMiscellaneousKanji(MiscellaneousKanjiEntry kanji){ this.repo.update(kanji); }
    public LiveData<List<MiscellaneousKanjiEntry>> getAllMiscellaneousKanji(){ return this.miscellaneousKanjiList; }
}
