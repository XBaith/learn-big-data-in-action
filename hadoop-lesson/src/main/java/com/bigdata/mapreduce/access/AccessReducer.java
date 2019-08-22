package com.bigdata.mapreduce.access;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class AccessReducer extends Reducer<Text, Access, Text, Access> {
    @Override
    protected void reduce(Text key, Iterable<Access> values, Context context) throws IOException, InterruptedException {
        Iterator<Access> iterator = values.iterator();
        long upSum = 0;
        long downSum = 0;
        while(iterator.hasNext()){
            Access access = iterator.next();
            upSum += access.getUp();
            downSum += access.getDown();
        }

        context.write(key, new Access(key.toString(), upSum, downSum));
    }
}
