package com.kanji4u.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LessonNavigationViewAdapter extends RecyclerView.Adapter<LessonNavigationViewAdapter.CustomViewHolder> {
    private List<RowFeedItem> rowFeedItemList;
    private Context context;

    private OnRowItemClickListener rowItemListener;

    public LessonNavigationViewAdapter(Context context, List<RowFeedItem> rows){
        this.rowFeedItemList = rows;
        this.context = context;
    }

    @Override
    public int getItemCount(){
        return (null != rowFeedItemList ? rowFeedItemList.size() : 0);
    }

    public OnRowItemClickListener getRowItemListener() {
        return this.rowItemListener;
    }

    public void setRowItemListener(OnRowItemClickListener rowListener){
        this.rowItemListener = rowListener;
    }

    /**
     * Inflates the view
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lesson_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    /**
     * Binds data to the view
     * @param customViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i){
        RowFeedItem row = this.rowFeedItemList.get(i);

        String lessonName = row.getRowTitle();
        customViewHolder.textView.setText(lessonName);

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                rowItemListener.onRowItemClick(row);
            }
        };

        customViewHolder.textView.setOnClickListener(listener);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView textView;

        public CustomViewHolder(View view){
            super(view);
            this.textView = (TextView) view.findViewById(R.id.rowText);
        }
    }

}
