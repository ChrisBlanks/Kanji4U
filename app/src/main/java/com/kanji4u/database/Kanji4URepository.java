package com.kanji4u.database;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;

import java.util.List;

public class Kanji4URepository {

    Kanji4UDatabase db;

    private KanjiDao kanjiDao;

    private JLPTOneKanjiDao jlptOneKanjiDao;
    private JLPTTwoKanjiDao jlptTwoKanjiDao;
    private JLPTThreeKanjiDao jlptThreeKanjiDao;
    private JLPTFourKanjiDao jlptFourKanjiDao;
    private MiscellaneousKanjiDao miscellaneousKanjiDao;

    private LiveData< List<KanjiEntry>>  kanjiList;

    private LiveData< List<JLPTOneKanjiEntry>>  jlptOneKanjiList;
    private LiveData< List<JLPTTwoKanjiEntry>>  jlptTwoKanjiList;
    private LiveData< List<JLPTThreeKanjiEntry>>  jlptThreeKanjiList;
    private LiveData< List<JLPTFourKanjiEntry>>  jlptFourKanjiList;
    private LiveData< List<MiscellaneousKanjiEntry>>  miscellaneousKanjiList;

    public Kanji4URepository(Application app){
        db = Kanji4UDatabase.getInstance(app);

        kanjiDao = db.kanjiDao();
        kanjiList = kanjiDao.getAll();

        this.jlptOneKanjiDao = db.jlptOneKanjiDao();
        this.jlptTwoKanjiDao = db.jlptTwoKanjiDao();
        this.jlptThreeKanjiDao = db.jlptThreeKanjiDao();
        this.jlptFourKanjiDao = db.jlptFourKanjiDao();
        this.miscellaneousKanjiDao = db.miscellaneousKanjiDao();

        this.jlptOneKanjiList = this.jlptOneKanjiDao.getAll();
        this.jlptTwoKanjiList = this.jlptTwoKanjiDao.getAll();
        this.jlptThreeKanjiList = this.jlptThreeKanjiDao.getAll();
        this.jlptFourKanjiList = this.jlptFourKanjiDao.getAll();
        this.miscellaneousKanjiList = this.miscellaneousKanjiDao.getAll();

    }

    public void clearAllTables(){
        this.db.clearAllTables();
    }

    public void insert(DBKanji kanji, KanjiEntryType entryType){
        switch (entryType){
            case JLPT1KANJI:
            default:
                new InsertJLPTOneKanjiEntryAsyncTask(this.jlptOneKanjiDao).execute((JLPTOneKanjiEntry)kanji);
                break;
            case JLPT2KANJI:
                new InsertJLPTTwoKanjiEntryAsyncTask(this.jlptTwoKanjiDao).execute((JLPTTwoKanjiEntry) kanji);
                break;
            case JLPT3KANJI:
                new InsertJLPTThreeKanjiEntryAsyncTask(this.jlptThreeKanjiDao).execute((JLPTThreeKanjiEntry) kanji);
                break;
            case JLPT4KANJI:
                new InsertJLPTFourKanjiEntryAsyncTask(this.jlptFourKanjiDao).execute((JLPTFourKanjiEntry) kanji);
                break;
            case MISCELLANEOUSKANJI:
                new InsertMiscellaneousKanjiEntryAsyncTask(this.miscellaneousKanjiDao).execute((MiscellaneousKanjiEntry) kanji);
                break;
        }
    }

    public void update(DBKanji kanji, KanjiEntryType entryType){
        switch (entryType){
            case JLPT1KANJI:
            default:
                new UpdateJLPTOneKanjiEntryAsyncTask(this.jlptOneKanjiDao).execute((JLPTOneKanjiEntry)kanji);
                break;
            case JLPT2KANJI:
                new UpdateJLPTTwoKanjiEntryAsyncTask(this.jlptTwoKanjiDao).execute((JLPTTwoKanjiEntry)kanji);
                break;
            case JLPT3KANJI:
                new UpdateJLPTThreeKanjiEntryAsyncTask(this.jlptThreeKanjiDao).execute((JLPTThreeKanjiEntry)kanji);
                break;
            case JLPT4KANJI:
                new UpdateJLPTFourKanjiEntryAsyncTask(this.jlptFourKanjiDao).execute((JLPTFourKanjiEntry)kanji);
                break;
            case MISCELLANEOUSKANJI:
                new UpdateMiscellaneousKanjiEntryAsyncTask(this.miscellaneousKanjiDao).execute((MiscellaneousKanjiEntry)kanji);
                break;
        }
    }

