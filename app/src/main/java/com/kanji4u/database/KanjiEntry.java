package com.kanji4u.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

//declare class as entity that will exist in the database
//Default name of table is the class name
@Entity(tableName = "kanji_entry")
public class KanjiEntry {

    //must define a primary key for an entity; autogenerates IDs for instances
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "kanji_literal")
    public String kanjiLiteral;

    @ColumnInfo(name = "jis_code")
    public String JISCode;

    @ColumnInfo(name = "unicode")
    public String unicode;

    @ColumnInfo(name = "grade")
    public String grade;

    @ColumnInfo(name = "stroke_num")
    public String strokeNum;

    @ColumnInfo(name = "frequency_rank")
    public String frequencyRank;

    @ColumnInfo(name = "jlpt_level")
    public String JLPTLevel;

    @ColumnInfo(name = "on_readings")
    public ArrayList<String> onReadings = new ArrayList<>();

    @ColumnInfo(name = "kun_readings")
    public ArrayList<String> kunReadings = new ArrayList<>();

    @ColumnInfo(name = "english_meanings")
    public ArrayList<String> englishMeanings = new ArrayList<>();


    public KanjiEntry( String kanjiLiteral, String JISCode, String unicode, String grade, String strokeNum, String frequencyRank, String JLPTLevel, ArrayList<String> onReadings, ArrayList<String> kunReadings, ArrayList<String> englishMeanings) {
        this.kanjiLiteral = kanjiLiteral;
        this.JISCode = JISCode;
        this.unicode = unicode;
        this.grade = grade;
        this.strokeNum = strokeNum;
        this.frequencyRank = frequencyRank;
        this.JLPTLevel = JLPTLevel;
        this.onReadings = onReadings;
        this.kunReadings = kunReadings;
        this.englishMeanings = englishMeanings;
    }
}
