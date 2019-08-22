package com.bigdata.hdfs.wc;

public class WordMapperImpl implements WordMapper{

    @Override
    public void map(String line, ContextCache context) {
        String[] words = line.split(" ");
        for(String word : words){
            Object w = context.get(word);
            if (w == null) {
                context.write(word, 1);
            } else {
                int num = Integer.parseInt(w.toString());
                context.write(word,++num);
            }

        }
    }
}
