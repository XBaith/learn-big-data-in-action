package com.bigdata.mapreduce.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    //将数据按自定义规则分割开
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] words = value.toString().split(" ");
        for(String word : words){
            if(word.contains(",") || word.contains(".")){
                word = word.substring(0, word.length() - 2);
            }
            context.write(new Text(word.toLowerCase()), new IntWritable(1));
        }
    }
}
