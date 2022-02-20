package com.kanji4u.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kanji4u.app.databinding.FragmentLessonNavigationBinding;

import java.util.ArrayList;
import java.util.List;

public class LessonNavigationFragment extends Fragment {

    private FragmentLessonNavigationBinding binding;

    private List<RowFeedItem> lessonNavigationRows;
    private RecyclerView lessonNavigationRecycleView;
    private LessonNavigationViewAdapter lessonNavigationViewAdapter;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLessonNavigationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setup recycleview
        lessonNavigationRecycleView = (RecyclerView) view.findViewById(R.id.lesson_navigation_recycle);
        lessonNavigationRecycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        lessonNavigationRows = new ArrayList<>();

        //create rows
        lessonNavigationRows.add( new RowFeedItem("JLPT N5"));
        lessonNavigationRows.add( new RowFeedItem("JLPT N4"));
        lessonNavigationRows.add( new RowFeedItem("JLPT N3"));
        lessonNavigationRows.add( new RowFeedItem("JLPT N2"));
        lessonNavigationRows.add( new RowFeedItem("JLPT N1"));
        lessonNavigationRows.add( new RowFeedItem("Miscellaneous"));

        lessonNavigationViewAdapter = new LessonNavigationViewAdapter(view.getContext(), lessonNavigationRows);
        lessonNavigationViewAdapter.setRowItemListener(new OnRowItemClickListener() {
            @Override
            public void onRowItemClick(RowFeedItem row) {
                Log.i("Lesson Nav Selection", "Selected: " + row.getRowTitle());

                Bundle selection = new Bundle();
                selection.putString("row_selection", row.getRowTitle());

                NavHostFragment.findNavController(LessonNavigationFragment.this)
                        .navigate(R.id.action_LessonNavigationFragment_to_lessonsFragment,selection);
            }
        });
        lessonNavigationRecycleView.setAdapter(lessonNavigationViewAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

    }

}