package com.kanji4u.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanji4u.app.databinding.FragmentLessonsBinding;
import com.kanji4u.database.Kanji4UDatabase;

import java.util.ArrayList;
import java.util.List;


public class LessonsFragment extends Fragment {

    private static String DEFAULT_ROW_SELECT = "JLPT N5";
    private static String ROW_SELECT_BUNDLE_KEY ="row_selection";

    private List<RowFeedItem> lessonsRows;
    private RecyclerView lessonsRecycleView;
    private LessonsViewAdapter lessonsViewAdapter;

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

        lessonsRecycleView = view.findViewById(R.id.lessons_recycle);
        lessonsRecycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        lessonsRows = new ArrayList<>();

        //To-Do: Figure out how many rows and which rows to create
        lessonsRows.add( new RowFeedItem("Lesson #1"));
        lessonsRows.add( new RowFeedItem("Lesson #2"));
        lessonsRows.add( new RowFeedItem("Lesson #3"));

        lessonsViewAdapter = new LessonsViewAdapter(view.getContext(),lessonsRows); //To-Do: pass context & rows List
        lessonsViewAdapter.setRowItemListener(new OnRowItemClickListener() {
            @Override
            public void onRowItemClick(RowFeedItem row) {
                Log.i("Lesson Nav Selection", "Selected: " + row.getRowTitle());

                Bundle selection = new Bundle();
                selection.putString("row_selection", row.getRowTitle());

                //go to kanji display screen
                NavHostFragment.findNavController(LessonsFragment.this)
                        .navigate(R.id.action_lessonsFragment_to_kanjiDisplayFragment,selection);

            }
        });

        lessonsRecycleView.setAdapter(lessonsViewAdapter);
    }

}