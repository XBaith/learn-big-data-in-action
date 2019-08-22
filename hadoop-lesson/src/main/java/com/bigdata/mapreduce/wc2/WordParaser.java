package com.bigdata.mapreduce.wc2;

import java.util.ArrayList;

public class WordParaser {

    public String getWord(String raw){
        char[] cs = raw.toCharArray();
        String word = "";
        for(int i = 0; i < cs.length; i++){
            if ((int)cs[i] > 125){
                word = raw.substring(i);
                break;
            }
        }
        return word;
    }
}
