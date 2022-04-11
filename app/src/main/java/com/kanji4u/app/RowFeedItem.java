package com.kanji4u.app;

public class RowFeedItem {
    private String rowTitle;

    private int index;

    public RowFeedItem(String rowTitle){
        this.rowTitle = rowTitle;
    }

    public RowFeedItem(String rowTitle, int index){
        this.rowTitle = rowTitle;
        this.index = index;
    }

    public String getRowTitle() {
        return rowTitle;
    }

    public void setRowTitle(String rowTitle) {
        this.rowTitle = rowTitle;
    }

    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }
}
