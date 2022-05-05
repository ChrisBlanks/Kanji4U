package com.kanji4u.app;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kanji4u.app.databinding.ActivityMainBinding;

import com.kanji4u.database.DBKanji;
import com.kanji4u.database.DatabaseViewModal;
import com.kanji4u.database.JLPTFourKanjiEntry;
import com.kanji4u.database.JLPTOneKanjiEntry;
import com.kanji4u.database.JLPTThreeKanjiEntry;
import com.kanji4u.database.JLPTTwoKanjiEntry;
import com.kanji4u.database.Kanji4UDatabase;
import com.kanji4u.database.KanjiDao;
import com.kanji4u.database.KanjiEntry;
import com.kanji4u.database.KanjiEntryType;
import com.kanji4u.database.MiscellaneousKanjiEntry;
import com.kanji4u.kanji.KanjiCollection;
import com.kanji4u.kanji.KanjiDIC;
import com.kanji4u.kanji.KanjiDictionary;

import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static int NUM_OF_THREADS = 4;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private Kanji4UDatabase kanjiDB;
    private DatabaseViewModal dbViewModal;

    private ExecutorService executorService = Executors.newFixedThreadPool(MainActivity.NUM_OF_THREADS);

    private boolean isDarkMode = false;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    private KanjiCollection kanjiDict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setup preferences
        prefs = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        prefsEditor = prefs.edit();

        //receive dark mode state from storage
        isDarkMode = prefs.getBoolean("isDarkMode", false);
        if(isDarkMode) { //if dark mode was set last, set dark mode
            Log.i("Darkmode", "Dark mode on");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //database loading
        this.dbViewModal = new ViewModelProvider(this).get(DatabaseViewModal.class);
        this.dbViewModal.getAllJLPTOneKanji().observe(this, new Observer<List<JLPTOneKanjiEntry>>() {
            @Override
            public void onChanged(List<JLPTOneKanjiEntry> jlptOneKanjiEntries) {
                if(jlptOneKanjiEntries.size() < 1){ //if no data in database, load kanji dictionary from file & store
                    if( kanjiDict == null || kanjiDict.getNumberOfKanjiLoaded() < 1){ //if no kanji loaded, load all kanji
                        loadKanjiDictionary();
                    }

                    for(KanjiDIC kanji : kanjiDict.getLoadedKanji()){
                        if(kanji.getKanjiJLPTLevel().equals("1")) {
                            dbViewModal.insert(kanji.createJLPTOneKanjiEntry(), KanjiEntryType.JLPT1KANJI);
                        }
                    }
                    Log.i("Database","JLPT 1 Kanji has finished loading into database.");
                } else{
                    Log.i("Database","JLPT 1 Kanji has been loaded already, so no action needed");
                }
            }
        });

        this.dbViewModal.getAllJLPTTwoKanji().observe(this, new Observer<List<JLPTTwoKanjiEntry>>() {
            @Override
            public void onChanged(List<JLPTTwoKanjiEntry> jlptTwoKanjiEntries) {
                if(jlptTwoKanjiEntries.size() < 1){ //if no data in database, load kanji dictionary from file & store
                    if( kanjiDict == null || kanjiDict.getNumberOfKanjiLoaded() < 1){ //if no kanji loaded, load all kanji
                        loadKanjiDictionary();
                    }

                    for(KanjiDIC kanji : kanjiDict.getLoadedKanji()){
                        if(kanji.getKanjiJLPTLevel().equals("2")) {
                            dbViewModal.insert(kanji.createJLPTTwoKanjiEntry(), KanjiEntryType.JLPT2KANJI);
                        }
                    }
                    Log.i("Database","JLPT 2 Kanji has finished loading into database.");
                } else{
                    Log.i("Database","JLPT 2 Kanji has been loaded already, so no action needed");
                }
            }
        });

        this.dbViewModal.getAllJLPTThreeKanji().observe(this, new Observer<List<JLPTThreeKanjiEntry>>() {
            @Override
            public void onChanged(List<JLPTThreeKanjiEntry> jlptThreeKanjiEntries) {
                if( jlptThreeKanjiEntries.size() < 1){ //if no data in database, load kanji dictionary from file & store
                    if( kanjiDict == null || kanjiDict.getNumberOfKanjiLoaded() < 1){ //if no kanji loaded, load all kanji
                        loadKanjiDictionary();
                    }

                    for(KanjiDIC kanji : kanjiDict.getLoadedKanji()){
                        if(kanji.getKanjiJLPTLevel().equals("3")) {
                            dbViewModal.insert(kanji.createJLPTThreeKanjiEntry(), KanjiEntryType.JLPT3KANJI);
                        }
                    }
                    Log.i("Database","JLPT 3 Kanji has finished loading into database.");
                } else{
                    Log.i("Database","JLPT 3 Kanji has been loaded already, so no action needed");
                }
            }
        });

        this.dbViewModal.getAllJLPTFourKanji().observe(this, new Observer<List<JLPTFourKanjiEntry>>() {
            @Override
            public void onChanged(List<JLPTFourKanjiEntry> jlptFourKanjiEntries) {
                if(jlptFourKanjiEntries.size() < 1){ //if no data in database, load kanji dictionary from file & store
                    if( kanjiDict == null || kanjiDict.getNumberOfKanjiLoaded() < 1){ //if no kanji loaded, load all kanji
                        loadKanjiDictionary();
                    }

                    for(KanjiDIC kanji : kanjiDict.getLoadedKanji()){
                        if(kanji.getKanjiJLPTLevel().equals("4")) {
                            dbViewModal.insert(kanji.createJLPTFourKanjiEntry(), KanjiEntryType.JLPT4KANJI);
                        }
                    }
                    Log.i("Database","JLPT 4 Kanji has finished loading into database.");
                } else{
                    Log.i("Database","JLPT 4 Kanji has been loaded already, so no action needed");
                }
            }
        });

        this.dbViewModal.getAllMiscellaneousKanji().observe(this, new Observer<List<MiscellaneousKanjiEntry>>() {
            @Override
            public void onChanged(List<MiscellaneousKanjiEntry> miscellaneousKanjiEntries) {
                if(miscellaneousKanjiEntries.size() < 1){ //if no data in database, load kanji dictionary from file & store
                    if( kanjiDict == null || kanjiDict.getNumberOfKanjiLoaded() < 1){ //if no kanji loaded, load all kanji
                        loadKanjiDictionary();
                    }

                    for(KanjiDIC kanji : kanjiDict.getLoadedKanji()){
                        if(kanji.getKanjiJLPTLevel().isEmpty()) {
                            dbViewModal.insert(kanji.createMisceallaneousKanjiEntry(), KanjiEntryType.MISCELLANEOUSKANJI);
                        }
                    }
                    Log.i("Database","Miscellaneous Kanji has finished loading into database.");
                } else{
                    Log.i("Database","Miscellaneous Kanji has been loaded already, so no action needed");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        } else if(id == R.id.darkmode){
            Log.i("Darkmode","Dark mode on");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            prefsEditor.putBoolean("isDarkMode",true);
            prefsEditor.apply();
        } else if(id == R.id.lightmode){
            Log.i("lightmode", "Dark mode off");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            prefsEditor.putBoolean("isDarkMode",false);
            prefsEditor.apply();
        } else if(id == R.id.reload_db){
            Log.i("Reload DB", "Reloading database");
            reloadDatabase();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void onPause(){
        super.onPause();

        //To-Do: save any persistent state before app pause is in effect

    }

    // private functions

    /**
     * Loads kanji dictionary for file and creates a dictionary object
     * @return KanjiCollection object filled with kanji information
     */
    private void loadKanjiDictionary(){
        //load kanji dictionary
        String resourcePath = String.format("%s:raw/%s", getPackageName(),getString(R.string.kanji_dict_file) );
        int resourceId = getResources().getIdentifier(resourcePath, null, null);
        InputStream iStream = getResources().openRawResource(resourceId);

        this.kanjiDict = new KanjiCollection();
        this.kanjiDict.loadKanjiDictionary(KanjiDictionary.KANJIDIC , iStream);
        Log.i("Loading Kanji", "Completed loading kanji.");
    }

    private void reloadDatabase(){
        Log.i("Delete All Kanji", "Deleting all kanji.");

        Runnable deleteAllRunnable = new Runnable() {
            @Override
            public void run() {
                dbViewModal.deleteAllKanjiEntries();
            }
        };
        Thread thread = new Thread(deleteAllRunnable);
        thread.start();

        Log.i("Delete All Kanji", "Repopulate tables");
        this.dbViewModal.getAllJLPTOneKanji().observe(this, new Observer<List<JLPTOneKanjiEntry>>() {
            @Override
            public void onChanged(List<JLPTOneKanjiEntry> jlptOneKanjiEntries) {
                if(jlptOneKanjiEntries.size() < 1){ //if no data in database, load kanji dictionary from file & store
                    if( kanjiDict == null || kanjiDict.getNumberOfKanjiLoaded() < 1){ //if no kanji loaded, load all kanji
                        loadKanjiDictionary();
                    }

                    for(KanjiDIC kanji : kanjiDict.getLoadedKanji()){
                        if(kanji.getKanjiJLPTLevel().equals("1")) {
                            dbViewModal.insert(kanji.createJLPTOneKanjiEntry(), KanjiEntryType.JLPT1KANJI);
                        }
                    }
                    Log.i("Database","JLPT 1 Kanji has finished loading into database.");
                } else{
                    Log.i("Database","JLPT 1 Kanji has been loaded already, so no action needed");
                }
            }
        });

        this.dbViewModal.getAllJLPTTwoKanji().observe(this, new Observer<List<JLPTTwoKanjiEntry>>() {
            @Override
            public void onChanged(List<JLPTTwoKanjiEntry> jlptTwoKanjiEntries) {
                if(jlptTwoKanjiEntries.size() < 1){ //if no data in database, load kanji dictionary from file & store
                    if( kanjiDict == null || kanjiDict.getNumberOfKanjiLoaded() < 1){ //if no kanji loaded, load all kanji
                        loadKanjiDictionary();
                    }

                    for(KanjiDIC kanji : kanjiDict.getLoadedKanji()){
                        if(kanji.getKanjiJLPTLevel().equals("2")) {
                            dbViewModal.insert(kanji.createJLPTTwoKanjiEntry(), KanjiEntryType.JLPT2KANJI);
                        }
                    }
                    Log.i("Database","JLPT 2 Kanji has finished loading into database.");
                } else{
                    Log.i("Database","JLPT 2 Kanji has been loaded already, so no action needed");
                }
            }
        });

        this.dbViewModal.getAllJLPTThreeKanji().observe(this, new Observer<List<JLPTThreeKanjiEntry>>() {
            @Override
            public void onChanged(List<JLPTThreeKanjiEntry> jlptThreeKanjiEntries) {
                if( jlptThreeKanjiEntries.size() < 1){ //if no data in database, load kanji dictionary from file & store
                    if( kanjiDict == null || kanjiDict.getNumberOfKanjiLoaded() < 1){ //if no kanji loaded, load all kanji
                        loadKanjiDictionary();
                    }

                    for(KanjiDIC kanji : kanjiDict.getLoadedKanji()){
                        if(kanji.getKanjiJLPTLevel().equals("3")) {
                            dbViewModal.insert(kanji.createJLPTThreeKanjiEntry(), KanjiEntryType.JLPT3KANJI);
                        }
                    }
                    Log.i("Database","JLPT 3 Kanji has finished loading into database.");
                } else{
                    Log.i("Database","JLPT 3 Kanji has been loaded already, so no action needed");
                }
            }
        });

        this.dbViewModal.getAllJLPTFourKanji().observe(this, new Observer<List<JLPTFourKanjiEntry>>() {
            @Override
            public void onChanged(List<JLPTFourKanjiEntry> jlptFourKanjiEntries) {
                if(jlptFourKanjiEntries.size() < 1){ //if no data in database, load kanji dictionary from file & store
                    if( kanjiDict == null || kanjiDict.getNumberOfKanjiLoaded() < 1){ //if no kanji loaded, load all kanji
                        loadKanjiDictionary();
                    }

                    for(KanjiDIC kanji : kanjiDict.getLoadedKanji()){
                        if(kanji.getKanjiJLPTLevel().equals("4")) {
                            dbViewModal.insert(kanji.createJLPTFourKanjiEntry(), KanjiEntryType.JLPT4KANJI);
                        }
                    }
                    Log.i("Database","JLPT 4 Kanji has finished loading into database.");
                } else{
                    Log.i("Database","JLPT 4 Kanji has been loaded already, so no action needed");
                }
            }
        });

        this.dbViewModal.getAllMiscellaneousKanji().observe(this, new Observer<List<MiscellaneousKanjiEntry>>() {
            @Override
            public void onChanged(List<MiscellaneousKanjiEntry> miscellaneousKanjiEntries) {
                if(miscellaneousKanjiEntries.size() < 1){ //if no data in database, load kanji dictionary from file & store
                    if( kanjiDict == null || kanjiDict.getNumberOfKanjiLoaded() < 1){ //if no kanji loaded, load all kanji
                        loadKanjiDictionary();
                    }

                    for(KanjiDIC kanji : kanjiDict.getLoadedKanji()){
                        if(kanji.getKanjiJLPTLevel().isEmpty()) {
                            dbViewModal.insert(kanji.createMisceallaneousKanjiEntry(), KanjiEntryType.MISCELLANEOUSKANJI);
                        }
                    }
                    Log.i("Database","Miscellaneous Kanji has finished loading into database.");
                } else{
                    Log.i("Database","Miscellaneous Kanji has been loaded already, so no action needed");
                }
            }
        });

        Log.i("Delete All Kanji", "Navigate to home");

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigateUp();
        navController.navigateUp();
        navController.navigateUp();
    }


}