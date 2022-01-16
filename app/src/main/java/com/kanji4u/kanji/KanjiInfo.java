package com.kanji4u.kanji;

public class KanjiInfo {
    private String kanjiLiteral;
    private String JISCode;
    private String unicode;
    private String grade;
    private String strokeNum;
    private String frequencyRank;
    private String JLPTLevel;

    public KanjiInfo(String kanjiLiteral, String JISCode, String unicode, String grade, String strokeNum, String frequencyRank, String JLPTLevel) {
        this.kanjiLiteral = kanjiLiteral;
        this.JISCode = JISCode;
        this.unicode = unicode;
        this.grade = grade;
        this.strokeNum = strokeNum;
        this.frequencyRank = frequencyRank;
        this.JLPTLevel = JLPTLevel;
    }

    public String getKanjiLiteral() {
        return kanjiLiteral;
    }

    public String getJISCode() {
        return JISCode;
    }

    public String getUnicode() {
        return unicode;
    }

    public String getGrade() {
        return grade;
    }

    public String getStrokeNum() {
        return strokeNum;
    }

    public String getFrequencyRank() {
        return frequencyRank;
    }

    public String getJLPTLevel() {
        return JLPTLevel;
    }

    @Override
    public String toString() {
        return "KanjiInfo{" +
                "kanjiLiteral='" + kanjiLiteral + '\'' +
                ", JISCode='" + JISCode + '\'' +
                ", unicode='" + unicode + '\'' +
                ", grade='" + grade + '\'' +
                ", strokeNum='" + strokeNum + '\'' +
                ", frequencyRank='" + frequencyRank + '\'' +
                ", JLPTLevel='" + JLPTLevel + '\'' +
                '}';
    }
}