    public void delete(DBKanji kanji, KanjiEntryType entryType){
        switch (entryType){
            case JLPT1KANJI:
            default:
                new DeleteJLPTOneKanjiEntryAsyncTask(this.jlptOneKanjiDao).execute((JLPTOneKanjiEntry)kanji);
                break;
            case JLPT2KANJI:
                new DeleteJLPTTwoKanjiEntryAsyncTask(this.jlptTwoKanjiDao).execute((JLPTTwoKanjiEntry)kanji);
                break;
            case JLPT3KANJI:
                new DeleteJLPTThreeKanjiEntryAsyncTask(this.jlptThreeKanjiDao).execute((JLPTThreeKanjiEntry)kanji);
                break;
            case JLPT4KANJI:
                new DeleteJLPTFourKanjiEntryAsyncTask(this.jlptFourKanjiDao).execute((JLPTFourKanjiEntry)kanji);
                break;
            case MISCELLANEOUSKANJI:
                new DeleteMiscellaneousKanjiEntryAsyncTask(this.miscellaneousKanjiDao).execute((MiscellaneousKanjiEntry)kanji);
                break;
        }
    }

    private class AllKanjiDao {
        public JLPTOneKanjiDao jlptOneKanjiDao;
        public JLPTTwoKanjiDao jlptTwoKanjiDao;
        public JLPTThreeKanjiDao jlptThreeKanjiDao;
        public JLPTFourKanjiDao jlptFourKanjiDao;
        public MiscellaneousKanjiDao miscellaneousKanjiDao;

        public AllKanjiDao(JLPTOneKanjiDao jlptOneKanjiDao, JLPTTwoKanjiDao jlptTwoKanjiDao, JLPTThreeKanjiDao jlptThreeKanjiDao, JLPTFourKanjiDao jlptFourKanjiDao, MiscellaneousKanjiDao miscellaneousKanjiDao ){
            this.jlptOneKanjiDao = jlptOneKanjiDao;
            this.jlptTwoKanjiDao = jlptTwoKanjiDao;
            this.jlptThreeKanjiDao = jlptThreeKanjiDao;
            this.jlptFourKanjiDao = jlptFourKanjiDao;
            this.miscellaneousKanjiDao = miscellaneousKanjiDao;
        }

    }

    public LiveData<List<JLPTOneKanjiEntry>> getAllJLPTOneKanji(){
        return this.jlptOneKanjiDao.getAll();

        //return this.jlptOneKanjiList;
    }
    public LiveData<List<JLPTTwoKanjiEntry>> getAllJLPTTwoKanji(){
        return this.jlptTwoKanjiDao.getAll();

        //return this.jlptTwoKanjiList;
    }
    public LiveData<List<JLPTThreeKanjiEntry>> getAllJLPTThreeKanji(){
        return this.jlptThreeKanjiDao.getAll();

        //return this.jlptThreeKanjiList;
    }
    public LiveData<List<JLPTFourKanjiEntry>> getAllJLPTFourKanji(){
        return this.jlptFourKanjiDao.getAll();

        //return this.jlptFourKanjiList;
    }
    public LiveData<List<MiscellaneousKanjiEntry>> getAllMiscellaneousKanji(){
        return this.miscellaneousKanjiDao.getAll();

        //return this.miscellaneousKanjiList;
    }

    //kanjidao async classes

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
            kanjiDao.updateKanji(kanji);
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
    private static class FindKanjiEntryByJLPTLevelAsyncTask extends AsyncTask<Void, Void, List<KanjiEntry>> {
        private KanjiDao kanjiDao;

