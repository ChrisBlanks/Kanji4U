package com.kanji4u.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DatabaseViewModal extends AndroidViewModel {
    private Kanji4URepository repo;
    private LiveData<List<KanjiEntry>> kanjiList;

    public DatabaseViewModal(@NonNull Application app){
        super(app);
        this.repo = new Kanji4URepository(app);
        kanjiList = this.repo.getAllKanji();
    }

    public void insert(KanjiEntry kanji){
        this.repo.insert(kanji);
    }

    public void update(KanjiEntry kanji){
        this.repo.update(kanji);
    }

    public void delete(KanjiEntry kanji){
        this.repo.delete(kanji);
    }

    public LiveData<List<KanjiEntry>> getAllKanji(){
        return this.kanjiList;
    }
}
