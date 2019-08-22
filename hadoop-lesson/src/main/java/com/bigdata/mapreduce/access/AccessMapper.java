package com.bigdata.mapreduce.access;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AccessMapper extends Mapper<LongWritable, Text, Text, Access> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] lines = value.toString().split("\t");

        String phoneNum = lines[1];
        long up = Long.parseLong(lines[lines.length - 3]);
        long down = Long.parseLong(lines[lines.length - 2]);
        context.write(new Text(phoneNum), new Access(phoneNum, up, down));
    }
}
