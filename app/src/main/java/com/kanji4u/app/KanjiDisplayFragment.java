package com.kanji4u.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanji4u.app.databinding.FragmentKanjiDisplayBinding;
import com.kanji4u.database.KanjiEntry;

import java.util.ArrayList;
import java.util.List;


public class KanjiDisplayFragment extends Fragment {

    private static String DEFAULT_ROW_SELECT = "Lesson 1";
    private static String ROW_SELECT_BUNDLE_KEY ="row_selection";

    private FragmentKanjiDisplayBinding binding;

    private ArrayList kanjiList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Bundle bundle =  getArguments();
        String rowSelection = bundle.getString(KanjiDisplayFragment.ROW_SELECT_BUNDLE_KEY, KanjiDisplayFragment.DEFAULT_ROW_SELECT );
        Log.i("kanjiDisplayFragment", "Received this row selection: " + rowSelection);

        ArrayList<KanjiEntry> kanjiList = bundle.getParcelableArrayList("kanji");
        KanjiEntry kanji =  kanjiList.get(0);
        Log.i("kanjiDisplayFragment", String.format("Number of kanji: %d",kanjiList.size()) );
        Log.i("kanjiDisplayFragment", String.format("First Kanji: %s",kanji.toString()));

        binding = FragmentKanjiDisplayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}