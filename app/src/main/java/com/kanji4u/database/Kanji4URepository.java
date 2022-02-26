package com.kanji4u.database;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;

import java.util.List;

public class Kanji4URepository {

    private KanjiDao kanjiDao;
    private LiveData< List<KanjiEntry>>  kanjiList;

    public Kanji4URepository(Application app){
        Kanji4UDatabase db = Kanji4UDatabase.getInstance(app);
        kanjiDao = db.kanjiDao();
        kanjiList = kanjiDao.getAll();
    }

    public void insert(KanjiEntry kanji){
        new InsertKanjiEntryAsyncTask(kanjiDao).execute(kanji);
    }

    public void update(KanjiEntry kanji){
        new UpdateKanjiEntryAsyncTask(kanjiDao).execute(kanji);
    }

    public void delete(KanjiEntry kanji){
        new DeleteKanjiEntryAsyncTask(kanjiDao).execute(kanji);
    }

    public LiveData<List<KanjiEntry>> getAllKanji(){
        return this.kanjiList;
    }

    // we are creating a async task method to insert new course.
    private static class InsertKanjiEntryAsyncTask extends AsyncTask<KanjiEntry, Void, Void> {
        private KanjiDao kanjiDao;

        private InsertKanjiEntryAsyncTask(KanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(KanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.insertAll(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class UpdateKanjiEntryAsyncTask extends AsyncTask<KanjiEntry, Void, Void> {
        private KanjiDao kanjiDao;

        private UpdateKanjiEntryAsyncTask(KanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(KanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.updateUsers(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class DeleteKanjiEntryAsyncTask extends AsyncTask<KanjiEntry, Void, Void> {
        private KanjiDao kanjiDao;

        private DeleteKanjiEntryAsyncTask(KanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(KanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.deleteKanjiEntry(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class GetAllKanjiEntryAsyncTask extends AsyncTask<Void, Void, List<KanjiEntry>> {
        private KanjiDao kanjiDao;

        private GetAllKanjiEntryAsyncTask(KanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected List<KanjiEntry> doInBackground(Void ... voids) {
            return kanjiDao.getAll().getValue();
        }

        @Override
        protected void onPostExecute(List<KanjiEntry> kanji){
            super.onPostExecute(kanji);
        }

    }

}
