package com.kanji4u.app;

import static android.app.Activity.RESULT_OK;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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

    private List<RowFeedItem> lessonRows;
    private RecyclerView lessonRecycleView;
    private LessonNavigationViewAdapter lessonAdapter;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLessonNavigationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setup recycleview
        lessonRecycleView = (RecyclerView) view.findViewById(R.id.lesson_recycle);
        lessonRecycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        lessonRows = new ArrayList<>();

        //create rows
        lessonRows.add( new RowFeedItem("JLPT N5"));
        lessonRows.add( new RowFeedItem("JLPT N4"));
        lessonRows.add( new RowFeedItem("JLPT N3"));
        lessonRows.add( new RowFeedItem("JLPT N2"));
        lessonRows.add( new RowFeedItem("JLPT N1"));
        lessonRows.add( new RowFeedItem("Miscellaneous"));

        lessonAdapter = new LessonNavigationViewAdapter(view.getContext(),lessonRows);
        lessonAdapter.setRowItemListener(new OnRowItemClickListener() {
            @Override
            public void onRowItemClick(RowFeedItem row) {
                Log.i("Lesson Nav Selection", "Selected: " + row.getRowTitle());

                Bundle selection = new Bundle();
                selection.putString("row_selection", row.getRowTitle());

                NavHostFragment.findNavController(LessonNavigationFragment.this)
                        .navigate(R.id.action_LessonNavigationFragment_to_lessonsFragment,selection);
            }
        });
        lessonRecycleView.setAdapter(lessonAdapter);

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