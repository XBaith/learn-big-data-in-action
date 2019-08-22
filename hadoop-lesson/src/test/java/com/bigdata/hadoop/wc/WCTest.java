package com.bigdata.hadoop.wc;

import com.bigdata.mapreduce.wc2.WordParaser;
import org.junit.Test;

public class WCTest {
    @Test
    public void wordParaser(){
        WordParaser paraser = new WordParaser();
        System.out.println(paraser.getWord("123为所欲为"));
    }

}
