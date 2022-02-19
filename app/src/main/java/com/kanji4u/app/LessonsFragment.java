package com.kanji4u.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanji4u.app.databinding.FragmentLessonsBinding;


public class LessonsFragment extends Fragment {

    private static String DEFAULT_ROW_SELECT = "JLPT N5";
    private static String ROW_SELECT_BUNDLE_KEY ="row_selection";

    private String rowSelection;

    private FragmentLessonsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Bundle bundle =  getArguments();
        String rowSelection = bundle.getString(LessonsFragment.ROW_SELECT_BUNDLE_KEY, LessonsFragment.DEFAULT_ROW_SELECT);
        Log.i("LessonsFragment", "Received this row selection: " + rowSelection);

        binding = FragmentLessonsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}