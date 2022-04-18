package com.kanji4u.database;

import java.util.ArrayList;

public interface DBKanji {

    public boolean isMemorizedKanji();
    public void setMemorizedKanji(boolean memorizedKanji);

    public String getKanjiLiteral();
    public void setKanjiLiteral(String kanjiLiteral);

    public String getJISCode();
    public void setJISCode(String JISCode);

    public String getUnicode();
    public void setUnicode(String unicode);

    public String getGrade();
    public void setGrade(String grade);

    public String getStrokeNum();
    public void setStrokeNum(String strokeNum);

    public String getFrequencyRank();
    public void setFrequencyRank(String frequencyRank);

    public String getJLPTLevel();
    public void setJLPTLevel(String JLPTLevel);

    public ArrayList<String> getOnReadings();
    public void setOnReadings(ArrayList<String> onReadings);

    public ArrayList<String> getKunReadings();
    public void setKunReadings(ArrayList<String> kunReadings);

    public ArrayList<String> getEnglishMeanings();
    public void setEnglishMeanings(ArrayList<String> englishMeanings);
}
