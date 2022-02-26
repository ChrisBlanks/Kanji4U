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
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.kanji4u.app.databinding.ActivityMainBinding;

import com.kanji4u.database.DatabaseViewModal;
import com.kanji4u.database.Kanji4UDatabase;
import com.kanji4u.database.KanjiDao;
import com.kanji4u.database.KanjiEntry;
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

        dbViewModal = new ViewModelProvider(this).get(DatabaseViewModal.class);
        dbViewModal.getAllKanji().observe(this, new Observer<List<KanjiEntry>>() {
            @Override
            public void onChanged(List<KanjiEntry> kanjiEntries) {
                if(kanjiEntries.size() < 1){ //if no data in datbase, load kanji dictionary from file & store
                    loadKanjiDictionary();
                    for(KanjiDIC kanji : kanjiDict.getLoadedKanji()){
                        dbViewModal.insert(kanji.createKanjiEntry());
                    }
                    Log.i("Database","Kanji has finished loading into database.");
                } else{
                    Log.i("Database","Kanji has been loaded already, so no action needed");
                }
            }
        });

        /*
        //schedule loading of kanji dictionary in a separate thread in the background
        this.executorService.execute(new Runnable() {
            @Override
            public void run() {
                //Check if database has already been populated in the past
                // if populated, skip loading kanji and adding to database
                if(kanjiDao.getAll().getValue().size() < 1){
                    loadKanjiDictionary();
                    ArrayList<KanjiDIC> kanjiCollection = kanjiDict.getLoadedKanji();
                    for(KanjiDIC kanji: kanjiCollection){
                        kanjiDao.insertAll(kanji.createKanjiEntry()); // all kanji into database
                    }
                } else{
                    Log.i("Database","Kanji has been loaded already, so no action needed");
                }

                Log.i("Database","Kanji loading is completed.");
                for( KanjiEntry kanji: kanjiDao.findKanjiEntryJLPTLevel("4")){
                    Log.i("Database Test", "For JLPT 4, found : "+ kanji.kanjiLiteral);
                }

                //To-Do: Split all kanji into JLPT levels in the database
                //To-Do: Find best way to allow fragments to access the database
            }
        });
        */
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
}