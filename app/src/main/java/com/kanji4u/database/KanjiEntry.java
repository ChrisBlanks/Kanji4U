package com.kanji4u.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

//declare class as entity that will exist in the database
//Default name of table is the class name
@Entity(tableName = "kanji_entry")
public class KanjiEntry implements Parcelable {

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

    @Override
    public String toString() {
        return "KanjiEntry{" +
                "uid=" + uid +
                ", kanjiLiteral='" + kanjiLiteral + '\'' +
                ", JISCode='" + JISCode + '\'' +
                ", unicode='" + unicode + '\'' +
                ", grade='" + grade + '\'' +
                ", strokeNum='" + strokeNum + '\'' +
                ", frequencyRank='" + frequencyRank + '\'' +
                ", JLPTLevel='" + JLPTLevel + '\'' +
                ", onReadings=" + onReadings +
                ", kunReadings=" + kunReadings +
                ", englishMeanings=" + englishMeanings +
                '}';
    }

    public KanjiEntry(String kanjiLiteral, String JISCode, String unicode, String grade, String strokeNum, String frequencyRank, String JLPTLevel, ArrayList<String> onReadings, ArrayList<String> kunReadings, ArrayList<String> englishMeanings) {
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

    //Parcelable implementation
    public KanjiEntry(Parcel in){
        //get uid first
        this.uid = in.readInt();

        //get kanji string data
        String[] stringData = new String[7];
        in.readStringArray(stringData);

        this.kanjiLiteral = stringData[0];
        this.JISCode = stringData[1];
        this.unicode = stringData[2];
        this.grade = stringData[3];
        this.strokeNum = stringData[4];
        this.frequencyRank = stringData[5];
        this.JLPTLevel = stringData[6];

        //get string array lists
        this.onReadings = in.readArrayList(new ArrayList<String>().getClass().getClassLoader());
        this.kunReadings = in.readArrayList(new ArrayList<String>().getClass().getClassLoader());
        this.englishMeanings = in.readArrayList(new ArrayList<String>().getClass().getClassLoader());
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeStringArray(new String[] {
                this.kanjiLiteral,
                this.JISCode,
                this.unicode,
                this.grade,
                this.strokeNum,
                this.frequencyRank,
                this.JLPTLevel
        });
        dest.writeList(this.onReadings);
        dest.writeList(this.kunReadings);
        dest.writeList(this.englishMeanings);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public KanjiEntry createFromParcel(Parcel in) {
            return new KanjiEntry(in);
        }

        public KanjiEntry[] newArray(int size) {
            return new KanjiEntry[size];
        }
    };
}
