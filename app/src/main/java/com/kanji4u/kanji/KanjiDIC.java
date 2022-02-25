package com.kanji4u.kanji;

import com.kanji4u.database.KanjiEntry;

import java.util.ArrayList;


public class KanjiDIC  implements Kanji{

    private KanjiInfo kanjiInfo;

    private ArrayList<String> onReadings = new ArrayList<>();
    private ArrayList<String> kunReadings = new ArrayList<>();
    private ArrayList<String> englishMeanings = new ArrayList<>();


    public KanjiDIC(KanjiInfo kInfo, ArrayList<String> onReadings, ArrayList<String> kunReadings,ArrayList<String> englishMeanings) {
        this.kanjiInfo = kInfo;
        this.onReadings.addAll(onReadings);
        this.kunReadings.addAll(kunReadings);
        this.englishMeanings.addAll(englishMeanings);
    }

    public String getKanjiLiteral() {
        return this.kanjiInfo.getKanjiLiteral();
    }

    public String getKanjiJISCode() {
        return this.kanjiInfo.getJISCode();
    }

    public String getUnicodeValue() {
        return this.kanjiInfo.getUnicode();
    }

    public String getKanjiGradeLevel() {
        return this.kanjiInfo.getGrade();
    }

    public String getKanjiStrokeCount(){
        return this.kanjiInfo.getStrokeNum();
    }

    public String getKanjiFrequency() {
        return this.kanjiInfo.getFrequencyRank();
    }

    public String getKanjiJLPTLevel() {
        return this.kanjiInfo.getJLPTLevel();
    }

    public ArrayList<String> getKanjiEnglishMeanings() {
        return this.englishMeanings;
    }

    public ArrayList<String> getKanjiOnReadings(){
        return this.onReadings;
    }

    public ArrayList<String> getKanjiKunReadings(){
        return this.kunReadings;
    }

    @Override
    public String toString() {
        return "KanjiDIC{" +
                "kanjiInfo=" + kanjiInfo +
                ", onReadings=" + onReadings +
                ", kunReadings=" + kunReadings +
                ", englishMeanings=" + englishMeanings +
                '}';
    }

    /**
     * Creates KanjiEntry object that can be stored in the app database
     * @return KanjiEntry object
     */
    public KanjiEntry createKanjiEntry(){
        String kanjiLiteral = kanjiInfo.getKanjiLiteral();
        String JISCode = kanjiInfo.getJISCode();
        String unicode = kanjiInfo.getUnicode();
        String grade = kanjiInfo.getGrade();
        String strokeNum = kanjiInfo.getStrokeNum();
        String frequencyRank = kanjiInfo.getFrequencyRank();
        String JLPTLevel = kanjiInfo.getJLPTLevel();
        return new KanjiEntry(kanjiLiteral, JISCode, unicode, grade, strokeNum, frequencyRank, JLPTLevel,
                this.onReadings, this.kunReadings, this.englishMeanings);
    }
}
