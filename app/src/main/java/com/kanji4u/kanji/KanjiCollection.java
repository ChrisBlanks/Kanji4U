package com.kanji4u.kanji;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KanjiCollection {

    ArrayList<KanjiDIC> loadedKanji;

    public KanjiCollection(){
        this.loadedKanji = new ArrayList<>();
    }

    //public functions

    /**
     * Loads a kanji dictionary of type 'dictType' from input stream and stores it in memory.
     * @param dictType Type of dictionary to be loaded which requires specific parsing
     * @param iStream Input stream that contains the file content
     */
    public void loadKanjiDictionary(KanjiDictionary dictType, InputStream iStream){
        try {
            byte[] buffer = new byte[iStream.available()];
            iStream.read(buffer);
            ByteArrayOutputStream oStream = new ByteArrayOutputStream();
            oStream.write(buffer);

            String fileContent = oStream.toString("EUC_JP"); //read from output stream w/ correct file encoding

            //select the proper parse method
            switch(dictType){
                case KANJIDIC:
                    parseKanjiDICDictionary(fileContent);
            }

        } catch(IOException e){
            Log.e("IO Error",e.toString());
        }
    }


    //private functions

    private boolean findJapaneseCharacters(String target){
        boolean result = false;
        Pattern pat = Pattern.compile("[\\p{InHiragana}\\p{InKatakana}\\p{InCJK_Symbols_and_Punctuation}\\p{InCJK_Unified_Ideographs}]+");
        Matcher matcher = pat.matcher(target);
        result = matcher.find();
        return result;
    }

    private boolean findHiraganaCharacters(String target){
        boolean result = false;

        if(findJapaneseCharacters(target)){

            Pattern pat = Pattern.compile("\\p{InHiragana}+");
            Matcher matcher = pat.matcher(target);
            result = matcher.find();
        }

        return result;
    }

    private boolean findKatakanaCharacters(String target){
        boolean result = false;

        if(findJapaneseCharacters(target)){
            Pattern pat = Pattern.compile("[\\p{InKatakana}]+");
            Matcher matcher = pat.matcher(target);
            result = matcher.find();
        }

        return result;
    }


    /**
     * Parses a string that contains the 'kanjidic' file, which is composed of multiple lines of
     * space delimited kanji data.
     * @param fileContent 'kanjidic' text contained in a string
     */
    private void parseKanjiDICDictionary(String fileContent){
        String[] lines = fileContent.split("\\r?\\n");
        for(String line : lines){
            if(line.startsWith("#")){
                continue; //skip comment lines
            }

            /*
                Each line of the kanjidic dictionary contains data regarding a single kanji. The data
                is formatted and coded.
            */

            String[] kanjiContent = line.split(" ");
            String kanjiLiteral = kanjiContent[0];
            String JISCode = kanjiContent[1];
            String unicode = kanjiContent[2];
            String grade = "";
            String strokes = "";
            String frequencyRank = "";
            String JLPTLevel = "";
            ArrayList<String> englishMeanings = new ArrayList<>();
            ArrayList<String> onReadings = new ArrayList<>();
            ArrayList<String> kunReadings = new ArrayList<>();

            boolean foundAcceptedStrokeCount = false;
            boolean englishMeaningStarted = false;
            String englishMeaningBuffer = "";

            //loop through other line data that doesn't have strict format & limit
            for(int indx=3; indx < kanjiContent.length; indx++){
                String indexContent = kanjiContent[indx];

                if(englishMeaningStarted){ //if code is still processing an english meaning, collect words
                    if(indexContent.endsWith("}")){ //if end of english meaning, end collection
                        englishMeaningBuffer = englishMeaningBuffer + " " + indexContent.replace("}","");
                        englishMeaningStarted = false; //stop collection
                        englishMeanings.add(englishMeaningBuffer);
                    } else{
                        englishMeaningBuffer = englishMeaningBuffer + " " + indexContent; //append to buffer
                    }
                    continue;
                }

                if(indexContent.startsWith("B") || indexContent.startsWith("C") ) {
                    continue; //ignore radical information
                } else if(indexContent.startsWith("X")){
                    continue; //ignore code points of similar or related kanji
                } else if(indexContent.startsWith("N") || indexContent.startsWith("V")){
                    continue; //ignore nelson numbers
                } else if(indexContent.startsWith("H")){
                    continue; //ignore NJECD number
                } else if(indexContent.startsWith("D") || indexContent.startsWith("L") || indexContent.startsWith("K")
                        || indexContent.startsWith("O") || indexContent.startsWith("M") || indexContent.startsWith("E")
                        || indexContent.startsWith("IN") ) {
                    continue; //ignore kanji dictionary index number
                } else if(indexContent.startsWith("P") || indexContent.startsWith("I") || indexContent.startsWith("Q")
                        || indexContent.startsWith("Z")   ){
                        continue; //ignore skip codes & kanji dictionary index numbers
                } else if(indexContent.startsWith("Y")){
                    continue; //ignore chinese readings
                } else if(indexContent.startsWith("W")){
                    continue; //ignore korean readings
                } else if (indexContent.startsWith("G")) {
                    grade = indexContent; //grade of kanji; G1 - G6 = elementary school, G8 = secondary school
                } else if(indexContent.startsWith("S") && !foundAcceptedStrokeCount){
                    strokes = indexContent.replace("S",""); //number of strokes for character
                    foundAcceptedStrokeCount = true; //ignore miscounts if listed in line
                } else if(indexContent.startsWith("F")){
                    frequencyRank = indexContent.replace("F",""); //frequency of use ranking
                } else if(indexContent.startsWith("J")){
                    JLPTLevel = indexContent.replace("J",""); //pre-2010 Jap. language proficiency test level
                }else if(indexContent.startsWith("{")){
                    if(indexContent.endsWith("}")){ //if english meaning is a single word
                        englishMeanings.add(indexContent.replace("{","").replace("}",""));
                        continue;
                    }
                    englishMeaningStarted = true; //start collecting english meaning within braces
                    englishMeaningBuffer = indexContent.replace("{","");
                } else if(findKatakanaCharacters(indexContent)){
                    onReadings.add(indexContent);
                } else if(findHiraganaCharacters(indexContent)){
                    kunReadings.add(indexContent);
                }

            }

            KanjiInfo kInfo = new KanjiInfo(kanjiLiteral, JISCode,unicode, grade,
                                            strokes, frequencyRank,JLPTLevel);
            KanjiDIC kanji = new KanjiDIC( kInfo, onReadings, kunReadings, englishMeanings);
            //Log.i("Kanji", kanji.toString());
            this.loadedKanji.add(kanji);
        }
    }

}
