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
import com.kanji4u.database.Kanji4UDatabase;
import com.kanji4u.database.KanjiEntry;

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
        dbViewModal.getAllKanji().observe(getViewLifecycleOwner(), new Observer<List<KanjiEntry>>() {
            @SuppressLint("NewApi")
            @Override
            public void onChanged(List<KanjiEntry> kanjiEntries) {
                ArrayList<KanjiEntry> selectedKanji;

                if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(0)) ){         //JLPT level 4
                    selectedKanji = (ArrayList<KanjiEntry>) kanjiEntries.stream().filter(kanji -> kanji.JLPTLevel.equals("4")).collect(Collectors.toList());
                } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(1)) ){  //JLPT level 3
                    selectedKanji = (ArrayList<KanjiEntry>) kanjiEntries.stream().filter(kanji -> kanji.JLPTLevel.equals("3")).collect(Collectors.toList());
                } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(2)) ){  //JLPT level 2
                    selectedKanji = (ArrayList<KanjiEntry>) kanjiEntries.stream().filter(kanji -> kanji.JLPTLevel.equals("2")).collect(Collectors.toList());
                } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(3)) ){  //JLPT level 1
                    selectedKanji = (ArrayList<KanjiEntry>) kanjiEntries.stream().filter(kanji -> kanji.JLPTLevel.equals("1")).collect(Collectors.toList());
                } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(4)) ){  //Miscellaneous kanji #1
                    List<KanjiEntry> temp = kanjiEntries.stream().filter(kanji -> kanji.JLPTLevel.isEmpty()).collect(Collectors.toList());
                    selectedKanji = new ArrayList<>(temp.subList(0,100)) ;
                } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(5)) ){  //Miscellaneous kanji #1
                    List<KanjiEntry> temp = kanjiEntries.stream().filter(kanji -> kanji.JLPTLevel.isEmpty()).collect(Collectors.toList());
                    selectedKanji = new ArrayList<>(temp.subList(100,200)) ;
                } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(6)) ){  //Miscellaneous kanji #1
                    List<KanjiEntry> temp = kanjiEntries.stream().filter(kanji -> kanji.JLPTLevel.isEmpty()).collect(Collectors.toList());
                    selectedKanji = new ArrayList<>(temp.subList(200,300)) ;
                } else if(jlptLevel.equals(LessonNavigationFragment.ROW_NAMES.get(7)) ){  //Miscellaneous kanji #1
                    List<KanjiEntry> temp = kanjiEntries.stream().filter(kanji -> kanji.JLPTLevel.isEmpty()).collect(Collectors.toList());
                    selectedKanji = new ArrayList<>(temp.subList(300,temp.size())) ;
                } else { //if nothing matches, use JLPT level 4
                    selectedKanji = (ArrayList<KanjiEntry>) kanjiEntries.stream().filter(kanji -> kanji.JLPTLevel.equals("4")).collect(Collectors.toList());
                }

                //calculate number of rows based
                int numOfKanji= selectedKanji.size();
                int numOfLessons = (int)(numOfKanji / LessonsFragment.NUM_OF_KANJI_PER_LESSON);
                numOfLessons += (numOfKanji % LessonsFragment.NUM_OF_KANJI_PER_LESSON) > 0 ? 1 : 0;
                for(int i = 1; i <=numOfLessons; i++){
                    lessonsRows.add( new RowFeedItem(String.format("Lesson %d", i), i - 1) );
                }
                Log.i("Number of rows: ",String.format("%d",numOfLessons));

                lessonsViewAdapter = new LessonsViewAdapter(view.getContext(),lessonsRows); //To-Do: pass context & rows List
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

                        ArrayList<KanjiEntry> subsetKanji= new ArrayList<>(selectedKanji.subList(lowerBound,upperBound)) ;

                        Bundle selection = new Bundle();
                        selection.putString("row_selection", row.getRowTitle());
                        selection.putParcelableArrayList("kanji", (ArrayList<? extends Parcelable>) subsetKanji);
                        selection.putString("jlpt",jlptLevel);

                        //go to kanji display screen
                        NavHostFragment.findNavController(LessonsFragment.this)
                                .navigate(R.id.action_lessonsFragment_to_kanjiDisplayFragment,selection);

                    }
                });

                lessonsRecycleView.setAdapter(lessonsViewAdapter);
            }
        });

        //setup initial view until database returns result
        lessonsViewAdapter = new LessonsViewAdapter(view.getContext(),lessonsRows); //To-Do: pass context & rows List
        lessonsRecycleView.setAdapter(lessonsViewAdapter);
    }

}