        private FindKanjiEntryByJLPTLevelAsyncTask(KanjiDao kanjiDao) {
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

    //jlpt ansync classes

    private static class GetJLPTOneKanjiEntryAsyncTask extends AsyncTask<Void, Void, List<JLPTOneKanjiEntry>> {
        private JLPTOneKanjiDao kanjiDao;

        private GetJLPTOneKanjiEntryAsyncTask(JLPTOneKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected List<JLPTOneKanjiEntry> doInBackground(Void ... voids) {
            return kanjiDao.getAll().getValue();
        }

        @Override
        protected void onPostExecute(List<JLPTOneKanjiEntry> kanji){
            super.onPostExecute(kanji);
        }

    }

    private static class GetJLPTTwoKanjiEntryAsyncTask extends AsyncTask<Void, Void, List<JLPTTwoKanjiEntry>> {
        private JLPTTwoKanjiDao kanjiDao;

        private GetJLPTTwoKanjiEntryAsyncTask(JLPTTwoKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected List<JLPTTwoKanjiEntry> doInBackground(Void ... voids) {
            return kanjiDao.getAll().getValue();
        }

        @Override
        protected void onPostExecute(List<JLPTTwoKanjiEntry> kanji){
            super.onPostExecute(kanji);
        }

    }

    private static class GetJLPTThreeKanjiEntryAsyncTask extends AsyncTask<Void, Void, List<JLPTThreeKanjiEntry>> {
        private JLPTThreeKanjiDao kanjiDao;

        private GetJLPTThreeKanjiEntryAsyncTask(JLPTThreeKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected List<JLPTThreeKanjiEntry> doInBackground(Void ... voids) {
            return kanjiDao.getAll().getValue();
        }

        @Override
        protected void onPostExecute(List<JLPTThreeKanjiEntry> kanji){
            super.onPostExecute(kanji);
        }

    }

    private static class GetJLPTFourKanjiEntryAsyncTask extends AsyncTask<Void, Void, List<JLPTFourKanjiEntry>> {
        private JLPTFourKanjiDao kanjiDao;

        private GetJLPTFourKanjiEntryAsyncTask(JLPTFourKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected List<JLPTFourKanjiEntry> doInBackground(Void ... voids) {
            return kanjiDao.getAll().getValue();
        }

        @Override
        protected void onPostExecute(List<JLPTFourKanjiEntry> kanji){
            super.onPostExecute(kanji);
        }

    }

    private static class GetMiscellaneousKanjiEntryAsyncTask extends AsyncTask<Void, Void, List<MiscellaneousKanjiEntry>> {
        private MiscellaneousKanjiDao kanjiDao;

        private GetMiscellaneousKanjiEntryAsyncTask(MiscellaneousKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected List<MiscellaneousKanjiEntry> doInBackground(Void ... voids) {
            return kanjiDao.getAll().getValue();
        }

        @Override
        protected void onPostExecute(List<MiscellaneousKanjiEntry> kanji){
            super.onPostExecute(kanji);
        }

    }

    ///jlpt 1
    private static class InsertJLPTOneKanjiEntryAsyncTask extends AsyncTask<JLPTOneKanjiEntry, Void, Void> {
        private JLPTOneKanjiDao kanjiDao;

        private InsertJLPTOneKanjiEntryAsyncTask(JLPTOneKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(JLPTOneKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            this.kanjiDao.insertAll(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class UpdateJLPTOneKanjiEntryAsyncTask extends AsyncTask<JLPTOneKanjiEntry, Void, Void> {
        private JLPTOneKanjiDao kanjiDao;

        private UpdateJLPTOneKanjiEntryAsyncTask(JLPTOneKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(JLPTOneKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.updateKanji(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class DeleteJLPTOneKanjiEntryAsyncTask extends AsyncTask<JLPTOneKanjiEntry, Void, Void> {
        private JLPTOneKanjiDao kanjiDao;

        private DeleteJLPTOneKanjiEntryAsyncTask(JLPTOneKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(JLPTOneKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.deleteKanjiEntry(kanji);
            return null;
        }
    }


    ///jlpt 2
    private static class InsertJLPTTwoKanjiEntryAsyncTask extends AsyncTask<JLPTTwoKanjiEntry, Void, Void> {
        private JLPTTwoKanjiDao kanjiDao;

        private InsertJLPTTwoKanjiEntryAsyncTask(JLPTTwoKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(JLPTTwoKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            this.kanjiDao.insertAll(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class UpdateJLPTTwoKanjiEntryAsyncTask extends AsyncTask<JLPTTwoKanjiEntry, Void, Void> {
        private JLPTTwoKanjiDao kanjiDao;

        private UpdateJLPTTwoKanjiEntryAsyncTask(JLPTTwoKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(JLPTTwoKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.updateKanji(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class DeleteJLPTTwoKanjiEntryAsyncTask extends AsyncTask<JLPTTwoKanjiEntry, Void, Void> {
        private JLPTTwoKanjiDao kanjiDao;

        private DeleteJLPTTwoKanjiEntryAsyncTask(JLPTTwoKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(JLPTTwoKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.deleteKanjiEntry(kanji);
            return null;
        }
    }

    ///jlpt 3
    private static class InsertJLPTThreeKanjiEntryAsyncTask extends AsyncTask<JLPTThreeKanjiEntry, Void, Void> {
        private JLPTThreeKanjiDao kanjiDao;

        private InsertJLPTThreeKanjiEntryAsyncTask(JLPTThreeKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(JLPTThreeKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            this.kanjiDao.insertAll(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class UpdateJLPTThreeKanjiEntryAsyncTask extends AsyncTask<JLPTThreeKanjiEntry, Void, Void> {
        private JLPTThreeKanjiDao kanjiDao;

        private UpdateJLPTThreeKanjiEntryAsyncTask(JLPTThreeKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(JLPTThreeKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.updateKanji(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class DeleteJLPTThreeKanjiEntryAsyncTask extends AsyncTask<JLPTThreeKanjiEntry, Void, Void> {
        private JLPTThreeKanjiDao kanjiDao;

        private DeleteJLPTThreeKanjiEntryAsyncTask(JLPTThreeKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(JLPTThreeKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.deleteKanjiEntry(kanji);
            return null;
        }
    }

    ///jlpt 4
    private static class InsertJLPTFourKanjiEntryAsyncTask extends AsyncTask<JLPTFourKanjiEntry, Void, Void> {
        private JLPTFourKanjiDao kanjiDao;

        private InsertJLPTFourKanjiEntryAsyncTask(JLPTFourKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(JLPTFourKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            this.kanjiDao.insertAll(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class UpdateJLPTFourKanjiEntryAsyncTask extends AsyncTask<JLPTFourKanjiEntry, Void, Void> {
        private JLPTFourKanjiDao kanjiDao;

        private UpdateJLPTFourKanjiEntryAsyncTask(JLPTFourKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(JLPTFourKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.updateKanji(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class DeleteJLPTFourKanjiEntryAsyncTask extends AsyncTask<JLPTFourKanjiEntry, Void, Void> {
        private JLPTFourKanjiDao kanjiDao;

        private DeleteJLPTFourKanjiEntryAsyncTask(JLPTFourKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(JLPTFourKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.deleteKanjiEntry(kanji);
            return null;
        }
    }

    ///miscellaneous kanji
    private static class InsertMiscellaneousKanjiEntryAsyncTask extends AsyncTask<MiscellaneousKanjiEntry, Void, Void> {
        private MiscellaneousKanjiDao kanjiDao;

        private InsertMiscellaneousKanjiEntryAsyncTask(MiscellaneousKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(MiscellaneousKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            this.kanjiDao.insertAll(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class UpdateMiscellaneousKanjiEntryAsyncTask extends AsyncTask<MiscellaneousKanjiEntry, Void, Void> {
        private MiscellaneousKanjiDao kanjiDao;

        private UpdateMiscellaneousKanjiEntryAsyncTask(MiscellaneousKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(MiscellaneousKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.updateKanji(kanji);
            return null;
        }
    }

    // we are creating a async task method to insert new course.
    private static class DeleteMiscellaneousKanjiEntryAsyncTask extends AsyncTask<MiscellaneousKanjiEntry, Void, Void> {
        private MiscellaneousKanjiDao kanjiDao;

        private DeleteMiscellaneousKanjiEntryAsyncTask(MiscellaneousKanjiDao kanjiDao) {
            this.kanjiDao = kanjiDao;
        }

        @Override
        protected Void doInBackground(MiscellaneousKanjiEntry ... kanji) {
            // below line is use to insert our modal in dao.
            kanjiDao.deleteKanjiEntry(kanji);
            return null;
        }
    }
}
