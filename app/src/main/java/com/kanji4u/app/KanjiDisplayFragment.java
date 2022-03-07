package com.kanji4u.app;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kanji4u.app.databinding.FragmentKanjiDisplayBinding;
import com.kanji4u.database.KanjiEntry;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class KanjiDisplayFragment extends Fragment {

    private static String DEFAULT_ROW_SELECT = "Lesson 1";
    private static String ROW_SELECT_BUNDLE_KEY ="row_selection";

    private int currentKanjiIndex = 0;
    private int numOfKanji = 0;
    private boolean[] memorizedStates;

    private FragmentKanjiDisplayBinding binding;

    private ArrayList<KanjiEntry> kanjiList;

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
        String rowSelection = bundle.getString(KanjiDisplayFragment.ROW_SELECT_BUNDLE_KEY, KanjiDisplayFragment.DEFAULT_ROW_SELECT );
        Log.i("kanjiDisplayFragment", "Received this row selection: " + rowSelection);

        this.kanjiList = bundle.getParcelableArrayList("kanji");
        this.numOfKanji = this.kanjiList.size();
        this.memorizedStates = new boolean[this.numOfKanji];

        binding = FragmentKanjiDisplayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        KanjiEntry initialKanji =  this.kanjiList.get(kanjiIndexToUse);

        this.kanjiNumOrderText.setText(String.format("%d of %d", kanjiIndexToUse + 1, this.numOfKanji) );
        this.kanjiDisplayText.setText(initialKanji.getKanjiLiteral());
        this.onReadingText.setText("On: " + initialKanji.getOnReadings().stream().map(Object::toString).collect(Collectors.joining(", ")) );
        this.kunReadingText.setText("Kun: " + initialKanji.getKunReadings().stream().map(Object::toString).collect(Collectors.joining(", ")));
        this.englishMeaningText.setText("Meaning: " + initialKanji.getEnglishMeanings().stream().map(Object::toString).collect(Collectors.joining(", ")));

        String frequencyTextValue = initialKanji.getFrequencyRank().isEmpty() ? "N/A": initialKanji.getFrequencyRank();
        this.frequencyRankText.setText("Frequency Rank: " + frequencyTextValue);

        this.memorizedCheckbox.setChecked(this.memorizedStates[kanjiIndexToUse]);
    }
}