package com.kanji4u.app;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toolbar;

import com.kanji4u.app.databinding.FragmentKanjiDisplayBinding;
import com.kanji4u.database.JLPTFourKanjiEntry;
import com.kanji4u.database.JLPTOneKanjiEntry;
import com.kanji4u.database.JLPTThreeKanjiEntry;
import com.kanji4u.database.JLPTTwoKanjiDao;
import com.kanji4u.database.JLPTTwoKanjiEntry;
import com.kanji4u.database.KanjiEntry;
import com.kanji4u.database.MiscellaneousKanjiEntry;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class KanjiDisplayFragment extends Fragment {

    private static String DEFAULT_ROW_SELECT = "Lesson 1";
    private static String ROW_SELECT_BUNDLE_KEY ="row_selection";

    private int currentKanjiIndex = 0;
    private int numOfKanji = 0;
    private boolean[] memorizedStates;
    private String rowSelection;
    private String jlptLevel;

    private FragmentKanjiDisplayBinding binding;

    private ArrayList<?> kanjiList;

    TextView kanjiNumOrderText;
    TextView kanjiDisplayText;
    TextView onReadingText;
    TextView kunReadingText;
    TextView englishMeaningText;
    TextView frequencyRankText;
    Button backButton;
    Button forwardButton;

    CheckBox memorizedCheckbox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Bundle bundle =  getArguments();
        this.rowSelection = bundle.getString(KanjiDisplayFragment.ROW_SELECT_BUNDLE_KEY, KanjiDisplayFragment.DEFAULT_ROW_SELECT );
        Log.i("kanjiDisplayFragment", "Received this row selection: " + this.rowSelection);

        this.jlptLevel = bundle.getString("jlpt");

        this.kanjiList = bundle.getParcelableArrayList("kanji");
        this.numOfKanji = this.kanjiList.size();
        this.memorizedStates = new boolean[this.numOfKanji];

        binding = FragmentKanjiDisplayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = String.format("%s - %s",this.jlptLevel, this.rowSelection);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title );

        this.kanjiNumOrderText = view.findViewById(R.id.kanjiNumOrder);
        this.kanjiDisplayText =  view.findViewById(R.id.kanjiDisplay);
        this.onReadingText = view.findViewById(R.id.onReading);
        this.kunReadingText = view.findViewById(R.id.kunReading);
        this.englishMeaningText = view.findViewById(R.id.englishMeaning);
        this.frequencyRankText = view.findViewById(R.id.frequencyRank);

        //setup lesson navigation callbacks
        this.backButton = (Button) view.findViewById(R.id.backButton);
        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if 1st index, set index to last index. if else, decrement index value
                int newIndex = (currentKanjiIndex == 0) ? numOfKanji - 1 : currentKanjiIndex -1 ;
                updateKanjiDisplayed(view, newIndex);
                currentKanjiIndex = newIndex;
            }
        });

        this.forwardButton = (Button) view.findViewById(R.id.forwardButton);
        this.forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if last index, set index to 1st index. if else, increment index value
                int newIndex = (currentKanjiIndex == (numOfKanji - 1) ) ? 0 : currentKanjiIndex + 1 ;
                updateKanjiDisplayed(view, newIndex);
                currentKanjiIndex = newIndex;
            }
        });

        this.memorizedCheckbox = view.findViewById(R.id.memorizedCheckbox);
        this.memorizedCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCheckboxClicked(view);
            }
        });

        //Setup initial view
        updateKanjiDisplayed(view, this.currentKanjiIndex);
    }

    public void onCheckboxClicked(View view){
        //set state of current kanji based on change in checkbox state
        this.memorizedStates[this.currentKanjiIndex] = ((CheckBox)view).isChecked();

        //To-Do: Need to save this state between fragments (e.g. going back and forth) and long term (storage or db w/ other metadata)
    }

    @SuppressLint("NewApi")
    public void updateKanjiDisplayed(View view, int kanjiIndexToUse){

        String kanjiLiteral = "";
        String onreading= "";
        String kunReading = "";
        String englishMeaning= "";
        String frequencyRank= "";

        if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(0)) ){         //JLPT level 4
            JLPTFourKanjiEntry kanji = (JLPTFourKanjiEntry) this.kanjiList.get(kanjiIndexToUse);
            kanjiLiteral = kanji.getKanjiLiteral();
            onreading= kanji.getOnReadings().stream().map(Object::toString).collect(Collectors.joining(", "));
            kunReading = kanji.getKunReadings().stream().map(Object::toString).collect(Collectors.joining(", "));
            englishMeaning= kanji.getEnglishMeanings().stream().map(Object::toString).collect(Collectors.joining(", "));
            frequencyRank= kanji.getFrequencyRank().isEmpty() ? "N/A": kanji.getFrequencyRank();
        } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(1)) ){  //JLPT level 3
            JLPTThreeKanjiEntry kanji = (JLPTThreeKanjiEntry) this.kanjiList.get(kanjiIndexToUse);
            kanjiLiteral = kanji.getKanjiLiteral();
            onreading= kanji.getOnReadings().stream().map(Object::toString).collect(Collectors.joining(", "));
            kunReading = kanji.getKunReadings().stream().map(Object::toString).collect(Collectors.joining(", "));
            englishMeaning= kanji.getEnglishMeanings().stream().map(Object::toString).collect(Collectors.joining(", "));
            frequencyRank= kanji.getFrequencyRank().isEmpty() ? "N/A": kanji.getFrequencyRank();
        } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(2)) ){  //JLPT level 2
            JLPTTwoKanjiEntry kanji = (JLPTTwoKanjiEntry) this.kanjiList.get(kanjiIndexToUse);
            kanjiLiteral = kanji.getKanjiLiteral();
            onreading= kanji.getOnReadings().stream().map(Object::toString).collect(Collectors.joining(", "));
            kunReading = kanji.getKunReadings().stream().map(Object::toString).collect(Collectors.joining(", "));
            englishMeaning= kanji.getEnglishMeanings().stream().map(Object::toString).collect(Collectors.joining(", "));
            frequencyRank= kanji.getFrequencyRank().isEmpty() ? "N/A": kanji.getFrequencyRank();
        } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(3)) ){  //JLPT level 1
            JLPTOneKanjiEntry kanji = (JLPTOneKanjiEntry) this.kanjiList.get(kanjiIndexToUse);
            kanjiLiteral = kanji.getKanjiLiteral();
            onreading= kanji.getOnReadings().stream().map(Object::toString).collect(Collectors.joining(", "));
            kunReading = kanji.getKunReadings().stream().map(Object::toString).collect(Collectors.joining(", "));
            englishMeaning= kanji.getEnglishMeanings().stream().map(Object::toString).collect(Collectors.joining(", "));
            frequencyRank= kanji.getFrequencyRank().isEmpty() ? "N/A": kanji.getFrequencyRank();
        }  else {  //Miscellaneous kanji
            MiscellaneousKanjiEntry kanji = (MiscellaneousKanjiEntry) this.kanjiList.get(kanjiIndexToUse);
            kanjiLiteral = kanji.getKanjiLiteral();
            onreading= kanji.getOnReadings().stream().map(Object::toString).collect(Collectors.joining(", "));
            kunReading = kanji.getKunReadings().stream().map(Object::toString).collect(Collectors.joining(", "));
            englishMeaning= kanji.getEnglishMeanings().stream().map(Object::toString).collect(Collectors.joining(", "));
            frequencyRank= kanji.getFrequencyRank().isEmpty() ? "N/A": kanji.getFrequencyRank();
        }

        /*
        else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(4)) ){  //Miscellaneous kanji #1
            MiscellaneousKanjiEntry kanji = (MiscellaneousKanjiEntry) this.kanjiList.get(kanjiIndexToUse);
        } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(5)) ){  //Miscellaneous kanji #1
            MiscellaneousKanjiEntry kanji = (MiscellaneousKanjiEntry) this.kanjiList.get(kanjiIndexToUse);
        } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(6)) ){  //Miscellaneous kanji #1
            MiscellaneousKanjiEntry kanji = (MiscellaneousKanjiEntry) this.kanjiList.get(kanjiIndexToUse);
        } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(7)) ){  //Miscellaneous kanji #1
            MiscellaneousKanjiEntry kanji = (MiscellaneousKanjiEntry) this.kanjiList.get(kanjiIndexToUse);
        }
        */

        this.kanjiNumOrderText.setText(String.format("%d of %d", kanjiIndexToUse + 1, this.numOfKanji) );
        this.kanjiDisplayText.setText(kanjiLiteral);
        this.onReadingText.setText("On: " + onreading );
        this.kunReadingText.setText("Kun: " + kunReading);
        this.englishMeaningText.setText("Meaning: " + englishMeaning);
        this.frequencyRankText.setText("Frequency Rank: " + frequencyRank);

        this.memorizedCheckbox.setChecked(this.memorizedStates[kanjiIndexToUse]);
    }

    public void saveState(){
        //To-Do: Save memorize kanji values before leaving fragment
    }
}