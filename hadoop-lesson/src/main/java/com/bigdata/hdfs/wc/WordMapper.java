package com.bigdata.hdfs.wc;

public interface WordMapper {
    public void map(String line, ContextCache context);
}
