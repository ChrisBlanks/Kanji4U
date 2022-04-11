package com.kanji4u.app;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.kanji4u.app.databinding.FragmentLessonsBinding;
import com.kanji4u.database.DatabaseViewModal;
import com.kanji4u.database.JLPTFourKanjiEntry;
import com.kanji4u.database.JLPTOneKanjiEntry;
import com.kanji4u.database.JLPTThreeKanjiEntry;
import com.kanji4u.database.JLPTTwoKanjiEntry;
import com.kanji4u.database.Kanji4UDatabase;
import com.kanji4u.database.KanjiEntry;
import com.kanji4u.database.MiscellaneousKanjiEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class LessonsFragment extends Fragment {

    private static String DEFAULT_ROW_SELECT = "JLPT Level 4";
    private static String ROW_SELECT_BUNDLE_KEY ="row_selection";
    private static int NUM_OF_KANJI_PER_LESSON = 10;

    private String jlptLevel;

    private List<RowFeedItem> lessonsRows;

    private RecyclerView lessonsRecycleView;
    private LessonsViewAdapter lessonsViewAdapter;
    private DatabaseViewModal dbViewModal;

    private FragmentLessonsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Bundle bundle =  getArguments();
        this.jlptLevel = bundle.getString(LessonsFragment.ROW_SELECT_BUNDLE_KEY, LessonsFragment.DEFAULT_ROW_SELECT);
        Log.i("LessonsFragment", "Received this row selection: " + this.jlptLevel);
        binding = FragmentLessonsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = String.format("%s - Lessons",this.jlptLevel);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title );

        lessonsRecycleView = view.findViewById(R.id.lessons_recycle);
        lessonsRecycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        lessonsRows = new ArrayList<>();

        dbViewModal = new ViewModelProvider(this).get(DatabaseViewModal.class);

        if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(0)) ){         //JLPT level 4

            dbViewModal.getAllJLPTFourKanji().observe(getViewLifecycleOwner(), new Observer<List<JLPTFourKanjiEntry>>() {
                @Override
                public void onChanged(List<JLPTFourKanjiEntry> jlptFourKanjiEntries) {
                    processKanji(view, jlptFourKanjiEntries);
                }

            });
        } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(1))){
            dbViewModal.getAllJLPTThreeKanji().observe(getViewLifecycleOwner(), new Observer<List<JLPTThreeKanjiEntry>>() {
                @Override
                public void onChanged(List<JLPTThreeKanjiEntry> jlptThreeKanjiEntries) {
                    processKanji(view, jlptThreeKanjiEntries);
                }

            });
        }else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(2))){
            dbViewModal.getAllJLPTTwoKanji().observe(getViewLifecycleOwner(), new Observer<List<JLPTTwoKanjiEntry>>() {
                @Override
                public void onChanged(List<JLPTTwoKanjiEntry> jlptTwoKanjiEntries) {
                    processKanji(view, jlptTwoKanjiEntries);
                }

            });
        }else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(3))){
            dbViewModal.getAllJLPTOneKanji().observe(getViewLifecycleOwner(), new Observer<List<JLPTOneKanjiEntry>>() {
                @Override
                public void onChanged(List<JLPTOneKanjiEntry> jlptOneKanjiEntries) {
                    processKanji(view, jlptOneKanjiEntries);
                }

            });
        }else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(4))){
            dbViewModal.getAllMiscellaneousKanji().observe(getViewLifecycleOwner(), new Observer<List<MiscellaneousKanjiEntry>>() {
                @Override
                public void onChanged(List<MiscellaneousKanjiEntry> miscellaneousKanjiEntries) {
                    ArrayList<MiscellaneousKanjiEntry> selectedKanji = new ArrayList<>(miscellaneousKanjiEntries.subList(0,100));
                    processKanji(view, selectedKanji);
                }

            });
        }else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(5))){
            dbViewModal.getAllMiscellaneousKanji().observe(getViewLifecycleOwner(), new Observer<List<MiscellaneousKanjiEntry>>() {
                @Override
                public void onChanged(List<MiscellaneousKanjiEntry> miscellaneousKanjiEntries) {
                    ArrayList<MiscellaneousKanjiEntry> selectedKanji = new ArrayList<>(miscellaneousKanjiEntries.subList(100,200));
                    processKanji(view, selectedKanji);
                }

            });
        }else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(6))){
            dbViewModal.getAllMiscellaneousKanji().observe(getViewLifecycleOwner(), new Observer<List<MiscellaneousKanjiEntry>>() {
                @Override
                public void onChanged(List<MiscellaneousKanjiEntry> miscellaneousKanjiEntries) {
                    ArrayList<MiscellaneousKanjiEntry> selectedKanji = new ArrayList<>(miscellaneousKanjiEntries.subList(200,300));
                    processKanji(view, selectedKanji);
                }

            });
        }else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(7))){
            dbViewModal.getAllMiscellaneousKanji().observe(getViewLifecycleOwner(), new Observer<List<MiscellaneousKanjiEntry>>() {
                @Override
                public void onChanged(List<MiscellaneousKanjiEntry> miscellaneousKanjiEntries) {
                    ArrayList<MiscellaneousKanjiEntry> selectedKanji = new ArrayList<>(miscellaneousKanjiEntries.subList(300,miscellaneousKanjiEntries.size() ));
                    processKanji(view, selectedKanji);
                }

            });
        }else { //default to JLPT 4 if nothing else matches
            dbViewModal.getAllJLPTFourKanji().observe(getViewLifecycleOwner(), new Observer<List<JLPTFourKanjiEntry>>() {
                @Override
                public void onChanged(List<JLPTFourKanjiEntry> jlptFourKanjiEntries) {
                    processKanji(view, jlptFourKanjiEntries);
                }

            });
        }

        //setup initial view until database returns result
        lessonsViewAdapter =new LessonsViewAdapter(view.getContext(),lessonsRows); //To-Do: pass context & rows List
        lessonsRecycleView.setAdapter(lessonsViewAdapter);
    }


    public void processKanji(View view, List<?> selectedKanji){

        //calculate number of rows based
        int numOfKanji = selectedKanji.size();
        int numOfLessons = (int) (numOfKanji / LessonsFragment.NUM_OF_KANJI_PER_LESSON);
        numOfLessons += (numOfKanji % LessonsFragment.NUM_OF_KANJI_PER_LESSON) > 0 ? 1 : 0;
        for (int i = 1; i <= numOfLessons; i++) {
            lessonsRows.add(new RowFeedItem(String.format("Lesson %d", i), i - 1));
        }
        Log.i("Number of rows: ", String.format("%d", numOfLessons));

        lessonsViewAdapter = new LessonsViewAdapter(view.getContext(), lessonsRows); //To-Do: pass context & rows List
        lessonsViewAdapter.setRowItemListener(new OnRowItemClickListener() { //navigate to next fragment
            @Override
            public void onRowItemClick(RowFeedItem row) {
                Log.i("Lesson Nav Selection", "Selected: " + row.getRowTitle());

                //create kanji subset for specific lesson
                int lessonNumber = row.getIndex();
                int lowerBound = lessonNumber * 10;
                int upperBound = lowerBound + 10;
                int listSize = selectedKanji.size();
                upperBound = (upperBound > listSize) ? listSize : upperBound;

                ArrayList<?> subsetKanji = new ArrayList<>(selectedKanji.subList(lowerBound, upperBound));

                Bundle selection = new Bundle();
                selection.putString("row_selection", row.getRowTitle());
                selection.putParcelableArrayList("kanji", (ArrayList<? extends Parcelable>) subsetKanji);
                selection.putString("jlpt", jlptLevel);

                //go to kanji display screen
                NavHostFragment.findNavController(LessonsFragment.this)
                        .navigate(R.id.action_lessonsFragment_to_kanjiDisplayFragment, selection);

            }
        });

        lessonsRecycleView.setAdapter(lessonsViewAdapter);

    }


}