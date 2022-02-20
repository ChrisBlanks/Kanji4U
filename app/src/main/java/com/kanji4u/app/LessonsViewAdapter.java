package com.kanji4u.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class LessonsViewAdapter extends RecyclerView.Adapter<LessonsViewAdapter.CustomViewHolder> {
    private List<RowFeedItem> rowFeedItemList;
    private Context context;

    private OnRowItemClickListener rowItemListener;

    public LessonsViewAdapter(Context context, List<RowFeedItem> rows){
        this.context = context;
        this.rowFeedItemList = rows;
    }

    @Override
    public void onBindViewHolder(LessonsViewAdapter.CustomViewHolder customViewHolder, int i) {
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

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lesson_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView textView;

        public CustomViewHolder(View view){
            super(view);
            this.textView = (TextView) view.findViewById(R.id.rowText);
        }
    }
}
