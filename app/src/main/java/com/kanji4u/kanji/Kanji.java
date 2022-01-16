package com.kanji4u.kanji;

import java.util.ArrayList;

public interface Kanji {
    String getKanjiLiteral(); // To-Do: Figure out how to handle the kanji literal value
    String getKanjiJISCode(); // To-Do: Possibly create an enum or POJO class  of these codes if it makes sense
    String getUnicodeValue();
    String getKanjiGradeLevel();
    String getKanjiStrokeCount();
    String getKanjiFrequency();
    String getKanjiJLPTLevel(); //To-Do: Possibly translate into 5-level system
    ArrayList<String> getKanjiOnReadings(); // Note: It should return an array of reading
    ArrayList<String> getKanjiKunReadings(); // Note: It should return an array of readings
    ArrayList<String> getKanjiEnglishMeanings(); // Note: It should return an array of meanings
}
